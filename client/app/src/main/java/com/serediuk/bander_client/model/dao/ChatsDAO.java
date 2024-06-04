package com.serediuk.bander_client.model.dao;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.serediuk.bander_client.model.DatabaseConnectionProvider;
import com.serediuk.bander_client.model.entity.Chat;
import com.serediuk.bander_client.model.entity.Message;
import com.serediuk.bander_client.model.enums.UserType;
import com.serediuk.bander_client.ui.chats.adapters.ChatsRecyclerAdapter;

import java.util.ArrayList;

public class ChatsDAO {
    private static ChatsDAO instance;
    private final FirebaseDatabase database;

    private static ArrayList<Chat> chatsList;
    private ChatsRecyclerAdapter chatsRecyclerAdapter;

    private ChatsDAO() {
        chatsList = new ArrayList<>();

        database = DatabaseConnectionProvider.getInstance().getDatabase();
        loadChats();
    }

    public static ChatsDAO getInstance() {
        if (instance == null) {
            instance = new ChatsDAO();
        }
        return instance;
    }

    public void createChat(Chat chat) {
        String key = database.getReference("chats").push().getKey();

        if (key != null) {
            chat.setChatUID(key);
            database.getReference("chats").child(key).setValue(chat);
            database.getReference("chats").child(key).child("newMessagesCount").removeValue();
            Log.d("CHAT DAO", "Adding chat:\n" + chat);
        } else {
            Log.d("CHAT DAO", "Key is null");
        }
    }

    public Chat readChat(String chatUID) {
        Log.d("CHAT DAO", "Reading chat: " + chatUID);
        if (chatsList.size()!= 0) {
            for (Chat chat : chatsList) {
                if (chat.getChatUID().equals(chatUID)) {
                    return chat;
                }
            }
        }
        return null;
    }

    public void updateChat(Chat chat) {
        database.getReference("chats").child(chat.getChatUID()).setValue(chat);

        for (int i = 0; i < chatsList.size(); i++) {
            if (chatsList.get(i).getChatUID().equals(chat.getChatUID())) {
                chatsList.set(i, chat);
                break;
            }
        }

        if (chatsRecyclerAdapter != null) {
            chatsRecyclerAdapter.setArrayList(chatsList);
            chatsRecyclerAdapter.notifyDataSetChanged();
        }

        Log.d("CHAT DAO", "Updating chat:\n" + chat);
    }

    public void deleteChat(String chatUID) {
        database.getReference("chats").child(chatUID).removeValue();

        for (Chat chat : chatsList) {
            if (chat.getChatUID().equals(chatUID)) {
                chatsList.remove(chat);
                break;
            }
        }

        if (chatsRecyclerAdapter!= null) {
            chatsRecyclerAdapter.setArrayList(chatsList);
            chatsRecyclerAdapter.notifyDataSetChanged();
        }

        Log.d("CHAT DAO", "Deleting chat:\n" + chatUID);
    }

    public void loadChats() {
        DatabaseReference chatsReference = database.getReference("chats");

        chatsReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                chatsList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String bandUID = dataSnapshot.child("bandMemberUID").getValue(String.class);
                    String candidateUID = dataSnapshot.child("candidateMemberUID").getValue(String.class);
                    String chatUID = dataSnapshot.child("chatUID").getValue(String.class);

                    ArrayList<Message> messages = new ArrayList<>();
                    for (DataSnapshot ds : dataSnapshot.child("messages").getChildren()) {
                        String messageUID = ds.child("messageUID").getValue(String.class);
                        String senderType = ds.child("senderType").getValue(String.class);
                        String message = ds.child("message").getValue(String.class);
                        String status = ds.child("status").getValue(String.class);
                        String datetime = ds.child("datetime").getValue(String.class);

                        Message msg = new Message(
                                messageUID,
                                chatUID,
                                senderType,
                                message,
                                datetime,
                                status
                        );
                        messages.add(msg);
                    }

                    Chat chat = new Chat(
                            chatUID,
                            candidateUID,
                            bandUID,
                            messages
                    );
                    chatsList.add(chat);
                }
                if (chatsRecyclerAdapter != null) {
                    chatsRecyclerAdapter.setArrayList(chatsList);
                    chatsRecyclerAdapter.notifyDataSetChanged();
                }

                Log.d("CHAT DAO", "Read " + chatsList.size() + " chats");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Log.d("CHAT DAO", "Loaded " + chatsList.size() + " chats");
    }

    public void setChatsRecyclerAdapter(ChatsRecyclerAdapter chatsRecyclerAdapter) {
        this.chatsRecyclerAdapter = chatsRecyclerAdapter;
    }

    public ArrayList<Chat> getChats(String userUID, String userType) {
        ArrayList<Chat> chatList = new ArrayList<>();

        if (userType.equals(UserType.CANDIDATE.toString())) {
            for (Chat chat : chatsList) {
                if (chat.getCandidateMemberUID().equals(userUID)) {
                    chatList.add(chat);
                }
            }
        } else {
            for (Chat chat : chatsList) {
                if (chat.getBandMemberUID().equals(userUID)) {
                    chatList.add(chat);
                }
            }
        }

        return chatList;
    }

    public void sendMessage(Chat chat, Message message) {
        String key = database.getReference("chats").child(chat.getChatUID()).child("messages").push().getKey();

        if (key != null) {
            message.setMessageUID(key);
            database.getReference("chats").child(chat.getChatUID()).child("messages").child(key).setValue(message);
            Log.d("CHAT DAO", "Adding message:\n" + message);
        } else {
            Log.d("CHAT DAO", "Key is null");
        }
    }
}
