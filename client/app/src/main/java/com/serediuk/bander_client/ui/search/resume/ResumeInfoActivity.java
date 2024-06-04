package com.serediuk.bander_client.ui.search.resume;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.serediuk.bander_client.R;
import com.serediuk.bander_client.auth.AuthUID;
import com.serediuk.bander_client.model.dao.BandsDAO;
import com.serediuk.bander_client.model.dao.CandidatesDAO;
import com.serediuk.bander_client.model.dao.ChatsDAO;
import com.serediuk.bander_client.model.dao.NotificationsDAO;
import com.serediuk.bander_client.model.dao.ResumesDAO;
import com.serediuk.bander_client.model.entity.Candidate;
import com.serediuk.bander_client.model.entity.Chat;
import com.serediuk.bander_client.model.entity.Message;
import com.serediuk.bander_client.model.entity.Notification;
import com.serediuk.bander_client.model.entity.Resume;
import com.serediuk.bander_client.model.enums.MessageStatus;
import com.serediuk.bander_client.model.enums.NotificationStatus;
import com.serediuk.bander_client.model.enums.ResumeStatus;
import com.serediuk.bander_client.model.enums.SenderType;
import com.serediuk.bander_client.model.storage.ImageStorageProvider;
import com.serediuk.bander_client.util.image.ImageOptions;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ResumeInfoActivity extends AppCompatActivity {
    private CandidatesDAO candidatesDAO;
    private ResumesDAO resumesDAO;
    private NotificationsDAO notificationsDAO;
    private ChatsDAO chatsDAO;
    private Resume resume;

    private TextView mName, mSurname, mRole, mExperience;
    private TextView mBirthday, mCity, mText;
    private TextView mAbout, mGenres, mLinks, mDatetime;
    private ImageView mResumeImage;

    private ImageStorageProvider imageStorageProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resume_info);

        candidatesDAO = CandidatesDAO.getInstance();
        resumesDAO = ResumesDAO.getInstance();
        notificationsDAO = NotificationsDAO.getInstance();
        chatsDAO = ChatsDAO.getInstance();
        resume = (Resume) getIntent().getSerializableExtra("resume");

        imageStorageProvider = ImageStorageProvider.getInstance();

        init();
        loadData();
    }

    private void init() {
        mName = findViewById(R.id.resumeInfoCandidateName);
        mSurname = findViewById(R.id.resumeInfoCandidateSurname);
        mRole = findViewById(R.id.resumeInfoRole);
        mExperience = findViewById(R.id.resumeInfoExperience);
        mBirthday = findViewById(R.id.resumeInfoBirthday);
        mCity = findViewById(R.id.resumeInfoCity);
        mText = findViewById(R.id.resumeInfoText);
        mAbout = findViewById(R.id.resumeInfoAbout);
        mGenres = findViewById(R.id.resumeInfoGenres);
        mLinks = findViewById(R.id.resumeInfoLinks);
        mDatetime = findViewById(R.id.resumeInfoDatetime);

        mResumeImage = findViewById(R.id.candidateImageView);
    }

    private void loadData() {
        Candidate candidate = candidatesDAO.readCandidate(resume.getCandidateUID());

        mName.setText(candidate.getName());
        mSurname.setText(candidate.getSurname());
        mRole.setText(candidate.getRole());
        mExperience.setText(candidate.getExperience());
        mBirthday.setText(candidate.getBirthday());
        mCity.setText(candidate.getCity());
        mText.setText(resume.getText());
        mAbout.setText(candidate.getAbout());
        mGenres.setText(candidate.getPreferredGenres());
        mLinks.setText(candidate.getVideoLinks());
        mDatetime.setText(resume.getDatetime());

        Uri imageUti = imageStorageProvider.downloadImageUri(resume.getCandidateUID());
        Glide
                .with(this)
                .load(imageUti)
                .apply(ImageOptions.imageOptions())
                .into(mResumeImage);
    }

    public void acceptResume(View view) {
        resume.setStatus(ResumeStatus.ACCEPTED.toString());
        resumesDAO.updateResume(resume);

        Toast.makeText(ResumeInfoActivity.this, "Resume accepted", Toast.LENGTH_SHORT).show();

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        String datetime = now.format(formatter);

        Notification notification = new Notification(
                "",
                resume.getCandidateUID(),
                getResources().getString(R.string.text_notification_accept_resume_title),
                BandsDAO.getInstance().readBand(AuthUID.getUID()).getName() + " " + getResources().getString(R.string.text_notification_accept_resume),
                datetime,
                NotificationStatus.NEW.toString()
        );
        notificationsDAO.createNotification(notification);

        Chat chat = new Chat(
                "",
                resume.getCandidateUID(),
                AuthUID.getUID(),
                new ArrayList<>()
        );
        chatsDAO.createChat(chat);

        Message message = new Message(
                "",
                chat.getChatUID(),
                SenderType.BAND.toString(),
                getResources().getString(R.string.text_hello_message),
                datetime,
                MessageStatus.SENT.toString()
        );
        chatsDAO.sendMessage(chat, message);

        finish();
    }

    public void declineResume(View view) {
        resume.setStatus(ResumeStatus.DECLINED.toString());
        resumesDAO.updateResume(resume);

        Toast.makeText(ResumeInfoActivity.this, "Resume declined", Toast.LENGTH_SHORT).show();

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        String datetime = now.format(formatter);

        Notification notification = new Notification(
                "",
                resume.getCandidateUID(),
                getResources().getString(R.string.text_notification_decline_resume_title),
                BandsDAO.getInstance().readBand(AuthUID.getUID()).getName() + " " + getResources().getString(R.string.text_notification_decline_resume),
                datetime,
                NotificationStatus.NEW.toString()
        );
        notificationsDAO.createNotification(notification);

        finish();
    }
}