package com.serediuk.bander_client.ui.profile;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.auth.FirebaseAuth;
import com.serediuk.bander_client.BandEditActivity;
import com.serediuk.bander_client.model.entity.Band;
import com.serediuk.bander_client.model.entity.User;
import com.serediuk.bander_client.model.enums.UserType;
import com.serediuk.bander_client.ui.LoginRegisterActivity;
import com.serediuk.bander_client.ProfileEditActivity;
import com.serediuk.bander_client.R;
import com.serediuk.bander_client.databinding.FragmentProfileBinding;
import com.serediuk.bander_client.model.entity.Candidate;

import java.util.Objects;

public class ProfileFragment extends Fragment {
    private ProfileViewModel profileViewModel;
    private ConstraintLayout candidateConstraintLayout;
    private ConstraintLayout bandConstraintLayout;

    private FragmentProfileBinding binding;

    private FirebaseAuth.AuthStateListener firebaseAuthStateListener;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);

        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        candidateConstraintLayout = binding.candidateLayout;
        bandConstraintLayout = binding.bandLayout;

        loadData();
        init();

        return root;
    }

    @SuppressLint("SetTextI18n")
    private void loadData() {
        User user = profileViewModel.getUser();
        while (user.getType() == null) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        if (Objects.equals(user.getType(), UserType.CANDIDATE.toString())) {
            candidateConstraintLayout.setVisibility(View.VISIBLE);
            bandConstraintLayout.setVisibility(View.INVISIBLE);

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
                mExperience.setText(R.string.text_experience_title + ": " + candidate.getExperience());
            }
            if (candidate.getAbout() != null) {
                TextView mAbout = binding.profileAbout;
                mAbout.setText(candidate.getAbout());
            }
            if (candidate.getRole() != null) {
                TextView mRole = binding.profileRoles;
                mRole.setText(candidate.getRole());
            }
            if (candidate.getPreferredGenres() != null) {
                TextView mPreferredGenres = binding.profileGenres;
                mPreferredGenres.setText(candidate.getPreferredGenres());
            }
            if (candidate.getVideoLinks()!= null) {
                TextView mVideoLinks = binding.profileLinks;
                mVideoLinks.setText(candidate.getVideoLinks());
            }
        } else if (Objects.equals(user.getType(), UserType.BAND.toString())) {
            candidateConstraintLayout.setVisibility(View.INVISIBLE);
            bandConstraintLayout.setVisibility(View.VISIBLE);

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
            if (band.getAbout() != null) {
                TextView mAbout = binding.bandAbout;
                mAbout.setText(band.getAbout());
            }
            if (band.getMembersID() != null) {
                TextView mMembers = binding.bandMembers;
                mMembers.setText(band.getMembersID().toString());
            }
            if (band.getVideoLinks() != null) {
                TextView mVideoLinks = binding.bandLinks;
                mVideoLinks.setText(band.getVideoLinks());
            }
        }
    }

    private void init() {
        firebaseAuthStateListener = firebaseAuth -> {
            try {
                Intent intent = new Intent(requireActivity(), LoginRegisterActivity.class);
                startActivity(intent);
                requireActivity().finish();
            } catch (IllegalStateException e) {
                Log.e("INTENT ERROR", "Can't find requireActivity()");
            }
        };

        ImageButton mSignOut = binding.signOutImageButton;
        mSignOut.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
            builder
                    .setTitle(R.string.text_to_sign_out_title)
                    .setMessage(R.string.text_to_sign_out_message)
                    .setPositiveButton(R.string.yes, (dialog, which) -> {
                        profileViewModel.addAuthStateListener(firebaseAuthStateListener);
                        profileViewModel.signOut();
                        Intent intent = new Intent(requireActivity(), LoginRegisterActivity.class);
                        startActivity(intent);
                        requireActivity().finish();
                    })
                    .setNegativeButton(R.string.no, (dialog, which) -> {})
                    .show();
        });

        ImageButton mEdit = binding.editImageButton;
        mEdit.setOnClickListener(v -> {
            Intent intent = new Intent(requireActivity(), ProfileEditActivity.class);
            startActivity(intent);
            requireActivity().finish();
        });

        ImageButton mBandSighOut = binding.bandSignOutImageButton;
        mBandSighOut.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
            builder
                    .setTitle(R.string.text_to_sign_out_title)
                    .setMessage(R.string.text_to_sign_out_message)
                    .setPositiveButton(R.string.yes, (dialog, which) -> {
                        profileViewModel.addAuthStateListener(firebaseAuthStateListener);
                        profileViewModel.signOut();
                        Intent intent = new Intent(requireActivity(), LoginRegisterActivity.class);
                        startActivity(intent);
                        requireActivity().finish();
                    })
                    .setNegativeButton(R.string.no, (dialog, which) -> {})
                    .show();
        });

        ImageButton mBandEdit = binding.bandEditImageButton;
        mBandEdit.setOnClickListener(v -> {
            Intent intent = new Intent(requireActivity(), BandEditActivity.class);
            startActivity(intent);
            requireActivity().finish();
        });
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