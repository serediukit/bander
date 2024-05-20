package com.serediuk.bander_client.model.dao;

import android.util.Log;

import com.google.firebase.database.FirebaseDatabase;
import com.serediuk.bander_client.model.DatabaseConnectionProvider;


public class UsersDAO {
    private static UsersDAO instance;
    private FirebaseDatabase database;

    private UsersDAO() {
        database = DatabaseConnectionProvider.getInstance().getDatabase();
    }

    public static UsersDAO getInstance() {
        if (instance == null) {
            instance = new UsersDAO();
        }
        return instance;
    }
}
