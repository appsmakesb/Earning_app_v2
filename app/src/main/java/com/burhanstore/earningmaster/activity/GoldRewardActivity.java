package com.burhanstore.earningmaster.activity;

import static com.burhanstore.earningmaster.helper.AppController.hidepDialog;
import static com.burhanstore.earningmaster.helper.PrefManager.User_Add_Coins;
import static com.burhanstore.earningmaster.helper.PrefManager.user_main_points;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.burhanstore.earningmaster.BaseUrl;
import com.burhanstore.earningmaster.R;
import com.burhanstore.earningmaster.helper.AppController;
import com.burhanstore.earningmaster.helper.JsonRequest;
import com.burhanstore.earningmaster.util.Ads_Controller;
import com.burhanstore.earningmaster.util.Ads_ID_Controller;
import com.burhanstore.earningmaster.util.SomeEarn_Controller;
import com.burhanstore.earningmaster.util.Constant;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.unity3d.ads.IUnityAdsInitializationListener;
import com.unity3d.ads.IUnityAdsListener;
import com.unity3d.ads.IUnityAdsShowListener;
import com.unity3d.ads.UnityAds;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class GoldRewardActivity extends AppCompatActivity implements IUnityAdsListener, IUnityAdsInitializationListener, IUnityAdsShowListener {
    //Unity
    private static final String PLACEMENT_ID_SKIPPABLE_VIDEO = "video";
    private static final String PLACEMENT_ID_REWARDED_VIDEO = "rewardedVideo";
    private String mGameId;
    private Dialog UnityDialog, dialogAdcolony, dialogStartAppVideoAds, ApplovinDialog;
    //

    CardView GetMyCoin;
    private Context activity;
    private TextView GoldRewardTextView, points_textView;
    SomeEarn_Controller someEarn_controller;
    Ads_Controller ads_controller;
    private LinearLayout adLayout;
    Ads_ID_Controller ads_id_controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gold_reward);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_baseline_back));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        activity = this;
        GetMyCoin = findViewById(R.id.GetMyCoin);
        GoldRewardTextView = findViewById(R.id.GoldRewardTextView);
        points_textView = findViewById(R.id.points_text_in_toolbar);
        ads_controller = new Ads_Controller(this);
        adLayout = findViewById(R.id.banner_container);

        ads_id_controller = new Ads_ID_Controller(this);

        GetMyCoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkDailyCoin();
            }
        });
        someEarn_controller = new SomeEarn_Controller(this);
        GoldRewardTextView.setText(someEarn_controller.getGoldRewardPoint());
        setPointsText();

        //Vungle
        Constant.initVungle((Activity) activity);
        Constant.loadAdVungle((Activity) activity);

        //Adcolony
        Constant.initRewardedAdAdColony((Activity) activity);

        if (ads_controller.getBannerAdsControl().equals("Admob")) {
            loadBannerAdmob();
        } else if (ads_controller.getBannerAdsControl().equals("Facebook")) {
            loadBannerFacebook();
        }

        //Unity ads
        Constant.initUnityAds((Activity) activity);

    }

    private void setPointsText() {

        points_textView.setText("0");
    }

    @Override
    protected void onResume() {
        AppController.getInstance();
        user_main_points(points_textView);
        super.onResume();
    }


    private void checkDailyCoin() {
        if (Constant.isNetworkAvailable(activity)) {

            String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
            Log.e("TAG", "onClick: Current Date" + currentDate);
            String gold_reward_date = Constant.getString(activity, Constant.GoldReward_DATE);
            Log.e("TAG", "onClick: gold_reward_date Date" + gold_reward_date);
            if (gold_reward_date.equals("")) {
                Constant.setString(activity, Constant.GoldReward_DATE, currentDate);
                //
                String DAILY_TYPE = "Gold Reward";
                User_Add_Coins(GoldRewardActivity.this, GoldRewardTextView.getText().toString(), DAILY_TYPE);

                showDialogOfPoints(Integer.parseInt(GoldRewardTextView.getText().toString()));
                if (getApplicationContext() == null) {
                    return;
                }

                //
                String work_dateDate = Constant.getString(activity, Constant.GoldReward_DATE);
                UserWorkUpdateDate(work_dateDate);
                //

                setPointsText();

            } else {
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                try {
                    Date pastDAte = sdf.parse(gold_reward_date);
                    Date currentDAte = sdf.parse(currentDate);
                    long diff = currentDAte.getTime() - pastDAte.getTime();
                    long difference_In_Days = (diff / (1000 * 60 * 60 * 24)) % 365;
                    Log.e("TAG", "onClick: Days Diffrernce" + difference_In_Days);
                    if (difference_In_Days > 0) {
                        Constant.setString(activity, Constant.GoldReward_DATE, currentDate);

                        //
                        //
                        String work_dateDate = Constant.getString(activity, Constant.GoldReward_DATE);
                        UserWorkUpdateDate(work_dateDate);
                        //

                        //
                        String DAILY_TYPE = "Gold Reward";
                        User_Add_Coins(GoldRewardActivity.this, GoldRewardTextView.getText().toString(), DAILY_TYPE);

                        showDialogOfPoints(Integer.parseInt(GoldRewardTextView.getText().toString()));
                        if (getApplicationContext() == null) {
                            return;
                        }

                    } else {
                        //   showDialogOfPoints(0);

                        Toast.makeText(activity, getResources().getString(R.string.today_checkin_over), Toast.LENGTH_SHORT).show();
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        } else {
            Constant.showInternetErrorDialog(activity, getResources().getString(R.string.internet_connection_of_text));
        }
    }

    public void showDialogOfPoints(int points) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.show_points_add_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);

        ImageView imageView = dialog.findViewById(R.id.points_image);
        TextView title_text = dialog.findViewById(R.id.title_text_points);
        TextView points_text = dialog.findViewById(R.id.points);
        AppCompatButton add_btn = dialog.findViewById(R.id.add_btn);

        if (points == Integer.parseInt(GoldRewardTextView.getText().toString())) {
            title_text.setText(getResources().getString(R.string.you_win));
            points_text.setVisibility(View.VISIBLE);
            points_text.setText(String.valueOf(points));
            add_btn.setText(getResources().getString(R.string.account_add_to_wallet));

        } else {
            title_text.setText(getResources().getString(R.string.today_checkin_over));
            points_text.setVisibility(View.GONE);
            add_btn.setText(getResources().getString(R.string.ok_text));

            //
            //
            String work_dateDate = Constant.getString(activity, Constant.GoldReward_DATE);
            UserWorkUpdateDate(work_dateDate);
            //


        }
        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                //  Video Ads
                String typeOfAds = ads_controller.getCollect_reward_ads();
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
                        //Unity
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
        });
        dialog.show();

    }

    @Override
    public void onInitializationComplete() {
        final java.util.Timer AdTimer = new Timer();
        AdTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(() -> {
                    showUnitySkippAbleVideoAd();
                });
            }
        }, 5000);
    }

    @Override
    public void onInitializationFailed(UnityAds.UnityAdsInitializationError error, String message) {

    }

    @Override
    public void onUnityAdsReady(String placementId) {

    }

    @Override
    public void onUnityAdsStart(String placementId) {

    }

    @Override
    public void onUnityAdsFinish(String placementId, UnityAds.FinishState result) {

    }

    @Override
    public void onUnityAdsError(UnityAds.UnityAdsError error, String message) {

    }

    @Override
    public void onUnityAdsShowFailure(String placementId, UnityAds.UnityAdsShowError error, String message) {

    }

    @Override
    public void onUnityAdsShowStart(String placementId) {

    }

    @Override
    public void onUnityAdsShowClick(String placementId) {

    }

    @Override
    public void onUnityAdsShowComplete(String placementId, UnityAds.UnityAdsShowCompletionState state) {

    }


    private void initUnityAds() {
        mGameId = ads_id_controller.getUnity_Game_Id();
        UnityAds.setDebugMode(true);
        UnityAds.addListener(this);
        UnityAds.initialize(GoldRewardActivity.this, mGameId, true, this);
    }

    private void showUnitySkippAbleVideoAd() {
        if (!UnityAds.isReady(PLACEMENT_ID_SKIPPABLE_VIDEO)) {

            return;
        }
        UnityAds.show(this, PLACEMENT_ID_SKIPPABLE_VIDEO, this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        //  Video Ads
        String typeOfAds = ads_controller.getCollect_reward_ads();
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
                //Unity
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

    private void loadBannerAdmob() {

        final AdView adView = new AdView(activity);
        adView.setAdSize(AdSize.MEDIUM_RECTANGLE);
        adView.setAdUnitId(ads_id_controller.getAdmob_banner_id());
        adView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                adLayout.addView(adView);
            }

            @Override
            public void onAdFailedToLoad(LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                Log.v("admob", "error" + loadAdError.getMessage());
            }
        });
        adView.loadAd(new AdRequest.Builder().build());

    }

    private void loadBannerFacebook() {
        com.facebook.ads.AdView adView = new com.facebook.ads.AdView(activity, getResources().getString(R.string.facebook_banner_ads_id), com.facebook.ads.AdSize.RECTANGLE_HEIGHT_250);
        adView.loadAd();
        adLayout.addView(adView);

    }


    private void UserWorkUpdateDate(String data) {
        JsonRequest jsonReq = new JsonRequest(Request.Method.POST, BaseUrl.LUCKY_WIN_API, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                hidepDialog();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("get_user_data_goldrr_date", "any");
                params.put("id", AppController.getInstance().getId());
                params.put("UpdateWork", data);

                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(jsonReq);
    }


}