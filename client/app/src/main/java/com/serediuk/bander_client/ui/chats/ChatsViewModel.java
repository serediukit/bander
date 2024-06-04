package com.serediuk.bander_client.ui.chats;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.serediuk.bander_client.model.dao.ChatsDAO;
import com.serediuk.bander_client.model.dao.UsersDAO;
import com.serediuk.bander_client.model.entity.Chat;
import com.serediuk.bander_client.model.entity.User;

import java.util.ArrayList;

public class ChatsViewModel extends ViewModel {
    private ChatsDAO chatsDAO;

    public ChatsViewModel() {
        chatsDAO = ChatsDAO.getInstance();
    }

    public ArrayList<Chat> getChatsList(String userUID) {
        User user = UsersDAO.getInstance().readUser(userUID);
        return chatsDAO.getChats(userUID, user.getType());
    }

    public void setChatsAdapter(ChatsRecyclerAdapter chatsRecyclerAdapter) {
        chatsDAO.setChatsRecyclerAdapter(chatsRecyclerAdapter);
    }
}