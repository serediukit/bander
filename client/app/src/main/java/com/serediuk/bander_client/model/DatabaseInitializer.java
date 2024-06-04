package com.serediuk.bander_client.model;

import com.serediuk.bander_client.model.dao.*;

public class DatabaseInitializer {
    private static UsersDAO usersDAO;
    private static CandidatesDAO candidatesDAO;
    private static BandsDAO bandsDAO;
    private static VacanciesDAO vacanciesDAO;
    private static ResumesDAO resumesDAO;
    private static NotificationsDAO notificationsDAO;
    private static ChatsDAO chatsDAO;
    private static MessagesDAO messagesDAO;

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
}
