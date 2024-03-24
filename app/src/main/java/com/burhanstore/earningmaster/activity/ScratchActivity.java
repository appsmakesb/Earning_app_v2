package com.burhanstore.earningmaster.activity;

import static com.burhanstore.earningmaster.helper.PrefManager.user_main_points;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.burhanstore.earningmaster.R;
import com.burhanstore.earningmaster.util.Ads_Controller;
import com.burhanstore.earningmaster.util.Constant;
import com.burhanstore.earningmaster.util.SomeEarn_Controller;

public class ScratchActivity extends AppCompatActivity {

    private Context activity;
    private TextView points_textView, Scratch_Text1, Scratch_Text2, Scratch_Text3, Scratch_Coin1, Scratch_Coin2, Scratch_Coin3;
    private Button Scratch_Btn1, Scratch_Btn2, Scratch_Btn3;
    private ImageView Scratch_Image1, Scratch_Image2, Scratch_Image3;
    Ads_Controller ads_controller;
    SomeEarn_Controller someEarn_controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scratch);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getString(R.string.scratch_to_win));
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_baseline_back));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        ads_controller = new Ads_Controller(this);
        activity = this;
        Scratch_Text1 = findViewById(R.id.Scratch_Text1);
        Scratch_Text2 = findViewById(R.id.Scratch_Text2);
        Scratch_Text3 = findViewById(R.id.Scratch_Text3);
        Scratch_Coin1 = findViewById(R.id.Scratch_Coin1);
        Scratch_Coin2 = findViewById(R.id.Scratch_Coin2);
        Scratch_Coin3 = findViewById(R.id.Scratch_Coin3);
        Scratch_Btn1 = findViewById(R.id.Scratch_Btn1);
        Scratch_Btn2 = findViewById(R.id.Scratch_Btn2);
        Scratch_Btn3 = findViewById(R.id.Scratch_Btn3);
        Scratch_Image1 = findViewById(R.id.Scratch_Image1);
        Scratch_Image2 = findViewById(R.id.Scratch_Image2);
        Scratch_Image3 = findViewById(R.id.Scratch_Image3);
        points_textView = findViewById(R.id.Balance_TextView);

        Scratch_Text1.setText("Additional Scratch");
        Scratch_Text2.setText("Extra Scratch");
        Scratch_Text3.setText("Great Scratch");
        //Coin

        someEarn_controller = new SomeEarn_Controller(this);
        Scratch_Coin1.setText(someEarn_controller.getAdditional_Scratch_Point());
        Scratch_Coin2.setText(someEarn_controller.getExtra_Scratch_Point());
        Scratch_Coin3.setText(someEarn_controller.getGreat_Scratch_Point());

        Scratch_Image1.setImageResource(R.drawable.scratch_ic);
        Scratch_Image2.setImageResource(R.drawable.scratch_ic);
        Scratch_Image3.setImageResource(R.drawable.scratch_ic);

        //onClick View
        OnClickView();
        setPointsText();

        //Vungle
        Constant.initVungle((Activity) activity);
        Constant.loadAdVungle((Activity) activity);

        //Adcolony
        Constant.initRewardedAdAdColony((Activity) activity);

        //Unity Ads
        Constant.initUnityAds((Activity) activity);

    }

    private void setPointsText() {
        points_textView.setText("0");
        user_main_points(points_textView);
    }

    private void OnClickView() {
        Scratch_Btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ScratchActivity.this, AScratchActivity.class);
                startActivity(i);
            }
        });
        Scratch_Btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ScratchActivity.this, ExtraScratchActivity.class);
                startActivity(i);
            }
        });
        Scratch_Btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ScratchActivity.this, GreatScratchActivity.class);
                startActivity(i);
            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        //  Video Ads
        String typeOfAds = ads_controller.getScratch_win_ads1();
        switch (typeOfAds) {
            case "Admob":
                Constant.showRewardedAdmobAds((Activity) activity);
                break;
            case "Facebook":
                Constant.showRewardedFacebookAds((Activity) activity);
                break;
            case "Vungle":
                Constant.showRewardedVungleAds((Activity) activity);
                break;

            case "UnityAds":
                //Unity A
                Constant.ShowUnityAds((Activity) activity);
                break;
            case "AdColony":
                Constant.ShowAdcolonyAds((Activity) activity);

                break;
            case "Startapp":
                Constant.StartappRewardedVideo((Activity) activity);

                break;
            case "applovin":
                Constant.ApplovinShowAds((Activity) activity);

                break;
        }

    }

}