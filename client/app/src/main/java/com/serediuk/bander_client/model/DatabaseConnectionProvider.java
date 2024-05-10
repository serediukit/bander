package com.serediuk.bander_client.model;

import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.serediuk.bander_client.model.entity.User;

public class DatabaseConnectionProvider {
    private static DatabaseConnectionProvider instance;

    private final FirebaseDatabase database;

    private DatabaseConnectionProvider() {
        database = FirebaseDatabase.getInstance("https://bander-63922-default-rtdb.europe-west1.firebasedatabase.app/");
        Log.d("DATABASE", "DB Connection Provider initialized");
    }

    public static DatabaseConnectionProvider getInstance() {
        if (instance == null) {
            instance = new DatabaseConnectionProvider();
        }
        return instance;
    }

    public void addUser(User user) {
        DatabaseReference userReference = database.getReference("users");
        userReference.push().setValue(user);
        Log.d("DATABASE", "Adding user: " + user.toString());
    }
}
