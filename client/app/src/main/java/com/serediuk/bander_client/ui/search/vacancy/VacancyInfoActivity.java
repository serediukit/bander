package com.serediuk.bander_client.ui.search.vacancy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.serediuk.bander_client.R;
import com.serediuk.bander_client.model.dao.BandsDAO;
import com.serediuk.bander_client.model.entity.Band;
import com.serediuk.bander_client.model.entity.Vacancy;

import org.w3c.dom.Text;

public class VacancyInfoActivity extends AppCompatActivity {
    private BandsDAO bandsDAO;
    private Vacancy vacancy;

    private TextView mTitle, mBand, mGenres, mSalary, mCity;
    private TextView mText, mAbout, mLinks, mDatetime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vacancy_info);

        bandsDAO = BandsDAO.getInstance();
        vacancy = (Vacancy) getIntent().getSerializableExtra("vacancy");

        init();
        loadData();
    }

    private void init() {
        mTitle = findViewById(R.id.vacancyInfoTitle);
        mBand = findViewById(R.id.vacancyInfoBand);
        mGenres = findViewById(R.id.vacancyInfoGenres);
        mSalary = findViewById(R.id.vacancyInfoSalary);
        mCity = findViewById(R.id.vacancyInfoCity);
        mText = findViewById(R.id.vacancyInfoText);
        mAbout = findViewById(R.id.vacancyInfoAboutBand);
        mLinks = findViewById(R.id.vacancyInfoBandLinks);
        mDatetime = findViewById(R.id.vacancyInfoDatetime);
    }

    private void loadData() {
        Band band = bandsDAO.readBand(vacancy.getBandUID());

        mTitle.setText(vacancy.getRole());
        mBand.setText(band.getName());
        mGenres.setText(band.getGenres());
        mSalary.setText(vacancy.getSalary());
        mCity.setText(band.getCity());
        mText.setText(vacancy.getText());
        mAbout.setText(band.getAbout());
        mLinks.setText(band.getVideoLinks());
        mDatetime.setText(vacancy.getDatetime());
    }

    public void back(View view) {
        finish();
    }

    public void sendResume(View view) {
    }
}