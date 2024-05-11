package com.serediuk.bander_client.model;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.serediuk.bander_client.model.entity.UserProfileInfo;

import java.util.ArrayList;

public class DatabaseProfileProvider {
    private static DatabaseProfileProvider instance = null;
    private final FirebaseDatabase database;

    private static ArrayList<UserProfileInfo> userProfileInfoList;
    private DatabaseProfileProvider() {
        database = DatabaseConnectionProvider.getInstance().getDatabase();

        userProfileInfoList = new ArrayList<>();
        loadUserProfileInfo();
    }

    public static DatabaseProfileProvider getInstance() {
        if (instance == null) {
            instance = new DatabaseProfileProvider();
        }
        return instance;
    }

    public void addUserProfileInfo(UserProfileInfo userProfileInfo) {
        DatabaseReference profileReference = database.getReference("profile_info");
        profileReference.push().setValue(userProfileInfo);
        Log.d("DATABASE", "Adding user profile info:\n" + userProfileInfo);
    }

    public UserProfileInfo getUserProfileInfoByUid(String uid) {
        for (UserProfileInfo userProfileInfo : userProfileInfoList) {
            if (userProfileInfo.getUid().equals(uid)) {
                return userProfileInfo;
            }
        }
        return new UserProfileInfo();
    }

    public void loadUserProfileInfo() {
        userProfileInfoList.clear();
        Log.d("DATABASE", "Loading user profile info...");
        DatabaseReference profileReference = database.getReference("profile_info");
        profileReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if (snapshot.exists()) {
                    UserProfileInfo userProfileInfo = snapshot.getValue(UserProfileInfo.class);
                    userProfileInfoList.add(userProfileInfo);
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
