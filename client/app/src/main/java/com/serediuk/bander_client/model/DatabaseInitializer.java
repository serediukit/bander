package com.serediuk.bander_client.model;

import com.serediuk.bander_client.model.dao.BandsDAO;
import com.serediuk.bander_client.model.dao.CandidatesDAO;
import com.serediuk.bander_client.model.dao.UsersDAO;

public class DatabaseInitializer {
    public static void init() {
        DatabaseConnectionProvider dbcProvider = DatabaseConnectionProvider.getInstance();
        UsersDAO usersDAO = UsersDAO.getInstance();
        CandidatesDAO candidatesDAO = CandidatesDAO.getInstance();
        BandsDAO bandsDAO = BandsDAO.getInstance();
    }
}
