package com.serediuk.bander_client.ui.search.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.serediuk.bander_client.R;
import com.serediuk.bander_client.model.dao.CandidatesDAO;
import com.serediuk.bander_client.model.entity.Candidate;
import com.serediuk.bander_client.model.entity.Resume;
import com.serediuk.bander_client.ui.search.resume.ResumeInfoActivity;

import java.util.ArrayList;

public class ReceivedResumesRecyclerAdapter extends RecyclerView.Adapter<ReceivedResumesRecyclerAdapter.ViewHolder>{
    private Context context;
    private ArrayList<Resume> receivedResumesList;
    private CandidatesDAO candidatesDAO;

    public ReceivedResumesRecyclerAdapter(Context context, ArrayList<Resume> receivedResumesList) {
        this.context = context;
        this.receivedResumesList = receivedResumesList;
        candidatesDAO = CandidatesDAO.getInstance();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.resume_item, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Resume resume = receivedResumesList.get(position);
        Candidate candidate = candidatesDAO.readCandidate(resume.getCandidateUID());

        holder.textTitle.setText(candidate.getName() + " " + candidate.getSurname());
        holder.textRole.setText(candidate.getRole());
        holder.textGenres.setText(candidate.getPreferredGenres());
        holder.textExperience.setText(candidate.getExperience());
        holder.textDatetime.setText(resume.getDatetime());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ResumeInfoActivity.class);
            intent.putExtra("resume", resume);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return receivedResumesList.size();
    }

    public void setArrayList(ArrayList<Resume> receivedResumes) {
        receivedResumesList = receivedResumes;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textTitle, textRole, textGenres, textExperience, textDatetime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textTitle = itemView.findViewById(R.id.resumeTitle);
            textRole = itemView.findViewById(R.id.resumeRole);
            textGenres = itemView.findViewById(R.id.resumeGenres);
            textExperience = itemView.findViewById(R.id.resumeExperience);
            textDatetime = itemView.findViewById(R.id.resumeDatetime);
        }
    }
}
