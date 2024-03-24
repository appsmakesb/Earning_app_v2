package com.burhanstore.earningmaster.fragment;

import static com.burhanstore.earningmaster.helper.AppController.hidepDialog;
import static com.burhanstore.earningmaster.helper.PrefManager.User_Add_Coins;
import static com.burhanstore.earningmaster.helper.PrefManager.user_main_points;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.adcolony.sdk.AdColony;
import com.adcolony.sdk.AdColonyAdOptions;
import com.adcolony.sdk.AdColonyAppOptions;
import com.adcolony.sdk.AdColonyInterstitial;
import com.adcolony.sdk.AdColonyInterstitialListener;
import com.adcolony.sdk.AdColonyReward;
import com.adcolony.sdk.AdColonyRewardListener;
import com.adcolony.sdk.AdColonyZone;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.applovin.adview.AppLovinInterstitialAd;
import com.applovin.adview.AppLovinInterstitialAdDialog;
import com.applovin.sdk.AppLovinAd;
import com.applovin.sdk.AppLovinAdDisplayListener;
import com.applovin.sdk.AppLovinSdk;
import com.burhanstore.earningmaster.BaseUrl;
import com.burhanstore.earningmaster.helper.AppController;
import com.burhanstore.earningmaster.helper.JsonRequest;
import com.unity3d.ads.UnityAds;
import com.burhanstore.earningmaster.util.Ads_ID_Controller;
import com.facebook.ads.Ad;
import com.facebook.ads.RewardedVideoAd;
import com.facebook.ads.RewardedVideoAdListener;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdCallback;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.burhanstore.earningmaster.util.AppConfig;
import com.burhanstore.earningmaster.R;
import com.burhanstore.earningmaster.util.Ads_Controller;
import com.burhanstore.earningmaster.util.SomeEarn_Controller;
import com.burhanstore.earningmaster.util.Constant;
import com.startapp.sdk.adsbase.StartAppAd;
import com.startapp.sdk.adsbase.VideoListener;
import com.startapp.sdk.adsbase.adlisteners.AdEventListener;
import com.vungle.warren.AdConfig;
import com.vungle.warren.InitCallback;
import com.vungle.warren.LoadAdCallback;
import com.vungle.warren.PlayAdCallback;
import com.vungle.warren.Vungle;
import com.vungle.warren.error.VungleException;

import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created by Burhan store
 */
public class VideoAdsFragment extends Fragment {
    private Context activity;
    private TextView Section5Title, Section1Title, Section1PointText, Section2Title, Section2PointText, Section3Title, Section3PointText, Section4Title, Section4PointText, RewardedAdsName1, RewardedAdsName2, RewardedAdsName3, RewardedAdsName4, RewardedAdsName5, RewardedAdsName6, Section5PointText, Section6Title, Section6PointText, Section7Title, RewardedAdsName7, Section7PointText, Section8Title, RewardedAdsName8, Section8PointText;
    private ImageButton Section1Btn, Section2Btn, Section3Btn, Section4Btn, Section5Btn, Section6Btn, Section7Btn, Section8Btn;

    //Vungle
    private String placementID;

    private Dialog UnityDialog, dialog, dialogAdcolony, dialogAdmobVideoAds, dialogStartAppVideoAds, dialogFacebookVideoAds;
    //AppLovin
    private AppLovinInterstitialAdDialog appLovinInterstitialAd;

    private AdColonyInterstitial rewardAdColony;
    private AdColonyInterstitialListener rewardListener;
    private AdColonyAdOptions rewardAdOptions;
    private static boolean isRewardLoaded;

    //Admob
    public RewardedAd rewarded_ad;
    public RewardedAdLoadCallback adLoadCallback;
    public boolean isShowRewarded = true;
    public boolean isShowfacebookRewarded = true;
    //Start
    private TextView Video_Ads_View_count_textView, points_textView, points_text;
    boolean first_time = true, Video_Ads_View_first = true;
    private int Video_Ads_View_count = 1;
    private final String TAG = "VideoAds";
    private String random_points;
    public int poiints = 0;
    Ads_Controller ads_controller;
    SomeEarn_Controller someEarn_controller;
    //
    //facebook
    public RewardedVideoAd rewardedVideoAd;
    Ads_ID_Controller ads_id_controller;

    //end
    public VideoAdsFragment() {

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_video_ads, container, false);

