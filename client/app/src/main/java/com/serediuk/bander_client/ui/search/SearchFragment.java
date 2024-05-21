package com.serediuk.bander_client.ui.search;

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

import com.serediuk.bander_client.databinding.FragmentSearchBinding;
import com.serediuk.bander_client.model.entity.Vacancy;

import java.util.ArrayList;

public class SearchFragment extends Fragment {
    private SearchViewModel searchViewModel;
    private RecyclerView recommendedVacanciesRecyclerView;
    private ArrayList<Vacancy> recommendedVacanciesList;
    private RecommendedVacanciesRecyclerAdapter adapter;

    private FragmentSearchBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        searchViewModel = new ViewModelProvider(this).get(SearchViewModel.class);

        binding = FragmentSearchBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        init();
        loadData();

        return root;
    }

    @SuppressLint("NotifyDataSetChanged")
    private void init() {
        recommendedVacanciesRecyclerView = binding.searchCandidateRecyclerView;
        recommendedVacanciesRecyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));

        recommendedVacanciesList = searchViewModel.getRecommendetVacancies();
        adapter = new RecommendedVacanciesRecyclerAdapter(requireActivity(), recommendedVacanciesList);
        recommendedVacanciesRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
    private void loadData() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onResume() {
        super.onResume();

        loadData();
    }
}