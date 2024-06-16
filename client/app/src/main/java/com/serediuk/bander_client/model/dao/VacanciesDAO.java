package com.serediuk.bander_client.model.dao;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.serediuk.bander_client.auth.AuthUID;
import com.serediuk.bander_client.model.database.DatabaseConnectionProvider;
import com.serediuk.bander_client.model.database.DatabaseInitializer;
import com.serediuk.bander_client.model.entity.Band;
import com.serediuk.bander_client.model.entity.Candidate;
import com.serediuk.bander_client.model.entity.Vacancy;
import com.serediuk.bander_client.ui.search.adapters.BandVacanciesRecyclerAdapter;
import com.serediuk.bander_client.ui.search.adapters.RecommendedVacanciesRecyclerAdapter;
import com.serediuk.bander_client.util.string.StringHelper;

import java.util.ArrayList;

public class VacanciesDAO {
    private static VacanciesDAO instance;
    private final FirebaseDatabase database;

    private static ArrayList<Vacancy> vacanciesList;

    private RecommendedVacanciesRecyclerAdapter recommendedVacanciesRecyclerAdapter;
    private BandVacanciesRecyclerAdapter bandVacanciesRecyclerAdapter;

    private VacanciesDAO() {
        vacanciesList = new ArrayList<>();

        database = DatabaseConnectionProvider.getInstance().getDatabase();
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

        for (int i = 0; i < vacanciesList.size(); i++) {
            if (vacanciesList.get(i).getVacancyUID().equals(vacancy.getVacancyUID())) {
                vacanciesList.set(i, vacancy);
                break;
            }
        }

        Log.d("VACANCY DAO", "Updating vacancy:\n" + vacancy);
    }

    public void deleteVacancy(String vacancyUID) {
        database.getReference("vacancies").child(vacancyUID).removeValue();

        for (Vacancy vacancy : vacanciesList) {
            if (vacancy.getVacancyUID().equals(vacancyUID)) {
                vacanciesList.remove(vacancy);
                break;
            }
        }

        Log.d("VACANCY DAO", "Deleting vacancy: " + vacancyUID);
        Log.d("VACANCY DAO", vacanciesList.toString());
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
                if (recommendedVacanciesRecyclerAdapter != null) {
                    recommendedVacanciesRecyclerAdapter.setArrayList(getRecommendedVacancies(AuthUID.getUID()));
                    recommendedVacanciesRecyclerAdapter.notifyDataSetChanged();
                }
                if (bandVacanciesRecyclerAdapter != null) {
                    bandVacanciesRecyclerAdapter.setArrayList(getBandVacancies(AuthUID.getUID()));
                    bandVacanciesRecyclerAdapter.notifyDataSetChanged();
                }
                Log.d("VACANCY DAO", "Read " + vacanciesList.size() + " vacancies");
                DatabaseInitializer.inc();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Log.d("VACANCY DAO", "Loaded " + vacanciesList.size() + " vacancies");
    }

    public ArrayList<Vacancy> getRecommendedVacancies(String candidateUID) {
        ResumesDAO resumesDAO = ResumesDAO.getInstance();
        ArrayList<Vacancy> recommendedVacancies = new ArrayList<>();
        for (Vacancy vacancy : vacanciesList) {
            if(!resumesDAO.isSentResume(vacancy.getVacancyUID())) {
                if (isRecommendedVacancy(vacancy, candidateUID)) {
                recommendedVacancies.add(vacancy);
                }
            }
        }
        return sortVacancy(recommendedVacancies);
    }

    private boolean isRecommendedVacancy(Vacancy vacancy, String candidateUID) {
        Candidate candidate = CandidatesDAO.getInstance().readCandidate(candidateUID);

        String bandCity = BandsDAO.getInstance().readBand(vacancy.getBandUID()).getCity();

        String vacancyRole = vacancy.getRole().toLowerCase();
        String candidateRole = candidate.getRole().toLowerCase();

        return bandCity.equals(candidate.getCity()) && StringHelper.inString(vacancyRole, candidateRole);
    }

    private ArrayList<Vacancy> sortVacancy(ArrayList<Vacancy> list) {
        return list;
    }

    public void setRecommendedVacanciesRecyclerAdapter(RecommendedVacanciesRecyclerAdapter adapter) {
        this.recommendedVacanciesRecyclerAdapter = adapter;
    }

    public ArrayList<Vacancy> getBandVacancies(String bandUID) {
        ArrayList<Vacancy> bandVacancies = new ArrayList<>();
        for (Vacancy vacancy : vacanciesList) {
            if (vacancy.getBandUID().equals(bandUID)) {
                bandVacancies.add(vacancy);
            }
        }
        return bandVacancies;
    }

    public void setBandVacanciesRecyclerAdapter(BandVacanciesRecyclerAdapter adapter) {
        this.bandVacanciesRecyclerAdapter = adapter;
    }
}
