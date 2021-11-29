package com.group1.billsplitter.repositories;

import com.google.firebase.auth.*;
import com.google.gson.JsonParser;
import com.group1.billsplitter.entities.Result;
import com.group1.billsplitter.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.GsonJsonParser;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class FirebaseAuthRepository {
    FirebaseAuth firebaseAuth;

    @Autowired
    public void setFirebaseAuth(FirebaseAuth firebaseAuth) {
        this.firebaseAuth = firebaseAuth;
    }

    public Result<String> createUser(String email, boolean emailVerified, String password, String phoneNumber, String name) {
        UserRecord.CreateRequest request;
        try {
            request = new UserRecord.CreateRequest()
                    .setEmail(email)
                    .setEmailVerified(emailVerified)
                    .setPassword(password)
                    .setDisplayName(name)
                    .setDisabled(false);
            if (phoneNumber != null && phoneNumber.length() > 0) {
                request.setPhoneNumber(phoneNumber);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new Result<>(false, "Error when creating user: " + e.getMessage());
        }
        try {
            UserRecord userRecord = firebaseAuth.createUser(request);
            return new Result<>(true, "Successfully created new user: " + userRecord.getDisplayName(),
                    userRecord.getEmail());
        } catch (Exception e) {
            e.printStackTrace();
            String reason = e.getCause().getMessage();
            reason = reason.substring(reason.indexOf("{"));
            reason = JsonParser.parseString(reason).getAsJsonObject().get("error").getAsJsonObject().get("message").getAsString();
            return new Result(false, "Error when creating user: " + reason);
//            return new Result(false, "Error when creating user: " + new Gson().fromJson(e.getCause().getMessage()));
        }
    }

    public Result<String> createUser(String email, String password, String phoneNumber, String name) {
        return createUser(email, false, password, phoneNumber, name);
    }

    public Result<String> createUser(String email, String password, String name) {
        return createUser(email, false, password, null, name);
    }

    public ListUsersPage getAllUsers() {
        try {
            ListUsersPage page = firebaseAuth.listUsers(null);
//            List<User> result = new ArrayList<>();
//            for (ExportedUserRecord user : page.iterateAll()) {
//                result.add(new User(user.getEmail(), user.getDisplayName(), get));
//            }
            return page;
        } catch (FirebaseAuthException e) {
            e.printStackTrace();
            System.out.println("Error when fetching the list of users: " + e.getCause());
            return null;
        }
    }

    public UserRecord getUserByEmail(String email) throws FirebaseAuthException {
        return firebaseAuth.getUserByEmail(email);
    }

    public UserRecord getUserByUid(String uid) throws FirebaseAuthException {
        return firebaseAuth.getUser(uid);
    }

    public Result<FirebaseToken> verifyIdToken(String idToken) {
        try {
            FirebaseToken decodedToken = firebaseAuth.verifyIdToken(idToken);
            System.out.println(decodedToken.getName());
            return new Result<>(true, decodedToken.getName(), decodedToken);
        } catch (Exception e) {
            return new Result<>(false, e.getMessage());
        }
    }
}
