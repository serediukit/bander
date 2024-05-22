package com.serediuk.bander_client.ui.search.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.serediuk.bander_client.R;
import com.serediuk.bander_client.model.dao.BandsDAO;
import com.serediuk.bander_client.model.dao.VacanciesDAO;
import com.serediuk.bander_client.model.entity.Band;
import com.serediuk.bander_client.model.entity.Resume;
import com.serediuk.bander_client.model.entity.Vacancy;
import com.serediuk.bander_client.model.enums.ResumeStatus;


import java.util.ArrayList;

public class ResumeHistoryRecyclerAdapter extends RecyclerView.Adapter<ResumeHistoryRecyclerAdapter.ViewHolder> {
    private final Context context;
    private final ArrayList<Resume> resumeHistoryList;
    private final BandsDAO bandsDAO;
    private final VacanciesDAO vacanciesDAO;

    public ResumeHistoryRecyclerAdapter(Context context, ArrayList<Resume> resumeHistoryList) {
        this.context = context;
        this.resumeHistoryList = resumeHistoryList;
        bandsDAO = BandsDAO.getInstance();
        vacanciesDAO = VacanciesDAO.getInstance();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.resume_status_item, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Resume resume = resumeHistoryList.get(position);
        Vacancy vacancy = vacanciesDAO.readVacancy(resume.getVacancyUID());
        Band band = bandsDAO.readBand(vacancy.getBandUID());

        holder.textVacancyTitle.setText(vacancy.getRole());
        holder.textVacancyBand.setText(band.getName());
        holder.textVacancyGenres.setText(band.getGenres());
        holder.textVacancySalary.setText(vacancy.getSalary());
        holder.textVacancyText.setText(vacancy.getText());
        holder.textResumeText.setText(resume.getText());
        holder.textResumeDatetime.setText(resume.getDatetime());
        holder.textResumeStatus.setText(resume.getStatus());

        if (resume.getStatus().equals(ResumeStatus.ACCEPTED.toString())) {
            holder.textResumeStatus.setBackground(context.getDrawable(R.drawable.corner_radius_status_accepted));
        } else if (resume.getStatus().equals(ResumeStatus.DECLINED.toString())) {
            holder.textResumeStatus.setBackground(context.getDrawable(R.drawable.corner_radius_status_declined));
        }
    }

    @Override
    public int getItemCount() {
        return resumeHistoryList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textVacancyTitle, textVacancyBand, textVacancyGenres, textVacancySalary;
        TextView textVacancyText, textResumeText, textResumeDatetime, textResumeStatus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textVacancyTitle = itemView.findViewById(R.id.vacancyTitle);
            textVacancyBand = itemView.findViewById(R.id.vacancyBand);
            textVacancyGenres = itemView.findViewById(R.id.vacancyGenres);
            textVacancySalary = itemView.findViewById(R.id.vacancySalary);
            textVacancyText = itemView.findViewById(R.id.vacancyText);
            textResumeText = itemView.findViewById(R.id.resumeText);
            textResumeDatetime = itemView.findViewById(R.id.resumeDatetime);
            textResumeStatus = itemView.findViewById(R.id.resumeStatus);
        }
    }
}
