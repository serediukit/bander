package com.serediuk.bander_client.ui.search.vacancy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.serediuk.bander_client.R;
import com.serediuk.bander_client.auth.AuthUID;
import com.serediuk.bander_client.model.dao.VacanciesDAO;
import com.serediuk.bander_client.model.entity.Vacancy;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CreateVacancyActivity extends AppCompatActivity {
    VacanciesDAO vacanciesDAO;
    private Spinner moneySpinner;
    private ArrayAdapter<String> moneyAdapter;

    private EditText mRole, mText, mSalary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_vacancy);

        init();
    }

    private void init() {
        vacanciesDAO = VacanciesDAO.getInstance();

        moneySpinner = findViewById(R.id.moneySpinner);
        String[] moneys = {
                getResources().getString(R.string.dollars),
                getResources().getString(R.string.hryvnya)
        };
        moneyAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, moneys);
        moneySpinner.setAdapter(moneyAdapter);

        mRole = findViewById(R.id.vacancyRoleEditText);
        mText = findViewById(R.id.vacancyTextEditText);
        mSalary = findViewById(R.id.vacancySalaryEditText);
    }

    public void createVacancy(View view) {
        String role = mRole.getText().toString();
        role = role.substring(0,1).toUpperCase() + role.substring(1).toLowerCase();
        String text = mText.getText().toString();
        text = text.substring(0,1).toUpperCase() + text.substring(1).toLowerCase();
        String salary = mSalary.getText().toString();
        String money = moneyAdapter.getItem(moneySpinner.getSelectedItemPosition());

        if (role.isEmpty() || text.isEmpty()) {
            Toast.makeText(CreateVacancyActivity.this, "Please, fill role and vacancy description", Toast.LENGTH_SHORT).show();
        } else {
            String salaryText = "\uD83D\uDCB0 -";
            if (!salary.isEmpty())
                salaryText = "\uD83D\uDCB0 " + salary + " " + money;
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
            String datetime = now.format(formatter);

            Vacancy vacancy = new Vacancy(
                    "",
                    AuthUID.getUID(),
                    role,
                    text,
                    salaryText,
                    datetime
            );
            vacanciesDAO.createVacancy(vacancy);
            Toast.makeText(CreateVacancyActivity.this, "Vacancy created", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    public void cancel(View view) {
        finish();
    }
}