package com.serediuk.bander_client.ui.search.vacancy;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.serediuk.bander_client.R;
import com.serediuk.bander_client.auth.AuthUID;
import com.serediuk.bander_client.model.dao.BandsDAO;
import com.serediuk.bander_client.model.dao.ResumesDAO;
import com.serediuk.bander_client.model.dao.UsersDAO;
import com.serediuk.bander_client.model.dao.VacanciesDAO;
import com.serediuk.bander_client.model.entity.Band;
import com.serediuk.bander_client.model.entity.Vacancy;
import com.serediuk.bander_client.model.enums.UserType;
import com.serediuk.bander_client.ui.auth.LoginRegisterActivity;
import com.serediuk.bander_client.ui.search.resume.SendResumeActivity;

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

        Button mSend = findViewById(R.id.sendResumeButton);
        Button mDelete = findViewById(R.id.deleteVacancyButton);
        if (UsersDAO.getInstance().readUser(AuthUID.getUID()).getType().equals(String.valueOf(UserType.CANDIDATE))) {
            mSend.setVisibility(View.VISIBLE);
            mDelete.setVisibility(View.INVISIBLE);
        } else {
            mSend.setVisibility(View.INVISIBLE);
            mDelete.setVisibility(View.VISIBLE);
        }
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
        Intent intent = new Intent(VacancyInfoActivity.this, SendResumeActivity.class);
        intent.putExtra("vacancy", vacancy);
        startActivity(intent);
    }

    public void deleteVacancy(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(VacancyInfoActivity.this);
        builder
                .setTitle(R.string.text_to_sign_out_title)
                .setMessage(R.string.text_delete_vacancy_warning)
                .setPositiveButton(R.string.yes, (dialog, which) -> {
                    VacanciesDAO vacanciesDAO = VacanciesDAO.getInstance();
                    vacanciesDAO.deleteVacancy(vacancy.getVacancyUID());
                    Toast.makeText(VacancyInfoActivity.this, "Vacancy deleted", Toast.LENGTH_SHORT).show();
                    ResumesDAO resumesDAO = ResumesDAO.getInstance();
                    resumesDAO.markAllResumesDeclinedForVacancy(vacancy.getVacancyUID());
                    finish();
                })
                .setNegativeButton(R.string.no, (dialog, which) -> {})
                .show();
    }
}