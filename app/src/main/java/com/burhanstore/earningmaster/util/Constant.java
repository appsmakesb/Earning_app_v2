package com.burhanstore.earningmaster.util;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.multidex.BuildConfig;

import com.adcolony.sdk.AdColony;
import com.adcolony.sdk.AdColonyAdOptions;
import com.adcolony.sdk.AdColonyAppOptions;
import com.adcolony.sdk.AdColonyInterstitial;
import com.adcolony.sdk.AdColonyInterstitialListener;
import com.adcolony.sdk.AdColonyReward;
import com.adcolony.sdk.AdColonyRewardListener;
import com.adcolony.sdk.AdColonyZone;
import com.applovin.adview.AppLovinInterstitialAd;
import com.applovin.adview.AppLovinInterstitialAdDialog;
import com.applovin.sdk.AppLovinAd;
import com.applovin.sdk.AppLovinAdDisplayListener;
import com.applovin.sdk.AppLovinSdk;
import com.burhanstore.earningmaster.helper.AppController;
import com.facebook.ads.Ad;
import com.facebook.ads.InterstitialAdListener;
import com.facebook.ads.RewardedVideoAd;
import com.facebook.ads.RewardedVideoAdListener;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdCallback;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.ironsource.mediationsdk.IronSource;
import com.startapp.sdk.adsbase.StartAppAd;
import com.startapp.sdk.adsbase.StartAppSDK;
import com.startapp.sdk.adsbase.VideoListener;
import com.startapp.sdk.adsbase.adlisteners.AdEventListener;
import com.unity3d.ads.IUnityAdsShowListener;
import com.unity3d.ads.UnityAds;
import com.vungle.warren.AdConfig;
import com.vungle.warren.InitCallback;
import com.vungle.warren.LoadAdCallback;
import com.vungle.warren.PlayAdCallback;
import com.vungle.warren.Vungle;
import com.vungle.warren.error.VungleException;
import com.burhanstore.earningmaster.R;
import com.burhanstore.earningmaster.services.PointsService;
import com.burhanstore.earningmaster.sharedPref.PrefManager;

import java.util.Collection;
import java.util.regex.Pattern;

public class Constant {

    private static AppLovinInterstitialAdDialog appLovinInterstitialAd;
    private static final String UNITY_PLACEMENT_ID_REWARDED_VIDEO = "rewardedVideo";
    private static String placementID, Vungel_InterstitialPlacementID;
    private static String mGameId;
    private static Dialog dialogVpn, UnityDialog, dialogAdcolony, dialogAdmobVideoAds, dialogStartAppVideoAds, dialogFacebookVideoAds, ApplovinDialog;
    public static final String IS_LOGIN = "IsLogin";
    public static final String USER_BLOCKED = "user_blocked";
    public static final String USER_NAME = "user_name";
    public static final String USER_NUMBER = "user_number";
    public static final String USER_EMAIL = "user_email";
    public static final String USER_POINTS = "user_points";
    public static final String USER_REFFER_CODE = "user_reffer_code";

    //Daily Reward
    public static final String LAST_DATE = "daily_last_date";
    //Gold Reward
    public static final String GoldReward_DATE = "gold_resdd_last";
    //King Pot
    public static final String King_Pot_DATE = "king_tot_date";
    //Pay Earn
    public static final String Pay_Earn_Gift_DATE = "pay_earn_gift_date";

    public static final String LAST_TIME_ADD_TO_SERVER = "last_time_added";
    public static final String REFER_CODE = "refer_code";
    public static final String USER_IMAGE = "user_image";

    //Additional Scratch
    public static final String Additional_Scratch_COUNT = "additional_scratch";
    public static final String LAST_DATE_Additional_SCRATCH = "last_date_additional_scratch";

    //Great Scratch
    public static final String Great_Scratch_COUNT = "great_scratch";
    public static final String LAST_DATE_Great_SCRATCH = "last_date_great_scratch";

