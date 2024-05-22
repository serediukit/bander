package com.serediuk.bander_client.ui.notifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.serediuk.bander_client.auth.AuthUID;
import com.serediuk.bander_client.databinding.FragmentNotificationsBinding;
import com.serediuk.bander_client.model.entity.Notification;
import com.serediuk.bander_client.ui.notifications.adapters.NotificationRecyclerAdapter;

import java.util.ArrayList;

public class NotificationsFragment extends Fragment {
    private NotificationsViewModel notificationsViewModel;
    private RecyclerView notificationRecyclerView;
    private ArrayList<Notification> notificationsList;
    private NotificationRecyclerAdapter notificationRecyclerAdapter;
    private TextView mEmptyText;

    private FragmentNotificationsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel = new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        init();

        return root;
    }

    private void init() {
        mEmptyText = binding.notificationEmptyText;
        notificationRecyclerView = binding.notificationRecyclerView;
        notificationRecyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));

        notificationsList = notificationsViewModel.getNotificationList(AuthUID.getUID());
        if (notificationsList.size() == 0) {
            mEmptyText.setVisibility(View.VISIBLE);
            notificationRecyclerView.setVisibility(View.INVISIBLE);
        } else {
            mEmptyText.setVisibility(View.INVISIBLE);
            notificationRecyclerView.setVisibility(View.VISIBLE);

            notificationRecyclerAdapter = new NotificationRecyclerAdapter(requireActivity(), notificationsList);
            notificationRecyclerView.setAdapter(notificationRecyclerAdapter);
            notificationsViewModel.setNotificationAdapter(notificationRecyclerAdapter);
;        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        notificationsViewModel.setNotificationsRead(AuthUID.getUID());
        binding = null;
    }
}