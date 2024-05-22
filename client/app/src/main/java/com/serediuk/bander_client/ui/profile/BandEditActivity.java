package com.serediuk.bander_client.ui.profile;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.serediuk.bander_client.R;
import com.serediuk.bander_client.auth.AuthProvider;
import com.serediuk.bander_client.model.dao.BandsDAO;
import com.serediuk.bander_client.model.entity.Band;
import com.serediuk.bander_client.ui.MainActivity;

public class BandEditActivity extends AppCompatActivity {
    EditText mName, mCity, mGenres;
    EditText mAbout, mVideoLinks;

    BandsDAO bandsDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_band_edit);

        bandsDAO = BandsDAO.getInstance();
        init();
    }

    private void init() {
        mName = findViewById(R.id.bandEditTextName);
        mCity = findViewById(R.id.bandEditTextCity);
        mGenres = findViewById(R.id.bandEditTextGenres);
        mAbout = findViewById(R.id.bandEditTextAbout);
        mVideoLinks = findViewById(R.id.bandEditTextLinks);

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

                    Intent intent = new Intent(BandEditActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                })
                .setNegativeButton(R.string.no, (dialog, which) -> {})
                .show();
    }

    public void cancel(View view) {
        Intent intent = new Intent(BandEditActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}