package com.group1.billsplitter.repositories;

import com.google.cloud.firestore.*;
import com.google.gson.Gson;
import com.group1.billsplitter.entities.Result;
import com.group1.billsplitter.entities.exceptions.NotFoundException;
import io.netty.handler.codec.serialization.ObjectEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Repository;

import java.util.*;
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
        if (documentSnapshot.getData() == null) {
            throw new NotFoundException("Document not found!");
        }
        return documentSnapshot;
    }

    public void setDocument(String collectionName, String documentId, Object content) {
        DocumentReference documentReference = firestore.collection(collectionName).document(documentId);
        documentReference.set(content, SetOptions.merge());
    }

    public void setDocument(String collectionName, String documentId, Map<String, Object> content) {
        DocumentReference documentReference = firestore.collection(collectionName).document(documentId);
        documentReference.set(content, SetOptions.merge());
    }

    public void updateDocument(String collectionName, String documentId, Object content) {
        DocumentReference documentReference = firestore.collection(collectionName).document(documentId);
        Gson gson = new Gson();
        documentReference.update(gson.fromJson(gson.toJson(content), HashMap.class));
    }

    public void updateDocument(String collectionName, String documentId, Map<String, Object> content) {
        DocumentReference documentReference = firestore.collection(collectionName).document(documentId);
        documentReference.update(content);
    }


    public List<QueryDocumentSnapshot> searchWhereArrayContains(String collectionName, String fieldName, Object value) throws ExecutionException, InterruptedException {
        CollectionReference collectionReference = firestore.collection(collectionName);
        Query query = collectionReference.whereArrayContains(fieldName, value);
        return query.get().get().getDocuments();
    }
}
