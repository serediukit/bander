package com.serediuk.bander_client.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.auth.FirebaseAuth;
import com.serediuk.bander_client.LoginRegisterActivity;
import com.serediuk.bander_client.databinding.FragmentProfileBinding;

public class ProfileFragment extends Fragment {
    private FragmentProfileBinding binding;

    private FirebaseAuth.AuthStateListener firebaseAuthStateListener;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        ProfileViewModel profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);

        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textProfile;
        profileViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        firebaseAuthStateListener = firebaseAuth -> {
            Intent intent = new Intent(requireActivity(), LoginRegisterActivity.class);
            startActivity(intent);
            requireActivity().finish();
        };

        Button mSignOut = binding.signoutButton;

        mSignOut.setOnClickListener(v -> {
            profileViewModel.addAuthStateListener(firebaseAuthStateListener);
            profileViewModel.signOut();
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}