package com.group1.billsplitter.repositories;

import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Repository;

import java.util.concurrent.ExecutionException;

@Repository
public class FirestoreRepository {
    Firestore firestore;

    @Autowired
    public void setFirestore(Firestore firestore) {
        this.firestore = firestore;
    }

    public DocumentSnapshot getDocument(String collectionName, String documentId) throws ExecutionException, InterruptedException {
        DocumentReference documentReference = firestore.collection(collectionName).document(documentId);
        DocumentSnapshot documentSnapshot = documentReference.get().get();
        return documentSnapshot;
    }
}
