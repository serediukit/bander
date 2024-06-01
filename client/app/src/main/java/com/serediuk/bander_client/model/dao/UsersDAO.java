package com.serediuk.bander_client.model.dao;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.serediuk.bander_client.R;
import com.serediuk.bander_client.model.DatabaseConnectionProvider;
import com.serediuk.bander_client.model.entity.User;
import com.serediuk.bander_client.ui.MainActivity;
import com.serediuk.bander_client.ui.auth.LoginRegisterActivity;

import java.util.ArrayList;

public class UsersDAO {
    private static UsersDAO instance;
    private FirebaseDatabase database;

    private ArrayList<User> usersList;

    private UsersDAO() {
        usersList = new ArrayList<>();

        database = DatabaseConnectionProvider.getInstance().getDatabase();
        loadUsers();
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

    public void loadUsers() {
        DatabaseReference candidatesReference = database.getReference("users");

        candidatesReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                usersList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    User user = dataSnapshot.getValue(User.class);
                    usersList.add(user);
                }
                Log.d("USER DAO", "Read " + usersList.size() + " users");

                LoginRegisterActivity.incrementCount();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Log.d("USER DAO", "Loaded " + usersList.size() + " users");
    }
}
