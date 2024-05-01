package com.serediuk.bander_client.auth;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.serediuk.bander_client.util.Validator;

public class AuthProvider {
    private static AuthProvider instance;

    private final FirebaseAuth auth;

    private AuthProvider() {
        auth = FirebaseAuth.getInstance();
    }

    public static AuthProvider getInstance() {
        if (instance == null) {
            instance = new AuthProvider();
        }
        return instance;
    }

    public FirebaseAuth getAuth() {
        return auth;
    }

    public void addAuthStateListener(FirebaseAuth.AuthStateListener listener) {
        auth.addAuthStateListener(listener);
    }

    public void removeAuthStateListener(FirebaseAuth.AuthStateListener listener) {
        auth.removeAuthStateListener(listener);
    }

    public Task<AuthResult> register(String email, String password) {
        if (Validator.isValidEmail(email) && Validator.isValidPassword(password)) {
            return auth.createUserWithEmailAndPassword(email, password);
        }
        return auth.createUserWithEmailAndPassword(email, password);
    }

    public Task<AuthResult> login(String email, String password) {
        return auth.signInWithEmailAndPassword(email, password);
    }

    public void signOut() {
        auth.signOut();
    }
}
