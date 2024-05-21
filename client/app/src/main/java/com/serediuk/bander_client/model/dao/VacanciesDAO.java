package com.serediuk.bander_client.model.dao;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.serediuk.bander_client.model.DatabaseConnectionProvider;
import com.serediuk.bander_client.model.entity.Vacancy;
import com.serediuk.bander_client.ui.search.RecommendedVacanciesRecyclerAdapter;

import java.util.ArrayList;

public class VacanciesDAO {
    private static VacanciesDAO instance;
    private final FirebaseDatabase database;

    private static ArrayList<Vacancy> vacanciesList;

    private static RecommendedVacanciesRecyclerAdapter recommendedVacanciesRecyclerAdapter;

    private VacanciesDAO() {
        vacanciesList = new ArrayList<>();

        database = DatabaseConnectionProvider.getInstance().getDatabase();
        createVacancy(new Vacancy(
                "234n12n3r13r1fsa2csafsa",
                "f1f442gfwfwff3",
                "drummer",
                "Searching for drummer",
                "500 dolars",
                "2024-12-31"));
        loadVacancies();
    }

    public static VacanciesDAO getInstance() {
        if (instance == null) {
            instance = new VacanciesDAO();
        }
        return instance;
    }

    public void createVacancy(Vacancy vacancy) {
        String key = database.getReference("vacancies").push().getKey();

        if (key != null) {
            vacancy.setVacancyUID(key);
            database.getReference("vacancies").child(key).setValue(vacancy);
            Log.d("VACANCY DAO", "Adding vacancy:\n" + vacancy);
        } else {
            Log.d("VACANCY DAO", "Key is null");
        }
    }

    public Vacancy readVacancy(String vacancyUID) {
        Log.d("VACANCY DAO", "Reading vacancy: " + vacancyUID);
        if (vacanciesList.size() != 0) {
            for (Vacancy vacancy : vacanciesList) {
                if (vacancy.getVacancyUID().equals(vacancyUID)) {
                    return vacancy;
                }
            }
        }
        return null;
    }

    public void updateVacancy(Vacancy vacancy) {
        database.getReference("vacancies").child(vacancy.getVacancyUID()).setValue(vacancy);

        Log.d("VACANCY DAO", "Updating vacancy:\n" + vacancy);
    }

    public void deleteVacancy(String vacancyUID) {
        database.getReference("vacancies").child(vacancyUID).removeValue();

        Log.d("VACANCY DAO", "Deleting vacancy: " + vacancyUID);
    }

    public void loadVacancies() {
        DatabaseReference vacanciesReference = database.getReference("vacancies");

        vacanciesReference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                vacanciesList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Vacancy vacancy = dataSnapshot.getValue(Vacancy.class);
                    vacanciesList.add(vacancy);
                }
                if (recommendedVacanciesRecyclerAdapter != null)
                    recommendedVacanciesRecyclerAdapter.notifyDataSetChanged();
                Log.d("VACANCY DAO", "Read " + vacanciesList.size() + " vacancies");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Log.d("VACANCY DAO", "Loaded " + vacanciesList.size() + " vacancies");
    }

    public ArrayList<Vacancy> getRecommendedVacancies() {
        return new ArrayList<>(vacanciesList);
    }

    public void setRecommendedVacanciesRecyclerAdapter(RecommendedVacanciesRecyclerAdapter adapter) {
        this.recommendedVacanciesRecyclerAdapter = adapter;
    }
}
