package com.serediuk.bander_client;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.serediuk.bander_client.auth.AuthProvider;
import com.serediuk.bander_client.model.DatabaseConnectionProvider;
import com.serediuk.bander_client.model.entity.User;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;


public class RegistrationActivity extends AppCompatActivity {
    private EditText mEmail, mPassword, mConfirmPassword;
    private EditText mName, mSurname, mBirthday, mCity;

    private AuthProvider authProvider;
    private FirebaseAuth.AuthStateListener firebaseAuthStateListener;

    private DatabaseConnectionProvider dbcProvider;

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

        dbcProvider = DatabaseConnectionProvider.getInstance();

        mEmail = findViewById(R.id.emailEditText);
        mPassword = findViewById(R.id.passwordEditText);
        mConfirmPassword = findViewById(R.id.confirmPasswordEditText);
        mName = findViewById(R.id.nameEditText);
        mSurname = findViewById(R.id.surnameEditText);
        mBirthday = findViewById(R.id.dateEditText);
        mCity = findViewById(R.id.cityEditText);

        Date currentDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.add(Calendar.YEAR, -12);
        transformIntoDatePicker(findViewById(R.id.dateEditText), this, calendar.getTime());
    }

    public void registerUser(View view) {
        final String email = mEmail.getText().toString();
        final String password = mPassword.getText().toString();
        final String confirmPassword = mConfirmPassword.getText().toString();
        final String name = mName.getText().toString();
        final String surname = mSurname.getText().toString();
        final String birthday = mBirthday.getText().toString();
        final String city = mCity.getText().toString();

        if (name.isEmpty() || surname.isEmpty() || birthday.isEmpty() || city.isEmpty()) {
            Toast.makeText(RegistrationActivity.this, "Fill all fields", Toast.LENGTH_SHORT).show();
            Log.d("Auth", "Some fields are empty");
            return;
        }

        authProvider.register(email, password, confirmPassword).addOnCompleteListener(RegistrationActivity.this, task -> {
            if (task.isSuccessful()) {
                User user = new User(authProvider.getUid(), name, surname, birthday, city);
                dbcProvider.addUser(user);

                Log.d("Auth", "Created new user: " + user);
                Log.d("Auth", "SIGN UP successfully");
            }
            else {
                Toast.makeText(RegistrationActivity.this, Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void openLoginActivity(View view) {
        Intent intent = new Intent(RegistrationActivity.this, LoginRegisterActivity.class);
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

    private static void transformIntoDatePicker(EditText editText, Context context, Date maxDate) {
        editText.setFocusableInTouchMode(false);
        editText.setClickable(true);
        editText.setFocusable(false);

        final Calendar myCalendar = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener datePickerOnDataSetListener =
                (view, year, monthOfYear, dayOfMonth) -> {
                    myCalendar.set(Calendar.YEAR, year);
                    myCalendar.set(Calendar.MONTH, monthOfYear);
                    myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy", Locale.UK);
                    editText.setText(sdf.format(myCalendar.getTime()));
                    Log.d("Auth", "Date picked: " + sdf.format(myCalendar.getTime()));
                };

        editText.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    context, datePickerOnDataSetListener, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)
            );
            if (maxDate != null) {
                datePickerDialog.getDatePicker().setMaxDate(maxDate.getTime());
            }
            datePickerDialog.show();
        });
    }
}