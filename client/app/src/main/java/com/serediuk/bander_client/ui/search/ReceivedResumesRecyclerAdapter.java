package com.serediuk.bander_client.ui.search;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
        return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(@NonNull View itemView) {
            super(itemView);


        }
    }
}
