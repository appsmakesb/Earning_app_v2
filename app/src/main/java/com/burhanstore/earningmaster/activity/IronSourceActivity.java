package com.burhanstore.earningmaster.activity;

import static com.burhanstore.earningmaster.helper.PrefManager.User_Add_Coins;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.TextView;

import com.burhanstore.earningmaster.util.Ads_ID_Controller;
import com.ironsource.mediationsdk.IronSource;
import com.ironsource.mediationsdk.logger.IronSourceError;
import com.ironsource.mediationsdk.sdk.OfferwallListener;
import com.burhanstore.earningmaster.R;
import com.burhanstore.earningmaster.util.Ads_Controller;
import com.burhanstore.earningmaster.util.SomeEarn_Controller;

public class IronSourceActivity extends AppCompatActivity implements OfferwallListener {
    private Context activity;
    private static final String TAG = "Custom_Ads_Tag";
    private Dialog dialog;

    //start
    private TextView IronSourceOfferWall_count_textView, points_textView, points_text;
    boolean first_time = true, IronSourceOfferWall_first = true;
    private int IronSourceOfferWall_count = 1;
    public int poiints = 0;
    public boolean rewardShow = true, interstitialShow = true;


    //
    Ads_Controller ads_controller;
    SomeEarn_Controller someEarn_controller;
    //end
    Ads_ID_Controller ads_id_controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iron_source);

        //
        activity = this;
        ads_controller = new Ads_Controller(this);
        someEarn_controller = new SomeEarn_Controller(this);
        ads_id_controller = new Ads_ID_Controller(this);

        initIronSource();
        initAdsListener();
        IronSource.showOfferwall();

        dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.loading_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

    }


    private void initIronSource() {
        IronSource.init(this, ads_id_controller.getIronSource_App_Key(), IronSource.AD_UNIT.OFFERWALL);
    }

    private void initAdsListener() {
        IronSource.setOfferwallListener(this);
    }

    //region Offerwall Ad
    @Override
    public void onOfferwallAvailable(boolean b) {
        Log.d(TAG, "onOfferwallAvailable: ");

        //Show
        IronSource.showOfferwall();
    }

    @Override
    public void onOfferwallOpened() {
        Log.d(TAG, "onOfferwallOpened: ");
        dialog.dismiss();
    }

    @Override
    public void onOfferwallShowFailed(IronSourceError ironSourceError) {
        Log.d(TAG, "onOfferwallShowFailed: ");
        //Show
        IronSource.showOfferwall();
    }

    @Override
    public boolean onOfferwallAdCredited(int i, int i1, boolean b) {
        Log.d(TAG, "onOfferwallAdCredited: ");


        String DAILY_TYPE = "Iron Source Reward";
        User_Add_Coins(activity, "50", DAILY_TYPE);


        return false;
    }

    @Override
    public void onGetOfferwallCreditsFailed(IronSourceError ironSourceError) {
        Log.d(TAG, "onGetOfferwallCreditsFailed: ");
    }

    @Override
    public void onOfferwallClosed() {
        Log.d(TAG, "onOfferwallClosed: ");

        Intent i = new Intent(IronSourceActivity.this, MainActivity.class);
        startActivity(i);
        finish();
    }
    //endregion

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
        IronSource.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: ");
        IronSource.onPause(this);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}