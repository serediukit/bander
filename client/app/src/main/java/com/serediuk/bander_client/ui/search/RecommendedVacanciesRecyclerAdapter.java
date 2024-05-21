package com.serediuk.bander_client.ui.search;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.serediuk.bander_client.R;
import com.serediuk.bander_client.model.entity.Vacancy;

import java.util.ArrayList;

public class RecommendedVacanciesRecyclerAdapter extends RecyclerView.Adapter<RecommendedVacanciesRecyclerAdapter.ViewHolder> {
    Context context;
    ArrayList<Vacancy> recommendedVacanciesList;

    public RecommendedVacanciesRecyclerAdapter(Context context, ArrayList<Vacancy> recommendedVacanciesList) {
        this.context = context;
        this.recommendedVacanciesList = recommendedVacanciesList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.vacancy_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Vacancy vacancy = recommendedVacanciesList.get(position);

        holder.textTitle.setText(vacancy.getRole());
        holder.textBand.setText(vacancy.getBandUID());
        holder.textInfo.setText(vacancy.getText());
        holder.textSalary.setText(vacancy.getSalary());
        holder.textDatetime.setText(vacancy.getDatetime());
    }

    @Override
    public int getItemCount() {
        return recommendedVacanciesList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textTitle, textBand, textInfo, textSalary, textDatetime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textTitle = itemView.findViewById(R.id.vacancyTitle);
            textBand = itemView.findViewById(R.id.vacancyBand);
            textInfo = itemView.findViewById(R.id.vacancyText);
            textSalary = itemView.findViewById(R.id.vacancySalary);
            textDatetime = itemView.findViewById(R.id.vacancyDatetime);


        }
    }
}
