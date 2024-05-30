package com.serediuk.bander_client.ui.home;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;


import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.FirebaseDatabase;
import com.serediuk.bander_client.R;
import com.serediuk.bander_client.auth.AuthUID;
import com.serediuk.bander_client.databinding.FragmentHomeBinding;
import com.serediuk.bander_client.model.entity.Band;
import com.serediuk.bander_client.model.entity.Candidate;
import com.serediuk.bander_client.model.entity.User;
import com.serediuk.bander_client.model.enums.UserType;
import com.serediuk.bander_client.ui.MainActivity;

import java.util.Objects;


public class HomeFragment extends Fragment {
    private HomeViewModel homeViewModel;
    private TextView homeProfileName, homeProfileGenres, homeProfileRoles;

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        init();
        loadData();

        return root;
    }

    private void init() {
        homeProfileName = binding.homeProfileName;
        homeProfileGenres = binding.homeProfileGenres;
        homeProfileRoles = binding.homeProfileRole;
    }

    @SuppressLint("SetTextI18n")
    private void loadData() {
        User user = homeViewModel.getUser();
        if (user != null) {
            if (Objects.equals(user.getType(), UserType.BAND.toString())) {
                homeProfileRoles.setVisibility(View.INVISIBLE);

                Band band = homeViewModel.getBand();
                homeProfileName.setText(band.getName());
                homeProfileGenres.setText(band.getGenres());
            } else {
                homeProfileRoles.setVisibility(View.VISIBLE);

                Candidate candidate = homeViewModel.getCandidate();
                homeProfileName.setText(candidate.getName() + " " + candidate.getSurname());
                homeProfileGenres.setText(candidate.getPreferredGenres());
                homeProfileRoles.setText(candidate.getRole());
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}