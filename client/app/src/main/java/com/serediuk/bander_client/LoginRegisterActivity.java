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

public class LoginRegisterActivity extends AppCompatActivity {
    private EditText mEmail, mPassword;

    private AuthProvider authProvider;
    private FirebaseAuth.AuthStateListener firebaseAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_register);

        authProvider = AuthProvider.getInstance();
        firebaseAuthStateListener = firebaseAuth -> {
            final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user != null) {
                Intent intent = new Intent(LoginRegisterActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        };

        Button mLoginButton = findViewById(R.id.login_button);
        Button mRegisterButton = findViewById(R.id.registration_button);

        mEmail = findViewById(R.id.emailEditText);
        mPassword = findViewById(R.id.passwordEditText);

        mLoginButton.setOnClickListener(v -> {
            final String email = mEmail.getText().toString();
            final String password = mPassword.getText().toString();
            authProvider.login(email, password).addOnCompleteListener(LoginRegisterActivity.this, task -> {
                if (!task.isSuccessful()) {
                    Toast.makeText(LoginRegisterActivity.this, "Log in error", Toast.LENGTH_SHORT).show();
                }
            });
        });

        mRegisterButton.setOnClickListener(v -> {
            Intent intent = new Intent(LoginRegisterActivity.this, RegistrationActivity.class);
            startActivity(intent);
            finish();
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