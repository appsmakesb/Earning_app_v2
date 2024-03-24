package com.burhanstore.earningmaster.some.topsheet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import com.burhanstore.earningmaster.R;

public class Coins_Dialog extends DialogFragment {
    View root_view;
    LinearLayout ok;
    TextView txt_2,coinss;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root_view = inflater.inflate(R.layout.coins__dialog, container, false);
        ok = root_view.findViewById(R.id.ok);
        txt_2 = root_view.findViewById(R.id.txt_2);
        coinss = root_view.findViewById(R.id.coins);
        String from = getArguments().getString("from");
        String coins = getArguments().getString("coins");
        txt_2.setText("You got "+coins+" coins from "+from);
        coinss.setText(coins);




        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (from.equals("GameZone")) {
                    getActivity().finish();
                    dismiss();
                }else
                {
                    dismiss();
                }
                }
        });
        return root_view;
    }
}