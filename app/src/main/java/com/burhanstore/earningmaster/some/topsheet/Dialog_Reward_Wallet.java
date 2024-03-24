package com.burhanstore.earningmaster.some.topsheet;

import static com.burhanstore.earningmaster.helper.PrefManager.redeem_package;

import android.content.Context;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.DialogFragment;

import com.burhanstore.earningmaster.R;
import com.burhanstore.earningmaster.helper.AppController;

public class Dialog_Reward_Wallet extends DialogFragment {
    View view;
    EditText pdReward;
    TextView valueReward;
    String p_detailsReward;
    AppCompatButton closeRewardR, redeemBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.dialog_reward_row, container, false);

        pdReward = view.findViewById(R.id.edit_text_redeem);
        closeRewardR = view.findViewById(R.id.close_redeem);
        redeemBtn = view.findViewById(R.id.redeemBtn);
        valueReward = view.findViewById(R.id.value_redeem);

        String coins = getArguments().getString("coins");
        String id = getArguments().getString("id");
        String uri = getArguments().getString("uri");
        String symbol = getArguments().getString("symbol");
        String amount = getArguments().getString("amount");
        String type = getArguments().getString("type").trim();
        String hint = getArguments().getString("hint");
        String more = getArguments().getString("more");
        String amount_id = getArguments().getString("amount_id");
        pdReward.setHint(hint);
        if (type.equals("email")) {
            pdReward.setText(AppController.getInstance().getEmail());
            pdReward.setEnabled(false);
            pdReward.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        } else if (type.equals("phone")) {
            pdReward.setEnabled(true);
            pdReward.setInputType(InputType.TYPE_CLASS_PHONE);
        } else if (type.equals("number")) {
            pdReward.setEnabled(true);
            pdReward.setInputType(InputType.TYPE_CLASS_NUMBER);
        } else if (type.equals("text")) {
            pdReward.setEnabled(true);
            pdReward.setInputType(InputType.TYPE_CLASS_TEXT);
        }
        valueReward.setText(coins + " = " + symbol + " " + amount);

        closeRewardR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        Context contextt = getContext();

        redeemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                p_detailsReward = String.valueOf(pdReward.getText());
                if (type.equals("email")) {
                    dismiss();
                    redeem_package(contextt, id, p_detailsReward, amount_id);
                } else if (type.equals("phone")) {
                    if (pdReward.length() >= 10) {
                        dismiss();
                        redeem_package(contextt, id, p_detailsReward, amount_id);
                    } else {
                        pdReward.setError("Enter valid number");
                    }
                } else if (type.equals("number")) {
                    if (pdReward.length() >= 1) {
                        dismiss();
                        redeem_package(contextt, id, p_detailsReward, amount_id);
                    } else {
                        dismiss();
                        pdReward.setError("Error");
                    }
                } else if (type.equals("text")) {
                    if (pdReward.length() > 0) {
                        dismiss();
                        redeem_package(contextt, id, p_detailsReward, amount_id);
                    } else {
                        pdReward.setError("Error");
                    }
                } else {
                    if (pdReward.length() > 0) {
                        dismiss();
                        redeem_package(contextt, id, p_detailsReward, amount_id);
                    } else {
                        pdReward.setError("Error");
                    }
                }
            }
        });

        return view;
    }


}