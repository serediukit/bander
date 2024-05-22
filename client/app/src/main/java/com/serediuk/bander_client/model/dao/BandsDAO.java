package com.serediuk.bander_client.model.dao;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.serediuk.bander_client.model.DatabaseConnectionProvider;
import com.serediuk.bander_client.model.entity.Band;
import com.serediuk.bander_client.model.entity.Band;

import java.util.ArrayList;

public class BandsDAO {
    private static BandsDAO instance;
    private final FirebaseDatabase database;

    private final ArrayList<Band> bandsList;

    private BandsDAO() {
        bandsList = new ArrayList<>();

        database = DatabaseConnectionProvider.getInstance().getDatabase();
        loadBands();
    }

    public static BandsDAO getInstance() {
        if (instance == null) {
            instance = new BandsDAO();
        }
        return instance;
    }

    public void createBand(Band band) {
        if (band.getBandUID() != null) {
            database.getReference("bands").child(band.getBandUID()).setValue(band);
            database.getReference("users").child(band.getBandUID()).setValue(band.getUser());

            Log.d("BAND DAO", "Adding band:\n" + band);
        } else {
            Log.d("BAND DAO", "Key is null");
        }
    }

    public Band readBand(String bandUID) {
        Log.d("BAND DAO", "Reading band: " + bandUID);
        if (bandsList.size() != 0) {
            for (Band band : bandsList) {
                if (band.getBandUID().equals(bandUID)) {
                    return band;
                }
            }
        }
        return null;
    }

    public void updateBand(Band band) {
        database.getReference("bands").child(band.getBandUID()).setValue(band);
        database.getReference("users").child(band.getBandUID()).setValue(band.getUser());

        for (int i = 0; i < bandsList.size(); i++) {
            if (bandsList.get(i).getBandUID().equals(band.getBandUID())) {
                bandsList.set(i, band);
                break;
            }
        }

        Log.d("BAND DAO", "Updating band:\n" + band);
    }

    public void loadBands() {
        DatabaseReference bandsReference = database.getReference("bands");

        bandsReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                bandsList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Band band = dataSnapshot.getValue(Band.class);
                    bandsList.add(band);
                }
                Log.d("BAND DAO", "Read " + bandsList.size() + " bands");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Log.d("BAND DAO", "Loaded " + bandsList.size() + " bands");
    }
}
