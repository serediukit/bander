package com.serediuk.bander_client.ui.profile;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.serediuk.bander_client.R;
import com.serediuk.bander_client.auth.AuthProvider;
import com.serediuk.bander_client.auth.AuthUID;
import com.serediuk.bander_client.model.dao.BandsDAO;
import com.serediuk.bander_client.model.entity.Band;
import com.serediuk.bander_client.model.storage.ImageStorageProvider;
import com.serediuk.bander_client.ui.MainActivity;
import com.serediuk.bander_client.util.image.ImageOptions;

import java.util.Objects;

public class BandEditActivity extends AppCompatActivity {
    EditText mName, mCity, mGenres;
    EditText mAbout, mVideoLinks;
    private ImageView mProfileImage;
    private ActivityResultLauncher<Intent> resultLauncher;
    private Uri profileImageUri;

    private ImageStorageProvider imageStorageProvider;
    private BandsDAO bandsDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_band_edit);

        imageStorageProvider = ImageStorageProvider.getInstance();
        bandsDAO = BandsDAO.getInstance();
        init();
        loadData();
    }

    private void init() {
        mName = findViewById(R.id.bandEditTextName);
        mCity = findViewById(R.id.bandEditTextCity);
        mGenres = findViewById(R.id.bandEditTextGenres);
        mAbout = findViewById(R.id.bandEditTextAbout);
        mVideoLinks = findViewById(R.id.bandEditTextLinks);

        mProfileImage = findViewById(R.id.bandImageButton);

        mProfileImage.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            resultLauncher.launch(intent);
        });

        resultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                activityResult -> {
                    try {
                        Uri imageUri = Objects.requireNonNull(activityResult.getData()).getData();
                        mProfileImage.setImageURI(imageUri);
                        profileImageUri = imageUri;
                    } catch (Exception e) {
                        Log.e("BAND EDIT", "Can't to pick the image");
                    }
                }
        );
    }

    private void loadData() {
        Band band = bandsDAO.readBand(AuthProvider.getInstance().getUid());

        if (band != null) {
            if (!band.getName().isEmpty())
                mName.setText(band.getName());
            if (!band.getCity().isEmpty())
                mCity.setText(band.getCity());
            if (!band.getGenres().isEmpty())
                mGenres.setText(band.getGenres());
            if (!band.getAbout().isEmpty())
                mAbout.setText(band.getAbout());
            if (!band.getVideoLinks().isEmpty())
                mVideoLinks.setText(band.getVideoLinks());
        }

        Uri profileImage = imageStorageProvider.downloadImageUri(AuthUID.getUID());
        Glide
                .with(this)
                .load(profileImage)
                .apply(ImageOptions.imageOptions())
                .into(mProfileImage);
    }

    public void save(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(BandEditActivity.this);
        builder
                .setTitle(R.string.text_to_sign_out_title)
                .setMessage(R.string.text_to_save_message)
                .setPositiveButton(R.string.yes, (dialog, which) -> {
                    String name = mName.getText().toString();
                    String city = mCity.getText().toString();
                    String genres = mGenres.getText().toString();
                    String about = mAbout.getText().toString();
                    String videoLinks = mVideoLinks.getText().toString();

                    if (name.isEmpty() || city.isEmpty()) {
                        Toast.makeText(BandEditActivity.this, "Fill all required fields", Toast.LENGTH_SHORT).show();
                        Log.d("BAND EDIT", "Some fields are empty");
                        return;
                    }

                    Band oldBand = bandsDAO.readBand(AuthProvider.getInstance().getUid());
                    Band newBand = new Band(
                            oldBand.getBandUID(),
                            oldBand.getEmail(),
                            name,
                            city,
                            genres,
                            about,
                            videoLinks,
                            oldBand.getMembersID());
                    bandsDAO.updateBand(newBand);

                    if (profileImageUri != null)
                        imageStorageProvider.uploadImage(this, AuthUID.getUID(), profileImageUri);

                    Intent intent = new Intent(BandEditActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                })
                .setNegativeButton(R.string.no, (dialog, which) -> {})
                .show();
    }

    public void cancel(View view) {
        profileImageUri = null;
        finish();
    }
}