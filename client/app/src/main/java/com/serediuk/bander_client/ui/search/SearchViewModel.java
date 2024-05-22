package com.serediuk.bander_client.ui.search;

import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.serediuk.bander_client.auth.AuthUID;
import com.serediuk.bander_client.model.dao.ResumesDAO;
import com.serediuk.bander_client.model.dao.UsersDAO;
import com.serediuk.bander_client.model.dao.VacanciesDAO;
import com.serediuk.bander_client.model.entity.Resume;
import com.serediuk.bander_client.model.entity.Vacancy;
import com.serediuk.bander_client.ui.search.adapters.ReceivedResumesRecyclerAdapter;
import com.serediuk.bander_client.ui.search.adapters.RecommendedVacanciesRecyclerAdapter;

import java.util.ArrayList;

public class SearchViewModel extends ViewModel {
    UsersDAO usersDAO;
    VacanciesDAO vacanciesDAO;
    ResumesDAO resumesDAO;


    public SearchViewModel() {
        usersDAO = UsersDAO.getInstance();
        vacanciesDAO = VacanciesDAO.getInstance();
        resumesDAO = ResumesDAO.getInstance();
        Log.d("SEARCH", "UID: " + AuthUID.getUID());
    }

    public ArrayList<Vacancy> getRecommendedVacancies() {
        return vacanciesDAO.getRecommendedVacancies();
    }

    public void setRecommendedAdapter(RecommendedVacanciesRecyclerAdapter adapter) {
        vacanciesDAO.setRecommendedVacanciesRecyclerAdapter(adapter);
    }

    public String getUserType() {
        return usersDAO.readUser(AuthUID.getUID()).getType();
    }

    public ArrayList<Resume> getReceivedResumesList() {
        return resumesDAO.getReceivedResumes();
    }

    public void setReceivedAdapter(ReceivedResumesRecyclerAdapter receivedResumesAdapter) {
        resumesDAO.setReceivedResumesRecyclerAdapter(receivedResumesAdapter);
    }
}