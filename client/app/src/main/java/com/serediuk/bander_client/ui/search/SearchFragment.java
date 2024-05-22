package com.serediuk.bander_client.ui.search;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.serediuk.bander_client.databinding.FragmentSearchBinding;
import com.serediuk.bander_client.model.entity.Resume;
import com.serediuk.bander_client.model.entity.Vacancy;
import com.serediuk.bander_client.model.enums.UserType;

import java.util.ArrayList;

public class SearchFragment extends Fragment {
    private SearchViewModel searchViewModel;
    private RecyclerView recommendedVacanciesRecyclerView;
    private RecyclerView receivedResumesRecyclerView;
    private ArrayList<Vacancy> recommendedVacanciesList;
    private ArrayList<Resume> receivedResumesList;
    private RecommendedVacanciesRecyclerAdapter recommendedVacanciesAdapter;
    private ReceivedResumesRecyclerAdapter receivedResumesAdapter;
    private TextView mEmptyText, mBandEmptyText;

    private ConstraintLayout candidateLayout;
    private ConstraintLayout bandLayout;



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
        candidateLayout = binding.candidateSearchLayout;
        bandLayout = binding.bandSearchLayout;
        String userType = searchViewModel.getUserType();

        if (userType.equals(UserType.CANDIDATE.toString())) {
            mEmptyText = binding.searchCandidateEmptyText;
            recommendedVacanciesRecyclerView = binding.searchCandidateRecyclerView;
            recommendedVacanciesRecyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));

            recommendedVacanciesList = searchViewModel.getRecommendedVacancies();
            if (recommendedVacanciesList.size() == 0) {
                mEmptyText.setVisibility(View.VISIBLE);
                recommendedVacanciesRecyclerView.setVisibility(View.INVISIBLE);
            } else {
                mEmptyText.setVisibility(View.INVISIBLE);
                recommendedVacanciesRecyclerView.setVisibility(View.VISIBLE);
                recommendedVacanciesAdapter = new RecommendedVacanciesRecyclerAdapter(requireActivity(), recommendedVacanciesList);
                recommendedVacanciesRecyclerView.setAdapter(recommendedVacanciesAdapter);
                searchViewModel.setRecommendedAdapter(recommendedVacanciesAdapter);
            }
        } else if (userType.equals(UserType.BAND.toString())) {
            mBandEmptyText = binding.searchBandEmptyText;
            receivedResumesRecyclerView = binding.searchBandRecyclerView;
            receivedResumesRecyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));

            receivedResumesList = searchViewModel.getReceivedResumesList();
            if (receivedResumesList.size() == 0) {
                mBandEmptyText.setVisibility(View.VISIBLE);
                receivedResumesRecyclerView.setVisibility(View.INVISIBLE);
            } else {
                mBandEmptyText.setVisibility(View.INVISIBLE);
                receivedResumesRecyclerView.setVisibility(View.VISIBLE);
                receivedResumesAdapter = new ReceivedResumesRecyclerAdapter(requireActivity(), receivedResumesList);
                receivedResumesRecyclerView.setAdapter(receivedResumesAdapter);
                searchViewModel.setReceivedAdapter(receivedResumesAdapter);
            }
        }
    }
    private void loadData() {
        String userType = searchViewModel.getUserType();

        if (userType.equals(UserType.CANDIDATE.toString())) {
            candidateLayout.setVisibility(View.VISIBLE);
            bandLayout.setVisibility(View.INVISIBLE);

            if (recommendedVacanciesList.size() == 0) {
                mEmptyText.setVisibility(View.VISIBLE);
                recommendedVacanciesRecyclerView.setVisibility(View.INVISIBLE);
            } else {
                mEmptyText.setVisibility(View.INVISIBLE);
                recommendedVacanciesRecyclerView.setVisibility(View.VISIBLE);
            }
        } else if (userType.equals(UserType.BAND.toString())) {
            candidateLayout.setVisibility(View.INVISIBLE);
            bandLayout.setVisibility(View.VISIBLE);

            if (receivedResumesList.size() == 0) {
                mBandEmptyText.setVisibility(View.VISIBLE);
                receivedResumesRecyclerView.setVisibility(View.INVISIBLE);
            } else {
                mBandEmptyText.setVisibility(View.INVISIBLE);
                receivedResumesRecyclerView.setVisibility(View.VISIBLE);
            }
        }
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