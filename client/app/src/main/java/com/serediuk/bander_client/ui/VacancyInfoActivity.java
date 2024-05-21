package com.serediuk.bander_client.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.serediuk.bander_client.R;
import com.serediuk.bander_client.model.entity.Vacancy;

import org.w3c.dom.Text;

public class VacancyInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vacancy_info);

        Vacancy vacancy = (Vacancy) getIntent().getSerializableExtra("vacancy");

        if (vacancy != null) {
            TextView textView = findViewById(R.id.vacancyInfoText);
            textView.setText(vacancy.toString());
        }

    }

    public void back(View view) {
        finish();
    }

    public void sendResume(View view) {
    }
}