        Section1Title = view.findViewById(R.id.Section1Title);
        Section1PointText = view.findViewById(R.id.Section1PointText);
        Section2Title = view.findViewById(R.id.Section2Title);
        Section2PointText = view.findViewById(R.id.Section2PointText);
        Section3Title = view.findViewById(R.id.Section3Title);
        Section3PointText = view.findViewById(R.id.Section3PointText);
        Section4Title = view.findViewById(R.id.Section4Title);
        Section4PointText = view.findViewById(R.id.Section4PointText);

        Section1Btn = view.findViewById(R.id.Section1Btn);
        Section2Btn = view.findViewById(R.id.Section2Btn);
        Section3Btn = view.findViewById(R.id.Section3Btn);
        Section4Btn = view.findViewById(R.id.Section4Btn);
        RewardedAdsName1 = view.findViewById(R.id.RewardedAdsName1);
        RewardedAdsName2 = view.findViewById(R.id.RewardedAdsName2);
        RewardedAdsName3 = view.findViewById(R.id.RewardedAdsName3);
        RewardedAdsName4 = view.findViewById(R.id.RewardedAdsName4);
        RewardedAdsName5 = view.findViewById(R.id.RewardedAdsName5);
        RewardedAdsName6 = view.findViewById(R.id.RewardedAdsName6);
        Section5PointText = view.findViewById(R.id.Section5PointText);
        Section6Title = view.findViewById(R.id.Section6Title);
        Section6PointText = view.findViewById(R.id.Section6PointText);
        Section5Btn = view.findViewById(R.id.Section5Btn);
        Section6Btn = view.findViewById(R.id.Section6Btn);
        Section5Title = view.findViewById(R.id.Section5Title);
        Section7Title = view.findViewById(R.id.Section7Title);
        RewardedAdsName7 = view.findViewById(R.id.RewardedAdsName7);
        Section7PointText = view.findViewById(R.id.Section7PointText);
        Section8Title = view.findViewById(R.id.Section8Title);
        RewardedAdsName8 = view.findViewById(R.id.RewardedAdsName8);
        Section8PointText = view.findViewById(R.id.Section8PointText);
        Section7Btn = view.findViewById(R.id.Section7Btn);
        Section8Btn = view.findViewById(R.id.Section8Btn);
        points_textView = view.findViewById(R.id.user_points_text_view);
        points_text = view.findViewById(R.id.textView_points_show);
        Video_Ads_View_count_textView = view.findViewById(R.id.limit_text);

        //Real Time Data
        ads_controller = new Ads_Controller(activity);
        someEarn_controller = new SomeEarn_Controller(activity);
        ads_id_controller = new Ads_ID_Controller(activity);

        if (Constant.isNetworkAvailable(activity)) {
            Constant.loadInterstitialAd();
            Constant.loadRewardedVideo((Activity) activity);
        } else {
            Constant.showInternetErrorDialog(activity, getResources().getString(R.string.internet_connection_of_text));
        }
        onEarningInit();
        setPointsText();


        OnclickView();
        SetAll();

        //Vungle
        initViews();
        initVungle();

        //AdColony
        initAdColonySdk();
        initRewardedAdAdColony();

        //Admob Video Ads
        LoadRewardedVideoAdmob();

        //date
        TextView text_view_date_activity = view.findViewById(R.id.text_view_date_activity);
        @SuppressLint("SimpleDateFormat") DateFormat dateFormat = new SimpleDateFormat(getString(R.string.date_formet));
        Date date = new Date();
        text_view_date_activity.setText(dateFormat.format(date));

        //Unity Ads
        Constant.IntUnityAds((Activity) activity);

