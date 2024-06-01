package com.serediuk.bander_client.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.serediuk.bander_client.R;
import com.serediuk.bander_client.databinding.ActivityMainBinding;
import com.serediuk.bander_client.ui.chats.ChatsFragment;
import com.serediuk.bander_client.ui.home.HomeFragment;
import com.serediuk.bander_client.ui.notifications.NotificationsFragment;
import com.serediuk.bander_client.ui.profile.ProfileFragment;
import com.serediuk.bander_client.ui.search.SearchFragment;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new HomeFragment());

        binding.navView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.navigation_home) {
                replaceFragment(new HomeFragment());
            } else if (item.getItemId() == R.id.navigation_chats) {
                replaceFragment(new ChatsFragment());
            } else if (item.getItemId() == R.id.navigation_search) {
                replaceFragment(new SearchFragment());
            } else if (item.getItemId() == R.id.navigation_notifications) {
                replaceFragment(new NotificationsFragment());
            } else if (item.getItemId() == R.id.navigation_profile) {
                replaceFragment(new ProfileFragment());
            }
            return true;
        });
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.nav_host_fragment_activity_main, fragment);
        ft.commit();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }
}