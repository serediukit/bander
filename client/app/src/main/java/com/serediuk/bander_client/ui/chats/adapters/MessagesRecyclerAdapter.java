package com.serediuk.bander_client.ui.chats.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.serediuk.bander_client.R;
import com.serediuk.bander_client.model.entity.Message;

import java.util.ArrayList;

public class MessagesRecyclerAdapter extends RecyclerView.Adapter<MessagesRecyclerAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Message> messagesList;

    public MessagesRecyclerAdapter(Context context, ArrayList<Message> messages) {
        this.context = context;
        this.messagesList = messages;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView messageText, messageDatetime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            messageText = itemView.findViewById(R.id.messageText);
            messageDatetime = itemView.findViewById(R.id.messageDatetime);
        }
    }
}
