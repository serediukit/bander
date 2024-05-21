package com.serediuk.bander_client.ui.search;

import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.serediuk.bander_client.auth.AuthUID;
import com.serediuk.bander_client.model.dao.UsersDAO;
import com.serediuk.bander_client.model.dao.VacanciesDAO;
import com.serediuk.bander_client.model.entity.Vacancy;

import java.util.ArrayList;

public class SearchViewModel extends ViewModel {
    UsersDAO usersDAO;
    VacanciesDAO vacanciesDAO;


    public SearchViewModel() {
        usersDAO = UsersDAO.getInstance();
        vacanciesDAO = VacanciesDAO.getInstance();
        Log.d("SEARCH", "UID: " + AuthUID.getUID());
    }

    public ArrayList<Vacancy> getRecommendedVacancies() {
        return vacanciesDAO.getRecommendedVacancies();
    }

    public void setRecommendedAdapter(RecommendedVacanciesRecyclerAdapter adapter) {
        vacanciesDAO.setRecommendedVacanciesRecyclerAdapter(adapter);
    }
}