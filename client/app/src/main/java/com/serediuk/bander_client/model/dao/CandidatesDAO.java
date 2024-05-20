package com.serediuk.bander_client.model.dao;

import android.util.Log;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.serediuk.bander_client.model.DatabaseConnectionProvider;
import com.serediuk.bander_client.model.entity.Candidate;

import org.checkerframework.checker.units.qual.C;

import java.util.ArrayList;

public class CandidatesDAO {
    private static CandidatesDAO instance;
    private FirebaseDatabase database;

    private ArrayList<Candidate> candidatesList;

    private CandidatesDAO() {
        database = DatabaseConnectionProvider.getInstance().getDatabase();
        readCandidates();
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

    private void readCandidates() {
        candidatesList = new ArrayList<>();
        final FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference candidatesReference = database.getReference("candidates");

        candidatesReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Candidate candidate = snapshot.getValue(Candidate.class);
                    candidatesList.add(candidate);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Log.d("CANDIDATE DAO", "Reading candidates: " + candidatesList.size() + " candidates");
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

    public ArrayList<Candidate> getAllCandidates() {
        return candidatesList;
    }
}
