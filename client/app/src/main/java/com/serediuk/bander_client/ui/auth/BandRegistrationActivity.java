package com.serediuk.bander_client.ui.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.serediuk.bander_client.R;
import com.serediuk.bander_client.auth.AuthProvider;
import com.serediuk.bander_client.model.dao.BandsDAO;
import com.serediuk.bander_client.model.entity.Band;
import com.serediuk.bander_client.ui.MainActivity;

import java.util.ArrayList;
import java.util.Objects;

public class BandRegistrationActivity extends AppCompatActivity {
    private EditText mEmail, mPassword, mConfirmPassword;
    private EditText mName, mCity;

    private AuthProvider authProvider;
    private FirebaseAuth.AuthStateListener firebaseAuthStateListener;

    private BandsDAO bandsDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_band_registration);

        authProvider = AuthProvider.getInstance();
        firebaseAuthStateListener = firebaseAuth -> {
            final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user != null) {
                Intent intent = new Intent(BandRegistrationActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        };

        bandsDAO = BandsDAO.getInstance();

        mEmail = findViewById(R.id.emailEditText);
        mPassword = findViewById(R.id.passwordEditText);
        mConfirmPassword = findViewById(R.id.confirmPasswordEditText);
        mName = findViewById(R.id.nameEditText);
        mCity = findViewById(R.id.cityEditText);
    }

    public void registerBand(View view) {
        final String email = mEmail.getText().toString();
        final String password = mPassword.getText().toString();
        final String confirmPassword = mConfirmPassword.getText().toString();
        final String name = mName.getText().toString();
        final String city = mCity.getText().toString();

        if (name.isEmpty() || city.isEmpty()) {
            Toast.makeText(BandRegistrationActivity.this, "Fill all fields", Toast.LENGTH_SHORT).show();
            Log.d("Auth", "Some fields are empty");
            return;
        }

        authProvider.register(email, password, confirmPassword).addOnCompleteListener(BandRegistrationActivity.this, task -> {
            if (task.isSuccessful()) {
                Band band = new Band(authProvider.getUid(), email, name, city, "", "", "", new ArrayList<>());
                bandsDAO.createBand(band);

                Log.d("Auth", "Created new band: " + band);
                Log.d("Auth", "SIGN UP successfully");
            }
            else {
                if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                    Toast.makeText(BandRegistrationActivity.this, "User with this email already exists", Toast.LENGTH_SHORT).show();
                    Log.d("Auth", "User with this email already exists");
                } else {
                    Toast.makeText(BandRegistrationActivity.this, Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                    Log.d("Auth", Objects.requireNonNull(task.getException().getMessage()));
                }
            }
        });
    }

    public void openLoginActivity(View view) {
        Intent intent = new Intent(BandRegistrationActivity.this, LoginRegisterActivity.class);
        startActivity(intent);
        finish();
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