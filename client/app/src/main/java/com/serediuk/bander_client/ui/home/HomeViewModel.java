package com.serediuk.bander_client.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.serediuk.bander_client.auth.AuthUID;
import com.serediuk.bander_client.model.dao.BandsDAO;
import com.serediuk.bander_client.model.dao.CandidatesDAO;
import com.serediuk.bander_client.model.dao.ChatsDAO;
import com.serediuk.bander_client.model.dao.MessagesDAO;
import com.serediuk.bander_client.model.dao.NotificationsDAO;
import com.serediuk.bander_client.model.dao.ResumesDAO;
import com.serediuk.bander_client.model.dao.UsersDAO;
import com.serediuk.bander_client.model.dao.VacanciesDAO;
import com.serediuk.bander_client.model.entity.Band;
import com.serediuk.bander_client.model.entity.Candidate;
import com.serediuk.bander_client.model.entity.Chat;
import com.serediuk.bander_client.model.entity.Notification;
import com.serediuk.bander_client.model.entity.Resume;
import com.serediuk.bander_client.model.entity.User;
import com.serediuk.bander_client.model.entity.Vacancy;
import com.serediuk.bander_client.model.enums.NotificationStatus;
import com.serediuk.bander_client.model.enums.ResumeStatus;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Map;

public class HomeViewModel extends ViewModel {
    private UsersDAO usersDAO;
    private BandsDAO bandsDAO;
    private CandidatesDAO candidatesDAO;
    private ChatsDAO chatsDAO;
    private MessagesDAO messagesDAO;
    private NotificationsDAO notificationsDAO;
    private VacanciesDAO vacanciesDAO;
    private ResumesDAO resumeDAO;

    public HomeViewModel() {
        usersDAO = UsersDAO.getInstance();
        bandsDAO = BandsDAO.getInstance();
        candidatesDAO = CandidatesDAO.getInstance();
        chatsDAO = ChatsDAO.getInstance();
        messagesDAO = MessagesDAO.getInstance();
        notificationsDAO = NotificationsDAO.getInstance();
        vacanciesDAO = VacanciesDAO.getInstance();
        resumeDAO = ResumesDAO.getInstance();
    }

    public User getUser() {
        return usersDAO.readUser(AuthUID.getUID());
    }

    public Band getBand() {
        return bandsDAO.readBand(AuthUID.getUID());
    }

    public Candidate getCandidate() {
        return candidatesDAO.readCandidate(AuthUID.getUID());
    }

    public int getNewMessagesCount() {
        ArrayList<Chat> chats = chatsDAO.getChats(AuthUID.getUID(), getUser().getType());
        int count = 0;

        for (Chat chat : chats) {
            if (messagesDAO.getNewMessagesCount(chat.getChatUID()) > 0) {
                count++;
            }
        }

        return count;
    }

    public int getNewNotificationsCount() {
        ArrayList<Notification> notifications = notificationsDAO.getNotifications(AuthUID.getUID());
        int count = 0;

        for (Notification notification : notifications) {
            if (notification.getStatus().equals(NotificationStatus.NEW.toString())) {
                count++;
            }
        }

        return count;
    }

    public String getUserType() {
        return getUser().getType();
    }

    public Vacancy getRecomendedVacancy() {
        ArrayList<Vacancy> vacancies = vacanciesDAO.getRecommendedVacancies(AuthUID.getUID());
        if (vacancies.size() == 0)
            return null;
        return vacancies.get(0);
    }

    public Band getBandForVacancy(String bandUID) {
        return bandsDAO.readBand(bandUID);
    }

    public Resume getReceivedVacancy() {
        ArrayList<Resume> resumes = resumeDAO.getReceivedResumes(AuthUID.getUID());
        if (resumes.size() == 0)
            return null;
        return resumes.get(0);
    }

    public Candidate getCandidateForResume(String candidateUID) {
        return candidatesDAO.readCandidate(candidateUID);
    }
}