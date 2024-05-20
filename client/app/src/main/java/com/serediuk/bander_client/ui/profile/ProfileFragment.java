package com.serediuk.bander_client.ui.profile;

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
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.auth.FirebaseAuth;
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

    private FragmentProfileBinding binding;

    private FirebaseAuth.AuthStateListener firebaseAuthStateListener;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);

        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        loadData();
        init();

        return root;
    }

    private void loadData() {
        User user = profileViewModel.getUser();
        if (Objects.equals(user.getType(), UserType.CANDIDATE.toString())) {
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
        } else if (Objects.equals(user.getType(), UserType.BAND.toString())) {
            // TODO: 21.05.24  
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
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}