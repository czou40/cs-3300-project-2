package com.group1.billsplitter.services;

import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.auth.UserRecord;
import com.group1.billsplitter.entities.Item;
import com.group1.billsplitter.entities.Result;
import com.group1.billsplitter.entities.Event;
import com.group1.billsplitter.entities.User;
import com.group1.billsplitter.entities.exceptions.UnauthorizedException;
import com.group1.billsplitter.entities.requests.AddItemRequest;
import com.group1.billsplitter.entities.requests.Message;
import com.group1.billsplitter.repositories.FirebaseAuthRepository;
import com.group1.billsplitter.repositories.FirestoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ExecutionException;

@Service
@EnableScheduling
public class FirebaseService {
    public final String DEFAULT = "default";
    public final String PAYPAL_EMAILS = "paypalEmails";
    public final String EVENTS = "events";
    private FirestoreRepository firestoreRepository;
    private FirebaseAuthRepository firebaseAuthRepository;
    private HashMap<String, Event> events = new HashMap<>();
    private HashSet<String> dirtyEvents = new HashSet<>();


    @Autowired
    private void setFirestoreRepository(FirestoreRepository firestoreRepository) {
        this.firestoreRepository = firestoreRepository;
    }

    @Autowired
    private void setFirebaseAuthRepository(FirebaseAuthRepository firebaseAuthRepository) {
        this.firebaseAuthRepository = firebaseAuthRepository;
    }

    public Result CreateUser(String email, String password, String name) {
        return firebaseAuthRepository.createUser(email, password, name);
    }

    public String testFirestore() throws ExecutionException, InterruptedException {
        DocumentSnapshot documentSnapshot = firestoreRepository.getDocument(DEFAULT, "0");
        return (String) documentSnapshot.getData().get("message");
    }

    public Result<String> getPaypalEmail(String uid) {
        try {
            DocumentSnapshot documentSnapshot = firestoreRepository.getDocument(PAYPAL_EMAILS, uid);
            return new Result<>(true, "Success.", documentSnapshot.getString("email"));
        } catch (Exception e) {
            e.printStackTrace();
            return new Result<>(false, e.getMessage());
        }
    }

    public Result<User> getUserByEmail(String email) {
        try {
            UserRecord userRecord = firebaseAuthRepository.getUserByEmail(email);
            Result<String> result = getPaypalEmail(userRecord.getUid());
            String paypalEmail = result.isSuccessful() ? result.getPayload():"";
            return new Result<>(true, "Success.", new User(userRecord.getEmail(), userRecord.getDisplayName(), paypalEmail));
        } catch (FirebaseAuthException e) {
            e.printStackTrace();
            return new Result<>(false, e.getMessage());
        }
    }

    public Result<User> getUserByUid(String uid) {
        try {
            UserRecord userRecord = firebaseAuthRepository.getUserByUid(uid);
            Result<String> result = getPaypalEmail(userRecord.getUid());
            String paypalEmail = result.isSuccessful() ? result.getPayload():"";
            return new Result<>(true, "Success.", new User(userRecord.getEmail(), userRecord.getDisplayName(), paypalEmail));
        } catch (FirebaseAuthException e) {
            e.printStackTrace();
            return new Result<>(false, e.getMessage());
        }
    }

    public Result<?> setPaypalEmail(String uid, String paypalEmail) {
        Map<String, Object> map = new HashMap<>();
        map.put("email", paypalEmail);
        try {
            firestoreRepository.setDocument(PAYPAL_EMAILS, uid, map);
            return new Result<>(true, "Success.");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result<>(false, e.getMessage());
        }
    }

    public Result<Event> createEvent(String eventName, User payer) {
        try {
            Event event = new Event(eventName, payer);
            firestoreRepository.setDocument(EVENTS, event.getId(), event);
            events.put(event.getId(), event);
            return new Result<>(true, "Success.", event);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result<>(false, e.getMessage());
        }
    }

