package com.serediuk.bander_client.ui.profile;

import android.content.Intent;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.serediuk.bander_client.LoginRegisterActivity;
import com.serediuk.bander_client.MainActivity;
import com.serediuk.bander_client.auth.AuthProvider;

public class ProfileViewModel extends ViewModel {
    AuthProvider authProvider;

    public ProfileViewModel() {
        authProvider = AuthProvider.getInstance();
    }

    public void signOut() {
        authProvider.signOut();
    }

    public void addAuthStateListener(FirebaseAuth.AuthStateListener listener) {
        authProvider.addAuthStateListener(listener);
    }
}