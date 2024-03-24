package com.burhanstore.earningmaster.activity;

import static com.burhanstore.earningmaster.helper.PrefManager.User_Add_Coins;
import static com.burhanstore.earningmaster.helper.PrefManager.user_main_points;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;

import com.bluehomestudio.luckywheel.LuckyWheel;
import com.bluehomestudio.luckywheel.OnLuckyWheelReachTheTarget;
import com.bluehomestudio.luckywheel.WheelItem;
import com.burhanstore.earningmaster.helper.AppController;
import com.burhanstore.earningmaster.util.Ads_ID_Controller;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.burhanstore.earningmaster.util.AppConfig;
import com.burhanstore.earningmaster.R;
import com.burhanstore.earningmaster.services.PointsService;
import com.burhanstore.earningmaster.util.Ads_Controller;
import com.burhanstore.earningmaster.util.SomeEarn_Controller;
import com.burhanstore.earningmaster.util.Constant;
import com.startapp.sdk.ads.banner.Banner;
import com.startapp.sdk.ads.banner.BannerListener;
import com.startapp.sdk.adsbase.StartAppAd;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class SpinToWinActivity extends AppCompatActivity {

    List<WheelItem> wheelItems = new ArrayList<>();
    LuckyWheel luckyWheel;
    SpinToWinActivity activity;
    private AppCompatButton play_btn;
    private String points;
    TextView user_points_text_view, spin_count_text_view;
    private int spin_count = 1;
    private String TAG = "SpinActivity";
    public StartAppAd startAppAd;
    public int poiints = 0;
    public boolean rewardShow = true, interstitialShow = true;
    SomeEarn_Controller someEarn_controller;
    Ads_Controller ads_controller;
    private LinearLayout adLayout;
    Ads_ID_Controller ads_id_controller;

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);

    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {

        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.spin_win_activity);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Lucky Spin");
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_baseline_back));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        activity = this;

        ads_id_controller = new Ads_ID_Controller(this);
        ads_controller = new Ads_Controller(this);
        someEarn_controller = new SomeEarn_Controller(this);
        luckyWheel = findViewById(R.id.lwv);
        adLayout = findViewById(R.id.banner_container);
        play_btn = findViewById(R.id.play);
        user_points_text_view = findViewById(R.id.user_points_text_view);
        spin_count_text_view = findViewById(R.id.spin_count_textView);

        wheelItems.add(new WheelItem(ResourcesCompat.getColor(getResources(), R.color.color3, null), BitmapFactory.decodeResource(getResources(), R.drawable.coin_ic), "0"));

        wheelItems.add(new WheelItem(ResourcesCompat.getColor(getResources(), R.color.color4, null), BitmapFactory.decodeResource(getResources(), R.drawable.coin_ic), "3"));

        wheelItems.add(new WheelItem(ResourcesCompat.getColor(getResources(), R.color.color5, null), BitmapFactory.decodeResource(getResources(), R.drawable.coin_ic), "5"));

        wheelItems.add(new WheelItem(ResourcesCompat.getColor(getResources(), R.color.color6, null), BitmapFactory.decodeResource(getResources(), R.drawable.coin_ic), "7"));

        wheelItems.add(new WheelItem(ResourcesCompat.getColor(getResources(), R.color.color7, null), BitmapFactory.decodeResource(getResources(), R.drawable.coin_ic), "9"));

        wheelItems.add(new WheelItem(ResourcesCompat.getColor(getResources(), R.color.color8, null), BitmapFactory.decodeResource(getResources(), R.drawable.coin_ic), "11"));

        wheelItems.add(new WheelItem(ResourcesCompat.getColor(getResources(), R.color.color9, null), BitmapFactory.decodeResource(getResources(), R.drawable.coin_ic), "13"));

        wheelItems.add(new WheelItem(ResourcesCompat.getColor(getResources(), R.color.color10, null), BitmapFactory.decodeResource(getResources(), R.drawable.coin_ic), "15"));

        wheelItems.add(new WheelItem(ResourcesCompat.getColor(getResources(), R.color.color11, null), BitmapFactory.decodeResource(getResources(), R.drawable.coin_ic), "18"));

        wheelItems.add(new WheelItem(ResourcesCompat.getColor(getResources(), R.color.color12, null), BitmapFactory.decodeResource(getResources(), R.drawable.coin_ic), "20"));


        luckyWheel.addWheelItems(wheelItems);
        if (Constant.isNetworkAvailable(activity)) {
            loadBanner();

            //  Interstitial Ads Load
            String typeOfAds = ads_controller.getSpin_Interstitial_ads();
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

        } else {
            Constant.showInternetErrorDialog(activity, getResources().getString(R.string.internet_connection_of_text));
        }
        onClick();

        //date
        TextView text_view_date_activity = findViewById(R.id.text_view_date_activity);
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
        text_view_date_activity.setText(dateFormat.format(date));

        //Vungle
        Constant.initVungle((Activity) activity);
        Constant.loadAdVungle((Activity) activity);

        //Adcolony
        Constant.initRewardedAdAdColony((Activity) activity);
        //Banner
        if (ads_controller.getBannerAdsControl().equals("Admob")) {
            loadBannerAdmob();
        } else if (ads_controller.getBannerAdsControl().equals("Facebook")) {
            loadBannerFacebook();
        }

        //unity Ads
        Constant.initUnityAds(activity);

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


    private void LoadInterstitial() {
        if (startAppAd == null) {
            startAppAd = new StartAppAd(AppConfig.getContext());
            startAppAd.loadAd(StartAppAd.AdMode.AUTOMATIC);
        } else {
            startAppAd.loadAd(StartAppAd.AdMode.AUTOMATIC);
        }
    }

    private void ShowInterstital() {
        if (startAppAd != null && startAppAd.isReady()) {
            startAppAd.showAd();
        }
    }

    private void setPointsText() {

        user_points_text_view.setText("0");
        user_main_points(user_points_text_view);
    }

    @Override
    protected void onResume() {
        AppController.getInstance();
        super.onResume();
    }


    private void onClick() {
        setPointsText();

        String spinCount = Constant.getString(activity, Constant.SPIN_COUNT);
        if (spinCount.equals("0")) {
            spinCount = "";
            Log.e("TAG", "onInit: spin card 0");
        }
        if (spinCount.equals("")) {
            Log.e("TAG", "onInit: spin card empty vala part");
            String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
            Log.e("TAG", "onClick: Current Date" + currentDate);
            String last_date = Constant.getString(activity, Constant.LAST_DATE_SPIN);
            Log.e("TAG", "Lat date" + last_date);
            if (last_date.equals("")) {
                Log.e("TAG", "onInit: last date empty part");
                spin_count_text_view.setText(someEarn_controller.getSpinCount());
                Constant.setString(activity, Constant.SPIN_COUNT, someEarn_controller.getSpinCount());
                Constant.setString(activity, Constant.LAST_DATE_SPIN, currentDate);
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
                        Constant.setString(activity, Constant.LAST_DATE_SPIN, currentDate);
                        Constant.setString(activity, Constant.SPIN_COUNT, someEarn_controller.getSpinCount());
                        spin_count_text_view.setText(Constant.getString(activity, Constant.SPIN_COUNT));
                        Log.e("TAG", "onClick: today date added to preference" + currentDate);
                    } else {
                        spin_count_text_view.setText("0");
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        } else {
            Log.e("TAG", "onInit: spin in preference part");
            spin_count_text_view.setText(spinCount);
        }

        play_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Constant.isNetworkAvailable(activity)) {
                    play_btn.setEnabled(false);
                    Random random = new Random();
                    points = String.valueOf(random.nextInt(10));
                    if (points.equals("0")) {
                        points = String.valueOf(1);
                    }
                    Log.e(TAG, "onClick: " + points);
                    luckyWheel.rotateWheelTo(Integer.parseInt(points));
                } else {
                    Constant.showInternetErrorDialog(activity, getResources().getString(R.string.internet_connection_of_text));
                }
            }
        });

        luckyWheel.setLuckyWheelReachTheTarget(new OnLuckyWheelReachTheTarget() {
            @Override
            public void onReachTarget() {
                try {
                    WheelItem item = wheelItems.get(Integer.parseInt(points) - 1);
                    String points_amount = item.text;
                    Log.e("TAG", "onReachTarget: " + points_amount);

                    int counter = Integer.parseInt(spin_count_text_view.getText().toString());
                    if (counter == 0) {
                        showDialogPoints(0, "0", counter);
                    } else {
                        showDialogPoints(1, points_amount, counter);
                    }
                } catch (Exception e) {
                    Log.e(TAG, "onReachTarget: " + e.getMessage().toString());
                }

            }
        });
    }

    private void showDialogPoints(final int addValueOrNoAdd, final String points, final int counter) {
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
            if (addValueOrNoAdd == 1) {
                if (points.equals("0")) {
                    Log.e("TAG", "spinDialog: 0 points");
                    //    imageView.setImageResource(R.drawable.ic_trophy);
                    title_text.setText(getResources().getString(R.string.better_luck_next_time));
                    points_text.setVisibility(View.VISIBLE);
                    points_text.setText(points);
                    add_btn.setText(getResources().getString(R.string.ok_text));
                } else {
                    Log.e("TAG", "spinDialog: points");
                    //    imageView.setImageResource(R.drawable.ic_trophy);
                    title_text.setText(getResources().getString(R.string.you_win));
                    points_text.setVisibility(View.VISIBLE);
                    points_text.setText(points);
                    add_btn.setText(getResources().getString(R.string.account_add_to_wallet));
                }
            } else {
                Log.e("TAG", "spinDialog: chance over");
                //  imageView.setImageResource(R.drawable.ic_trophy);
                title_text.setText(getResources().getString(R.string.today_work_is_over));
                points_text.setVisibility(View.GONE);
                add_btn.setText(getResources().getString(R.string.ok_text));
            }

            add_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    //  Interstitial Ads Show
                    String typeOfAdsTimer = ads_controller.getSpin_Interstitial_ads();
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


                    play_btn.setEnabled(true);
                    poiints = 0;
                    if (addValueOrNoAdd == 1) {
                        if (points.equals("0")) {
                            String current_counter = String.valueOf(counter - 1);
                            Constant.setString(activity, Constant.SPIN_COUNT, current_counter);
                            spin_count_text_view.setText(Constant.getString(activity, Constant.SPIN_COUNT));
                            dialog.dismiss();
                        } else {
                            String current_counter = String.valueOf(counter - 1);
                            Constant.setString(activity, Constant.SPIN_COUNT, current_counter);
                            spin_count_text_view.setText(Constant.getString(activity, Constant.SPIN_COUNT));
                            try {
                                int finalPoint;
                                if (points.equals("")) {
                                    finalPoint = 0;
                                } else {
                                    finalPoint = Integer.parseInt(points);
                                }
                                poiints = finalPoint;
                                Log.e(TAG, "onClick: " + poiints);

                                //
                                String DAILY_TYPE = "Spin Win Reward";
                                User_Add_Coins(SpinToWinActivity.this, String.valueOf(finalPoint), DAILY_TYPE);


                                setPointsText();
                            } catch (NumberFormatException ex) {
                                Log.e("TAG", "onScratchComplete: " + ex.getMessage().toString());
                            }
                            dialog.dismiss();
                        }
                    } else {
                        dialog.dismiss();
                    }
                    if (spin_count == Integer.parseInt(someEarn_controller.getRewarded_and_interstitial_count())) {
                        if (rewardShow) {
                            Log.e(TAG, "onReachTarget: rewaded ads showing method");
                            if (ads_controller.getInt_AdsApp().equals("startapp")) {
                                ShowInterstital();
                            } else {
                                showDailog();
                            }
                            rewardShow = false;
                            interstitialShow = true;
                            spin_count = 1;
                        } else if (interstitialShow) {
                            Log.e(TAG, "onReachTarget: interstital ads showing method");

                            //  Interstitial Ads Show
                            String typeOfAds = ads_controller.getSpin_Interstitial_ads();
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

                            rewardShow = true;
                            interstitialShow = false;
                            spin_count = 1;
                        }
                    } else {
                        spin_count += 1;
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

        AppCompatButton cancel_btn_video_ads = dialog.findViewById(R.id.cancel_btn_video_ads);
        cancel_btn_video_ads.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                    startService(serviceIntent);
                    setPointsText();
                }
            }
        });

        AppCompatButton add_btn_video_ads = dialog.findViewById(R.id.add_btn_video_ads);
        add_btn_video_ads.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();

                //  Video Ads
                String typeOfAds = ads_controller.getSpin_reward_ads();
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
                        Constant.ShowUnityAds(activity);
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
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_enter, R.anim.slide_exit);
        finish();
        //  Video Ads
        String typeOfAds = ads_controller.getSpin_reward_ads();
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
                Constant.ShowUnityAds(activity);

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

    private void loadBanner() {
        final LinearLayout layout = findViewById(R.id.banner_container);
        String typeOfAds = ads_controller.getBanner_Ads();
        if (typeOfAds.equals("admob")) {
            final AdView adView = new AdView(activity);
            adView.setAdSize(AdSize.BANNER);
            adView.setAdUnitId(ads_id_controller.getAdmob_banner_id());
            adView.setAdListener(new AdListener() {
                @Override
                public void onAdLoaded() {
                    super.onAdLoaded();
                    layout.addView(adView);
                }

                @Override
                public void onAdFailedToLoad(LoadAdError loadAdError) {
                    super.onAdFailedToLoad(loadAdError);
                    Log.v("appnext", "client connection error");
                }
            });
            adView.loadAd(new AdRequest.Builder().build());
        } else if (typeOfAds.equals("facebook")) {
            com.facebook.ads.AdView adView = new com.facebook.ads.AdView(activity, getResources().getString(R.string.facebook_banner_ads_id), com.facebook.ads.AdSize.BANNER_HEIGHT_50);
            adView.loadAd();
            layout.addView(adView);
        } else if (typeOfAds.equals("Startapp")) {
            Banner startAppBanner = new Banner(activity, new BannerListener() {
                @Override
                public void onReceiveAd(View view) {

                }

                @Override
                public void onFailedToReceiveAd(View view) {

                }

                @Override
                public void onImpression(View view) {

                }

                @Override
                public void onClick(View view) {

                }
            });
            startAppBanner.loadAd();
            layout.addView(startAppBanner);
        }
    }

}