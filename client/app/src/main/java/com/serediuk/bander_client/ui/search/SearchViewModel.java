package com.serediuk.bander_client.ui.search;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.serediuk.bander_client.auth.AuthProvider;
import com.serediuk.bander_client.auth.AuthUID;
import com.serediuk.bander_client.model.dao.VacanciesDAO;

public class SearchViewModel extends ViewModel {
    VacanciesDAO vacanciesDAO;

    public SearchViewModel() {
        vacanciesDAO = VacanciesDAO.getInstance();
        Log.d("SEARCH", "UID: " + AuthUID.getUID());
    }

}