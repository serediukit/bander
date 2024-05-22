package com.serediuk.bander_client.ui.search.vacancy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.serediuk.bander_client.R;
import com.serediuk.bander_client.auth.AuthUID;
import com.serediuk.bander_client.model.dao.VacanciesDAO;
import com.serediuk.bander_client.model.entity.Vacancy;
import com.serediuk.bander_client.ui.search.adapters.BandVacanciesRecyclerAdapter;

import java.util.ArrayList;

public class BandVacanciesActivity extends AppCompatActivity {
    private RecyclerView bandVacanciesRecyclerView;
    private ArrayList<Vacancy> bandVacanciesList;
    private BandVacanciesRecyclerAdapter bandVacanciesRecyclerAdapter;
    private TextView mEmptyText;
    private VacanciesDAO vacanciesDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_band_vacancies);

        init();
    }

    private void init() {
        vacanciesDAO = VacanciesDAO.getInstance();
        mEmptyText = findViewById(R.id.bandVacanciesEmptyText);
        bandVacanciesRecyclerView = findViewById(R.id.bandVacanciesRecyclerView);
        bandVacanciesRecyclerView.setLayoutManager(new LinearLayoutManager(BandVacanciesActivity.this));

        bandVacanciesList = vacanciesDAO.getBandVacancies(AuthUID.getUID());
        if (bandVacanciesList.size() == 0) {
            mEmptyText.setVisibility(View.VISIBLE);
            bandVacanciesRecyclerView.setVisibility(View.INVISIBLE);
        } else {
            mEmptyText.setVisibility(View.INVISIBLE);
            bandVacanciesRecyclerView.setVisibility(View.VISIBLE);

            bandVacanciesRecyclerAdapter = new BandVacanciesRecyclerAdapter(BandVacanciesActivity.this, bandVacanciesList);
            bandVacanciesRecyclerView.setAdapter(bandVacanciesRecyclerAdapter);
            vacanciesDAO.setBandVacanciesRecyclerAdapter(bandVacanciesRecyclerAdapter);
        }
    }
}