package com.serediuk.bander_client.ui.chats;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.serediuk.bander_client.R;
import com.serediuk.bander_client.auth.AuthUID;
import com.serediuk.bander_client.model.dao.UsersDAO;
import com.serediuk.bander_client.model.entity.Chat;
import com.serediuk.bander_client.model.storage.ImageStorageProvider;
import com.serediuk.bander_client.util.image.ImageOptions;

import org.w3c.dom.Text;

public class ChatActivity extends AppCompatActivity {
    private ImageView chatImage;
    private TextView chatTitle;

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

        init();
        loadData();
    }

    private void init() {
        chatImage = findViewById(R.id.messageChatImage);
        chatTitle = findViewById(R.id.messageChatTitle);
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

    public void sendMessage(View view) {
        ((TextView) findViewById(R.id.messageWriteEditText)).setText("axaxax");
    }

    public void back(View view) {
        finish();
    }
}