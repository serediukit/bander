package com.serediuk.bander_client.ui.profile;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
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
import com.serediuk.bander_client.model.dao.CandidatesDAO;
import com.serediuk.bander_client.model.entity.Candidate;
import com.serediuk.bander_client.model.storage.ImageStorageProvider;
import com.serediuk.bander_client.ui.MainActivity;
import com.serediuk.bander_client.util.image.ImageOptions;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class ProfileEditActivity extends AppCompatActivity {
    private EditText mName, mSurname, mBirthday, mCity;
    private EditText mExperience, mAbout, mRoles, mPreferredGenres, mVideoLinks;
    private ImageView mProfileImage;
    private ActivityResultLauncher<Intent> resultLauncher;
    private Uri profileImageUri;

    private ImageStorageProvider imageStorageProvider;
    private CandidatesDAO candidatesDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);

        imageStorageProvider = ImageStorageProvider.getInstance();
        candidatesDAO = CandidatesDAO.getInstance();
        init();
        loadData();
    }

    private void init() {
        mName = findViewById(R.id.profileEditTextName);
        mSurname = findViewById(R.id.profileEditTextSurname);
        mBirthday = findViewById(R.id.profileEditTextBirthday);
        mCity = findViewById(R.id.profileEditTextCity);
        mExperience = findViewById(R.id.profileEditTextExperience);
        mAbout = findViewById(R.id.profileEditTextAbout);
        mRoles = findViewById(R.id.profileEditTextRoles);
        mPreferredGenres = findViewById(R.id.profileEditTextGenres);
        mVideoLinks = findViewById(R.id.profileEditTextLinks);

        mProfileImage = findViewById(R.id.profileImageButton);

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
                        Log.e("PROFILE EDIT", "Can't to pick the image");
                    }
                }
        );
    }

    private void loadData() {
        Candidate candidate = candidatesDAO.readCandidate(AuthProvider.getInstance().getUid());

        if (candidate != null) {
            if (!candidate.getName().isEmpty())
                mName.setText(candidate.getName());
            if (!candidate.getSurname().isEmpty())
                mSurname.setText(candidate.getSurname());
            if (!candidate.getBirthday().isEmpty())
                mBirthday.setText(candidate.getBirthday());
            if (!candidate.getCity().isEmpty())
                mCity.setText(candidate.getCity());
            if (!candidate.getExperience().isEmpty())
                mExperience.setText(candidate.getExperience());
            if (!candidate.getAbout().isEmpty())
                mAbout.setText(candidate.getAbout());
            if (!candidate.getRole().isEmpty())
                mRoles.setText(candidate.getRole());
            if (!candidate.getPreferredGenres().isEmpty())
                mPreferredGenres.setText(candidate.getPreferredGenres());
            if (!candidate.getVideoLinks().isEmpty())
                mVideoLinks.setText(candidate.getVideoLinks());
        }

        Uri profileImage = imageStorageProvider.downloadImageUri(AuthUID.getUID());
        Glide
                .with(this)
                .load(profileImage)
                .apply(ImageOptions.imageOptions())
                .into(mProfileImage);

        Date currentDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.add(Calendar.YEAR, -12);
        transformIntoDatePicker(mBirthday, this, calendar.getTime());
    }

    private static void transformIntoDatePicker(EditText editText, Context context, Date maxDate) {
        editText.setFocusableInTouchMode(false);
        editText.setClickable(true);
        editText.setFocusable(false);

        final Calendar myCalendar = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener datePickerOnDataSetListener =
                (view, year, monthOfYear, dayOfMonth) -> {
                    myCalendar.set(Calendar.YEAR, year);
                    myCalendar.set(Calendar.MONTH, monthOfYear);
                    myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy", Locale.UK);
                    editText.setText(sdf.format(myCalendar.getTime()));
                    Log.d("Auth", "Date picked: " + sdf.format(myCalendar.getTime()));
                };

        editText.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    context, datePickerOnDataSetListener, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)
            );
            if (maxDate != null) {
                datePickerDialog.getDatePicker().setMaxDate(maxDate.getTime());
            }
            datePickerDialog.show();
        });
    }

    public void save(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ProfileEditActivity.this);
        builder
                .setTitle(R.string.text_to_sign_out_title)
                .setMessage(R.string.text_to_save_message)
                .setPositiveButton(R.string.yes, (dialog, which) -> {
                    String name = mName.getText().toString();
                    String surname = mSurname.getText().toString();
                    String birthday = mBirthday.getText().toString();
                    String city = mCity.getText().toString();
                    String experience = mExperience.getText().toString();
                    String about = mAbout.getText().toString();
                    String roles = mRoles.getText().toString();
                    String preferredGenres = mPreferredGenres.getText().toString();
                    String videoLinks = mVideoLinks.getText().toString();

                    if (name.isEmpty() || surname.isEmpty() || birthday.isEmpty() || city.isEmpty()) {
                        Toast.makeText(ProfileEditActivity.this, "Fill all required fields", Toast.LENGTH_SHORT).show();
                        Log.d("PROFILE EDIT", "Some fields are empty");
                        return;
                    }

                    Candidate oldCandidate = candidatesDAO.readCandidate(AuthProvider.getInstance().getUid());
                    Candidate newCandidate = new Candidate(
                            oldCandidate.getCandidateUID(),
                            oldCandidate.getEmail(),
                            name,
                            surname,
                            birthday,
                            city,
                            about,
                            roles,
                            preferredGenres,
                            experience,
                            videoLinks);
                    candidatesDAO.updateCandidate(newCandidate);

                    imageStorageProvider.uploadImage(this, AuthUID.getUID(), profileImageUri);

                    Intent intent = new Intent(ProfileEditActivity.this, MainActivity.class);
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

    @Override
    protected void onRestart() {
        super.onRestart();
        loadData();
    }
}