package com.serediuk.bander_client.model;

import android.util.Log;

import com.serediuk.bander_client.model.dao.BandsDAO;
import com.serediuk.bander_client.model.dao.CandidatesDAO;
import com.serediuk.bander_client.model.dao.UsersDAO;

public class DatabaseInitializer {
    private static UsersDAO usersDAO;
    private static CandidatesDAO candidatesDAO;
    private static BandsDAO bandsDAO;

    public static void init() {
        DatabaseConnectionProvider dbcProvider = DatabaseConnectionProvider.getInstance();
        usersDAO = UsersDAO.getInstance();
        candidatesDAO = CandidatesDAO.getInstance();
        bandsDAO = BandsDAO.getInstance();
    }

    public static void load() {
        init();
        usersDAO.loadUsers();
        candidatesDAO.loadCandidates();
        bandsDAO.loadBands();
    }
}
