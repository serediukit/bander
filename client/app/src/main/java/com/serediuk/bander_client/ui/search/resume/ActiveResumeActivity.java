package com.serediuk.bander_client.ui.search.resume;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.serediuk.bander_client.R;
import com.serediuk.bander_client.auth.AuthUID;
import com.serediuk.bander_client.model.dao.ResumesDAO;
import com.serediuk.bander_client.model.entity.Resume;
import com.serediuk.bander_client.ui.search.adapters.ActiveResumeRecyclerAdapter;

import java.util.ArrayList;

public class ActiveResumeActivity extends AppCompatActivity {
    private RecyclerView activeResumeRecyclerView;
    private ArrayList<Resume> activeResumeList;
    private ActiveResumeRecyclerAdapter activeResumeRecyclerAdapter;;
    private TextView mEmptyText;
    private ResumesDAO resumesDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active_resume);

        init();
    }

    private void init() {
        resumesDAO = ResumesDAO.getInstance();
        mEmptyText = findViewById(R.id.activeResumeEmptyText);
        activeResumeRecyclerView = findViewById(R.id.activeResumesRecyclerView);
        activeResumeRecyclerView.setLayoutManager(new LinearLayoutManager(ActiveResumeActivity.this));

        activeResumeList = resumesDAO.getActiveResumes(AuthUID.getUID());
        if (activeResumeList.size() == 0) {
            mEmptyText.setVisibility(View.VISIBLE);
            activeResumeRecyclerView.setVisibility(View.INVISIBLE);
        } else {
            mEmptyText.setVisibility(View.INVISIBLE);
            activeResumeRecyclerView.setVisibility(View.VISIBLE);

            activeResumeRecyclerAdapter = new ActiveResumeRecyclerAdapter(ActiveResumeActivity.this, activeResumeList);
            activeResumeRecyclerView.setAdapter(activeResumeRecyclerAdapter);
            resumesDAO.setActiveResumesRecyclerAdapter(activeResumeRecyclerAdapter);
        }
    }
}