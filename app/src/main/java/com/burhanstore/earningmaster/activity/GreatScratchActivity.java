package com.burhanstore.earningmaster.activity;

import static com.burhanstore.earningmaster.helper.AppController.hidepDialog;
import static com.burhanstore.earningmaster.helper.PrefManager.User_Add_Coins;
import static com.burhanstore.earningmaster.helper.PrefManager.user_main_points;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
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
import com.burhanstore.earningmaster.helper.AppController;
import com.burhanstore.earningmaster.helper.JsonRequest;
import com.burhanstore.earningmaster.util.Ads_ID_Controller;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.burhanstore.earningmaster.R;
import com.burhanstore.earningmaster.services.PointsService;
import com.burhanstore.earningmaster.util.Ads_Controller;
import com.burhanstore.earningmaster.util.SomeEarn_Controller;
import com.burhanstore.earningmaster.util.Constant;
import com.unity3d.ads.IUnityAdsInitializationListener;
import com.unity3d.ads.IUnityAdsListener;
import com.unity3d.ads.IUnityAdsShowListener;
import com.unity3d.ads.UnityAds;

import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import dev.skymansandy.scratchcardlayout.listener.ScratchListener;
import dev.skymansandy.scratchcardlayout.ui.ScratchCardLayout;

public class GreatScratchActivity extends AppCompatActivity implements ScratchListener, IUnityAdsListener, IUnityAdsInitializationListener, IUnityAdsShowListener {
    //Unity
    private static final String PLACEMENT_ID_SKIPPABLE_VIDEO = "video";
    private static final String PLACEMENT_ID_REWARDED_VIDEO = "rewardedVideo";
    private String mGameId;
    private Dialog UnityDialog;
    //

    private LinearLayout adLayout;
    private Context activity;
    private TextView collectReward_count_textView, points_textView, points_text;
    boolean first_time = true, collectReward_first = true;
    private int collectReward_count = 1;
    private final String TAG = "AScratchActivity";
    private String random_points;
    public int poiints = 0;
    public boolean rewardShow = true, interstitialShow = true;
    ScratchCardLayout scratchCardLayout;

