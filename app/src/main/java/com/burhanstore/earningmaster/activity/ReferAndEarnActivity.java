package com.burhanstore.earningmaster.activity;

import static com.burhanstore.earningmaster.helper.PrefManager.user_main_points;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.burhanstore.earningmaster.R;
import com.burhanstore.earningmaster.helper.AppController;
import com.burhanstore.earningmaster.util.Ads_Controller;
import com.burhanstore.earningmaster.util.Constant;

public class ReferAndEarnActivity extends AppCompatActivity {
    TextView ReferCode, ReferShare, txtcopy, txtinvite;
    Toolbar toolbar;
    String preText = "";
    private TextView points_textView;
    private Context mContext;
    Ads_Controller ads_controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refer_and_earn);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_baseline_back));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mContext = this;
        ReferCode = findViewById(R.id.ReferCode);
        txtcopy = findViewById(R.id.txtcopy);
        ReferShare = findViewById(R.id.ReferShare);
        points_textView = findViewById(R.id.user_points_text_view);

        setPointsText();

        ReferCode.setText(AppController.getInstance().getRefercodee());
        txtcopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ClipboardManager clipboard = (ClipboardManager) mContext.getSystemService(CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("label", AppController.getInstance().getRefercodee());
                clipboard.setPrimaryClip(clip);
                Constant.showToastMessage(mContext, "Refer Code Copied");
            }
        });
        ReferShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, mContext.getResources().getString(R.string.share_text) + "' " + AppController.getInstance().getRefercodee() + " '" + " Download Link = " + " https://play.google.com/store/apps/details?id=" + mContext.getPackageName());
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
            }
        });


    }

    private void setPointsText() {

        points_textView.setText("0");
        user_main_points(points_textView);
    }


}