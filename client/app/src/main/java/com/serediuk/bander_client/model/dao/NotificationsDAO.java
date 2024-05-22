package com.serediuk.bander_client.model.dao;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.serediuk.bander_client.model.DatabaseConnectionProvider;
import com.serediuk.bander_client.model.entity.Notification;
import com.serediuk.bander_client.model.enums.NotificationStatus;
import com.serediuk.bander_client.ui.notifications.adapters.NotificationRecyclerAdapter;

import java.util.ArrayList;

public class NotificationsDAO {
    private static NotificationsDAO instance;
    private final FirebaseDatabase database;

    private static ArrayList<Notification> notificationsList;
    private NotificationRecyclerAdapter notificationRecyclerAdapter;


    private NotificationsDAO() {
        notificationsList = new ArrayList<>();

        database = DatabaseConnectionProvider.getInstance().getDatabase();
        loadNotifications();
    }

    public static NotificationsDAO getInstance() {
        if (instance == null) {
            instance = new NotificationsDAO();
        }
        return instance;
    }

    public void createNotification(Notification notification) {
        String key = database.getReference("notifications").push().getKey();

        if (key != null) {
            notification.setNotificationUID(key);
            database.getReference("notifications").child(key).setValue(notification);
            Log.d("NOTIFICATION DAO", "Adding notification:\n" + notification);
        } else {
            Log.d("NOTIFICATION DAO", "Key is null");
        }
    }

    public Notification readNotification(String notificationUID) {
        Log.d("NOTIFICATION DAO", "Reading notification: " + notificationUID);
        if (notificationsList.size() != 0) {
            for (Notification notification : notificationsList) {
                if (notification.getNotificationUID().equals(notificationUID)) {
                    return notification;
                }
            }
        }
        return null;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateNotification(Notification notification) {
        database.getReference("notifications").child(notification.getNotificationUID()).setValue(notification);

        for (int i = 0; i < notificationsList.size(); i++) {
            if (notificationsList.get(i).getNotificationUID().equals(notification.getNotificationUID())) {
                notificationsList.set(i, notification);
                break;
            }
        }

        if (notificationRecyclerAdapter != null) {
            notificationRecyclerAdapter.setArrayList(notificationsList);
            notificationRecyclerAdapter.notifyDataSetChanged();
        }

        Log.d("NOTIFICATION DAO", "Updating notification:\n" + notification);
    }

    public void deleteNotification(String notificationUID) {
        database.getReference("notifications").child(notificationUID).removeValue();

        for (Notification notification : notificationsList) {
            if (notification.getNotificationUID().equals(notificationUID)) {
                notificationsList.remove(notification);
                break;
            }
        }

        Log.d("NOTIFICATION DAO", "Deleting notification: " + notificationUID);
    }

    public void loadNotifications() {
        DatabaseReference notificationsReference = database.getReference("notifications");

        notificationsReference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                notificationsList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Notification notification = dataSnapshot.getValue(Notification.class);
                    notificationsList.add(notification);
                }
                if (notificationRecyclerAdapter != null) {
                    notificationRecyclerAdapter.setArrayList(notificationsList);
                    notificationRecyclerAdapter.notifyDataSetChanged();
                }

                Log.d("NOTIFICATION DAO", "Read " + notificationsList.size() + " notifications");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Log.d("NOTIFICATION DAO", "Loaded " + notificationsList.size() + " notifications");
    }

    public ArrayList<Notification> getNotifications(String userUID) {
        ArrayList<Notification> userNotifications = new ArrayList<>();

        for (int i = notificationsList.size() - 1; i >= 0; i--) {
            if (notificationsList.get(i).getReceiverUID().equals(userUID)) {
                userNotifications.add(notificationsList.get(i));
            }
        }

        return userNotifications;
    }

    public void setNotificationRecyclerAdapter(NotificationRecyclerAdapter notificationRecyclerAdapter) {
        this.notificationRecyclerAdapter = notificationRecyclerAdapter;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setNotificationsRead(String uid) {
        for (Notification notification : notificationsList) {
            if (notification.getReceiverUID().equals(uid) && notification.getStatus().equals(NotificationStatus.NEW.toString())) {
                notification.setStatus(NotificationStatus.READ.toString());
                updateNotification(notification);
            }
        }

        if (notificationRecyclerAdapter != null) {
            notificationRecyclerAdapter.setArrayList(notificationsList);
            notificationRecyclerAdapter.notifyDataSetChanged();
        }

        Log.d("NOTIFICATION DAO", "Marking notifications as read");
    }
}
