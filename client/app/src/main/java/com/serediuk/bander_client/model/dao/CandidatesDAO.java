package com.serediuk.bander_client.model.dao;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.serediuk.bander_client.model.DatabaseConnectionProvider;
import com.serediuk.bander_client.model.entity.Candidate;

import java.util.ArrayList;

public class CandidatesDAO {
    private static CandidatesDAO instance;
    private final FirebaseDatabase database;

    private final ArrayList<Candidate> candidatesList;

    private CandidatesDAO() {
        candidatesList = new ArrayList<>();

        database = DatabaseConnectionProvider.getInstance().getDatabase();
        loadCandidates();
    }

    public static CandidatesDAO getInstance() {
        if (instance == null) {
            instance = new CandidatesDAO();
        }
        return instance;
    }

    public void createCandidate(Candidate candidate) {
        if (candidate.getCandidateUID() != null) {
            database.getReference("candidates").child(candidate.getCandidateUID()).setValue(candidate);
            database.getReference("users").child(candidate.getCandidateUID()).setValue(candidate.getUser());

            Log.d("CANDIDATE DAO", "Adding candidate:\n" + candidate);
        } else {
            Log.d("CANDIDATE DAO", "Key is null");
        }
    }

    public Candidate readCandidate(String candidateUID) {
        Log.d("CANDIDATE DAO", "Reading candidate: " + candidateUID);
        if (candidatesList.size() != 0) {
            for (Candidate candidate : candidatesList) {
                if (candidate.getCandidateUID().equals(candidateUID)) {
                    return candidate;
                }
            }
        }
        return null;
    }

    public void updateCandidate(Candidate candidate) {
        database.getReference("candidates").child(candidate.getCandidateUID()).setValue(candidate);
        database.getReference("users").child(candidate.getUser().getUid()).setValue(candidate.getUser());

        Log.d("CANDIDATE DAO", "Updating candidate:\n" + candidate);
    }

    public void deleteCandidate(String candidateUID) {
        database.getReference("candidates").child(candidateUID).removeValue();
        database.getReference("users").child(candidateUID).removeValue();

        Log.d("CANDIDATE DAO", "Deleting candidate: " + candidateUID);
    }

    private void loadCandidates() {
        DatabaseReference candidatesReference = database.getReference("candidates");

        candidatesReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                candidatesList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Candidate candidate = dataSnapshot.getValue(Candidate.class);
                    candidatesList.add(candidate);
                }
                Log.d("CANDIDATE DAO", "Read " + candidatesList.size() + " candidates");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Log.d("CANDIDATE DAO", "Loaded " + candidatesList.size() + " candidates");
    }
}
