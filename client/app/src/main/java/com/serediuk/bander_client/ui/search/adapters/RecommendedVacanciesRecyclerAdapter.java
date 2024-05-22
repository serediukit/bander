package com.serediuk.bander_client.ui.search.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.serediuk.bander_client.R;
import com.serediuk.bander_client.model.dao.BandsDAO;
import com.serediuk.bander_client.model.entity.Band;
import com.serediuk.bander_client.ui.search.VacancyInfoActivity;
import com.serediuk.bander_client.model.entity.Vacancy;

import java.util.ArrayList;

public class RecommendedVacanciesRecyclerAdapter extends RecyclerView.Adapter<RecommendedVacanciesRecyclerAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Vacancy> recommendedVacanciesList;
    private BandsDAO bandsDAO;

    public RecommendedVacanciesRecyclerAdapter(Context context, ArrayList<Vacancy> recommendedVacanciesList) {
        this.context = context;
        this.recommendedVacanciesList = recommendedVacanciesList;
        bandsDAO = BandsDAO.getInstance();
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
        Band band = bandsDAO.readBand(vacancy.getBandUID());

        holder.textTitle.setText(vacancy.getRole());
        holder.textBand.setText(band.getName());
        holder.textGenres.setText(band.getGenres());
        holder.textInfo.setText(vacancy.getText());
        holder.textSalary.setText(vacancy.getSalary());
        holder.textDatetime.setText(vacancy.getDatetime());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, VacancyInfoActivity.class);
            intent.putExtra("vacancy", vacancy);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return recommendedVacanciesList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textTitle, textBand, textGenres, textInfo, textSalary, textDatetime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textTitle = itemView.findViewById(R.id.vacancyTitle);
            textBand = itemView.findViewById(R.id.vacancyBand);
            textGenres = itemView.findViewById(R.id.vacancyGenres);
            textInfo = itemView.findViewById(R.id.vacancyText);
            textSalary = itemView.findViewById(R.id.vacancySalary);
            textDatetime = itemView.findViewById(R.id.vacancyDatetime);
        }
    }
}
