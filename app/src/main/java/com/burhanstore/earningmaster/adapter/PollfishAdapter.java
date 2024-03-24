package com.burhanstore.earningmaster.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

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
import com.burhanstore.earningmaster.util.AppConfig;
import com.burhanstore.earningmaster.R;
import com.burhanstore.earningmaster.util.Ads_ID_Controller;

public class PollfishAdapter implements
        PollfishSurveyCompletedListener,
        PollfishOpenedListener,
        PollfishClosedListener,
        PollfishSurveyReceivedListener,
        PollfishSurveyNotAvailableListener,
        PollfishUserNotEligibleListener,
        PollfishUserRejectedSurveyListener {

    private static Dialog dialog;
    private static Ads_ID_Controller ads_id_controller;

    public static void initPollfish(Activity activity) {
        ads_id_controller = new Ads_ID_Controller(AppConfig.getContext());
        Params params = new Params.Builder(ads_id_controller.getpollfish_key())
                .rewardMode(true)
                .offerwallMode(true)
                .build();

        Pollfish.initWith(activity, params);
    }


    @Override
    public void onUserRejectedSurvey() {

    }

    @Override
    public void onPollfishClosed() {

    }

    @Override
    public void onPollfishOpened() {

    }

    @Override
    public void onPollfishSurveyCompleted(@NonNull SurveyInfo surveyInfo) {

    }

    @Override
    public void onPollfishSurveyNotAvailable() {
        Toast.makeText(AppConfig.getContext(), "Pollfish Survey Not Available", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPollfishSurveyReceived(@Nullable SurveyInfo surveyInfo) {
        dialog.dismiss();
        Pollfish.show();
        //     coinsBtn.setText(getString(R.string.win_coins, surveyInfo != null && surveyInfo.getRewardValue() != null ? surveyInfo.getRewardValue() : 100));
        Toast.makeText(AppConfig.getContext(), AppConfig.getContext().getString(R.string.survey_received), Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onUserNotEligible() {

    }
}
