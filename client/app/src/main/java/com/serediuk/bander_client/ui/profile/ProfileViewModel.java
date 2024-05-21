package com.serediuk.bander_client.ui.profile;

import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.serediuk.bander_client.auth.AuthProvider;
import com.serediuk.bander_client.model.DatabaseConnectionProvider;
import com.serediuk.bander_client.model.dao.BandsDAO;
import com.serediuk.bander_client.model.dao.CandidatesDAO;
import com.serediuk.bander_client.model.dao.UsersDAO;
import com.serediuk.bander_client.model.entity.Band;
import com.serediuk.bander_client.model.entity.Candidate;
import com.serediuk.bander_client.model.entity.User;

import java.util.ArrayList;

public class ProfileViewModel extends ViewModel {
    AuthProvider authProvider;
    CandidatesDAO candidatesDAO;
    BandsDAO bandsDAO;
    UsersDAO usersDAO;

    public ProfileViewModel() {
        authProvider = AuthProvider.getInstance();
        candidatesDAO = CandidatesDAO.getInstance();
        bandsDAO = BandsDAO.getInstance();
        usersDAO = UsersDAO.getInstance();
        Log.d("PROFILE", "UID: " + authProvider.getUid());
    }

    public void signOut() {
        authProvider.signOut();
    }

    public void addAuthStateListener(FirebaseAuth.AuthStateListener listener) {
        authProvider.addAuthStateListener(listener);
    }

    public User getUser() {
        return usersDAO.readUser(authProvider.getUid());
    }

    public Candidate getCandidate() {
        return candidatesDAO.readCandidate(authProvider.getUid());
    }

    public Band getBand() {
        return bandsDAO.readBand(authProvider.getUid());
    }
}