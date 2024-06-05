package com.serediuk.bander_client.ui.chats.adapters;

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
import com.serediuk.bander_client.auth.AuthUID;
import com.serediuk.bander_client.model.dao.UsersDAO;
import com.serediuk.bander_client.model.entity.Message;
import com.serediuk.bander_client.model.enums.MessageStatus;
import com.serediuk.bander_client.model.enums.SenderType;

import java.util.ArrayList;

public class MessagesRecyclerAdapter extends RecyclerView.Adapter<MessagesRecyclerAdapter.ViewHolder> {
    private static final int MY = 1;
    private static final int CHAT_MEMBER = 2;
    String myType;

    private Context context;
    private ArrayList<Message> messagesList;

    private UsersDAO usersDAO;

    public MessagesRecyclerAdapter(Context context, ArrayList<Message> messages) {
        this.context = context;
        this.messagesList = messages;

        usersDAO = UsersDAO.getInstance();

        myType = usersDAO.readUser(AuthUID.getUID()).getType();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view;
        if (viewType == CHAT_MEMBER)
            view = inflater.inflate(R.layout.message_item, parent, false);
        else
            view = inflater.inflate(R.layout.message_user_item, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Message message = messagesList.get(position);

        holder.messageText.setText(message.getMessage());
        holder.messageDatetime.setText(message.getDatetime());

        if (message.getSenderType().equals(myType)) {
            holder.constraintLayout.setBackground(context.getDrawable(R.drawable.corner_radius_message_my));
        } else if (message.getStatus().equals(MessageStatus.SENT.toString())) {
            holder.constraintLayout.setBackground(context.getDrawable(R.drawable.corner_radius_message_new));
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setArrayList(ArrayList<Message> messages) {
        this.messagesList = messages;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        Message message = messagesList.get(position);
        return message.getSenderType().equals(myType) ? MY : CHAT_MEMBER;
    }

    @Override
    public int getItemCount() {
        return messagesList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView messageText, messageDatetime;
        ConstraintLayout constraintLayout;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            messageText = itemView.findViewById(R.id.messageText);
            messageDatetime = itemView.findViewById(R.id.messageDatetime);

            constraintLayout = itemView.findViewById(R.id.messageLayout);
        }
    }
}
