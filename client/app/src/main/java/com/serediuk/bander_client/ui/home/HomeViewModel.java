package com.serediuk.bander_client.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.serediuk.bander_client.auth.AuthUID;
import com.serediuk.bander_client.model.dao.BandsDAO;
import com.serediuk.bander_client.model.dao.CandidatesDAO;
import com.serediuk.bander_client.model.dao.UsersDAO;
import com.serediuk.bander_client.model.entity.Band;
import com.serediuk.bander_client.model.entity.Candidate;
import com.serediuk.bander_client.model.entity.User;

public class HomeViewModel extends ViewModel {
    private UsersDAO usersDAO;
    private BandsDAO bandsDAO;
    private CandidatesDAO candidatesDAO;

    public HomeViewModel() {
        usersDAO = UsersDAO.getInstance();
        bandsDAO = BandsDAO.getInstance();
        candidatesDAO = CandidatesDAO.getInstance();
    }

    public User getUser() {
        return usersDAO.readUser(AuthUID.getUID());
    }

    public Band getBand() {
        return bandsDAO.readBand(AuthUID.getUID());
    }

    public Candidate getCandidate() {
        return candidatesDAO.readCandidate(AuthUID.getUID());
    }
}