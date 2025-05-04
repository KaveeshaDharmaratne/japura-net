package com.japura.forum.forum_api.service;

import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import com.japura.forum.forum_api.dto.AuthPayload;
import com.japura.forum.forum_api.dto.SignupInput;
import com.japura.forum.forum_api.dto.User;
import com.japura.forum.forum_api.model.UserDocument;

@Service
public class AuthServiceImpl implements AuthService {

    private final FirebaseAuth firebaseAuth;
    private final Firestore firestore;

    public AuthServiceImpl(FirebaseAuth firebaseAuth, Firestore firestore) {
        this.firebaseAuth = firebaseAuth;
        this.firestore = firestore;
    }

    @Override
    public AuthPayload signUp(SignupInput input) {

        if (!input.getPassword().equals(input.getConfirmPassword())) {
            throw new RuntimeException("Passwords do not match");
        }

        try {

            UserRecord.CreateRequest request = new UserRecord.CreateRequest()
                    .setEmail(input.getEmail())
                    .setPassword(input.getPassword())
                    .setDisplayName(input.getFirstName() + " " + input.getLastName())
                    .setEmailVerified(false)
                    .setDisabled(false);

            UserRecord userRecord = firebaseAuth.createUser(request);
            String firebaseUid = userRecord.getUid();

            UserDocument userEntity = new UserDocument();
            userEntity.setFirstName(input.getFirstName());
            userEntity.setLastName(input.getLastName());
            userEntity.setEmail(input.getEmail());
            userEntity.setFaculty(input.getFaculty());

            ApiFuture<WriteResult> writeResult = firestore.collection("users")
                    .document(firebaseUid)
                    .set(userEntity);

            writeResult.get();

            String customToken = firebaseAuth.createCustomToken(firebaseUid);

            User userDto = new User(
                    userEntity.getId(),
                    userEntity.getFirstName(),
                    userEntity.getLastName(),
                    userEntity.getEmail(),
                    userEntity.getFaculty());

            return new AuthPayload(customToken, userDto);

        } catch (FirebaseAuthException e) {

            System.err.println("Firebase signup failed: " + e.getMessage());
            throw new RuntimeException("Firebase signup failed: " + e.getMessage(), e);

        } catch (InterruptedException | ExecutionException e) {

            System.err.println("Signup failed: " + e.getMessage());
            throw new RuntimeException("Signup failed: " + e.getMessage(), e);
        }

    }

    @Override
    public User getUserById(String firebaseUid) {

        try {
            DocumentReference docRef = firestore.collection("users").document(firebaseUid);
            ApiFuture<DocumentSnapshot> future = docRef.get();
            DocumentSnapshot document = future.get();

            if (document.exists()) {
                UserDocument userDocument = document.toObject(UserDocument.class);
                if (userDocument != null) {
                    userDocument.setId(document.getId());

                    return new User(
                            userDocument.getId(),
                            userDocument.getFirstName(),
                            userDocument.getLastName(),
                            userDocument.getEmail(),
                            userDocument.getFaculty());
                } else {
                    System.err.println("User document conversion failed for UID: " + firebaseUid);
                    return null;
                }
            } else {
                System.err.println("User document not found in Firestore for UID: " + firebaseUid);
                return null;
            }
        } catch (InterruptedException | ExecutionException e) {
            System.err.println("Error fetching user from Firestore: " + e.getMessage());
            throw new RuntimeException("Failed to fetch user details from database", e);
        }
    }

}
