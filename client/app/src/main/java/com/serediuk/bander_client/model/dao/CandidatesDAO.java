package com.serediuk.bander_client.model.dao;

import com.google.firebase.database.FirebaseDatabase;
import com.serediuk.bander_client.model.DatabaseConnectionProvider;

public class CandidatesDAO {
    private static CandidatesDAO instance;
    private FirebaseDatabase database;

    private CandidatesDAO() {
        database = DatabaseConnectionProvider.getInstance().getDatabase();
    }

    public static CandidatesDAO getInstance() {
        if (instance == null) {
            instance = new CandidatesDAO();
        }
        return instance;
    }
}