    //Extra Scratch
    public static final String Extra_Scratch_COUNT = "extra_scratch";
    public static final String LAST_DATE_Extra_SCRATCH = "last_date_extra_scratch";

    public static final String Collect_Reward_COUNT = "collect_reward_count";
    public static final String LAST_DATE_Collect_Reward = "last_date_collectreward";

    //Everyday Gifts
    public static final String EverydayGift_Reward_COUNT = "EverydayGift_reward";
    public static final String LAST_DATE_EverydayGift_Reward = "last_date_EverydayGift_reward";


    //OfferWall
    public static final String OfferWall_Reward_COUNT = "OfferWall_reward";
    public static final String LAST_DATE_OfferWall_Reward = "last_date_OfferWall_reward";

    //Complete Survey
    public static final String CompleteSurvey_Reward_COUNT = "CompleteSurvey_reward";
    public static final String LAST_DATE_CompleteSurvey_Reward = "last_date_CompleteSurvey_reward";

    //Video Ads View
    public static final String Video_Ads_View_Reward_COUNT = "video_ads_view_reward";
    public static final String LAST_DATE_Video_Ads_View_Reward = "last_date_video_ads_view_reward";


    public static final String Open_Reward_COUNT = "open_reward";
    public static final String LAST_DATE_Open_Reward = "last_date_open_reward";

    //Spin
    public static final String SPIN_COUNT = "spin_count";
    public static final String LAST_DATE_SPIN = "last_date_spin";
    //
    public static final String TICTAC_COUNT = "tictac_count";
    public static final String LAST_DATE_TICTAC = "last_date_tictac";
    //

    public static final String USER_ID = "user_id";
    public static final String IS_UPDATE = "user_update";
    public static final String USER_PASSWORD = "password";
    private static PrefManager prefManager;
    public static InterstitialAd interstitial_Ad;
    public static com.facebook.ads.InterstitialAd interstitialAd;
    public static RewardedAd rewarded_ad;
    public static boolean isShowInterstitial = true;
    public static boolean isShowRewarded = true;
    public static boolean isShowfacebookRewarded = true;
    public static boolean isAttachedInterstitial = true;
    public static boolean isAttachedfacebookInterstitial = true;
    public static boolean isShowFacebookInterstitial = true;
    public static RewardedAdLoadCallback adLoadCallback;
    public static final String TAG = "Constant";
    public static InterstitialAdListener interstitialAdListener;
    public static RewardedVideoAd rewardedVideoAd;
    public static ProgressDialog alertDialog;
    public static Ads_Controller ads_controller;
    public static Ads_ID_Controller ads_id_controller;
    public SomeEarn_Controller someEarn_controller;
    public static Activity context;

    private static AdColonyInterstitial rewardAdColony;
    private static AdColonyInterstitialListener rewardListener;
    private static AdColonyAdOptions rewardAdOptions;
    private static boolean isRewardLoaded;


    private final static String APP_ID = "app7dd43d11ae344f74af";
    private final static String BANNER_ZONE_ID = "vz5ccc44c8de0c43f7a8";
    private final static String INTERSTITIAL_ZONE_ID = "vz896de92b61e14e6695";
    private static AdColonyInterstitial interstitialAdColony;
    private static AdColonyInterstitialListener interstitialListener;
    private static AdColonyAdOptions interstitialAdOptions;
    private static boolean isInterstitialLoaded;
    private static StartAppAd startAppAd = new StartAppAd(AppController.getContext());

    //IronSource Interstitial
    private static final String PLACEMENT_INTERSTITIAL = "DefaultInterstitial";
    //Admob Banner
    private static LinearLayout adLayout;
    public static AppLovinAd appLovinAd;

    //vpn
    public static ConnectivityManager cm;
    public static android.net.NetworkInfo wifi;
    public static android.net.NetworkInfo datac;


