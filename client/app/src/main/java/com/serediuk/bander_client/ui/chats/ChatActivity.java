package com.serediuk.bander_client.ui.chats;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.serediuk.bander_client.R;
import com.serediuk.bander_client.auth.AuthUID;
import com.serediuk.bander_client.model.database.DatabaseConnectionProvider;
import com.serediuk.bander_client.model.dao.UsersDAO;
import com.serediuk.bander_client.model.entity.Chat;
import com.serediuk.bander_client.model.entity.Message;
import com.serediuk.bander_client.model.enums.MessageStatus;
import com.serediuk.bander_client.model.storage.ImageStorageProvider;
import com.serediuk.bander_client.ui.chats.adapters.MessagesRecyclerAdapter;
import com.serediuk.bander_client.util.image.ImageOptions;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ChatActivity extends AppCompatActivity {
    private RecyclerView messageRecyclerView;
    private ArrayList<Message> messagesList;
    private MessagesRecyclerAdapter messagesRecyclerAdapter;
    private DatabaseReference messageReference;
    private UsersDAO usersDAO;

    private ImageView chatImage;
    private TextView chatTitle;
    private EditText messageText;

    private Chat chat;
    private String myType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.primary));

        usersDAO = UsersDAO.getInstance();

        chat = (Chat) getIntent().getSerializableExtra("chat");
        myType = usersDAO.readUser(AuthUID.getUID()).getType();

        init();
        loadData();
        loadMessages();
    }

    private void init() {
        messageReference = DatabaseConnectionProvider.getInstance().getDatabase().getReference("messages");
        messagesList = new ArrayList<>();

        chatImage = findViewById(R.id.messageChatImage);
        chatTitle = findViewById(R.id.messageChatTitle);
        messageText = findViewById((R.id.messageWriteEditText));

        messageRecyclerView = findViewById(R.id.messageRecyclerView);
        messageRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void loadData() {
        String chatMemberUid = chat.getCandidateMemberUID().equals(AuthUID.getUID()) ? chat.getBandMemberUID() : chat.getCandidateMemberUID();
        Log.d("CHAT", "Chat member UID: " + chatMemberUid);

        ImageStorageProvider imageStorageProvider = ImageStorageProvider.getInstance();
        Uri imageUri = imageStorageProvider.downloadImageUri(chatMemberUid);
        Glide
                .with(this)
                .load(imageUri)
                .apply(ImageOptions.imageOptions())
                .into(chatImage);

        chatTitle.setText(UsersDAO.getInstance().readUser(chatMemberUid).getName());
    }

    private void loadMessages() {
        messageReference.addValueEventListener(new ValueEventListener() {

            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messagesList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Message message = dataSnapshot.getValue(Message.class);

                    if (message != null && message.getChatUID().equals(chat.getChatUID())) {
                        messagesList.add(message);
                    }
                }
                messagesRecyclerAdapter = new MessagesRecyclerAdapter(ChatActivity.this, messagesList);
                messageRecyclerView.setAdapter(messagesRecyclerAdapter);
                messagesRecyclerAdapter.notifyDataSetChanged();
                messageRecyclerView.scrollToPosition(messagesRecyclerAdapter.getItemCount() - 1);

                Log.d("CHAT", "Load " + messagesList.size() + " messages");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void sendMessage(View view) {
        String message = messageText.getText().toString();

        if (!message.isEmpty()) {
            String key = messageReference.push().getKey();

            if (key != null) {
                LocalDateTime now = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
                String datetime = now.format(formatter);

                Message newMessage = new Message(
                        key,
                        chat.getChatUID(),
                        myType,
                        message,
                        datetime,
                        MessageStatus.SENT.toString()
                );

                messageReference.child(key).setValue(newMessage);
                messageText.setText("");

                Log.d("CHAT", "Message sent: " + newMessage);
            } else {
                Log.d("CHAT", "Key is null");
            }
        }
    }

    public void back(View view) {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        for (Message message : messagesList) {
            if (!message.getSenderType().equals(myType) && message.getStatus().equals(MessageStatus.SENT.toString())) {
                messageReference.child(message.getMessageUID()).child("status").setValue(MessageStatus.READ.toString());
            }
        }
    }

    public void scrollDown(View view) {
        new Handler().postDelayed(() -> {
            messageRecyclerView.scrollToPosition(messagesRecyclerAdapter.getItemCount() - 1);
        }, 300);
    }
}