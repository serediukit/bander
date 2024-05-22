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
import com.serediuk.bander_client.model.entity.Resume;
import com.serediuk.bander_client.model.enums.ResumeStatus;
import com.serediuk.bander_client.ui.search.adapters.ReceivedResumesRecyclerAdapter;

import java.util.ArrayList;

public class ResumesDAO {
    private static ResumesDAO instance;
    private final FirebaseDatabase database;

    private static ArrayList<Resume> resumesList;

    private ReceivedResumesRecyclerAdapter receivedResumesRecyclerAdapter;

    private ResumesDAO() {
        resumesList = new ArrayList<>();

        database = DatabaseConnectionProvider.getInstance().getDatabase();
        loadResumes();
    }

    public static ResumesDAO getInstance() {
        if (instance == null) {
            instance = new ResumesDAO();
        }
        return instance;
    }

    public void createResume(Resume resume) {
        String key = database.getReference("resumes").push().getKey();

        if (key != null) {
            resume.setResumeUID(key);
            database.getReference("resumes").child(key).setValue(resume);
            Log.d("RESUME DAO", "Adding resume:\n" + resume);
        } else {
            Log.d("RESUME DAO", "Key is null");
        }
    }

    public Resume readResume(String resumeUID) {
        Log.d("RESUME DAO", "Reading resume: " + resumeUID);
        if (resumesList.size() != 0) {
            for (Resume resume : resumesList) {
                if (resume.getResumeUID().equals(resumeUID)) {
                    return resume;
                }
            }
        }
        return null;
    }

    public void updateResume(Resume resume) {
        database.getReference("resumes").child(resume.getResumeUID()).setValue(resume);

        Log.d("RESUME DAO", "Updating resume:\n" + resume);
    }

    public void deleteResume(String resumeUID) {
        database.getReference("resumes").child(resumeUID).removeValue();

        Log.d("RESUME DAO", "Deleting resume: " + resumeUID);
    }

    public void loadResumes() {
        DatabaseReference resumesReference = database.getReference("resumes");

        resumesReference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                resumesList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Resume resume = dataSnapshot.getValue(Resume.class);
                    resumesList.add(resume);
                }
                if (receivedResumesRecyclerAdapter != null)
                    receivedResumesRecyclerAdapter.notifyDataSetChanged();
                Log.d("RESUME DAO", "Read " + resumesList.size() + " resumes");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Log.d("RESUME DAO", "Loaded " + resumesList.size() + " resumes");
    }

    public ArrayList<Resume> getReceivedResumes(String bandUID) {
        ArrayList<Resume> resList = new ArrayList<Resume>;
        for (Resume resume : resumesList) {
            if (resume.getBandUID().equals(bandUID)) {
                resList.add(resume);
            }
        }
    }

    public void setReceivedResumesRecyclerAdapter(ReceivedResumesRecyclerAdapter adapter) {
        this.receivedResumesRecyclerAdapter = adapter;
    }

    public void markAllResumesDeclinedForVacancy(String vacancyUID) {
        for (Resume resume : resumesList) {
            if (resume.getVacancyUID().equals(vacancyUID)) {
                resume.setStatus(ResumeStatus.DECLINED);
                updateResume(resume);
            }
        }
    }
}
