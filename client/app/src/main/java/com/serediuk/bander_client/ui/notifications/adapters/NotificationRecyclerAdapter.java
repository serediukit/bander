package com.serediuk.bander_client.ui.notifications.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.serediuk.bander_client.R;
import com.serediuk.bander_client.model.entity.Notification;
import com.serediuk.bander_client.model.enums.NotificationStatus;

import java.util.ArrayList;

public class NotificationRecyclerAdapter extends RecyclerView.Adapter<NotificationRecyclerAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Notification> notificationsList;

    public NotificationRecyclerAdapter(Context context, ArrayList<Notification> notificationsList) {
        this.context = context;
        this.notificationsList = notificationsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.notification_item, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Notification notification = notificationsList.get(position);

        holder.textTitle.setText(notification.getTitle());
        holder.textText.setText(notification.getText());
        holder.textDatetime.setText(notification.getDatetime());

        if (notification.getStatus().equals(NotificationStatus.NEW.toString()))
            holder.constraintLayout.setBackground(context.getDrawable(R.drawable.corner_radius_small_new_notification));
    }

    @Override
    public int getItemCount() {
        return notificationsList.size();
    }

    public void setArrayList(ArrayList<Notification> notificationsList) {
        this.notificationsList = notificationsList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textTitle, textText, textDatetime;
        ConstraintLayout constraintLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textTitle = itemView.findViewById(R.id.notificationTitle);
            textText = itemView.findViewById(R.id.notificationText);
            textDatetime = itemView.findViewById(R.id.notificationDatetime);

            constraintLayout = itemView.findViewById(R.id.notificationLayout);
        }
    }
}