        return view;
    }

    @SuppressLint("SetTextI18n")
    private void SetAll() {

        Section1PointText.setText(someEarn_controller.getVungle_Rewarded());
        Section2PointText.setText(someEarn_controller.getUnityAds_Rewarded());
        Section3PointText.setText(someEarn_controller.getAppLovin_Rewarded());
        Section4PointText.setText(someEarn_controller.getAdColony_Rewarded());
        Section5PointText.setText(someEarn_controller.getAdmob_Rewarded());
        Section6PointText.setText(someEarn_controller.getStartapp_Rewarded());
        Section7PointText.setText(someEarn_controller.getFacebook_Rewarded());
        Section8PointText.setText(someEarn_controller.getAppLovin_Rewarded());

        Section1Title.setText("Watch Great \nVideos Bonus");
        Section2Title.setText("Additional \nPoints ");
        Section4Title.setText(R.string.Extra_Bonus);
        Section3Title.setText(R.string.Extra_points);
        Section5Title.setText(R.string.watch_great);
        Section6Title.setText("Additional \nBonus ");
        Section7Title.setText("Extra Points ");
        Section8Title.setText("Extra Points ");

        RewardedAdsName1.setText("Vungle");
        RewardedAdsName2.setText("UnityAds");
        RewardedAdsName3.setText("AppLovin");
        RewardedAdsName4.setText("AdColony");
        RewardedAdsName5.setText("Admob");
        RewardedAdsName6.setText("Startapp");
        RewardedAdsName7.setText("Facebook");
        RewardedAdsName8.setText("AppLovin");


    }

    private void OnclickView() {
        //
        String work_dateDate = Constant.getString(activity, Constant.LAST_DATE_Video_Ads_View_Reward);
        UserWorkUpdateDate(work_dateDate);
        //

        Section1Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Vungle
                String count = Video_Ads_View_count_textView.getText().toString();
                if (count.equals("0")) {
                    Toast.makeText(activity, getString(R.string.adds_video_unavailable), Toast.LENGTH_SHORT).show();
                } else {
                    loadAdVungle();
                    playAdVungle();
                    VungleEarningPointAddSection();
                }

            }
        });


        Section2Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //UnityAds
                String count = Video_Ads_View_count_textView.getText().toString();
                if (count.equals("0")) {
                    Toast.makeText(activity, getString(R.string.adds_video_unavailable), Toast.LENGTH_SHORT).show();
                } else {
                    UnityDialog = new Dialog(activity);
                    UnityDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    UnityDialog.setContentView(R.layout.loading_dialog);
                    UnityDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    UnityDialog.show();

                    if (UnityAds.isReady()) {
                        UnityAds.show((Activity) activity);
                        UnityEarningPointAddSection();
                        UnityDialog.dismiss();
                        CallCoinAdded();
                    } else {
                        UnityAds.initialize(activity, ads_id_controller.getUnity_Game_Id());
                    }
                }

            }
        });
        Section3Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //appLovin
                String count = Video_Ads_View_count_textView.getText().toString();
                if (count.equals("0")) {
                    Toast.makeText(activity, getString(R.string.adds_video_unavailable), Toast.LENGTH_SHORT).show();
                } else {
                    dialog = new Dialog(activity);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.loading_dialog);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog.show();
                    ApplovinEarningPointAddSection();
                    appLovinInterstitialAd = AppLovinInterstitialAd.create(AppLovinSdk.getInstance(activity), activity);
                    appLovinInterstitialAd.show();
                    appLovinInterstitialAd.setAdDisplayListener(new AppLovinAdDisplayListener() {
                        @Override
                        public void adDisplayed(AppLovinAd ad) {
                            dialog.dismiss();
                        }

                        @Override
                        public void adHidden(AppLovinAd ad) {
                            CallCoinAdded();
                        }
                    });
                }
            }
        });
        Section4Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //AdColony
                String count = Video_Ads_View_count_textView.getText().toString();
                if (count.equals("0")) {
                    Toast.makeText(activity, getString(R.string.adds_video_unavailable), Toast.LENGTH_SHORT).show();
                } else {
                    dialogAdcolony = new Dialog(activity);
                    dialogAdcolony.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialogAdcolony.setContentView(R.layout.loading_dialog);
                    dialogAdcolony.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialogAdcolony.show();
                    AdcolonyEarningPointAddSection();
                    if (rewardAdColony != null && isRewardLoaded) {
                        rewardAdColony.show();
                        isRewardLoaded = false;
                    } else {
                        Toast.makeText(activity, "Reward Ad Is Not Loaded Yet Please Try Again ", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
        Section5Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Admob
                String count = Video_Ads_View_count_textView.getText().toString();
                if (count.equals("0")) {
                    Toast.makeText(activity, getString(R.string.adds_video_unavailable), Toast.LENGTH_SHORT).show();
                } else {
                    showVideoAdsSectionRewardedAds();
                    AdmobEarningPointAddSection();
                }
            }
        });
        Section6Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Startapp
                String count = Video_Ads_View_count_textView.getText().toString();
                if (count.equals("0")) {
                    Toast.makeText(activity, getString(R.string.adds_video_unavailable), Toast.LENGTH_SHORT).show();
                } else {
                    StartappRewardedVideo();
                    StartappEarningPointAddSection();
                }
            }
        });
        Section7Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Facebook Video Ads
                String count = Video_Ads_View_count_textView.getText().toString();
                if (count.equals("0")) {
                    Toast.makeText(activity, getString(R.string.adds_video_unavailable), Toast.LENGTH_SHORT).show();
                } else {
                    FacebookRewardedVideoAd();
                    ShowFacebookVideo();
                    FacebookEarningPointAddSection();
                }
            }
        });
        Section8Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //appLovin
                String count = Video_Ads_View_count_textView.getText().toString();
                if (count.equals("0")) {
                    Toast.makeText(activity, getString(R.string.adds_video_unavailable), Toast.LENGTH_SHORT).show();
                } else {
                    dialog = new Dialog(activity);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.loading_dialog);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog.show();
                    ApplovinEarningPointAddSection();
                    appLovinInterstitialAd = AppLovinInterstitialAd.create(AppLovinSdk.getInstance(activity), activity);
                    appLovinInterstitialAd.show();
                    appLovinInterstitialAd.setAdDisplayListener(new AppLovinAdDisplayListener() {
                        @Override
                        public void adDisplayed(AppLovinAd ad) {
                            dialog.dismiss();

                        }

                        @Override
                        public void adHidden(AppLovinAd ad) {
                            CallCoinAdded();
                        }
                    });
                }
            }
        });
    }

    private void VungleEarningPointAddSection() {

        if (Video_Ads_View_first) {
            Video_Ads_View_first = false;
            Log.e(TAG, "onVideo_Ads_ViewStarted: " + random_points);
            if (Constant.isNetworkAvailable(activity)) {
                random_points = "";
                //     Random random = new Random();
                random_points = String.valueOf(someEarn_controller.getVungle_Rewarded());
                if (random_points == null || random_points.equalsIgnoreCase("null")) {
                    random_points = String.valueOf(1);
                }
                points_text.setText(random_points);
                Log.e(TAG, "onVideo_Ads_ViewStarted: " + random_points);
            } else {
                Constant.showInternetErrorDialog(activity, getResources().getString(R.string.internet_connection_of_text));
            }
        }

    }

    private void ApplovinEarningPointAddSection() {

        if (Video_Ads_View_first) {
            Video_Ads_View_first = false;
            Log.e(TAG, "onVideo_Ads_ViewStarted: " + random_points);
            if (Constant.isNetworkAvailable(activity)) {
                random_points = "";
                //     Random random = new Random();
                random_points = String.valueOf(someEarn_controller.getAppLovin_Rewarded());
                if (random_points == null || random_points.equalsIgnoreCase("null")) {
                    random_points = String.valueOf(1);
                }
                points_text.setText(random_points);
                Log.e(TAG, "onVideo_Ads_ViewStarted: " + random_points);
            } else {
                Constant.showInternetErrorDialog(activity, getResources().getString(R.string.internet_connection_of_text));
            }
        }

    }

    private void UnityEarningPointAddSection() {

        if (Video_Ads_View_first) {
            Video_Ads_View_first = false;
            Log.e(TAG, "onVideo_Ads_ViewStarted: " + random_points);
            if (Constant.isNetworkAvailable(activity)) {
                random_points = "";
                //     Random random = new Random();
                random_points = String.valueOf(someEarn_controller.getUnityAds_Rewarded());
                if (random_points == null || random_points.equalsIgnoreCase("null")) {
                    random_points = String.valueOf(1);
                }
                points_text.setText(random_points);
                Log.e(TAG, "onVideo_Ads_ViewStarted: " + random_points);
            } else {
                Constant.showInternetErrorDialog(activity, getResources().getString(R.string.internet_connection_of_text));
            }
        }

    }

    private void AdcolonyEarningPointAddSection() {

        if (Video_Ads_View_first) {
            Video_Ads_View_first = false;
            Log.e(TAG, "onVideo_Ads_ViewStarted: " + random_points);
            if (Constant.isNetworkAvailable(activity)) {
                random_points = "";
                //     Random random = new Random();
                random_points = String.valueOf(someEarn_controller.getAdColony_Rewarded());
                if (random_points == null || random_points.equalsIgnoreCase("null")) {
                    random_points = String.valueOf(1);
                }
                points_text.setText(random_points);
                Log.e(TAG, "onVideo_Ads_ViewStarted: " + random_points);
            } else {
                Constant.showInternetErrorDialog(activity, getResources().getString(R.string.internet_connection_of_text));
            }
        }


    }

    private void setPointsText() {
        points_textView.setText("0");
        user_main_points(points_textView);
    }


    private void onEarningInit() {

        String Video_Ads_ViewCount = Constant.getString(activity, Constant.Video_Ads_View_Reward_COUNT);
        if (Video_Ads_ViewCount.equals("0")) {
            Video_Ads_ViewCount = "";
            Log.e("TAG", "onInit: scratch card 0");
        }
        if (Video_Ads_ViewCount.equals("")) {
            Log.e("TAG", "onInit: scratch card empty vala part");
            String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
            Log.e("TAG", "onClick: Current Date" + currentDate);
            String last_date = Constant.getString(activity, Constant.LAST_DATE_Video_Ads_View_Reward);
            Log.e("TAG", "Lat date" + last_date);
            if (last_date.equals("")) {
                //
                Log.e("TAG", "onInit: last date empty part");
                setVideoAdsViewCount(someEarn_controller.getVideo_Ads_Available_Views());
                Constant.setString(activity, Constant.Video_Ads_View_Reward_COUNT, someEarn_controller.getVideo_Ads_Available_Views());
                Constant.setString(activity, Constant.LAST_DATE_Video_Ads_View_Reward, currentDate);

                //
                //
                String work_dateDate = Constant.getString(activity, Constant.LAST_DATE_Video_Ads_View_Reward);
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
                        Constant.setString(activity, Constant.LAST_DATE_Video_Ads_View_Reward, currentDate);

                        //
                        String work_dateDate = Constant.getString(activity, Constant.LAST_DATE_Video_Ads_View_Reward);
                        UserWorkUpdateDate(work_dateDate);

                        //     Constant.setString(activity, Constant.Collect_Reward_COUNT, someEarn_controller.getCollectRewardCount());
                        setVideoAdsViewCount(Constant.getString(activity, Constant.Collect_Reward_COUNT));
                        Log.e("TAG", "onClick: today date added to preference" + currentDate);


                    } else {
                        setVideoAdsViewCount("0");
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        } else {
            Log.e("TAG", "onInit: scracth card in preference part");
            setVideoAdsViewCount(Video_Ads_ViewCount);
        }
    }

    private void setVideoAdsViewCount(String string) {
        if (string != null || !string.equalsIgnoreCase(""))
            Video_Ads_View_count_textView.setText(string);

        UserWorkUpdate(string);
        //
        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        UserWorkUpdateDate(currentDate);

    }

    private void CallCoinAdded() {
        if (first_time) {
            first_time = false;
            Log.e("onVideo_Ads_View", "Complete");
            Log.e("onVideo_Ads_View", "Complete" + random_points);
            String count = Video_Ads_View_count_textView.getText().toString();
            String counteee = count;
            String ran = counteee;
            Log.e(TAG, "onVideo_Ads_ViewComplete: " + ran);
            int counter = Integer.parseInt(ran.trim());
            if (counter == 0) {
                RewardVideoPointAdded(0, "0", counter);
            } else {
                RewardVideoPointAdded(1, points_text.getText().toString(), counter);
                Toast.makeText(activity, ">>>>>You get " + points_text.getText().toString() + " coins! <<<<<", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void RewardVideoPointAdded(final int addOrNoAddValue, final String points, final int counter) {

        first_time = true;
        Video_Ads_View_first = true;

        poiints = 0;
        if (addOrNoAddValue == 1) {
            if (points.equals("0")) {
                String current_counter = String.valueOf(counter - 1);
                Constant.setString(activity, Constant.Video_Ads_View_Reward_COUNT, current_counter);
                setVideoAdsViewCount(Constant.getString(activity, Constant.Video_Ads_View_Reward_COUNT));
            } else {
                String current_counter = String.valueOf(counter - 1);
                Constant.setString(activity, Constant.Video_Ads_View_Reward_COUNT, current_counter);
                setVideoAdsViewCount(Constant.getString(activity, Constant.Video_Ads_View_Reward_COUNT));
                try {
                    int finalPoint;
                    if (points.equals("")) {
                        finalPoint = 0;
                    } else {
                        finalPoint = Integer.parseInt(points);
                    }
                    poiints = finalPoint;

                    String DAILY_TYPE = "Video Wall Reward";
                    User_Add_Coins(activity, String.valueOf(finalPoint), DAILY_TYPE);


                    setPointsText();
                } catch (NumberFormatException ignored) {

                }
            }
        }
        if (Video_Ads_View_count == Integer.parseInt(someEarn_controller.getRewarded_and_interstitial_count())) {

        } else {
            Video_Ads_View_count += 1;
        }

    }

    private void initAdColonySdk() {
        AdColonyAppOptions appOptions = new AdColonyAppOptions().setKeepScreenOn(true);
        AdColony.configure((Activity) activity, appOptions, ads_id_controller.getAdcolonyAPP_ID(), ads_id_controller.getAdcolonyREWARD_ZONE_ID());
    }

    private void initRewardedAdAdColony() {

        AdColony.setRewardListener(new AdColonyRewardListener() {
            @Override
            public void onReward(AdColonyReward reward) {
                if (reward.success()) {
                    Toast.makeText(activity, "Reward Earned", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(activity, "Reward Cancelled", Toast.LENGTH_SHORT).show();
                }
            }
        });
        rewardListener = new AdColonyInterstitialListener() {
            @Override
            public void onRequestFilled(AdColonyInterstitial adReward) {
                rewardAdColony = adReward;
                isRewardLoaded = true;
            }

            @Override
            public void onRequestNotFilled(AdColonyZone zone) {
            }

            @Override
            public void onOpened(AdColonyInterstitial ad) {
                super.onOpened(ad);
                dialogAdcolony.dismiss();
            }

            @Override
            public void onClosed(AdColonyInterstitial ad) {
                super.onClosed(ad);
                AdColony.requestInterstitial(ads_id_controller.getAdcolonyREWARD_ZONE_ID(), rewardListener, rewardAdOptions);
                CallCoinAdded();
            }

            @Override
            public void onClicked(AdColonyInterstitial ad) {
                super.onClicked(ad);
            }

            @Override
            public void onLeftApplication(AdColonyInterstitial ad) {
                super.onLeftApplication(ad);
            }

            @Override
            public void onExpiring(AdColonyInterstitial ad) {
                super.onExpiring(ad);
            }
        };
        rewardAdOptions = new AdColonyAdOptions()
                .enableConfirmationDialog(false)
                .enableResultsDialog(false);
        AdColony.requestInterstitial(ads_id_controller.getAdcolonyREWARD_ZONE_ID(), rewardListener, rewardAdOptions);

    }

    private void initViews() {

    }

    private void initVungle() {

        Vungle.init(ads_id_controller.getVungle_key(), activity.getApplicationContext(), new InitCallback() {
            @Override
            public void onSuccess() {

                Collection<String> placements = Vungle.getValidPlacements();
                String[] placementsArray = placements.toArray(new String[0]);

                placementID = placementsArray[0];

            }

            @Override
            public void onError(VungleException e) {
                //   showToastMessage("SDK Init Error : " + e.getLocalizedMessage());
            }

            @Override
            public void onAutoCacheAdAvailable(String pid) {
                //      showToastMessage("Auto Cache Ad Available For Placement : " + pid);
            }
        });
    }

    private void loadAdVungle() {

        Vungle.loadAd(placementID, new LoadAdCallback() {
            @Override
            public void onAdLoad(String id) {
                Toast.makeText(activity, "Congratulation Ads Loaded", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(String id, VungleException e) {
                showToastMessage("Ad Load Error : Please Try Again");
            }
        });
    }

    private void playAdVungle() {

        Vungle.playAd(placementID, new AdConfig(), new PlayAdCallback() {
            @Override
            public void onAdStart(String placementReferenceID) {
                //       showToastMessage("Ad Start");
            }

            @Override
            public void onAdViewed(String placementReferenceID) {
                //     showToastMessage("Ad Viewed");
            }

            // Deprecated
            @Override
            public void onAdEnd(String id, boolean completed, boolean isCTAClicked) {
                // showToastMessage("Ad End : Completed : " + completed + " Clicked : " + isCTAClicked);

            }

            @Override
            public void onAdEnd(String placementReferenceID) {
                //      showToastMessage("Ad End");

            }

            @Override
            public void onAdClick(String placementReferenceID) {
                //     showToastMessage("Ad Clicked");
            }

            @Override
            public void onAdRewarded(String placementReferenceID) {
                //    showToastMessage("User Rewarded");
                CallCoinAdded();
            }

            @Override
            public void onAdLeftApplication(String placementReferenceID) {
                //     showToastMessage("User Left Application");

            }

            @Override
            public void creativeId(String creativeId) {
                //   showToastMessage("Will play creative " + creativeId);
            }

            @Override
            public void onError(String id, VungleException e) {
                //     showToastMessage("Ad Play Error : " + e.getLocalizedMessage());
            }
        });
    }


    private void showToastMessage(String message) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
    }


    private void AdmobEarningPointAddSection() {

        if (Video_Ads_View_first) {
            Video_Ads_View_first = false;
            Log.e(TAG, "onVideo_Ads_ViewStarted: " + random_points);
            if (Constant.isNetworkAvailable(activity)) {
                random_points = "";
                //     Random random = new Random();
                random_points = String.valueOf(someEarn_controller.getAdmob_Rewarded());
                if (random_points == null || random_points.equalsIgnoreCase("null")) {
                    random_points = String.valueOf(1);
                }
                points_text.setText(random_points);
                Log.e(TAG, "onVideo_Ads_ViewStarted: " + random_points);
            } else {
                Constant.showInternetErrorDialog(activity, getResources().getString(R.string.internet_connection_of_text));
            }
        }


    }


    public void LoadRewardedVideoAdmob() {
        if (rewarded_ad != null) {
            try {
                rewarded_ad = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        rewarded_ad = new RewardedAd(activity, ads_id_controller.getAdmob_rewarded_id());
        AttachedRewaredCallBack();
        rewarded_ad.loadAd(new AdRequest.Builder().build(), adLoadCallback);

    }

    public void AttachedRewaredCallBack() {
        if (adLoadCallback != null) {
            adLoadCallback = null;
        }
        adLoadCallback = new RewardedAdLoadCallback() {
            @Override
            public void onRewardedAdLoaded() {
                if (!isShowRewarded) {
                    showVideoAdsSectionRewardedAds();
                }
            }

            @Override
            public void onRewardedAdFailedToLoad(LoadAdError adError) {
            }
        };
    }

    public void showVideoAdsSectionRewardedAds() {

        dialogAdmobVideoAds = new Dialog(activity);
        dialogAdmobVideoAds.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogAdmobVideoAds.setContentView(R.layout.loading_dialog);
        dialogAdmobVideoAds.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogAdmobVideoAds.show();

        if (rewarded_ad != null && rewarded_ad.isLoaded()) {
            RewardedAdCallback adCallback = new RewardedAdCallback() {
                @Override
                public void onRewardedAdOpened() {
                    dialogAdmobVideoAds.dismiss();
                }

                @Override
                public void onRewardedAdClosed() {
                    LoadRewardedVideoAdmob();
                }

                @Override
                public void onUserEarnedReward(@NonNull RewardItem reward) {
                    CallCoinAdded();
                }

                @Override
                public void onRewardedAdFailedToShow(AdError adError) {

                }
            };
            rewarded_ad.show((Activity) activity, adCallback);
            isShowRewarded = true;
        } else {
            isShowRewarded = false;
            Constant.showToastMessage(activity, "");
        }

    }

    private void StartappEarningPointAddSection() {

        if (Video_Ads_View_first) {
            Video_Ads_View_first = false;
            Log.e(TAG, "onVideo_Ads_ViewStarted: " + random_points);
            if (Constant.isNetworkAvailable(activity)) {
                random_points = "";
                //     Random random = new Random();
                random_points = String.valueOf(someEarn_controller.getStartapp_Rewarded());
                if (random_points == null || random_points.equalsIgnoreCase("null")) {
                    random_points = String.valueOf(1);
                }
                points_text.setText(random_points);
                Log.e(TAG, "onVideo_Ads_ViewStarted: " + random_points);
            } else {
                Constant.showInternetErrorDialog(activity, getResources().getString(R.string.internet_connection_of_text));
            }
        }


    }

    public void StartappRewardedVideo() {

        dialogStartAppVideoAds = new Dialog(activity);
        dialogStartAppVideoAds.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogStartAppVideoAds.setContentView(R.layout.loading_dialog);
        dialogStartAppVideoAds.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogStartAppVideoAds.show();
        final StartAppAd rewardedVideo = new StartAppAd(activity);
        rewardedVideo.setVideoListener(new VideoListener() {
            @Override
            public void onVideoCompleted() {
                CallCoinAdded();
                // Grant the reward to user
            }
        });

        rewardedVideo.loadAd(StartAppAd.AdMode.REWARDED_VIDEO, new AdEventListener() {
            @Override
            public void onReceiveAd(com.startapp.sdk.adsbase.Ad ad) {
                rewardedVideo.showAd();
                dialogStartAppVideoAds.dismiss();
            }

            @Override
            public void onFailedToReceiveAd(com.startapp.sdk.adsbase.Ad ad) {

            }
        });
    }

    private void FacebookEarningPointAddSection() {

        if (Video_Ads_View_first) {
            Video_Ads_View_first = false;
            Log.e(TAG, "onVideo_Ads_ViewStarted: " + random_points);
            if (Constant.isNetworkAvailable(activity)) {
                random_points = "";
                //     Random random = new Random();
                random_points = String.valueOf(someEarn_controller.getFacebook_Rewarded());
                if (random_points == null || random_points.equalsIgnoreCase("null")) {
                    random_points = String.valueOf(1);
                }
                points_text.setText(random_points);
                Log.e(TAG, "onVideo_Ads_ViewStarted: " + random_points);
            } else {
                Constant.showInternetErrorDialog(activity, getResources().getString(R.string.internet_connection_of_text));
            }
        }


    }

    public void FacebookRewardedVideoAd() {

        rewardedVideoAd = new RewardedVideoAd(AppConfig.getContext(), ads_id_controller.getAdmob_rewarded_id());
        RewardedVideoAdListener rewardedVideoAdListener = new RewardedVideoAdListener() {

            @Override
            public void onError(Ad ad, com.facebook.ads.AdError adError) {
                Log.e(TAG, "Rewarded video ad failed to load: " + adError.getErrorMessage());
                dialogFacebookVideoAds.dismiss();
                Toast.makeText(activity, "Rewarded Video ad Failed to Load", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdLoaded(Ad ad) {
                dialogFacebookVideoAds.dismiss();
                Log.d(TAG, "Rewarded video ad is loaded and ready to be displayed!");
                if (!isShowfacebookRewarded) {
                    ShowFacebookVideo();
                }
            }

            @Override
            public void onAdClicked(Ad ad) {
                Log.d(TAG, "Rewarded video ad clicked!");
            }

            @Override
            public void onLoggingImpression(Ad ad) {
                Log.d(TAG, "Rewarded video ad impression logged!");
            }

            @Override
            public void onRewardedVideoCompleted() {
                Toast.makeText(activity, "Rewarded video completed", Toast.LENGTH_SHORT).show();
                CallCoinAdded();
                Log.d(TAG, "Rewarded video completed!");
            }

            @Override
            public void onRewardedVideoClosed() {
                Log.d(TAG, "Rewarded video ad closed!");
            }
        };
        rewardedVideoAd.loadAd(
                rewardedVideoAd.buildLoadAdConfig()
                        .withAdListener(rewardedVideoAdListener)
                        .build());


    }

    public void ShowFacebookVideo() {

        dialogFacebookVideoAds = new Dialog(activity);
        dialogFacebookVideoAds.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogFacebookVideoAds.setContentView(R.layout.loading_dialog);
        dialogFacebookVideoAds.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogFacebookVideoAds.show();

        if (rewardedVideoAd != null && rewardedVideoAd.isAdLoaded()) {
            rewardedVideoAd.show();
            isShowfacebookRewarded = true;
        } else {
            isShowfacebookRewarded = false;
            Constant.showToastMessage(activity, "Video Ad not Ready");
        }

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
                Toast.makeText(activity, error.toString(), Toast.LENGTH_LONG).show();
                hidepDialog();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("get_user_data_videowall", "any");
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
                Toast.makeText(activity, error.toString(), Toast.LENGTH_LONG).show();
                hidepDialog();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("get_user_data_videowall_date", "any");
                params.put("id", AppController.getInstance().getId());
                params.put("UpdateWork", data);

                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(jsonReq);
    }


}