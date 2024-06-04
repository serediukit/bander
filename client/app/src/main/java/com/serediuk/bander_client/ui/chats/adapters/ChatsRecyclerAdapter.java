package com.serediuk.bander_client.ui.chats.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.serediuk.bander_client.R;
import com.serediuk.bander_client.auth.AuthUID;
import com.serediuk.bander_client.model.dao.BandsDAO;
import com.serediuk.bander_client.model.dao.CandidatesDAO;
import com.serediuk.bander_client.model.dao.UsersDAO;
import com.serediuk.bander_client.model.entity.Chat;
import com.serediuk.bander_client.model.entity.Message;
import com.serediuk.bander_client.ui.chats.ChatActivity;
import com.serediuk.bander_client.util.image.ImageOptions;

import java.util.ArrayList;

public class ChatsRecyclerAdapter extends RecyclerView.Adapter<ChatsRecyclerAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Chat> chatsList;
    private UsersDAO usersDAO;

    public ChatsRecyclerAdapter(Context context, ArrayList<Chat> chatsList) {
        this.context = context;
        this.chatsList = chatsList;

        usersDAO = UsersDAO.getInstance();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.chat_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Chat chat = chatsList.get(position);
        Message lastMessage = chat.getLastMessage();

        String userUID = chat.getCandidateMemberUID().equals(AuthUID.getUID()) ? chat.getBandMemberUID() : chat.getCandidateMemberUID();

        Glide
                .with(context)
                .load(userUID)
                .apply(ImageOptions.imageOptions())
                .into(holder.chatImage);

        holder.chatTitle.setText(usersDAO.readUser(userUID).getName());
        holder.chatMessage.setText(lastMessage.getMessage());
        holder.chatDatetime.setText(lastMessage.getDatetime());
        holder.chatNewMessagesCount.setText(chat.getNewMessagesCount());

        holder.constraintLayout.setOnClickListener(v -> {
            Intent intent = new Intent(context, ChatActivity.class);
            intent.putExtra("chat", chat);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return chatsList.size();
    }

    public void setArrayList(ArrayList<Chat> chatsList) {
        this.chatsList = chatsList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView chatImage;
        TextView chatTitle, chatMessage, chatNewMessagesCount, chatDatetime;
        ConstraintLayout constraintLayout;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            chatImage = itemView.findViewById(R.id.chatImage);

            chatTitle = itemView.findViewById(R.id.chatTitle);
            chatMessage = itemView.findViewById(R.id.chatLastMessage);
            chatNewMessagesCount = itemView.findViewById(R.id.chatNewMassagesCount);
            chatDatetime = itemView.findViewById(R.id.chatDatetime);

            constraintLayout = itemView.findViewById(R.id.chatLayout);
        }
    }
}
