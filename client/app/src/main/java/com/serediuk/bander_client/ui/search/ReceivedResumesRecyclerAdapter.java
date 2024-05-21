package com.serediuk.bander_client.ui.search;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.serediuk.bander_client.R;
import com.serediuk.bander_client.model.entity.Resume;

import java.util.ArrayList;

public class ReceivedResumesRecyclerAdapter extends RecyclerView.Adapter<ReceivedResumesRecyclerAdapter.ViewHolder>{
    Context context;
    ArrayList<Resume> receivedResumesList;

    public ReceivedResumesRecyclerAdapter(Context context, ArrayList<Resume> receivedResumesList) {
        this.context = context;
        this.receivedResumesList = receivedResumesList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.resume_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return receivedResumesList.size();
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
