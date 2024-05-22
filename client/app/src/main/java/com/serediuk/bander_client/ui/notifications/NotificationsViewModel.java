package com.serediuk.bander_client.ui.notifications;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.serediuk.bander_client.model.dao.NotificationsDAO;
import com.serediuk.bander_client.model.entity.Notification;
import com.serediuk.bander_client.ui.notifications.adapters.NotificationRecyclerAdapter;

import java.util.ArrayList;

public class NotificationsViewModel extends ViewModel {
    NotificationsDAO notificationsDAO;

    public NotificationsViewModel() {
        notificationsDAO = NotificationsDAO.getInstance();
    }

    public ArrayList<Notification> getNotificationList(String userUID) {
        return notificationsDAO.getNotifications(userUID);
    }

    public void setNotificationAdapter(NotificationRecyclerAdapter notificationRecyclerAdapter) {
        notificationsDAO.setNotificationRecyclerAdapter(notificationRecyclerAdapter);
    }
}