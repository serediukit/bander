package com.serediuk.bander_client.ui.search;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.serediuk.bander_client.auth.AuthProvider;
import com.serediuk.bander_client.auth.AuthUID;
import com.serediuk.bander_client.model.dao.UsersDAO;
import com.serediuk.bander_client.model.dao.VacanciesDAO;

public class SearchViewModel extends ViewModel {
    UsersDAO usersDAO;
    VacanciesDAO vacanciesDAO;


    public SearchViewModel() {
        usersDAO = UsersDAO.getInstance();
        vacanciesDAO = VacanciesDAO.getInstance();
        Log.d("SEARCH", "UID: " + AuthUID.getUID());
    }

}