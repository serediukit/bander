package com.serediuk.bander_client.ui.search.resume;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.serediuk.bander_client.R;
import com.serediuk.bander_client.auth.AuthUID;
import com.serediuk.bander_client.model.dao.ResumesDAO;
import com.serediuk.bander_client.model.entity.Resume;
import com.serediuk.bander_client.model.entity.Vacancy;
import com.serediuk.bander_client.model.enums.ResumeStatus;
import com.serediuk.bander_client.ui.MainActivity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SendResumeActivity extends AppCompatActivity {
    private ResumesDAO resumesDAO;
    private Vacancy vacancy;
    private TextView mText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_resume);

        resumesDAO = ResumesDAO.getInstance();
        vacancy = (Vacancy) getIntent().getSerializableExtra("vacancy");

        mText = findViewById(R.id.resumeTextEditText);
    }

    public void sendResume(View view) {
        String resumeText = mText.getText().toString();

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        String datetime = now.format(formatter);

        Resume resume = new Resume(
                "",
                AuthUID.getUID(),
                vacancy.getVacancyUID(),
                resumeText,
                datetime,
                ResumeStatus.NEW.toString()
        );
        resumesDAO.createResume(resume);
        Toast.makeText(SendResumeActivity.this, "Resume sended", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(SendResumeActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void cancel(View view) {
        finish();
    }
}