    //Vungle Interstitial Ads Star
    public static void initVungleInterstitial() {
        ads_id_controller = new Ads_ID_Controller(AppController.getContext());
        Vungle.init(ads_id_controller.getVungle_key(), AppController.getContext().getApplicationContext(), new InitCallback() {
            @Override
            public void onSuccess() {

                Collection<String> placements = Vungle.getValidPlacements();
                String[] placementsArray = placements.toArray(new String[0]);

                Vungel_InterstitialPlacementID = placementsArray[0];
            }

            @Override
            public void onError(VungleException e) {

            }

            @Override
            public void onAutoCacheAdAvailable(String pid) {
                //   showToastMessage("Auto Cache Ad Available For Placement : " + pid);
            }
        });
    }

    public static void loadVungleAdInterstitial() {
        ads_id_controller = new Ads_ID_Controller(AppController.getContext());
        Vungel_InterstitialPlacementID = ads_id_controller.getVungle_InterstitialPlacementID();
        Vungle.loadAd(Vungel_InterstitialPlacementID, new LoadAdCallback() {
            @Override
            public void onAdLoad(String id) {

            }

            @Override
            public void onError(String id, VungleException e) {

            }
        });
    }

    public static void playVungleAdInterstitial() {
        ads_id_controller = new Ads_ID_Controller(AppController.getContext());
        Vungel_InterstitialPlacementID = ads_id_controller.getVungle_InterstitialPlacementID();
        Vungle.playAd(Vungel_InterstitialPlacementID, new AdConfig(), new PlayAdCallback() {
            @Override
            public void onAdStart(String placementReferenceID) {
                //  showToastMessage("Ad Start");
            }

            @Override
            public void onAdViewed(String placementReferenceID) {

            }

            // Deprecated
            @Override
            public void onAdEnd(String id, boolean completed, boolean isCTAClicked) {


            }

            @Override
            public void onAdEnd(String placementReferenceID) {

            }

            @Override
            public void onAdClick(String placementReferenceID) {

            }

            @Override
            public void onAdRewarded(String placementReferenceID) {

            }

            @Override
            public void onAdLeftApplication(String placementReferenceID) {

            }

            @Override
            public void creativeId(String creativeId) {

            }

            @Override
            public void onError(String id, VungleException e) {

            }
        });


    }

    //Vungle Interstitial Ads End

    //IronSource Interstitial Start

    public static void IronSourceInterstitialInt(Activity activity) {
        ads_id_controller = new Ads_ID_Controller(AppController.getContext());
        IronSource.init(activity, ads_id_controller.getIronSource_App_Key(), IronSource.AD_UNIT.INTERSTITIAL);
        IronSource.loadInterstitial();
    }

    public static void IronSourceInterstitialShow() {
        IronSource.showInterstitial(PLACEMENT_INTERSTITIAL);
    }

    public static void startAppInterstitialShow() {
        startAppAd.showAd();
    }

    public static void LoadStartAppInterstitial(Activity activity) {
        ads_id_controller = new Ads_ID_Controller(AppController.getContext());
        StartAppSDK.init(activity, ads_id_controller.getStartaApp_app_id(), true);
        StartAppSDK.setTestAdsEnabled(BuildConfig.DEBUG);
        StartAppAd.disableSplash();
        StartAppAd.enableConsent(activity, false);
        StartAppSDK.setTestAdsEnabled(false);

        startAppAd.loadAd();
    }

