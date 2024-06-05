package com.serediuk.bander_client.ui.home;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;


import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.FirebaseDatabase;
import com.serediuk.bander_client.R;
import com.serediuk.bander_client.auth.AuthUID;
import com.serediuk.bander_client.databinding.FragmentHomeBinding;
import com.serediuk.bander_client.model.entity.Band;
import com.serediuk.bander_client.model.entity.Candidate;
import com.serediuk.bander_client.model.entity.Resume;
import com.serediuk.bander_client.model.entity.User;
import com.serediuk.bander_client.model.entity.Vacancy;
import com.serediuk.bander_client.model.enums.UserType;
import com.serediuk.bander_client.model.storage.ImageStorageProvider;
import com.serediuk.bander_client.ui.MainActivity;
import com.serediuk.bander_client.ui.chats.ChatsFragment;
import com.serediuk.bander_client.ui.notifications.NotificationsFragment;
import com.serediuk.bander_client.ui.profile.ProfileFragment;
import com.serediuk.bander_client.util.image.ImageOptions;

import java.util.Objects;


public class HomeFragment extends Fragment {
    private HomeViewModel homeViewModel;
    private TextView homeProfileName, homeProfileGenres, homeProfileRoles;
    private TextView homeNewMessagesCount, homeNewNotificationsCount;
    private ConstraintLayout profileLayout, chatsLayout, notificationsLayout, searchLayout;
    private ConstraintLayout resumeLayout, vacancyLayout;
    private TextView searchTitle, emptySearchText;
    private ImageView homeProfileImage;

    private ImageStorageProvider imageStorageProvider;

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        imageStorageProvider = ImageStorageProvider.getInstance();

        init();
        setTransitions();
        loadData();
        loadSearch();

        return root;
    }

    private void init() {
        homeNewMessagesCount = binding.homeNewMessagesCount;
        homeNewNotificationsCount = binding.homeNewNotificationsCount;

        resumeLayout = binding.homeResumeLayout;
        vacancyLayout = binding.homeVacancyLayout;
        searchTitle = binding.homeSearchTitle;
        emptySearchText = binding.homeSearchEmptyText;
    }

    private void setTransitions() {
        homeProfileName = binding.homeProfileName;
        homeProfileGenres = binding.homeProfileGenres;
        homeProfileRoles = binding.homeProfileRole;
        homeProfileImage = binding.homeProfileImage;

        profileLayout = binding.homeProfileLayout;
        profileLayout.setOnClickListener(v -> {
            Fragment profileFragment = new ProfileFragment();
            FragmentTransaction ft = requireActivity().getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.nav_host_fragment_activity_main, profileFragment).commit();
            BottomNavigationView navView = requireActivity().findViewById(R.id.nav_view);
            navView.setSelectedItemId(R.id.navigation_profile);
        });

        chatsLayout = binding.homeChatsLayout;
        chatsLayout.setOnClickListener(v -> {
            Fragment chatsFragment = new ChatsFragment();
            FragmentTransaction ft = requireActivity().getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.nav_host_fragment_activity_main, chatsFragment).commit();
            BottomNavigationView navView = requireActivity().findViewById(R.id.nav_view);
            navView.setSelectedItemId(R.id.navigation_chats);
        });

        notificationsLayout = binding.homeNotificationsLayout;
        notificationsLayout.setOnClickListener(v -> {
            Fragment notificationFragment = new NotificationsFragment();
            FragmentTransaction ft = requireActivity().getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.nav_host_fragment_activity_main, notificationFragment).commit();
            BottomNavigationView navView = requireActivity().findViewById(R.id.nav_view);
            navView.setSelectedItemId(R.id.navigation_notifications);
        });

        searchLayout = binding.homeSearchLayout;
        searchLayout.setOnClickListener(v -> {
            Fragment searchFragment = new ChatsFragment();
            FragmentTransaction ft = requireActivity().getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.nav_host_fragment_activity_main, searchFragment).commit();
            BottomNavigationView navView = requireActivity().findViewById(R.id.nav_view);
            navView.setSelectedItemId(R.id.navigation_search);
        });
    }

    @SuppressLint("SetTextI18n")
    private void loadData() {
        User user = homeViewModel.getUser();
        if (user != null) {
            Uri imageUri = imageStorageProvider.downloadImageUri(user.getUid());
            Glide
                    .with(requireActivity())
                    .load(imageUri)
                    .apply(ImageOptions.imageOptions())
                    .into(homeProfileImage);

            int countNewChats = homeViewModel.getNewMessagesCount();
            if (countNewChats > 0) {
                homeNewMessagesCount.setVisibility(View.VISIBLE);
                homeNewMessagesCount.setText(String.valueOf(countNewChats));
            }

            int countNewNotifications = homeViewModel.getNewNotificationsCount();
            if (countNewNotifications > 0) {
                homeNewNotificationsCount.setVisibility(View.VISIBLE);
                homeNewNotificationsCount.setText(String.valueOf(countNewNotifications));
            }

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

    private void loadSearch() {
        String userType = homeViewModel.getUserType();

        if (userType.equals(UserType.CANDIDATE.toString()))
            loadVacancyLayout();
        else
            loadResumeLayout();
    }

    private void loadVacancyLayout() {
        searchTitle.setText(getResources().getString(R.string.text_candidate_vacancies_search_title));

        Vacancy vacancy = homeViewModel.getRecomendedVacancy();
        if (vacancy == null) {
            emptySearchText.setVisibility(View.VISIBLE);
            vacancyLayout.setVisibility(View.INVISIBLE);
        } else {
            emptySearchText.setVisibility(View.INVISIBLE);
            vacancyLayout.setVisibility(View.VISIBLE);

            TextView vacancyTitle = binding.homeVacancyTitle;
            TextView vacancyBand = binding.homeVacancyBand;
            TextView vacancyGenres = binding.homeVacancyGenres;
            TextView vacancyText = binding.homeVacancyText;
            TextView vacancySalary = binding.homeVacancySalary;
            TextView vacancyDatetime = binding.homeVacancyDatetime;

            Band band = homeViewModel.getBandForVacancy(vacancy.getBandUID());

            vacancyTitle.setText(vacancy.getRole());
            vacancyBand.setText(band.getName());
            vacancyGenres.setText(band.getGenres());
            vacancyText.setText(vacancy.getText());
            vacancySalary.setText(vacancy.getSalary());
            vacancyDatetime.setText(vacancy.getDatetime());
        }
    }

    @SuppressLint("SetTextI18n")
    private void loadResumeLayout() {
        searchTitle.setText(getResources().getString(R.string.text_band_resumes_search_title));

        Resume resume = homeViewModel.getReceivedVacancy();
        if (resume == null) {
            emptySearchText.setVisibility(View.VISIBLE);
            resumeLayout.setVisibility(View.INVISIBLE);
        } else {
            emptySearchText.setVisibility(View.INVISIBLE);
            resumeLayout.setVisibility(View.VISIBLE);

            TextView resumeTitle = binding.homeResumeTitle;
            TextView resumeRole = binding.homeResumeRole;
            TextView resumeGenres = binding.homeResumeGenres;
            TextView resumeExperience = binding.homeResumeExperience;
            TextView resumeDatetime = binding.homeResumeDatetime;

            Candidate candidate = homeViewModel.getCandidateForResume(resume.getCandidateUID());

            resumeTitle.setText(candidate.getName() + " " + candidate.getSurname());
            resumeRole.setText(candidate.getRole());
            resumeGenres.setText(candidate.getPreferredGenres());
            resumeExperience.setText(candidate.getExperience());
            resumeDatetime.setText(resume.getDatetime());
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}