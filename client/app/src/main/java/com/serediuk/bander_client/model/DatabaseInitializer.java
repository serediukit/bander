package com.serediuk.bander_client.model;

import com.serediuk.bander_client.model.dao.*;
import com.serediuk.bander_client.ui.auth.LoginRegisterActivity;

public class DatabaseInitializer {
    private static UsersDAO usersDAO;
    private static CandidatesDAO candidatesDAO;
    private static BandsDAO bandsDAO;
    private static VacanciesDAO vacanciesDAO;
    private static ResumesDAO resumesDAO;
    private static NotificationsDAO notificationsDAO;
    private static ChatsDAO chatsDAO;
    private static MessagesDAO messagesDAO;

    private static int initializeCount = 0;


    public static void init() {
        DatabaseConnectionProvider dbcProvider = DatabaseConnectionProvider.getInstance();
        usersDAO = UsersDAO.getInstance();
        candidatesDAO = CandidatesDAO.getInstance();
        bandsDAO = BandsDAO.getInstance();
        vacanciesDAO = VacanciesDAO.getInstance();
        resumesDAO = ResumesDAO.getInstance();
        notificationsDAO = NotificationsDAO.getInstance();
        chatsDAO = ChatsDAO.getInstance();
        messagesDAO = MessagesDAO.getInstance();
    }

    public static void inc() {
        initializeCount++;
        if (initializeCount == 3) {
            LoginRegisterActivity.removeCover();
        }
    }

    public static boolean isInitialized() {
        return initializeCount >= 3;
    }
}
