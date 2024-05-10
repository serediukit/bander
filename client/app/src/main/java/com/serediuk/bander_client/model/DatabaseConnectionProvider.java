package com.serediuk.bander_client.model;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.serediuk.bander_client.R;
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

    public User getUserData(String uid) {
        DatabaseReference userReference = database.getReference("users");
        final User[] findedUser = new User[1];
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    User user = ds.getValue(User.class);
                    if (user != null && user.getUid().equals(uid)) {
                        findedUser[0] = user;
                        return;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        userReference.addValueEventListener(listener);
        return findedUser[0];
    }
}