    //
    Ads_Controller ads_controller;
    SomeEarn_Controller someEarn_controller;
    Ads_ID_Controller ads_id_controller;

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_great_scratch);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Additional Scratch");
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_baseline_back));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //
        activity = this;
        ads_controller = new Ads_Controller(this);
        someEarn_controller = new SomeEarn_Controller(this);
        ads_id_controller = new Ads_ID_Controller(this);

        adLayout = findViewById(R.id.banner_container);
        points_text = findViewById(R.id.textView_points_show);
        collectReward_count_textView = findViewById(R.id.limit_text);
        points_textView = findViewById(R.id.Balance_TextView);

        if (ads_controller.getBannerAdsControl().equals("Admob")) {
            loadBannerAdmob();
        } else if (ads_controller.getBannerAdsControl().equals("Facebook")) {
            loadBannerFacebook();
        }

        if (Constant.isNetworkAvailable(activity)) {

            //  Interstitial Ads Load
            String typeOfAds = ads_controller.getScratch_win_ads3_Interstitial();
            switch (typeOfAds) {
                case "Admob":
                    Constant.LoadAdmobInterstitialAd();
                    break;
                case "Facebook":
                    Constant.LoadFacebookInterstitialAd();
                    break;
                case "Vungle":
                    Constant.initVungleInterstitial();
                    Constant.loadVungleAdInterstitial();
                    break;

                case "AdColony":
                    Constant.AdcolonyInitInterstitialAd();
                    break;
                case "Startapp":
                    Constant.LoadStartAppInterstitial((Activity) activity);
                    break;
                case "IronSource":
                    Constant.IronSourceInterstitialInt((Activity) activity);
                    break;

            }

            Constant.loadRewardedVideo((Activity) activity);
        } else {
            Constant.showInternetErrorDialog(activity, getResources().getString(R.string.internet_connection_of_text));
        }
        onInit();
        setPointsText();

        //

        if (collectReward_first) {
            collectReward_first = false;
            Log.e(TAG, "onCollectRewardStarted: " + random_points);
            if (Constant.isNetworkAvailable(activity)) {
                random_points = "";
                //     Random random = new Random();
                random_points = String.valueOf(someEarn_controller.getGreat_Scratch_Point());
                if (random_points == null || random_points.equalsIgnoreCase("null")) {
                    random_points = String.valueOf(1);
                }
                points_text.setText(random_points);
                Log.e(TAG, "onCollectRewardStarted: " + random_points);
            } else {
                Constant.showInternetErrorDialog(activity, getResources().getString(R.string.internet_connection_of_text));
            }
        }

        scratchCardLayout = findViewById(R.id.scratch_view_layout);
        scratchCardLayout.setScratchListener(this);

        //Today Date
        DateToday();

        //Vungle
        Constant.initVungle((Activity) activity);
        Constant.loadAdVungle((Activity) activity);

        //Adcolony
        Constant.initRewardedAdAdColony((Activity) activity);

        //Unity Ads
        Constant.initUnityAds((Activity) activity);
    }

    private void DateToday() {
        TextView text_view_date_activity = findViewById(R.id.text_view_date_activity);
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
        text_view_date_activity.setText(dateFormat.format(date));
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


    private void onInit() {

        String CollectRewardCount = Constant.getString(activity, Constant.Great_Scratch_COUNT);
        if (CollectRewardCount.equals("0")) {
            CollectRewardCount = "";
            Log.e("TAG", "onInit: scratch card 0");
        }
        if (CollectRewardCount.equals("")) {
            Log.e("TAG", "onInit: scratch card empty vala part");
            String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
            Log.e("TAG", "onClick: Current Date" + currentDate);
            String last_date = Constant.getString(activity, Constant.LAST_DATE_Great_SCRATCH);
            Log.e("TAG", "Lat date" + last_date);
            if (last_date.equals("")) {
                Log.e("TAG", "onInit: last date empty part");
                setScratchCount(someEarn_controller.getAdditional_Scratch_Chances());
                Constant.setString(activity, Constant.Great_Scratch_COUNT, someEarn_controller.getAdditional_Scratch_Chances());
                Constant.setString(activity, Constant.LAST_DATE_Great_SCRATCH, currentDate);
                //
                Log.e("TAG", "onInit: last date empty part");
                setScratchCount(someEarn_controller.getAdditional_Scratch_Chances());
                Constant.setString(activity, Constant.Great_Scratch_COUNT, someEarn_controller.getAdditional_Scratch_Chances());
                Constant.setString(activity, Constant.LAST_DATE_Great_SCRATCH, currentDate);

                //
                //
                String work_dateDate = Constant.getString(activity, Constant.LAST_DATE_Great_SCRATCH);
                UserWorkUpdateDate(work_dateDate);
                //

            } else {
                Log.e("TAG", "onInit: last date not empty part");
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                try {
                    Date current_date = sdf.parse(currentDate);
                    Date lastDate = sdf.parse(last_date);
                    long diff = current_date.getTime() - lastDate.getTime();
                    long difference_In_Days = (diff / (1000 * 60 * 60 * 24)) % 365;
                    Log.e("TAG", "onClick: Days Difference" + difference_In_Days);
                    if (difference_In_Days > 0) {

                        //
                        Constant.setString(activity, Constant.LAST_DATE_Great_SCRATCH, currentDate);

                        //
                        String work_dateDate = Constant.getString(activity, Constant.LAST_DATE_Great_SCRATCH);
                        UserWorkUpdateDate(work_dateDate);

                        //     Constant.setString(activity, Constant.Collect_Reward_COUNT, someEarn_controller.getCollectRewardCount());
                        setScratchCount(Constant.getString(activity, Constant.Great_Scratch_COUNT));
                        Log.e("TAG", "onClick: today date added to preference" + currentDate);
                    } else {
                        setScratchCount("0");
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        } else {
            Log.e("TAG", "onInit: scracth card in preference part");
            setScratchCount(CollectRewardCount);
        }
    }

    private void setScratchCount(String string) {
        if (string != null || !string.equalsIgnoreCase(""))
            collectReward_count_textView.setText("" + string);

        UserWorkUpdate(string);
        //
        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        UserWorkUpdateDate(currentDate);

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

    private void showDialogPoints(final int addOrNoAddValue, final String points, final int counter) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.show_points_add_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);

        ImageView imageView = dialog.findViewById(R.id.points_image);
        TextView title_text = dialog.findViewById(R.id.title_text_points);
        TextView points_text = dialog.findViewById(R.id.points);
        AppCompatButton add_btn = dialog.findViewById(R.id.add_btn);
        if (Constant.isNetworkAvailable(activity)) {
            if (addOrNoAddValue == 1) {
                if (points.equals("0")) {
                    Log.e("TAG", "showDialogPoints: 0 points");
                    imageView.setImageResource(R.drawable.sad_ic);
                    title_text.setText(getResources().getString(R.string.better_luck_next_time));
                    points_text.setVisibility(View.VISIBLE);
                    points_text.setText(points);
                    add_btn.setText(getResources().getString(R.string.ok_text));
                } else {
                    Log.e("TAG", "showDialogPoints: points");

                    title_text.setText(getResources().getString(R.string.you_win));
                    points_text.setVisibility(View.VISIBLE);
                    points_text.setText(points);
                    add_btn.setText(getResources().getString(R.string.account_add_to_wallet));
                }
            } else {
                Log.e("TAG", "showDialogPoints: chance over");
                imageView.setImageResource(R.drawable.donee);
                title_text.setText(getResources().getString(R.string.today_work_is_over));
                points_text.setVisibility(View.GONE);
                add_btn.setText(getResources().getString(R.string.ok_text));

                //
                String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                UserWorkUpdateDate(currentDate);

            }
            add_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //  Interstitial Ads Show
                    String typeOfAds = ads_controller.getScratch_win_ads3_Interstitial();
                    switch (typeOfAds) {
                        case "Admob":
                            Constant.ShowAdmobInterstitialAds();
                            break;
                        case "Facebook":
                            Constant.ShowFacebookInterstitialAds();
                            break;
                        case "Vungle":
                            Constant.playVungleAdInterstitial();
                            break;
                        case "AdColony":
                            Constant.AdcolonyDisplayInterstitialAd();
                            break;
                        case "Startapp":
                            Constant.startAppInterstitialShow();
                            break;
                        case "IronSource":
                            Constant.IronSourceInterstitialShow();
                            break;

                    }

                    first_time = true;
                    collectReward_first = true;
                    scratchCardLayout.resetScratch();
                    poiints = 0;
                    if (addOrNoAddValue == 1) {
                        if (points.equals("0")) {
                            String current_counter = String.valueOf(counter - 1);
                            Constant.setString(activity, Constant.Great_Scratch_COUNT, current_counter);
                            setScratchCount(Constant.getString(activity, Constant.Great_Scratch_COUNT));
                            dialog.dismiss();
                        } else {
                            String current_counter = String.valueOf(counter - 1);
                            Constant.setString(activity, Constant.Great_Scratch_COUNT, current_counter);
                            setScratchCount(Constant.getString(activity, Constant.Great_Scratch_COUNT));
                            try {
                                int finalPoint;
                                if (points.equals("")) {
                                    finalPoint = 0;
                                } else {
                                    finalPoint = Integer.parseInt(points);
                                }
                                poiints = finalPoint;

                                //
                                String DAILY_TYPE = "Great Scratch Win Reward";
                                User_Add_Coins(GreatScratchActivity.this, String.valueOf(finalPoint), DAILY_TYPE);

                                setPointsText();
                            } catch (NumberFormatException ex) {
                                Log.e("TAG", "onScratchComplete: " + ex.getMessage());
                            }
                            dialog.dismiss();
                        }
                    } else {
                        dialog.dismiss();
                    }
                    if (collectReward_count == Integer.parseInt(someEarn_controller.getRewarded_and_interstitial_count())) {
                        if (rewardShow) {
                            Log.e(TAG, "onReachTarget: rewaded ads showing method");
                            showDailog();
                            rewardShow = false;
                            interstitialShow = true;
                            collectReward_count = 1;
                        } else if (interstitialShow) {
                            Log.e(TAG, "onReachTarget: interstital ads showing method");
                            //  Interstitial Ads Show
                            String typeOfAdsTimer = ads_controller.getScratch_win_ads3_Interstitial();
                            switch (typeOfAdsTimer) {
                                case "Admob":
                                    Constant.ShowAdmobInterstitialAds();
                                    break;
                                case "Facebook":
                                    Constant.ShowFacebookInterstitialAds();
                                    break;
                                case "Vungle":
                                    Constant.playVungleAdInterstitial();
                                    break;
                                case "AdColony":
                                    Constant.AdcolonyDisplayInterstitialAd();
                                    break;
                                case "Startapp":
                                    Constant.startAppInterstitialShow();
                                    break;
                                case "IronSource":
                                    Constant.IronSourceInterstitialShow();
                                    break;

                            }

                            rewardShow = true;
                            interstitialShow = false;
                            collectReward_count = 1;
                        }
                    } else {
                        collectReward_count += 1;
                    }
                }
            });
        } else {
            Constant.showInternetErrorDialog(activity, getResources().getString(R.string.internet_connection_of_text));
        }
        dialog.show();
    }

    public void showDailog() {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.show_points_dialog_video_ads_show);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);

        ImageView imageView = dialog.findViewById(R.id.points_image);
        TextView title_text = dialog.findViewById(R.id.title_text_points);
        TextView points_text = dialog.findViewById(R.id.points);
        AppCompatButton add_btn = dialog.findViewById(R.id.add_btn_video_ads);
        AppCompatButton cancel_btn = dialog.findViewById(R.id.cancel_btn_video_ads);
        cancel_btn.setVisibility(View.VISIBLE);
        imageView.setImageResource(R.drawable.video_ads);
        add_btn.setText("Yes");
        title_text.setText("Watch Full Video");
        points_text.setText("To Unlock this Reward Points");

        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
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

        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (poiints != 0) {
                    String po = Constant.getString(activity, Constant.USER_POINTS);
                    if (po.equals("")) {
                        po = "0";
                    }
                    int current_Points = Integer.parseInt(po);
                    int finalPoints = current_Points - poiints;
                    Constant.setString(activity, Constant.USER_POINTS, String.valueOf(finalPoints));
                    Intent serviceIntent = new Intent(activity, PointsService.class);
                    serviceIntent.putExtra("points", Constant.getString(activity, Constant.USER_POINTS));
                    activity.startService(serviceIntent);
                    setPointsText();
                }
            }
        });

        dialog.show();
    }


    @Override
    public void onScratchComplete() {
        if (first_time) {
            first_time = false;
            Log.e("onCollectReward", "Complete");
            Log.e("onCollectReward", "Complete" + random_points);

            int counter = Integer.parseInt(collectReward_count_textView.getText().toString());
            if (counter == 0) {
                showDialogPoints(0, "0", counter);

            } else {
                showDialogPoints(1, points_text.getText().toString(), counter);
            }
        }

    }

    @Override
    public void onScratchProgress(@NonNull ScratchCardLayout scratchCardLayout, int i) {

        if (collectReward_first) {
            collectReward_first = false;
            Log.e(TAG, "onCollectRewardStarted: " + random_points);
            if (Constant.isNetworkAvailable(activity)) {
                random_points = "";
                random_points = String.valueOf(someEarn_controller.getGreat_Scratch_Point());
                if (random_points == null || random_points.equalsIgnoreCase("null")) {
                    random_points = String.valueOf(1);
                }
                points_text.setText(random_points);
                Log.e(TAG, "onCollectRewardStarted: " + random_points);
            } else {
                Constant.showInternetErrorDialog(activity, getResources().getString(R.string.internet_connection_of_text));
            }
        }
    }

    @Override
    public void onScratchStarted() {

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        //  Video Ads
        String typeOfAds = ads_controller.getScratch_win_ads3();
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

    private void initUnityAds() {
        mGameId = ads_id_controller.getUnity_Game_Id();
        UnityAds.setDebugMode(true);
        UnityAds.addListener(this);
        UnityAds.initialize(GreatScratchActivity.this, mGameId, true, this);
    }

    private void showUnitySkippAbleVideoAd() {
        if (!UnityAds.isReady(PLACEMENT_ID_SKIPPABLE_VIDEO)) {

            return;
        }
        UnityAds.show(this, PLACEMENT_ID_SKIPPABLE_VIDEO, this);
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


    //
    private void UserWorkUpdate(String count) {
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
                params.put("get_user_data_grtsc", "any");
                params.put("id", AppController.getInstance().getId());
                params.put("UpdateWork", count);

                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(jsonReq);
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
                params.put("get_user_data_grtsc_date", "any");
                params.put("id", AppController.getInstance().getId());
                params.put("UpdateWork", data);

                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(jsonReq);
    }


}