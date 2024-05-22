package com.serediuk.bander_client.ui.search.resume;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.serediuk.bander_client.R;
import com.serediuk.bander_client.auth.AuthUID;
import com.serediuk.bander_client.model.dao.ResumesDAO;
import com.serediuk.bander_client.model.dao.VacanciesDAO;
import com.serediuk.bander_client.model.entity.Resume;
import com.serediuk.bander_client.model.entity.Vacancy;
import com.serediuk.bander_client.ui.search.adapters.BandVacanciesRecyclerAdapter;
import com.serediuk.bander_client.ui.search.adapters.ResumeHistoryRecyclerAdapter;

import java.util.ArrayList;

public class ResumeHistoryActivity extends AppCompatActivity {
    private RecyclerView resumeHistoryRecyclerView;
    private ArrayList<Resume> resumeHistoryList;
    private ResumeHistoryRecyclerAdapter resumeHistoryRecyclerAdapter;
    private TextView mEmptyText;
    private ResumesDAO resumesDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resume_history);

        init();
    }

    private void init() {
        resumesDAO = ResumesDAO.getInstance();
        mEmptyText = findViewById(R.id.resumeHistoryEmptyText);
        resumeHistoryRecyclerView = findViewById(R.id.resumeHistoryRecyclerView);
        resumeHistoryRecyclerView.setLayoutManager(new LinearLayoutManager(ResumeHistoryActivity.this));

        resumeHistoryList = resumesDAO.getResumeHistory(AuthUID.getUID());
        Log.d("TEST", "Resume history size: " + resumeHistoryList.size());
        Log.d("TEST", "UID: " + AuthUID.getUID());
        if (resumeHistoryList.size() == 0) {
            mEmptyText.setVisibility(View.VISIBLE);
            resumeHistoryRecyclerView.setVisibility(View.INVISIBLE);
        } else {
            mEmptyText.setVisibility(View.INVISIBLE);
            resumeHistoryRecyclerView.setVisibility(View.VISIBLE);

            resumeHistoryRecyclerAdapter = new ResumeHistoryRecyclerAdapter(ResumeHistoryActivity.this, resumeHistoryList);
            resumeHistoryRecyclerView.setAdapter(resumeHistoryRecyclerAdapter);
            resumesDAO.setResumeHistoryRecyclerAdapter(resumeHistoryRecyclerAdapter);
        }
    }
}