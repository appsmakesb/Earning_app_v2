package com.burhanstore.earningmaster.activity;

import static com.burhanstore.earningmaster.helper.PrefManager.User_Add_Coins;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Window;
import android.widget.Toast;

import com.burhanstore.earningmaster.R;
import com.burhanstore.earningmaster.util.Ads_Controller;
import com.burhanstore.earningmaster.util.Ads_ID_Controller;
import com.burhanstore.earningmaster.util.SomeEarn_Controller;
import com.burhanstore.earningmaster.util.Constant;
import com.pollfish.Pollfish;
import com.pollfish.builder.Params;
import com.pollfish.callback.PollfishClosedListener;
import com.pollfish.callback.PollfishOpenedListener;
import com.pollfish.callback.PollfishSurveyCompletedListener;
import com.pollfish.callback.PollfishSurveyNotAvailableListener;
import com.pollfish.callback.PollfishSurveyReceivedListener;
import com.pollfish.callback.PollfishUserNotEligibleListener;
import com.pollfish.callback.PollfishUserRejectedSurveyListener;
import com.pollfish.callback.SurveyInfo;

import org.jetbrains.annotations.NotNull;

public class PollfishActivity extends Activity implements
        PollfishSurveyCompletedListener,
        PollfishOpenedListener,
        PollfishClosedListener,
        PollfishSurveyReceivedListener,
        PollfishSurveyNotAvailableListener,
        PollfishUserNotEligibleListener,
        PollfishUserRejectedSurveyListener {

    private static final String TAG = "MainActivity";

    //start
    private Context activity;
    private String random_points;
    public int poiints = 0;
    public boolean rewardShow = true, interstitialShow = true;

    //
    Ads_Controller ads_controller;
    SomeEarn_Controller someEarn_controller;
    //end
    private Dialog dialog;
    Ads_ID_Controller ads_id_controller;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pollfish);

        activity = this;
        ads_controller = new Ads_Controller(this);
        someEarn_controller = new SomeEarn_Controller(this);
        ads_id_controller = new Ads_ID_Controller(this);

        //Loading Dialog
        dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.loading_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        //end
        initPollfish();


        if (Constant.isNetworkAvailable(activity)) {

        } else {
            Constant.showInternetErrorDialog(activity, getResources().getString(R.string.internet_connection_of_text));
        }


    }


    //end

    public void initPollfish() {
        Params params = new Params.Builder(ads_id_controller.getpollfish_key())
                .rewardMode(true)
                .offerwallMode(true)
                .build();

        Pollfish.initWith(this, params);
    }

    @Override
    public void onPollfishSurveyCompleted(@NotNull SurveyInfo surveyInfo) {

        String DAILY_TYPE = "Complete Survey Reward";
        random_points = String.valueOf(surveyInfo.getRewardValue());
        User_Add_Coins(PollfishActivity.this, random_points, DAILY_TYPE);

        Toast.makeText(activity, ">>>>>You get " + surveyInfo.getRewardValue() + " coins! <<<<<", Toast.LENGTH_SHORT).show();


    }

    @Override
    public void onPollfishSurveyReceived(SurveyInfo surveyInfo) {
        dialog.dismiss();
        Pollfish.show();
        //     coinsBtn.setText(getString(R.string.win_coins, surveyInfo != null && surveyInfo.getRewardValue() != null ? surveyInfo.getRewardValue() : 100));
        Toast.makeText(activity, getString(R.string.survey_received), Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onPollfishClosed() {

    }

    @Override
    public void onPollfishOpened() {

    }

    @Override
    public void onPollfishSurveyNotAvailable() {
        Toast.makeText(activity, "Pollfish Survey Not Available", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUserNotEligible() {
        //  coinsBtn.setVisibility(View.GONE);
        //  loggingTxt.setText(R.string.user_not_eligible);
    }

    @Override
    public void onUserRejectedSurvey() {
        // coinsBtn.setVisibility(View.GONE);
        //    loggingTxt.setText(R.string.user_rejected_survey);
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(PollfishActivity.this, MainActivity.class);
        startActivity(i);
        finish();
    }
}
