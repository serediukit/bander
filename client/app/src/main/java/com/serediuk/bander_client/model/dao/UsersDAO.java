package com.serediuk.bander_client.model.dao;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.serediuk.bander_client.model.DatabaseConnectionProvider;
import com.serediuk.bander_client.model.entity.Candidate;
import com.serediuk.bander_client.model.entity.User;

import java.util.ArrayList;

public class UsersDAO {
    private static UsersDAO instance;
    private FirebaseDatabase database;

    private ArrayList<User> usersList;

    private UsersDAO() {
        usersList = new ArrayList<User>();

        database = DatabaseConnectionProvider.getInstance().getDatabase();
    }

    public static UsersDAO getInstance() {
        if (instance == null) {
            instance = new UsersDAO();
        }

        return instance;
    }

    public User readUser(String userUID) {
        Log.d("USER DAO", "Reading user: " + userUID);
        if (usersList.size() != 0) {
            for (User user : usersList) {
                if (user.getUid().equals(userUID)) {
                    return user;
                }
            }
        }
        return null;
    }

    private void loadUsers() {
        DatabaseReference candidatesReference = database.getReference("users");

        candidatesReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                usersList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    User user = dataSnapshot.getValue(User.class);
                    usersList.add(user);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Log.d("USER DAO", "Loaded " + usersList.size() + " users");
    }
}