    public static void StartappRewardedVideo(final Activity context) {

        dialogStartAppVideoAds = new Dialog(context);
        dialogStartAppVideoAds.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogStartAppVideoAds.setContentView(R.layout.loading_dialog);
        dialogStartAppVideoAds.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogStartAppVideoAds.show();

        final StartAppAd rewardedVideo = new StartAppAd(context);
        rewardedVideo.setVideoListener(new VideoListener() {
            @Override
            public void onVideoCompleted() {
                Toast.makeText(context, "Video Completed", Toast.LENGTH_SHORT).show();
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

    public static void ApplovinShowAds(final Activity context) {

        ApplovinDialog = new Dialog(context);
        ApplovinDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        ApplovinDialog.setContentView(R.layout.loading_dialog);
        ApplovinDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        ApplovinDialog.show();

        appLovinInterstitialAd = AppLovinInterstitialAd.create(AppLovinSdk.getInstance(context), context);
        appLovinInterstitialAd.show();
        appLovinInterstitialAd.setAdDisplayListener(new AppLovinAdDisplayListener() {
            @Override
            public void adDisplayed(AppLovinAd ad) {
                ApplovinDialog.dismiss();
            }

            @Override
            public void adHidden(AppLovinAd ad) {

            }
        });

    }


    //Adcolony
    public static void initRewardedAdAdColony(final Activity activity) {
        ads_id_controller = new Ads_ID_Controller(AppController.getContext());
        AdColonyAppOptions appOptions = new AdColonyAppOptions().setKeepScreenOn(true);
        AdColony.configure((Activity) activity, appOptions, ads_id_controller.getAdcolonyAPP_ID(), ads_id_controller.getAdcolonyREWARD_ZONE_ID(), ads_id_controller.getAdcolonyINT_ZONE_ID());

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

    public static void AdcolonyDisplayInterstitialAd() {
        if (interstitialAdColony != null && isInterstitialLoaded) {
            interstitialAdColony.show();
            isInterstitialLoaded = false;
        } else {

        }
    }

    public static void AdcolonyInitInterstitialAd() {

        interstitialListener = new AdColonyInterstitialListener() {
            @Override
            public void onRequestFilled(AdColonyInterstitial adIn) {
                interstitialAdColony = adIn;
                isInterstitialLoaded = true;
            }

            @Override
            public void onRequestNotFilled(AdColonyZone zone) {
                super.onRequestNotFilled(zone);
            }

            @Override
            public void onOpened(AdColonyInterstitial ad) {
                super.onOpened(ad);
            }

            @Override
            public void onClosed(AdColonyInterstitial ad) {
                super.onClosed(ad);
                AdColony.requestInterstitial(INTERSTITIAL_ZONE_ID, interstitialListener, interstitialAdOptions);
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
        interstitialAdOptions = new AdColonyAdOptions();
        AdColony.requestInterstitial(INTERSTITIAL_ZONE_ID, interstitialListener, interstitialAdOptions);
    }


    public static void ShowAdcolonyAds(final Activity activity) {

        dialogAdcolony = new Dialog(activity);
        dialogAdcolony.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogAdcolony.setContentView(R.layout.loading_dialog);
        dialogAdcolony.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogAdcolony.show();
        if (rewardAdColony != null && isRewardLoaded) {
            rewardAdColony.show();
            dialogAdcolony.dismiss();
        } else {
            Toast.makeText(activity, "Reward Ad Is Not Loaded Yet Please Try Again ", Toast.LENGTH_SHORT).show();
        }

    }

    public static void FacebookRewardedVideoAd(final Activity context) {
        ads_id_controller = new Ads_ID_Controller(AppController.getContext());

        rewardedVideoAd = new RewardedVideoAd(AppController.getContext(), ads_id_controller.getAdmob_rewarded_id());
        RewardedVideoAdListener rewardedVideoAdListener = new RewardedVideoAdListener() {

            @Override
            public void onError(Ad ad, com.facebook.ads.AdError adError) {
                Log.e(TAG, "Rewarded video ad failed to load: " + adError.getErrorMessage());
                dialogFacebookVideoAds.dismiss();
                Toast.makeText(context, "Rewarded Video ad Failed to Load", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdLoaded(Ad ad) {
                dialogFacebookVideoAds.dismiss();
                Log.d(TAG, "Rewarded video ad is loaded and ready to be displayed!");
                if (!isShowfacebookRewarded) {
                    showRewardedAdmobAds(context);
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
                Toast.makeText(context, "Rewarded video completed", Toast.LENGTH_SHORT).show();

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

    public static void ShowFacebookVideo(final Activity context) {

        dialogFacebookVideoAds = new Dialog(context);
        dialogFacebookVideoAds.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogFacebookVideoAds.setContentView(R.layout.loading_dialog);
        dialogFacebookVideoAds.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogFacebookVideoAds.show();

        if (rewardedVideoAd != null && rewardedVideoAd.isAdLoaded()) {
            rewardedVideoAd.show();
            isShowfacebookRewarded = true;
        } else {
            isShowfacebookRewarded = false;
            Constant.showToastMessage(context, "Video Ad not Ready");
        }
    }

    public static void LoadAdmobInterstitialAd() {
        ads_id_controller = new Ads_ID_Controller(AppController.getContext());

        if (interstitial_Ad == null) {
            interstitial_Ad = new InterstitialAd(AppController.getContext());
            interstitial_Ad.setAdUnitId(ads_id_controller.getAdmob_Interstitial_id());
            interstitial_Ad.loadAd(new AdRequest.Builder().build());
            AttachListner();
            isAttachedInterstitial = false;
        } else if (interstitial_Ad.isLoading()) {
            Log.e("TAG", "loadInterstitialAd: isLoading");
        } else {
            interstitial_Ad.loadAd(new AdRequest.Builder().build());
        }
    }

    public static void LoadFacebookInterstitialAd() {
        ads_id_controller = new Ads_ID_Controller(AppController.getContext());
        if (interstitialAd == null) {
            interstitialAd = new com.facebook.ads.InterstitialAd(AppController.getContext(), ads_id_controller.getFacebook_Interstitial_id());
            AttachFacebookListner();
            interstitialAd.loadAd(
                    interstitialAd.buildLoadAdConfig()
                            .withAdListener(interstitialAdListener)
                            .build());
            isAttachedfacebookInterstitial = false;
        } else {
            interstitialAd.loadAd();
        }
    }

    public static void loadInterstitialAd() {
        ads_controller = new Ads_Controller(AppController.getContext());
        ads_id_controller = new Ads_ID_Controller(AppController.getContext());
        String typeOfAds = ads_controller.getInt_AdsApp();
        switch (typeOfAds) {
            case "Facebook":
                if (interstitialAd == null) {
                    interstitialAd = new com.facebook.ads.InterstitialAd(AppController.getContext(), ads_id_controller.getFacebook_Interstitial_id());
                    AttachFacebookListner();
                    interstitialAd.loadAd(
                            interstitialAd.buildLoadAdConfig()
                                    .withAdListener(interstitialAdListener)
                                    .build());
                    isAttachedfacebookInterstitial = false;
                } else {
                    interstitialAd.loadAd();
                }
                break;
            case "Admob":
                if (interstitial_Ad == null) {
                    interstitial_Ad = new InterstitialAd(AppController.getContext());
                    interstitial_Ad.setAdUnitId(ads_id_controller.getAdmob_Interstitial_id());
                    interstitial_Ad.loadAd(new AdRequest.Builder().build());
                    AttachListner();
                    isAttachedInterstitial = false;
                } else if (interstitial_Ad.isLoading()) {
                    Log.e("TAG", "loadInterstitialAd: isLoading");
                } else {
                    interstitial_Ad.loadAd(new AdRequest.Builder().build());
                }
                break;
            case "StartApp":
                startAppAd.loadAd();
                break;


        }
    }

    public static void AttachFacebookListner() {
        if (isAttachedInterstitial) {
            interstitialAdListener = new InterstitialAdListener() {
                @Override
                public void onInterstitialDisplayed(Ad ad) {

                    Log.e(TAG, "Interstitial ad displayed.");
                }

                @Override
                public void onInterstitialDismissed(Ad ad) {

                    Log.e(TAG, "Interstitial ad dismissed.");
                }

                @Override
                public void onError(Ad ad, com.facebook.ads.AdError adError) {
                    Log.e(TAG, "Interstitial ad failed to load: " + adError.getErrorMessage());
                }

                @Override
                public void onAdLoaded(Ad ad) {
                    Log.d(TAG, "Interstitial ad is loaded and ready to be displayed!");
                    if (!isShowFacebookInterstitial) {
                        showInterstitialAds();
                    }
                }

                @Override
                public void onAdClicked(Ad ad) {
                    Log.d(TAG, "Interstitial ad clicked!");
                }

                @Override
                public void onLoggingImpression(Ad ad) {
                    Log.d(TAG, "Interstitial ad impression logged!");
                }
            };
        }
    }

    public static void AttachListner() {
        if (isAttachedInterstitial) {
            interstitial_Ad.setAdListener(new AdListener() {
                @Override
                public void onAdLoaded() {
                    super.onAdLoaded();
                    Log.e("adLoaded", "adLoaded: interstitial");
                    if (!isShowInterstitial) {
                        showInterstitialAds();
                    }
                }

                @Override
                public void onAdClosed() {
                    super.onAdClosed();
                    Log.e("onAdClosed", "onAdClosed: ");
                    loadInterstitialAd();
                }

                @Override
                public void onAdImpression() {
                    super.onAdImpression();
                    Log.e("adOpened", "adOpened: ");
                }

                @Override
                public void onAdFailedToLoad(LoadAdError loadAdError) {
                    super.onAdFailedToLoad(loadAdError);
                    Log.e("adError", "adError: " + loadAdError.toString());
                }
            });
        }
    }


    public static void hideProgressDialog() {
        if (alertDialog != null && alertDialog.isShowing()) {
            alertDialog.dismiss();
        }
    }

    public static void ShowAdmobInterstitialAds() {
        if (interstitial_Ad != null && interstitial_Ad.isLoaded()) {
            hideProgressDialog();
            interstitial_Ad.show();

            isShowInterstitial = true;
        } else {
            hideProgressDialog();
            isShowInterstitial = false;
        }
    }

    public static void ShowFacebookInterstitialAds() {
        if (interstitialAd != null && interstitialAd.isAdLoaded()) {
            hideProgressDialog();
            interstitialAd.show();
            isShowFacebookInterstitial = true;
        } else {
            hideProgressDialog();
            isShowFacebookInterstitial = false;
        }
    }

    public static void showInterstitialAds() {
        ads_controller = new Ads_Controller(AppController.getContext());
        String typeOfAds = ads_controller.getInt_AdsApp();
        if (typeOfAds.equals("Admob")) {
            if (interstitial_Ad != null && interstitial_Ad.isLoaded()) {
                hideProgressDialog();
                interstitial_Ad.show();

                isShowInterstitial = true;
            } else {
                hideProgressDialog();
                isShowInterstitial = false;
            }
        } else if (typeOfAds.equals("Facebook")) {
            if (interstitialAd != null && interstitialAd.isAdLoaded()) {
                hideProgressDialog();
                interstitialAd.show();
                isShowFacebookInterstitial = true;
            } else {
                hideProgressDialog();
                isShowFacebookInterstitial = false;
            }
        }
    }

    public static void showRewardedAdmobAds(final Activity context) {
        if (rewarded_ad != null && rewarded_ad.isLoaded()) {
            RewardedAdCallback adCallback = new RewardedAdCallback() {
                @Override
                public void onRewardedAdOpened() {

                }

                @Override
                public void onRewardedAdClosed() {

                    loadRewardedVideo(context);
                }

                @Override
                public void onUserEarnedReward(@NonNull RewardItem reward) {

                }

                @Override
                public void onRewardedAdFailedToShow(AdError adError) {

                }
            };
            rewarded_ad.show(context, adCallback);
            isShowRewarded = true;
        } else {
            isShowRewarded = false;
            Constant.showToastMessage(context, "Video Ad not Ready");
        }
    }

    public static void showRewardedFacebookAds(final Activity context) {
        if (rewardedVideoAd != null && rewardedVideoAd.isAdLoaded()) {
            rewardedVideoAd.show();
            isShowfacebookRewarded = true;
        } else {
            isShowfacebookRewarded = false;
            Constant.showToastMessage(context, "Video Ad not Ready");
        }
    }


    public static void showRewardedVungleAds(final Activity context) {
        playAdVungle(context);
    }


    public static void loadRewardedVideo(final Activity activity) {
        ads_id_controller = new Ads_ID_Controller(AppController
                .getContext());

        //Admob
        if (rewarded_ad != null) {
            try {
                rewarded_ad = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        rewarded_ad = new RewardedAd(activity, ads_id_controller.getAdmob_rewarded_id());
        AttachedRewaredCallBack(activity);
        rewarded_ad.loadAd(new AdRequest.Builder().build(), adLoadCallback);

        //Facebook
        if (rewardedVideoAd != null) {
            try {
                rewardedVideoAd = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        rewardedVideoAd = new RewardedVideoAd(AppController.getContext(), ads_id_controller.getAdmob_rewarded_id());
        RewardedVideoAdListener rewardedVideoAdListener = new RewardedVideoAdListener() {

            @Override
            public void onError(Ad ad, com.facebook.ads.AdError adError) {
                Log.e(TAG, "Rewarded video ad failed to load: " + adError.getErrorMessage());
            }

            @Override
            public void onAdLoaded(Ad ad) {

                Log.d(TAG, "Rewarded video ad is loaded and ready to be displayed!");
                if (!isShowfacebookRewarded) {
                    showRewardedAdmobAds(activity);
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

    public static void AttachedRewaredCallBack(final Activity activity) {
        if (adLoadCallback != null) {
            adLoadCallback = null;
        }
        adLoadCallback = new RewardedAdLoadCallback() {
            @Override
            public void onRewardedAdLoaded() {
                if (!isShowRewarded) {
                    showRewardedAdmobAds(activity);
                }
            }

            @Override
            public void onRewardedAdFailedToLoad(LoadAdError adError) {
            }
        };
    }

    public static void GotoNextActivity(Context context, Class nextActivity, String msg) {
        if (context != null && nextActivity != null) {
            if (msg == null) {
                msg = "";
            }
            Intent intent = new Intent(context, nextActivity);
            intent.putExtra("Intent", msg);
            context.startActivity(intent);
        }
    }

    public static boolean isValidEmailAddress(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        boolean isMatches = pattern.matcher(email).matches();
        Log.e("Boolean Value", "" + isMatches);
        return isMatches;
    }

    public static void showToastMessage(Context context, String message) {
        if (context != null && message != null) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        }
    }

    public static void setString(Context context, String preKey, String setString) {
        if (prefManager == null) {
            prefManager = new PrefManager(context);
        }
        prefManager.setString(preKey, setString);
    }

    public static String getString(Context context, String prefKey) {
        if (prefManager == null) {
            prefManager = new PrefManager(context);
        }
        return prefManager.getString(prefKey);
    }

    public static void hideKeyboard(Activity activity) {
        if (activity == null) {
            return;
        }
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager inm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            inm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static void addPoints(Context context, int points, int type) {
        if (context != null) {
            String po = Constant.getString(context, Constant.USER_POINTS);
            if (po.equals("")) {
                po = "0";
            }
            if (type == 1) {
                Constant.setString(context, Constant.USER_POINTS, po);
                Intent serviceIntent = new Intent(context, PointsService.class);
                serviceIntent.putExtra("points", Constant.getString(context, Constant.USER_POINTS));
                context.startService(serviceIntent);
            } else {
                int current_Points = Integer.parseInt(po);
                String total_points = String.valueOf(current_Points + points);
                Constant.setString(context, Constant.USER_POINTS, total_points);
                Intent serviceIntent = new Intent(context, PointsService.class);
                serviceIntent.putExtra("points", Constant.getString(context, Constant.USER_POINTS));
                context.startService(serviceIntent);
            }
        }
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static void showInternetErrorDialog(Context context, String msg) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.show_points_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);

        ImageView imageView = dialog.findViewById(R.id.points_image);
        TextView title_text = dialog.findViewById(R.id.title_text_points);
        TextView points_text = dialog.findViewById(R.id.points);
        AppCompatButton add_btn = dialog.findViewById(R.id.add_btn);

        imageView.setImageResource(R.drawable.ic_internet);
        title_text.setText(msg);
        points_text.setVisibility(View.GONE);
        add_btn.setText(context.getResources().getString(R.string.ok_text));

        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public static void showBlockedDialog(final Context context, String msg) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.show_points_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);

        ImageView imageView = dialog.findViewById(R.id.points_image);
        TextView title_text = dialog.findViewById(R.id.title_text_points);
        TextView points_text = dialog.findViewById(R.id.points);
        AppCompatButton add_btn = dialog.findViewById(R.id.add_btn);

        imageView.setImageResource(R.drawable.ic_close);
        title_text.setText(msg);
        points_text.setVisibility(View.GONE);
        add_btn.setText(context.getResources().getString(R.string.ok_text));

        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public static void referApp(Context context, String refer_code) {
        try {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, context.getResources().getString(R.string.share_text) + "' " + refer_code + " '" + " Download Link = " + " https://play.google.com/store/apps/details?id=" + context.getPackageName());
            sendIntent.setType("text/plain");
            context.startActivity(sendIntent);
        } catch (Exception e) {
            Log.e("TAG", "referApp: " + e.getMessage());
        }
    }

    //Vungle
    public static void initVungle(final Activity activity) {
        ads_id_controller = new Ads_ID_Controller(AppController.getContext());
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

    public static void loadAdVungle(final Activity activity) {

        Vungle.loadAd(placementID, new LoadAdCallback() {
            @Override
            public void onAdLoad(String id) {
                //  Toast.makeText(activity, "Congratulation Ads Loaded", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(String id, VungleException e) {

            }
        });

    }

    public static void playAdVungle(final Activity activity) {

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

    //Unity
    public static void initUnityAds(final Activity activity) {
        ads_id_controller = new Ads_ID_Controller(AppController.getContext());
        UnityAds.initialize(activity, ads_id_controller.getUnity_Game_Id());

        if (UnityAds.isReady()) {
            UnityAds.show(activity);
        } else {
            UnityAds.initialize(activity, ads_id_controller.getUnity_Game_Id());
        }

    }

    public static void showRewardedVideoAd(final Activity activity) {
        if (!UnityAds.isReady(UNITY_PLACEMENT_ID_REWARDED_VIDEO)) {
            return;
        }
        UnityAds.show(activity, UNITY_PLACEMENT_ID_REWARDED_VIDEO, (IUnityAdsShowListener) activity);
        //   dialog.dismiss();
    }

    public static void IntUnityAds(Activity activity) {
        ads_id_controller = new Ads_ID_Controller(AppController.getContext());
        UnityAds.initialize(activity, ads_id_controller.getUnity_Game_Id());

        if (UnityAds.isReady()) {
            // UnityAds.show(activity);
        } else {
            UnityAds.initialize(activity, ads_id_controller.getUnity_Game_Id());
        }


    }

    public static void ShowUnityAds(Activity activity) {

        UnityDialog = new Dialog(activity);
        UnityDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        UnityDialog.setContentView(R.layout.loading_dialog);
        UnityDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        UnityDialog.show();

        ads_id_controller = new Ads_ID_Controller(AppController.getContext());
        if (UnityAds.isReady()) {
            UnityAds.show(activity);
            UnityDialog.dismiss();
        } else {
            UnityAds.initialize(activity, ads_id_controller.getUnity_Game_Id());
        }

    }


}
