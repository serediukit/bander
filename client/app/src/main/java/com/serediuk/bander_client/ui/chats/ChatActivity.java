package com.serediuk.bander_client.ui.chats;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.serediuk.bander_client.R;
import com.serediuk.bander_client.model.entity.Chat;

import org.w3c.dom.Text;

public class ChatActivity extends AppCompatActivity {
    private Chat chat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.primary));

        chat = (Chat) getIntent().getSerializableExtra("chat");


    }

    public void sendMessage(View view) {
        ((TextView) findViewById(R.id.messageWriteEditText)).setText("axaxax");
    }
}