package com.serediuk.bander_client.model;

import android.util.Log;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.Nullable;
import com.serediuk.bander_client.R;
import com.serediuk.bander_client.model.entity.User;

import java.util.ArrayList;
import java.util.Objects;

public class DatabaseConnectionProvider {
    private static DatabaseConnectionProvider instance;

    private final FirebaseDatabase database;

    private static ArrayList<User> usersList;
    private static User user;

    private DatabaseConnectionProvider() {
        database = FirebaseDatabase.getInstance("https://bander-63922-default-rtdb.europe-west1.firebasedatabase.app/");
        Log.d("DATABASE", "DB Connection Provider initialized");

        usersList = new ArrayList<>();
        user = new User();
        loadUsers();
    }

    public static DatabaseConnectionProvider getInstance() {
        if (instance == null) {
            instance = new DatabaseConnectionProvider();
        }

        return instance;
    }

    public FirebaseDatabase getDatabase() {
        return database;
    }

    public void addUser(User user) {
        DatabaseReference userReference = database.getReference("users");
        userReference.push().setValue(user);
        Log.d("DATABASE", "Adding user: " + user.toString());
    }

    public User getUser() {
        return user;
    }

    public ArrayList<User> getUsersList() {
        return usersList;
    }

    public User getUserByUid(String uid) {
        for (User user : usersList) {
            if (user.getUid().equals(uid)) {
                return new User(user);
            }
        }
        return new User();
    }

    public void loadUsers() {
        usersList.clear();
        Log.d("DATABASE", "Loading users...");
        final FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference userReference = database.getReference("users");
        userReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if (snapshot.exists()) {
                    User sUser = snapshot.getValue(User.class);
                    usersList.add(sUser);
                    if (sUser.getUid().equals(currentUser.getUid())) {
                        user.setUserData(sUser);
                        Log.d("DATABASE", "Current user\n" + user);
                    }
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @androidx.annotation.Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @androidx.annotation.Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }


        });
    }

    public static void clear() {
        user.clear();
    }
}


