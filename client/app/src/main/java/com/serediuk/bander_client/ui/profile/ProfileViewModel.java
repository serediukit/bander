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

    private final MutableLiveData<String> mText;

    public ProfileViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is profile fragment");

        authProvider = AuthProvider.getInstance();
    }

    public LiveData<String> getText() {
        return mText;
    }

    public void signOut() {
        authProvider.signOut();
    }

    public void addAuthStateListener(FirebaseAuth.AuthStateListener listener) {
        authProvider.addAuthStateListener(listener);
    }
}