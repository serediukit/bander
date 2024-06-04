package com.serediuk.bander_client.model.dao;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.serediuk.bander_client.model.DatabaseConnectionProvider;
import com.serediuk.bander_client.model.entity.Message;

import java.util.ArrayList;

public class MessagesDAO {
    private static MessagesDAO instance;
    private final FirebaseDatabase database;

    private final ArrayList<Message> messagesList;

    private MessagesDAO() {
        messagesList = new ArrayList<>();

        database = DatabaseConnectionProvider.getInstance().getDatabase();
        loadMessages();
    }

    public static MessagesDAO getInstance() {
        if (instance == null) {
            instance = new MessagesDAO();
        }
        return instance;
    }

    public void createMessage(Message message) {
        if (message.getMessageUID() != null) {
            database.getReference("messages").child(message.getMessageUID()).setValue(message);

            Log.d("MESSAGE DAO", "Adding message:\n" + message);
        } else {
            Log.d("MESSAGE DAO", "Key is null");
        }
    }

    public Message readMessage(String messageUID) {
        Log.d("MESSAGE DAO", "Reading message: " + messageUID);
        if (messagesList.size() != 0) {
            for (Message message : messagesList) {
                if (message.getMessageUID().equals(messageUID)) {
                    return message;
                }
            }
        }
        return null;
    }

    public void updateMessage(Message message) {
        database.getReference("messages").child(message.getMessageUID()).setValue(message);

        for (int i = 0; i < messagesList.size(); i++) {
            if (messagesList.get(i).getMessageUID().equals(message.getMessageUID())) {
                messagesList.set(i, message);
                break;
            }
        }

        Log.d("MESSAGE DAO", "Updating message:\n" + message);
    }

    public void loadMessages() {
        DatabaseReference messagesReference = database.getReference("messages");

        messagesReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messagesList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Message message = dataSnapshot.getValue(Message.class);
                    messagesList.add(message);
                }
                Log.d("MESSAGE DAO", "Read " + messagesList.size() + " messages");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Log.d("MESSAGE DAO", "Loaded " + messagesList.size() + " messages");
    }
}
