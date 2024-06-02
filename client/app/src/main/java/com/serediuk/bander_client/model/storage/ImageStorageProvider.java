package com.serediuk.bander_client.model.storage;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.serediuk.bander_client.auth.AuthUID;
import com.serediuk.bander_client.model.dao.ImagesDAO;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ImageStorageProvider {
    private static ImageStorageProvider instance;

    private static FirebaseStorage storage;

    private ImageStorageProvider() {
        storage = FirebaseStorage.getInstance("gs://bander-63922.appspot.com");
        Log.d("STORAGE", "Image Storage Provider initialized");
    }

    public static ImageStorageProvider getInstance() {
        if (instance == null) {
            instance = new ImageStorageProvider();
        }
        return instance;
    }

    public void uploadImage(Context context, String userUid, Uri imageUri) {
        StorageReference filepath = storage.getReference().child("profileImages").child(userUid);
        Log.d("STORAGE", "Filepath reference: " + filepath);
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), imageUri);

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 20, byteArrayOutputStream);

            byte[] imageData = byteArrayOutputStream.toByteArray();
            UploadTask uploadTask = filepath.putBytes(imageData);
            uploadTask.addOnSuccessListener(taskSnapshot -> {
                Uri downloadUri = taskSnapshot.getUploadSessionUri();
                Log.d("STORAGE", "Image Uri: " + downloadUri);
            });
            uploadTask.addOnFailureListener(e -> {
                Log.e("STORAGE", "Can't upload the image for user " + userUid);
            });
        } catch (IOException e) {
            Log.e("STORAGE", "Can't upload the image for user " + userUid);
        }
    }

    public Uri downloadImageUri(String userUid) {
        Uri imageUri = Uri.parse("https://firebasestorage.googleapis.com/v0/b/bander-63922.appspot.com/o/profileImages%2F" + userUid + "?alt=media");
        Log.d("STORAGE", "Download Image Uri: " + imageUri);
        return imageUri;
    }
}
