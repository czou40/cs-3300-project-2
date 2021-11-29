package com.group1.billsplitter.services;

import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.group1.billsplitter.entities.Result;
import com.group1.billsplitter.repositories.FirebaseAuthRepository;
import com.group1.billsplitter.repositories.FirestoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
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

    public Result CreateUser(String email, String password, String name) {
        return firebaseAuthRepository.createUser(email, password, name);
    }

    public Result verifyIdToken(String token) {
        return firebaseAuthRepository.verifyIdToken(token);
    }
}
