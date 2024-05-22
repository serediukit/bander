package com.serediuk.bander_client.ui.search;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.serediuk.bander_client.R;
import com.serediuk.bander_client.model.dao.CandidatesDAO;
import com.serediuk.bander_client.model.entity.Candidate;
import com.serediuk.bander_client.model.entity.Resume;

public class ResumeInfoActivity extends AppCompatActivity {
    private CandidatesDAO candidatesDAO;
    private Resume resume;

    private TextView mName, mSurname, mRole, mExperience;
    private TextView mBirthday, mCity, mText;
    private TextView mAbout, mGenres, mLinks, mDatetime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resume_info);

        candidatesDAO = CandidatesDAO.getInstance();
        resume = (Resume) getIntent().getSerializableExtra("resume");

        init();
        loadData();
    }

    private void init() {
        mName = findViewById(R.id.resumeInfoCandidateName);
        mSurname = findViewById(R.id.resumeInfoCandidateSurname);
        mRole = findViewById(R.id.resumeInfoRole);
        mExperience = findViewById(R.id.resumeInfoExperience);
        mBirthday = findViewById(R.id.resumeInfoBirthday);
        mCity = findViewById(R.id.resumeInfoCity);
        mText = findViewById(R.id.resumeInfoText);
        mAbout = findViewById(R.id.resumeInfoAbout);
        mGenres = findViewById(R.id.resumeInfoGenres);
        mLinks = findViewById(R.id.resumeInfoLinks);
        mDatetime = findViewById(R.id.resumeInfoDatetime);
    }

    private void loadData() {
        Candidate candidate = candidatesDAO.readCandidate(resume.getCandidateUID());

        mName.setText(candidate.getName());
        mSurname.setText(candidate.getSurname());
        mRole.setText(candidate.getRole());
        mExperience.setText(candidate.getExperience());
        mBirthday.setText(candidate.getBirthday());
        mCity.setText(candidate.getCity());
        mText.setText(resume.getText());
        mAbout.setText(candidate.getAbout());
        mGenres.setText(candidate.getPreferredGenres());
        mLinks.setText(candidate.getVideoLinks());
        mDatetime.setText(resume.getDatetime());
    }

    public void acceptResume(View view) {
    }

    public void declineResume(View view) {
    }
}