    public Result<Event> getEvent(String id) {
        if (events.containsKey(id)) {
            return new Result<>(true, "Success.", events.get(id));
        }
        try {
            DocumentSnapshot documentSnapshot = firestoreRepository.getDocument(EVENTS, id);
            Event result = documentSnapshot.toObject(Event.class);
            events.put(result.getId(), result);
            return new Result<>(true, "Success.", result);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new Result<>(false, e.getMessage());
        }
    }

    public Result<Message> setEvent(String id, Event event) {
        if (events.containsKey(id)) {
            events.put(id, event);
        } else {
            Result<Event> result = getEvent(id); // will add event to memory
            if (!result.isSuccessful()) {
                return new Result<>(false, result.getMessage());
            }
        }
        dirtyEvents.add(id);
        return new Result<>(true, "Success.");
    }

    public Result<Message> addItem(String eventId, Item item) {
        Result<Event> event = getEvent(eventId);
        if (!event.isSuccessful()) {
            return new Result<>(false, event.getMessage());
        }
        event.getPayload().addItem(item);
        events.put(eventId, event.getPayload());
        dirtyEvents.add(eventId);
        return new Result<>(true, "Success.");
    }

    public Result<Message> deleteItem(String eventId, String itemId) {
        Result<Event> event = getEvent(eventId);
        if (!event.isSuccessful()) {
            return new Result<>(false, event.getMessage());
        }
        event.getPayload().removeItem(itemId);
        events.put(eventId, event.getPayload());
        dirtyEvents.add(eventId);
        return new Result<>(true, "Success.");
    }

    public Result<Message> addAttendee(String eventId, String email) {
        Result<Event> event = getEvent(eventId);
        Result<User> user = getUserByEmail(email);
        if (!event.isSuccessful()) {
            return new Result<>(false, event.getMessage());
        }
        if (!user.isSuccessful()) {
            return new Result<>(false, user.getMessage());
        }

        event.getPayload().addAttendee(user.getPayload());
        events.put(eventId, event.getPayload());
        dirtyEvents.add(eventId);
        return new Result<>(true, "Success.");
    }

    public Result<Message> deleteAttendee(String eventId, String email) {
        Result<Event> event = getEvent(eventId);
        Result<User> user = getUserByEmail(email);
        if (!event.isSuccessful()) {
            return new Result<>(false, event.getMessage());
        }
        if (!user.isSuccessful()) {
            return new Result<>(false, user.getMessage());
        }
        event.getPayload().removeAttendee(user.getPayload());
        events.put(eventId, event.getPayload());
        dirtyEvents.add(eventId);
        return new Result<>(true, "Success.");
    }


    @Scheduled(fixedRate = 20000)
    public void syncEvent() {
        System.out.println("Syncing Events.");
        try {
            dirtyEvents.removeIf(i->{
                if (events.containsKey(i)) {
                    firestoreRepository.updateDocument(EVENTS, i, events.get(i));
                    System.out.println("Synced " + i);
                } else {
                    System.out.println("Error, unknown event!");
                }
                return true;
            });
        } catch (Exception e) {
            System.out.println("Error syncing events!");
            System.out.println(e.getMessage());
        }
    }

    public Result<List<Event>> getEventsForUser(User user) {
        try {
            List<QueryDocumentSnapshot> res = firestoreRepository.searchWhereArrayContains(EVENTS, "attendees", user);
            List<Event> ans = new ArrayList<>();
            if (res == null) {
                return new Result<>(true, "No events found.", ans);
            }
            for (QueryDocumentSnapshot i: res) {
                ans.add(i.toObject(Event.class));
            }
            return new Result<>(true, "Events found!", ans);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            return new Result<>(false, e.getMessage());
        }
    }

    public FirebaseToken verifyIdToken(String token) {
        token = token.trim();
        if (token.toLowerCase(Locale.US).startsWith("bearer ")) {
            token = token.substring(7).trim();
        }
        Result<FirebaseToken> result = firebaseAuthRepository.verifyIdToken(token);
        if (!result.isSuccessful()) {
            throw new UnauthorizedException(result.getMessage());
        }
        return result.getPayload();
    }
}
