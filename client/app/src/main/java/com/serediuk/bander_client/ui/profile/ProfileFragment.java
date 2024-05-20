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
import com.serediuk.bander_client.LoginRegisterActivity;
import com.serediuk.bander_client.ProfileEditActivity;
import com.serediuk.bander_client.R;
import com.serediuk.bander_client.databinding.FragmentProfileBinding;
import com.serediuk.bander_client.model.entity.Candidate;
import com.serediuk.bander_client.model.entity.User;

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

        Candidate candidate = profileViewModel.getCandidate();
        Log.d("PROFILE", "Loaded candidate:\n" + candidate);
        TextView mName = binding.profileNameTextView;
        mName.setText(candidate.getName());
//        TextView mSurname = binding.profileSurnameTextView;
//        mSurname.setText(user.getSurname());
//        TextView mBirthday = binding.profileBirthdayTextView;
//        mBirthday.setText(user.getBirthday());
//        TextView mCity = binding.profileCityTextView;
//        mCity.setText(user.getCity());
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