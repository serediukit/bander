package com.serediuk.bander_client.ui.home;

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
import androidx.navigation.Navigation;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.serediuk.bander_client.R;
import com.serediuk.bander_client.databinding.FragmentHomeBinding;
import com.serediuk.bander_client.model.dao.*;
import com.serediuk.bander_client.model.entity.User;
import com.serediuk.bander_client.ui.profile.ProfileFragment;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    HomeViewModel homeViewModel;
    ConstraintLayout profileLayout, chatsLayout, notificationsLayout, searchLayout;

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

//        init();

        return root;
    }

//    private void init() {
//        profileLayout = binding.homeProfileLayout;
//        profileLayout.setOnClickListener(v -> {
//            Navigation.findNavController(v).navigate(R.id.navigation_profile);
//        });
//
//        chatsLayout = binding.homeChatsLayout;
//
//        notificationsLayout = binding.homeNotificationsLayout;
//
//        searchLayout = binding.homeSearchLayout;
//    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}