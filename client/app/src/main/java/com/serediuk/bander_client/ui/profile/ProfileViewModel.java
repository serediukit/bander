package com.serediuk.bander_client.ui.profile;

import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.serediuk.bander_client.auth.AuthProvider;
import com.serediuk.bander_client.model.DatabaseConnectionProvider;
import com.serediuk.bander_client.model.entity.User;

import java.util.ArrayList;

public class ProfileViewModel extends ViewModel {
    AuthProvider authProvider;
    DatabaseConnectionProvider dbcProvider;

    public ProfileViewModel() {
        authProvider = AuthProvider.getInstance();
        dbcProvider = DatabaseConnectionProvider.getInstance();
        Log.d("PROFILE", "UID: " + authProvider.getUid());
    }

    public void signOut() {
        authProvider.signOut();
    }

    public void addAuthStateListener(FirebaseAuth.AuthStateListener listener) {
        authProvider.addAuthStateListener(listener);
    }

    public User getUserProfileData() {
        dbcProvider.loadUsers();
        User user = dbcProvider.getUser();
        if (!user.getUid().equals("Empty"))
            return user;
        else
            return dbcProvider.getUserByUid(authProvider.getUid());
    }

    public ArrayList<User> getUsersList() {
        return dbcProvider.getUsersList();
    }
}