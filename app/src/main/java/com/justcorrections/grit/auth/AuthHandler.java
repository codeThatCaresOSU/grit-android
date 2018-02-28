package com.justcorrections.grit.auth;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuth.AuthStateListener;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by ianwillis on 2/21/18.
 */

public class AuthHandler {

    private static AuthHandler INSTANCE;

    private FirebaseAuth firebaseAuth;

    public static AuthHandler getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new AuthHandler();
        }
        return INSTANCE;
    }

    private AuthHandler() {
        firebaseAuth = FirebaseAuth.getInstance();
    }

    public FirebaseUser getCurrentUser() {
        return firebaseAuth.getCurrentUser();
    }

    public void addLoginListener() {
        firebaseAuth.addAuthStateListener(new AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

            }
        });
    }

    public void login(String email, String password, final FirebaseLoginListener loginListener) {
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    loginListener.onSuccess(getCurrentUser());
                } else {
                    loginListener.onFailure(task.getResult().toString());
                }
            }
        });
    }

    public interface FirebaseLoginListener {
        void onSuccess(FirebaseUser user);

        void onFailure(String errorMessage);
    }

    public void signOut() {
        firebaseAuth.signOut();
    }

    public void createUser(String email, String password, final FirebaseCreateUserListener createUserListener) {
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    createUserListener.onSuccess(getCurrentUser());
                } else {
                    createUserListener.onFailure(task.getResult().toString());
                }
            }
        });
    }

    public interface FirebaseCreateUserListener {
        void onSuccess(FirebaseUser user);

        void onFailure(String errorMessage);
    }

    public void sendPasswordResetEmail(String email, final FirebasePasswordResetListener passwordResetListener) {
        firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    passwordResetListener.onSuccess();
                } else {
                    passwordResetListener.onFailure(task.getResult().toString());
                }
            }
        });
    }

    public interface FirebasePasswordResetListener {
        void onSuccess();

        void onFailure(String errorMessage);
    }

}
