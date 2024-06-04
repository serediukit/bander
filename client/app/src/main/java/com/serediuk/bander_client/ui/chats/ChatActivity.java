package com.serediuk.bander_client.ui.chats;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.serediuk.bander_client.R;
import com.serediuk.bander_client.model.entity.Chat;

public class ChatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Chat chat = (Chat) getIntent().getSerializableExtra("chat");

        ((TextView) findViewById(R.id.tempView)).setText(chat.toString());
    }
}