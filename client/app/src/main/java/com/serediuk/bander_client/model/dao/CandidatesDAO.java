package com.serediuk.bander_client.model.dao;

import android.util.Log;

import com.google.firebase.database.FirebaseDatabase;
import com.serediuk.bander_client.model.DatabaseConnectionProvider;
import com.serediuk.bander_client.model.entity.Candidate;

public class CandidatesDAO {
    private static CandidatesDAO instance;
    private FirebaseDatabase database;

    private CandidatesDAO() {
        database = DatabaseConnectionProvider.getInstance().getDatabase();
    }

    public static CandidatesDAO getInstance() {
        if (instance == null) {
            instance = new CandidatesDAO();
        }
        return instance;
    }

    public void createCandidate(Candidate candidate) {
        database.getReference("candidates").push().setValue(candidate);
        database.getReference("users").push().setValue(candidate.getUser());

        Log.d("CANDIDATE DAO", "Adding candidate:\n" + candidate);
    }

    public void updateCandidate(Candidate candidate) {
        database.getReference("candidates").child(candidate.getCandidateUID()).setValue(candidate);

        Log.d("CANDIDATE DAO", "Updating candidate:\n" + candidate);
    }
}
