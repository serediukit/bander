package com.serediuk.bander_client.model.dao;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.serediuk.bander_client.model.DatabaseConnectionProvider;
import com.serediuk.bander_client.ui.auth.LoginRegisterActivity;

import java.util.HashMap;
import java.util.Map;

public class ImagesDAO {
    private static ImagesDAO instance;
    private FirebaseDatabase database;
    private Map<String, Uri> usersImages;

    private ImagesDAO() {
        usersImages = new HashMap<>();

        database = DatabaseConnectionProvider.getInstance().getDatabase();
        loadImages();
    }

    public static ImagesDAO getInstance() {
        if (instance == null) {
            instance = new ImagesDAO();
        }
        return instance;
    }

    public void updateImage(String userUid, Uri imageFilepath) {
        database.getReference("images").child(userUid).setValue(imageFilepath);

        Log.d("IMAGE DAO", "Adding image filepath: {"
                + "\nuserUid: " + userUid
                + "\nimageFilepath: " + imageFilepath
                + "\n}"
        );
    }

    public void loadImages() {
        DatabaseReference imagesReference = database.getReference("images");

        imagesReference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                usersImages.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String userUid = dataSnapshot.getKey();
                    String image = dataSnapshot.getValue(String.class);

                    usersImages.put(userUid, Uri.parse(image));
                }
                Log.d("IMAGE DAO", "Read " + usersImages.size() + " images");

                LoginRegisterActivity.incrementCount();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Log.d("IMAGE DAO", "Loaded " + usersImages.size() + " images");
    }
}
