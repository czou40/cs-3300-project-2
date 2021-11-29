package com.group1.billsplitter.controllers;

import com.google.firebase.auth.FirebaseToken;
import com.group1.billsplitter.entities.Item;
import com.group1.billsplitter.entities.Result;
import com.group1.billsplitter.entities.Event;
import com.group1.billsplitter.entities.User;
import com.group1.billsplitter.entities.exceptions.NotFoundException;
import com.group1.billsplitter.entities.requests.*;
import com.group1.billsplitter.services.FirebaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin
public class UserController {
    private FirebaseService firebaseService;

    @Autowired
    public void setFirebaseService(FirebaseService firebaseService) {
        this.firebaseService = firebaseService;
    }

    private ResponseEntity<Message> respondWithMessage(Result<?> result) {
        return respondWithMessage(result, HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<Message> respondWithMessage(Result<?> result, HttpStatus badStatus) {
        return new ResponseEntity<>(new Message(result.getMessage()), result.isSuccessful() ? HttpStatus.OK : badStatus);
    }
//
    private ResponseEntity<Object> respondWithEntity(Result<?> result) {
        return respondWithEntity(result, HttpStatus.NOT_FOUND);
    }

    private ResponseEntity<Object> respondWithEntity(Result<?> result, HttpStatus badStatus) {
        if (result.isSuccessful()) {
            return new ResponseEntity<>(result.getPayload(), HttpStatus.OK);
        }
        return new ResponseEntity<>(new Message(result.getMessage()), badStatus);
    }

    @PostMapping("/signup")
    private ResponseEntity<Message> signup(@Valid @RequestBody CreateUserRequest body) {
        String email = "", password = "", name = "";
        try {
            email = body.getEmail().trim();
            password = body.getPassword();
            name = body.getName().trim();
        } catch (Exception e) {
            return new ResponseEntity<>(new Message("Please specify your full name, email address, and password!"), HttpStatus.BAD_REQUEST);
        }
        if (email.equals("") || password.equals("") || name.equals("")) {
            return new ResponseEntity<>(new Message("Please specify your full name, email address, and password!"), HttpStatus.BAD_REQUEST);
        }
        if (!email.endsWith("@gatech.edu")) {
            System.out.println(email);
            return new ResponseEntity<>(new Message("You can only register with a Georgia Tech email!"), HttpStatus.BAD_REQUEST);
        } else {
            Result result = firebaseService.CreateUser(email, password, name);
            return new ResponseEntity<>(new Message(result.getMessage()), result.isSuccessful() ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/me/paypalEmail")
    private ResponseEntity<Message> setPaypalEmail(@RequestHeader(value = "Authorization") String token, @Valid @RequestBody SetPaypalEmailRequest body) {
        FirebaseToken verified = firebaseService.verifyIdToken(token);
        String paypalEmail = body.getPaypalEmail();
        String uid = verified.getUid();
        Result<?> result = firebaseService.setPaypalEmail(uid, paypalEmail);
        return respondWithMessage(result);
    }

    @GetMapping("/me")
    private ResponseEntity<Object> getMyInfo(@RequestHeader(value = "Authorization") String token) {
        FirebaseToken verified = firebaseService.verifyIdToken(token);
        Result<User> result = firebaseService.getUserByUid(verified.getUid());
        return respondWithEntity(result);
    }


    @GetMapping("/users/{email}")
    private ResponseEntity<Object> getUserInfo(@RequestHeader(value = "Authorization") String token, @PathVariable String email) {
        firebaseService.verifyIdToken(token);
        Result<User> result = firebaseService.getUserByEmail(email.trim());
        return respondWithEntity(result);
    }

    @GetMapping("/users/{email}/events")
    private ResponseEntity<Object> getEventsForUser(@RequestHeader(value = "Authorization") String token, @PathVariable String email) {
        firebaseService.verifyIdToken(token);
        Result<User> result = firebaseService.getUserByEmail(email.trim());
        if (!result.isSuccessful()) {
            throw new NotFoundException(result.getMessage());
        }
        Result<List<Event>> events = firebaseService.getEventsForUser(result.getPayload());
        return respondWithEntity(events);
    }

    @PostMapping("/events")
    private ResponseEntity<Object> createEvent(@RequestHeader(value = "Authorization") String token, @Valid @RequestBody CreateEventRequest body) {
        FirebaseToken verified = firebaseService.verifyIdToken(token);
        String eventName = body.getEventName();
        Result<Event> result = firebaseService.createEvent(eventName, firebaseService.getUserByUid(verified.getUid()).getPayload());
        return respondWithEntity(result);
    }

    @GetMapping("/events/{id}")
    private ResponseEntity<Object> getEvent(@RequestHeader(value = "Authorization") String token, @PathVariable String id) {
        firebaseService.verifyIdToken(token);
        Result<Event> result = firebaseService.getEvent(id.trim());
        return respondWithEntity(result);
    }

    @PutMapping("/events/{id}")
    @PostMapping("/events/{id}")
    private ResponseEntity<Message> updateEvent(@RequestHeader(value = "Authorization") String token,
                                                @PathVariable String id, @Valid @RequestBody Event body)  {
        firebaseService.verifyIdToken(token);
        if (!body.getId().equals(id)) {
            throw new IllegalArgumentException("Invalid Event ID!");
        }
        Result<Message> result = firebaseService.setEvent(id, body);
        return respondWithMessage(result);
    }

    @PostMapping("/events/{id}/items")
    private ResponseEntity<Message> addItem(@RequestHeader(value = "Authorization") String token,
                                            @PathVariable String id, @Valid @RequestBody AddItemRequest body)  {
        FirebaseToken verified = firebaseService.verifyIdToken(token);
        User user = firebaseService.getUserByUid(verified.getUid()).getPayload();
        Item item = new Item(body.getName(), body.getPrice(), body.getQuantity(), user);
        Result<Message> result = firebaseService.addItem(id, item);
        return respondWithMessage(result);
    }

    @DeleteMapping("/events/{id}/items/{itemId}")
    private ResponseEntity<Message> deleteItem(@RequestHeader(value = "Authorization") String token,
                                            @PathVariable String id, @PathVariable String itemId)  {
        FirebaseToken verified = firebaseService.verifyIdToken(token);
        User user = firebaseService.getUserByUid(verified.getUid()).getPayload();
        Result<Message> result = firebaseService.deleteItem(id, itemId);
        return respondWithMessage(result);
    }

    @PostMapping("/events/{id}/attendees")
    private ResponseEntity<Message> joinEvent(@RequestHeader(value = "Authorization") String token,
                                            @PathVariable String id)  {
        FirebaseToken verified = firebaseService.verifyIdToken(token);
        User user = firebaseService.getUserByUid(verified.getUid()).getPayload();
        Result<Message> result = firebaseService.addAttendee(id, user.getEmail());
        return respondWithMessage(result);
    }

    @DeleteMapping("/events/{id}/attendees")
    private ResponseEntity<Message> quitEvent(@RequestHeader(value = "Authorization") String token,
                                              @PathVariable String id)  {
        FirebaseToken verified = firebaseService.verifyIdToken(token);
        User user = firebaseService.getUserByUid(verified.getUid()).getPayload();
        Result<Message> result = firebaseService.deleteAttendee(id, user.getEmail());
        return respondWithMessage(result);
    }

    @GetMapping("/")
    private ResponseEntity<Message> displayWelcomeMessage(@RequestHeader(value = "Authorization") String token) {
        firebaseService.verifyIdToken(token);
        try {
            return new ResponseEntity<>(new Message(firebaseService.testFirestore()), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new Message("Unknown Error!"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}