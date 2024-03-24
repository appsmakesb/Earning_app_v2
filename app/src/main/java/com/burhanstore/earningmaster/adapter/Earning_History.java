package com.burhanstore.earningmaster.adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.burhanstore.earningmaster.R;
import com.burhanstore.earningmaster.models.Model_History;

import java.util.List;

public class Earning_History extends RecyclerView.Adapter<Earning_History.ViewHolder> {
   // implementation 'com.google.firebase:firebase-analytics:17.2.2'
    public List<Model_History> historyList;


    public Earning_History(List<Model_History> historyList) {

        this.historyList = historyList;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_trans, parent, false);
        return new ViewHolder(view);

    }

    @NonNull

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Model_History model = historyList.get(position);
        holder.setDesc(model.getDesc());
        holder.setDate(model.getDate());
        holder.setCoin("+" + model.getCoin());
    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView date, coin, desc;

        public ViewHolder(View itemView) {
            super(itemView);
            date = (TextView) itemView.findViewById(R.id.date);
            coin = (TextView) itemView.findViewById( R.id.coin);
            desc = (TextView) itemView.findViewById(R.id.desc);
        }

        public void setDate(String title) {
            date.setText(title);
        }

        public void setCoin(String description) {
            coin.setText(description);
        }

        public void setDesc(String description) {
            desc.setText(description);
        }


    }
}
