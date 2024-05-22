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

import java.util.ArrayList;

public class NotificationsDAO {
    private static NotificationsDAO instance;
    private final FirebaseDatabase database;

    private static ArrayList<Notification> notificationsList;


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

    public void updateNotification(Notification notification) {
        database.getReference("notifications").child(notification.getNotificationUID()).setValue(notification);

        for (int i = 0; i < notificationsList.size(); i++) {
            if (notificationsList.get(i).getNotificationUID().equals(notification.getNotificationUID())) {
                notificationsList.set(i, notification);
                break;
            }
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
                Log.d("NOTIFICATION DAO", "Read " + notificationsList.size() + " notifications");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Log.d("NOTIFICATION DAO", "Loaded " + notificationsList.size() + " notifications");
    }
}
