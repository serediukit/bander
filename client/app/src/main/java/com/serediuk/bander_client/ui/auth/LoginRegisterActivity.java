package com.serediuk.bander_client.ui.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.serediuk.bander_client.R;
import com.serediuk.bander_client.auth.AuthProvider;
import com.serediuk.bander_client.model.DatabaseInitializer;
import com.serediuk.bander_client.ui.MainActivity;

public class LoginRegisterActivity extends AppCompatActivity {
    private EditText mEmail, mPassword;

    private AuthProvider authProvider;
    private static FirebaseAuth.AuthStateListener firebaseAuthStateListener;

    private static int count = 0;

    public static void incrementCount() {
        count++;
        if (count >= 3)
            AuthProvider.getInstance().addAuthStateListener(firebaseAuthStateListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_register);

        DatabaseInitializer.init();
        authProvider = AuthProvider.getInstance();
        firebaseAuthStateListener = firebaseAuth -> {
            final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user != null) {
                Intent intent = new Intent(LoginRegisterActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            } else {
                ImageView mCoverImage = findViewById(R.id.coverImage);
                mCoverImage.setVisibility(View.INVISIBLE);
                Button mLoginButton = findViewById(R.id.login_button);
                mLoginButton.setVisibility(View.VISIBLE);
            }
        };

        mEmail = findViewById(R.id.emailEditText);
        mPassword = findViewById(R.id.passwordEditText);

        try {
            new Handler().postDelayed(() -> {
                AuthProvider.getInstance().addAuthStateListener(firebaseAuthStateListener);
            }, 5000);
        } catch (Exception ignored) {}
    }

    public void login(View view) {
        final String email = mEmail.getText().toString();
        final String password = mPassword.getText().toString();
        authProvider.login(email, password).addOnCompleteListener(LoginRegisterActivity.this, task -> {
            if (task.isSuccessful()) {
                Log.d("Auth", "LOG IN successfully");
            }
            else {
                Toast.makeText(LoginRegisterActivity.this, "Log in error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void openRegistrationActivity(View view) {
        Intent intent = new Intent(LoginRegisterActivity.this, RegistrationActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        //authProvider.addAuthStateListener(firebaseAuthStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        authProvider.removeAuthStateListener(firebaseAuthStateListener);
    }

    public void openBandRegistrationActivity(View view) {
        Intent intent = new Intent(LoginRegisterActivity.this, BandRegistrationActivity.class);
        startActivity(intent);
        finish();
    }
}