package com.serediuk.bander_client.ui.profile;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;
import com.google.firebase.auth.FirebaseAuth;
import com.serediuk.bander_client.databinding.FragmentProfileBinding;
import com.serediuk.bander_client.model.entity.Band;
import com.serediuk.bander_client.model.entity.User;
import com.serediuk.bander_client.model.enums.UserType;
import com.serediuk.bander_client.model.storage.ImageStorageProvider;
import com.serediuk.bander_client.ui.auth.LoginRegisterActivity;
import com.serediuk.bander_client.R;
import com.serediuk.bander_client.model.entity.Candidate;
import com.serediuk.bander_client.util.image.ImageOptions;
import com.serediuk.bander_client.util.string.ArrayListStringCreator;

import java.util.Objects;

public class ProfileFragment extends Fragment {
    private ProfileViewModel profileViewModel;
    private ScrollView candidateConstraintLayout;
    private ScrollView bandConstraintLayout;
    private Button mEdit, mBandEdit, mSignOut;

    private FragmentProfileBinding binding;

    private FirebaseAuth.AuthStateListener firebaseAuthStateListener;
    private ImageStorageProvider imageStorageProvider;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);

        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        imageStorageProvider = ImageStorageProvider.getInstance();

        init();
        loadData();

        return root;
    }

    private void init() {
        candidateConstraintLayout = binding.candidateLayout;
        bandConstraintLayout = binding.bandLayout;

        firebaseAuthStateListener = firebaseAuth -> {
            try {
                Intent intent = new Intent(requireActivity(), LoginRegisterActivity.class);
                startActivity(intent);
                requireActivity().finish();
            } catch (IllegalStateException e) {
                Log.e("INTENT ERROR", "Can't find requireActivity()");
            }
        };

        mSignOut = binding.signOutButton;
        mSignOut.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
            builder
                    .setTitle(R.string.text_to_sign_out_title)
                    .setMessage(R.string.text_to_sign_out_message)
                    .setPositiveButton(R.string.yes, (dialog, which) -> {
                        Log.d("PROFILE", "Signed out");
                        profileViewModel.addAuthStateListener(firebaseAuthStateListener);
                        profileViewModel.signOut();
                        Intent intent = new Intent(requireActivity(), LoginRegisterActivity.class);
                        startActivity(intent);
                        requireActivity().finish();
                    })
                    .setNegativeButton(R.string.no, (dialog, which) -> {})
                    .show();
        });

        mEdit = binding.editButton;
        mEdit.setOnClickListener(v -> {
            Intent intent = new Intent(requireActivity(), ProfileEditActivity.class);
            startActivity(intent);
        });

        mBandEdit = binding.bandEditButton;
        mBandEdit.setOnClickListener(v -> {
            Intent intent = new Intent(requireActivity(), BandEditActivity.class);
            startActivity(intent);
        });

        TextView profileVideoLink = binding.profileLinks;
        profileVideoLink.setOnClickListener(v -> {
            String link = profileVideoLink.getText().toString();
            openVideo(link);
        });

        TextView bandVideoLink = binding.bandLinks;
        profileVideoLink.setOnClickListener(v -> {
            String link = bandVideoLink.getText().toString();
            openVideo(link);
        });
    }

    @SuppressLint("SetTextI18n")
    private void loadData() {
        User user = profileViewModel.getUser();
        Log.d("PROFILE", "User type: " + user.getType());
        if (Objects.equals(user.getType(), UserType.CANDIDATE.toString())) {
            candidateConstraintLayout.setVisibility(View.VISIBLE);
            bandConstraintLayout.setVisibility(View.INVISIBLE);
            mEdit.setVisibility(View.VISIBLE);
            mEdit.setHeight(mSignOut.getHeight());
            mBandEdit.setVisibility(View.INVISIBLE);
            mBandEdit.setHeight(0);

            ImageView mProfileImage = binding.accountImageView;
            Uri imageUri = imageStorageProvider.downloadImageUri(user.getUid());
            Glide
                    .with(requireActivity())
                    .load(imageUri)
                    .apply(ImageOptions.imageOptions())
                    .into(mProfileImage);

            Candidate candidate = profileViewModel.getCandidate();
            Log.d("PROFILE", "Loaded candidate:\n" + candidate);

            if (candidate.getName() != null) {
                TextView mName = binding.profileNameTextView;
                mName.setText(candidate.getName());
            }
            if (candidate.getSurname() != null) {
                TextView mSurname = binding.profileSurnameTextView;
                mSurname.setText(candidate.getSurname());
            }
            if (candidate.getBirthday() != null) {
                TextView mBirthday = binding.profileBirthdayTextView;
                mBirthday.setText(candidate.getBirthday());
            }
            if (candidate.getCity() != null) {
                TextView mCity = binding.profileCityTextView;
                mCity.setText(candidate.getCity());
            }
            if (candidate.getExperience() != null) {
                TextView mExperience = binding.profileExperience;
                String experienceText = getResources().getString(R.string.text_experience_title);
                mExperience.setText(experienceText + ": " + candidate.getExperience());
            }
            if (candidate.getAbout() != null && !candidate.getAbout().isEmpty()) {
                TextView mAbout = binding.profileAbout;
                mAbout.setText(candidate.getAbout());
            }
            if (candidate.getRole() != null && !candidate.getRole().isEmpty()) {
                TextView mRole = binding.profileRoles;
                mRole.setText(candidate.getRole());
            }
            if (candidate.getPreferredGenres() != null && !candidate.getPreferredGenres().isEmpty()) {
                TextView mPreferredGenres = binding.profileGenres;
                mPreferredGenres.setText(candidate.getPreferredGenres());
            }
            if (candidate.getVideoLinks()!= null && !candidate.getVideoLinks().isEmpty()) {
                TextView mVideoLinks = binding.profileLinks;
                mVideoLinks.setText(candidate.getVideoLinks());
            }
        } else if (Objects.equals(user.getType(), UserType.BAND.toString())) {
            candidateConstraintLayout.setVisibility(View.INVISIBLE);
            bandConstraintLayout.setVisibility(View.VISIBLE);
            mBandEdit.setVisibility(View.VISIBLE);
            mBandEdit.setHeight(mSignOut.getHeight());
            mEdit.setVisibility(View.INVISIBLE);
            mEdit.setHeight(0);

            ImageView mProfileImage = binding.bandAccountImageView;
            Uri imageUri = imageStorageProvider.downloadImageUri(user.getUid());
            Glide
                    .with(requireActivity())
                    .load(imageUri)
                    .apply(ImageOptions.imageOptions())
                    .into(mProfileImage);

            Band band = profileViewModel.getBand();
            Log.d("PROFILE", "Loaded band:\n" + band);

            if (band.getName() != null) {
                TextView mName = binding.bandNameTextView;
                mName.setText(band.getName());
            }
            if (band.getCity() != null) {
                TextView mCity = binding.bandCityTextView;
                mCity.setText(band.getCity());
            }
            if (band.getGenres() != null) {
                TextView mGenres = binding.bandGenresTextView;
                mGenres.setText(band.getGenres());
            }
            if (band.getAbout() != null && !band.getAbout().isEmpty()) {
                TextView mAbout = binding.bandAbout;
                mAbout.setText(band.getAbout());
            }
//            if (band.getMembersID() != null && !band.getMembersID().isEmpty()) {
//                TextView mMembers = binding.bandMembers;
//                mMembers.setText(ArrayListStringCreator.getStringsFromArrayList(band.getMembersID()));
//            }
            if (band.getVideoLinks() != null && !band.getVideoLinks().isEmpty()) {
                TextView mVideoLinks = binding.bandLinks;
                mVideoLinks.setText(band.getVideoLinks());
            }
        }
    }

    private void openVideo(String link) {
        if (link.startsWith("http")) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setPackage("com.google.android.youtube");

            try {
                startActivity(intent);
            } catch (ActivityNotFoundException e) {
                intent.setPackage(null);
                startActivity(intent);
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onResume() {
        super.onResume();

        loadData();
    }
}