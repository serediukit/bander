package com.serediuk.bander_client;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.serediuk.bander_client.auth.AuthProvider;

public class RegistrationActivity extends AppCompatActivity {
    private EditText mEmail, mPassword;

    private AuthProvider authProvider;
    private FirebaseAuth.AuthStateListener firebaseAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        authProvider = AuthProvider.getInstance();
        firebaseAuthStateListener = firebaseAuth -> {
            final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user != null) {
                Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        };

        Button mRegistrationButton = findViewById(R.id.registration_button);

        mEmail = findViewById(R.id.emailEditText);
        mPassword = findViewById(R.id.passwordEditText);

        mRegistrationButton.setOnClickListener(v -> {
            final String email = mEmail.getText().toString();
            final String password = mPassword.getText().toString();
            authProvider.register(email, password).addOnCompleteListener(RegistrationActivity.this, task -> {
                if (!task.isSuccessful()) {
                    Toast.makeText(RegistrationActivity.this, "Sign up error", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        authProvider.addAuthStateListener(firebaseAuthStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        authProvider.removeAuthStateListener(firebaseAuthStateListener);
    }
}