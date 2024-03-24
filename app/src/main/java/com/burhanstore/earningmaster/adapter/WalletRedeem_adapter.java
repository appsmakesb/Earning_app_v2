package com.burhanstore.earningmaster.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.burhanstore.earningmaster.BaseUrl;
import com.burhanstore.earningmaster.R;
import com.burhanstore.earningmaster.models.Redeem_model;
import com.burhanstore.earningmaster.some.topsheet.Dialog_Reward_Wallet;

import java.util.List;


public class WalletRedeem_adapter extends RecyclerView.Adapter<WalletRedeem_adapter.ViewHolder> {
    public List<Redeem_model> historyList;
    public Context context;


    public WalletRedeem_adapter(List<Redeem_model> historyList, Context context) {
        this.historyList = historyList;
        this.context = context;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_redeem_package_row, parent, false);
        return new ViewHolder(view);

    }

    @NonNull

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Redeem_model model = historyList.get(position);
        Glide.with(context).load(BaseUrl.ADMIN_URL + model.getImage())
                .apply(new RequestOptions().placeholder(R.drawable.app_icon))
                .into(holder.img);
        holder.amount.setText(model.getSymbol() + model.getAmount());
        holder.coins.setText(model.getCoins());


        holder.click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
                Dialog_Reward_Wallet newFragment = new Dialog_Reward_Wallet();
                Bundle args = new Bundle();
                args.putString("coins", model.getCoins());
                args.putString("id", model.getId());
                args.putString("uri", model.getImage());
                args.putString("symbol", model.getSymbol());
                args.putString("amount", model.getAmount());
                args.putString("type", model.getType());
                args.putString("hint", model.getHint());
                args.putString("more", model.getDetails());
                args.putString("amount_id", model.getAmount_id());

                newFragment.setArguments(args);
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                transaction.add(android.R.id.content, newFragment).addToBackStack(null).commit();
            }
        });


    }


    @Override
    public int getItemCount() {

        return historyList.size();


    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView amount, coins;
        RelativeLayout click;

        public ViewHolder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img);
            amount = itemView.findViewById(R.id.amount);
            coins = itemView.findViewById(R.id.coins);
            click = itemView.findViewById(R.id.click);


        }


    }
}

