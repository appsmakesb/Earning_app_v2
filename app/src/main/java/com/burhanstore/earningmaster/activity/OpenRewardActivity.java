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
import com.burhanstore.earningmaster.util.LockableScrollView;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class OpenRewardActivity extends AppCompatActivity {

    TextView Timer, CounterPoint;
    private LinearLayout adLayout;
    private Context activity;
    private Toolbar toolbar;
    private TextView OpenReward_count_textView, points_text;
    boolean first_time = true, openReward_first = true;
    private int openReward_count = 1;
    private final String TAG = "Silver Fragment";
    private String random_points;
    public int poiints = 0;
    public boolean rewardShow = true, interstitialShow = true;
    private LockableScrollView scrollView;
    CardView GetMyCoin;
    TextView points;
    //
    Ads_Controller ads_controller;
    SomeEarn_Controller someEarn_controller;
    Ads_ID_Controller ads_id_controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_reward);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Open Reward");
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
        OpenReward_count_textView = findViewById(R.id.limit_text);
        points = findViewById(R.id.points_text_in_toolbar);

        if (Constant.isNetworkAvailable(activity)) {

            //  Interstitial Ads Load
            String typeOfAds = ads_controller.getOpen_Interstitial_ads();
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
        GetMyCoin = findViewById(R.id.GetMyCoin);
        CounterPoint = findViewById(R.id.CounterPoint);

        CounterPoint.setText("+" + someEarn_controller.getOpenReward());

        if (openReward_first) {
            openReward_first = false;
            Log.e(TAG, "onOpenRewardStarted: " + random_points);
            if (Constant.isNetworkAvailable(activity)) {
                random_points = "";
                //     Random random = new Random();
                random_points = String.valueOf(someEarn_controller.getOpenReward());
                if (random_points == null || random_points.equalsIgnoreCase("null")) {
                    random_points = String.valueOf(1);
                }
                points_text.setText(random_points);
                Log.e(TAG, "onOpenRewardStarted: " + random_points);
            } else {
                Constant.showInternetErrorDialog(activity, getResources().getString(R.string.internet_connection_of_text));
            }
        }

        GetMyCoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (first_time) {
                    first_time = false;
                    Log.e("onOpenReward", "Complete");
                    Log.e("onOpenReward", "Complete" + random_points);
                    String count = OpenReward_count_textView.getText().toString();
                    String[] counteee = count.split("=", 2);
                    String ran = counteee[1];
                    Log.e(TAG, "onOpenRewardComplete: " + ran);
                    int counter = Integer.parseInt(ran.trim());
                    if (counter == 0) {
                        showDialogPoints(0, "0", counter);
                    } else {
                        showDialogPoints(1, points_text.getText().toString(), counter);
                    }
                }

            }
        });

        //Vungle
        Constant.initVungle((Activity) activity);
        Constant.loadAdVungle((Activity) activity);

        //Adcolony
        Constant.initRewardedAdAdColony((Activity) activity);

        //Unity Ads
        Constant.initUnityAds((Activity) activity);
    }

    private void setPointsText() {
        points.setText("0");
    }

    private void onInit() {

        String OpenRewardCount = Constant.getString(activity, Constant.Open_Reward_COUNT);
        if (OpenRewardCount.equals("0")) {
            OpenRewardCount = "";
            Log.e("TAG", "onInit: scratch card 0");
        }
        if (OpenRewardCount.equals("")) {
            Log.e("TAG", "onInit: scratch card empty vala part");
            String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
            Log.e("TAG", "onClick: Current Date" + currentDate);
            String last_date = Constant.getString(activity, Constant.LAST_DATE_Open_Reward);
            Log.e("TAG", "Lat date" + last_date);
            if (last_date.equals("")) {

                //
                Log.e("TAG", "onInit: last date empty part");
                setScratchCount(someEarn_controller.getOpenRewardCount());
                Constant.setString(activity, Constant.Open_Reward_COUNT, someEarn_controller.getOpenRewardCount());
                Constant.setString(activity, Constant.LAST_DATE_Open_Reward, currentDate);

                //
                //
                String work_dateDate = Constant.getString(activity, Constant.LAST_DATE_Collect_Reward);
                UserWorkUpdateDate(work_dateDate);

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
                        Constant.setString(activity, Constant.LAST_DATE_Open_Reward, currentDate);

                        //
                        String work_dateDate = Constant.getString(activity, Constant.LAST_DATE_Open_Reward);
                        UserWorkUpdateDate(work_dateDate);

                        //     Constant.setString(activity, Constant.Collect_Reward_COUNT, someEarn_controller.getCollectRewardCount());
                        setScratchCount(Constant.getString(activity, Constant.Open_Reward_COUNT));
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
            setScratchCount(OpenRewardCount);
        }
    }

    private void setScratchCount(String string) {
        if (string != null || !string.equalsIgnoreCase(""))
            OpenReward_count_textView.setText("Your Today Open Reward Count left = " + string);

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
                    String typeOfAds = ads_controller.getOpen_Interstitial_ads();
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
                    openReward_first = true;

                    poiints = 0;
                    if (addOrNoAddValue == 1) {
                        if (points.equals("0")) {
                            String current_counter = String.valueOf(counter - 1);
                            Constant.setString(activity, Constant.Open_Reward_COUNT, current_counter);
                            setScratchCount(Constant.getString(activity, Constant.Open_Reward_COUNT));
                            dialog.dismiss();
                        } else {
                            String current_counter = String.valueOf(counter - 1);
                            Constant.setString(activity, Constant.Open_Reward_COUNT, current_counter);
                            setScratchCount(Constant.getString(activity, Constant.Open_Reward_COUNT));
                            try {
                                int finalPoint;
                                if (points.equals("")) {
                                    finalPoint = 0;
                                } else {
                                    finalPoint = Integer.parseInt(points);
                                }
                                poiints = finalPoint;

                                String DAILY_TYPE = "Open Reward";
                                User_Add_Coins(OpenRewardActivity.this, someEarn_controller.getOpenReward(), DAILY_TYPE);


                                setPointsText();
                            } catch (NumberFormatException ex) {
                                Log.e("TAG", "onScratchComplete: " + ex.getMessage());
                            }
                            dialog.dismiss();
                        }
                    } else {
                        dialog.dismiss();
                    }
                    if (openReward_count == Integer.parseInt(someEarn_controller.getRewarded_and_interstitial_count())) {
                        if (rewardShow) {
                            Log.e(TAG, "onReachTarget: rewaded ads showing method");
                            showDailog();
                            rewardShow = false;
                            interstitialShow = true;
                            openReward_count = 1;
                        } else if (interstitialShow) {
                            Log.e(TAG, "onReachTarget: interstital ads showing method");

                            //  Interstitial Ads Show
                            String typeOfAdsTimer = ads_controller.getOpen_Interstitial_ads();
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
                            openReward_count = 1;
                        }
                    } else {
                        openReward_count += 1;
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
                String typeOfAds = ads_controller.getOpen_Reward_ads();
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
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        //  Video Ads
        String typeOfAds = ads_controller.getOpen_Reward_ads();
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

    @Override
    protected void onResume() {
        AppController.getInstance();
        user_main_points(points);
        super.onResume();
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
                params.put("get_user_data_openress", "any");
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
                params.put("get_user_data_openree_date", "any");
                params.put("id", AppController.getInstance().getId());
                params.put("UpdateWork", data);

                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(jsonReq);
    }


}