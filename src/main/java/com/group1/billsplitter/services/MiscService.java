package com.example.project1.services;

import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.group1.billsplitter.entities.Result;
import com.group1.billsplitter.entities.User;
import com.group1.billsplitter.repositories.FirebaseAuthRepository;
import com.group1.billsplitter.repositories.FirestoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class MiscService {
    public final String MESSAGES = "messages";
    private FirestoreRepository firestoreRepository;
    private FirebaseAuthRepository firebaseAuthRepository;

    @Autowired
    private void setFirestoreRepository(FirestoreRepository firestoreRepository) {
        this.firestoreRepository = firestoreRepository;
    }

    @Autowired
    private void setFirebaseAuthRepository(FirebaseAuthRepository firebaseAuthRepository) {
        this.firebaseAuthRepository = firebaseAuthRepository;
    }

    public String testFirestore() throws ExecutionException, InterruptedException {
        DocumentSnapshot documentSnapshot = firestoreRepository.getDocument(MESSAGES, "0");
        return (String) documentSnapshot.getData().get("text");
    }

    public Result testCreateUser(String email, String password, String name) {
        return firebaseAuthRepository.createUser(email, password, name);
    }

    public List<User> testGetAllUsers() {
       return firebaseAuthRepository.getAllUsers();
    }
}
