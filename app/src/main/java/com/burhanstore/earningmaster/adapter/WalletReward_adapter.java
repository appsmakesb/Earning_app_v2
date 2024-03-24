package com.burhanstore.earningmaster.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.burhanstore.earningmaster.BaseUrl;
import com.burhanstore.earningmaster.R;
import com.burhanstore.earningmaster.activity.RedeemActivity;
import com.burhanstore.earningmaster.helper.AppController;
import com.burhanstore.earningmaster.models.Reward_model;
import com.squareup.picasso.Picasso;

import java.util.List;

public class WalletReward_adapter extends RecyclerView.Adapter<WalletReward_adapter.ViewHolder> {
    public List<Reward_model> historyList;
    public Context context;
    Picasso picasso;

    public WalletReward_adapter(List<Reward_model> historyList, Context context) {

        this.historyList = historyList;
        this.context = context;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reward_row, parent, false);
        return new ViewHolder(view);
    }

    @NonNull

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Reward_model model = historyList.get(position);

        Glide.with(context).load(BaseUrl.ADMIN_URL + model.getImage())
                .apply(new RequestOptions().placeholder(R.drawable.app_icon))
                .into(holder.img);
        //holder.symb.setText(""+model.getSymbol());
        holder.title.setText(model.getName());


        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, RedeemActivity.class);
                i.putExtra("arry", model.getArr());
                i.putExtra("symb", model.getSymbol());
                i.putExtra("image", model.getImage());
                i.putExtra("input", model.getInput_type());
                i.putExtra("hint", model.getHint());
                i.putExtra("title", model.getName());
                i.putExtra("id", "" + model.getId());
                i.putExtra("type", model.getInput_type());
                i.putExtra("details", model.getDetails());
                context.startActivity(i);


                //show_package(context,model.getCoins(),""+model.getId(),model.getImage(),model.getSymbol(),model.getAmount(),model.getInput_type(),model.getHint(),model.getDetails());
            }
        });

        int coi = Integer.parseInt(AppController.getInstance().getPoints());
        if (coi >= model.getMinimum()) {
            holder.points.setText("Completed!");
            holder.rupee.setVisibility(View.GONE);
        } else {
            int a = model.getMinimum() - coi;
            holder.points.setText(a + " coins need to redeem !");
        }


    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView points, title;
        ImageView img, rupee;
        LinearLayout pend, claim;
        LinearLayout cv;

        public ViewHolder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img);
            cv = itemView.findViewById(R.id.cv);
            points = itemView.findViewById(R.id.points);
            title = itemView.findViewById(R.id.title);
            rupee = itemView.findViewById(R.id.rupee);


        }

    }

}

