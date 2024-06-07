package com.serediuk.bander_client.ui.chats;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.serediuk.bander_client.auth.AuthUID;
import com.serediuk.bander_client.databinding.FragmentChatsBinding;
import com.serediuk.bander_client.model.entity.Chat;
import com.serediuk.bander_client.ui.chats.adapters.ChatsRecyclerAdapter;

import java.util.ArrayList;

public class ChatsFragment extends Fragment {
    private ChatsViewModel chatsViewModel;
    private RecyclerView chatsRecyclerView;
    private ArrayList<Chat> chatsList;
    private ChatsRecyclerAdapter chatsRecyclerAdapter;
    private TextView mEmptyText;

    private FragmentChatsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        chatsViewModel = new ViewModelProvider(this).get(ChatsViewModel.class);

        binding = FragmentChatsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        init();

        return root;
    }

    private void init() {
        mEmptyText = binding.chatsEmptyText;
        chatsRecyclerView = binding.chatsRecyclerView;
        chatsRecyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));

        chatsList = chatsViewModel.getChatsList(AuthUID.getUID());
        if (chatsList.size() == 0) {
            mEmptyText.setVisibility(View.VISIBLE);
            chatsRecyclerView.setVisibility(View.INVISIBLE);
        } else {
            mEmptyText.setVisibility(View.INVISIBLE);
            chatsRecyclerView.setVisibility(View.VISIBLE);

            chatsRecyclerAdapter = new ChatsRecyclerAdapter(requireActivity(), chatsList);
            chatsRecyclerView.setAdapter(chatsRecyclerAdapter);
            chatsViewModel.setChatsAdapter(chatsRecyclerAdapter);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}