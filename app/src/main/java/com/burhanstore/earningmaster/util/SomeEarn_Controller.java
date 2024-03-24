package com.burhanstore.earningmaster.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

public class SomeEarn_Controller {
    private Context context;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @SuppressLint("CommitPrefEdits")
    public SomeEarn_Controller(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences("SomeEarn_Controller", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }


    public void setDate(int date) {
        editor.putInt("date", date);
        editor.commit();
    }

    public int getDate() {
        int date = sharedPreferences.getInt("date", 0);
        return date;
    }


    public void dataStore(String CollectReward, String WatchVideo, String OpenReward, String EverydayGifts_Count, String CollectRewardCount, String OpenRewardCount, String SpinCount, String TicTacCount, String TicTacReward, String daily_check, String GoldRewardPoint, String KingPotPoint, String PayEarnGift, String Pollfish_point, String Fyber_point, String ironSource_point, String Video_Ads_Available_Views, String Vungle_Rewarded, String UnityAds_Rewarded, String AppLovin_Rewarded, String AdColony_Rewarded, String Admob_Rewarded, String Startapp_Rewarded, String Facebook_Rewarded, String Slide_image1, String Slide_image2, String Slide_image3, String CompleteSurvey_reward, String Additional_Scratch_Chances, String Additional_Scratch_Point, String Extra_Scratch_Chances, String Extra_Scratch_Point, String Great_Scratch_Chances, String Great_Scratch_Point,
                          String rewarded_and_interstitial_count,
                          String Slide1_openurl,
                          String Slide1_url_control,
                          String Slide2_openurl,
                          String Slide2_url_control,
                          String Slide3_openurl,
                          String Slide3_url_control) {
        editor.putString("CollectReward", CollectReward);
        editor.putString("WatchVideo", WatchVideo);
        editor.putString("OpenReward", OpenReward);
        editor.putString("EverydayGifts_Count", EverydayGifts_Count);
        editor.putString("CollectRewardCount", CollectRewardCount);
        editor.putString("OpenRewardCount", OpenRewardCount);
        editor.putString("SpinCount", SpinCount);
        editor.putString("TicTacCount", TicTacCount);
        editor.putString("TicTacReward", TicTacReward);
        editor.putString("daily_check", daily_check);
        editor.putString("GoldRewardPoint", GoldRewardPoint);
        editor.putString("KingPotPoint", KingPotPoint);
        editor.putString("PayEarnGift", PayEarnGift);
        editor.putString("Pollfish_point", Pollfish_point);
        editor.putString("Fyber_point", Fyber_point);
        editor.putString("ironSource_point", ironSource_point);
        editor.putString("Video_Ads_Available_Views", Video_Ads_Available_Views);
        editor.putString("Vungle_Rewarded", Vungle_Rewarded);
        editor.putString("UnityAds_Rewarded", UnityAds_Rewarded);
        editor.putString("AppLovin_Rewarded", AppLovin_Rewarded);
        editor.putString("AdColony_Rewarded", AdColony_Rewarded);
        editor.putString("Admob_Rewarded", Admob_Rewarded);
        editor.putString("Startapp_Rewarded", Startapp_Rewarded);
        editor.putString("Facebook_Rewarded", Facebook_Rewarded);
        editor.putString("Slide_image1", Slide_image1);
        editor.putString("Slide_image2", Slide_image2);
        editor.putString("Slide_image3", Slide_image3);
        editor.putString("CompleteSurvey_reward", CompleteSurvey_reward);
        editor.putString("Additional_Scratch_Chances", Additional_Scratch_Chances);
        editor.putString("Additional_Scratch_Point", Additional_Scratch_Point);
        editor.putString("Extra_Scratch_Chances", Extra_Scratch_Chances);
        editor.putString("Extra_Scratch_Point", Extra_Scratch_Point);
        editor.putString("Great_Scratch_Chances", Great_Scratch_Chances);
        editor.putString("Great_Scratch_Point", Great_Scratch_Point);
        editor.putString("rewarded_and_interstitial_count", rewarded_and_interstitial_count);
        editor.putString("Slide1_openurl", Slide1_openurl);
        editor.putString("Slide1_url_control", Slide1_url_control);
        editor.putString("Slide2_openurl", Slide2_openurl);
        editor.putString("Slide2_url_control", Slide2_url_control);
        editor.putString("Slide3_openurl", Slide3_openurl);
        editor.putString("Slide3_url_control", Slide3_url_control);
        editor.commit();
    }

    public String getCollectReward() {
        String CollectReward = sharedPreferences.getString("CollectReward", "0");
        return CollectReward;
    }

    public String getWatchVideo() {
        String WatchVideo = sharedPreferences.getString("WatchVideo", "0");
        return WatchVideo;
    }

    public String getOpenReward() {
        String OpenReward = sharedPreferences.getString("OpenReward", "");
        return OpenReward;
    }

    public String getEverydayGifts_Count() {
        String EverydayGifts_Count = sharedPreferences.getString("EverydayGifts_Count", "");
        return EverydayGifts_Count;
    }

    public String getCollectRewardCount() {
        String CollectRewardCount = sharedPreferences.getString("CollectRewardCount", "");
        return CollectRewardCount;
    }

    public String getOpenRewardCount() {
        String OpenRewardCount = sharedPreferences.getString("OpenRewardCount", "");
        return OpenRewardCount;
    }

    public String getSpinCount() {
        String SpinCount = sharedPreferences.getString("SpinCount", "");
        return SpinCount;
    }

    public String getTicTacCount() {
        String TicTacCount = sharedPreferences.getString("TicTacCount", "");
        return TicTacCount;
    }

    public String getTicTacReward() {
        String TicTacReward = sharedPreferences.getString("TicTacReward", "");
        return TicTacReward;
    }

    public String getEverydayGiftReward() {
        String EverydayGiftReward = sharedPreferences.getString("EverydayGiftReward", "");
        return EverydayGiftReward;
    }

    public String getEverydayGiftCount() {
        String EverydayGiftCount = sharedPreferences.getString("EverydayGiftCount", "");
        return EverydayGiftCount;
    }

    public String getDaily_check() {
        String daily_check = sharedPreferences.getString("daily_check", "");
        return daily_check;
    }

    public String getGoldRewardPoint() {
        String GoldRewardPoint = sharedPreferences.getString("GoldRewardPoint", "");
        return GoldRewardPoint;
    }

    public String getKingPotPoint() {
        String KingPotPoint = sharedPreferences.getString("KingPotPoint", "");
        return KingPotPoint;
    }

    public String getPayEarnGift() {
        String PayEarnGift = sharedPreferences.getString("PayEarnGift", "");
        return PayEarnGift;
    }

    public String getPollfish_point() {
        String Pollfish_point = sharedPreferences.getString("Pollfish_point", "");
        return Pollfish_point;
    }

    public String getFyber_point() {
        String Fyber_point = sharedPreferences.getString("Fyber_point", "");
        return Fyber_point;
    }

    public String getironSource_point() {
        String ironSource_point = sharedPreferences.getString("ironSource_point", "");
        return ironSource_point;
    }

    public String getVideo_Ads_Available_Views() {
        String Video_Ads_Available_Views = sharedPreferences.getString("Video_Ads_Available_Views", "");
        return Video_Ads_Available_Views;
    }

    public String getVungle_Rewarded() {
        String Vungle_Rewarded = sharedPreferences.getString("Vungle_Rewarded", "");
        return Vungle_Rewarded;
    }

    public String getUnityAds_Rewarded() {
        String UnityAds_Rewarded = sharedPreferences.getString("UnityAds_Rewarded", "");
        return UnityAds_Rewarded;
    }

    public String getAppLovin_Rewarded() {
        String AppLovin_Rewarded = sharedPreferences.getString("AppLovin_Rewarded", "");
        return AppLovin_Rewarded;
    }

    public String getAdColony_Rewarded() {
        String AdColony_Rewarded = sharedPreferences.getString("AdColony_Rewarded", "");
        return AdColony_Rewarded;
    }

    public String getAdmob_Rewarded() {
        String Admob_Rewarded = sharedPreferences.getString("Admob_Rewarded", "");
        return Admob_Rewarded;
    }

    public String getStartapp_Rewarded() {
        String Startapp_Rewarded = sharedPreferences.getString("Startapp_Rewarded", "");
        return Startapp_Rewarded;
    }

    public String getFacebook_Rewarded() {
        String Facebook_Rewarded = sharedPreferences.getString("Facebook_Rewarded", "");
        return Facebook_Rewarded;
    }

    public String getSlide_image1() {
        String Slide_image1 = sharedPreferences.getString("Slide_image1", "");
        return Slide_image1;
    }

    public String getSlide_image2() {
        String Slide_image2 = sharedPreferences.getString("Slide_image2", "");
        return Slide_image2;
    }

    public String getSlide_image3() {
        String Slide_image3 = sharedPreferences.getString("Slide_image3", "");
        return Slide_image3;
    }

    public String getCompleteSurvey_reward() {
        String CompleteSurvey_reward = sharedPreferences.getString("CompleteSurvey_reward", "");
        return CompleteSurvey_reward;
    }

    public String getAdditional_Scratch_Chances() {
        String Additional_Scratch_Chances = sharedPreferences.getString("Additional_Scratch_Chances", "");
        return Additional_Scratch_Chances;
    }

    public String getAdditional_Scratch_Point() {
        String Additional_Scratch_Point = sharedPreferences.getString("Additional_Scratch_Point", "");
        return Additional_Scratch_Point;
    }

    public String getExtra_Scratch_Chances() {
        String Extra_Scratch_Chances = sharedPreferences.getString("Extra_Scratch_Chances", "");
        return Extra_Scratch_Chances;
    }

    public String getExtra_Scratch_Point() {
        String Extra_Scratch_Point = sharedPreferences.getString("Extra_Scratch_Point", "");
        return Extra_Scratch_Point;
    }

    public String getGreat_Scratch_Chances() {
        String Great_Scratch_Chances = sharedPreferences.getString("Great_Scratch_Chances", "");
        return Great_Scratch_Chances;
    }

    public String getGreat_Scratch_Point() {
        String Great_Scratch_Point = sharedPreferences.getString("Great_Scratch_Point", "");
        return Great_Scratch_Point;
    }

    public String getRewarded_and_interstitial_count() {
        String rewarded_and_interstitial_count = sharedPreferences.getString("rewarded_and_interstitial_count", "");
        return rewarded_and_interstitial_count;
    }
    public String getSlide1_openurl() {
        String Slide1_openurl = sharedPreferences.getString("Slide1_openurl", "");
        return Slide1_openurl;
    }
    public String getSlide1_url_control() {
        String Slide1_url_control = sharedPreferences.getString("Slide1_url_control", "");
        return Slide1_url_control;
    }
    public String getSlide2_openurl() {
        String Slide2_openurl = sharedPreferences.getString("Slide2_openurl", "");
        return Slide2_openurl;
    }
    public String getSlide2_url_control() {
        String Slide2_url_control = sharedPreferences.getString("Slide2_url_control", "");
        return Slide2_url_control;
    }
    public String getSlide3_openurl() {
        String Slide3_openurl = sharedPreferences.getString("Slide3_openurl", "");
        return Slide3_openurl;
    }
    public String getSlide3_url_control() {
        String Slide3_url_control = sharedPreferences.getString("Slide3_url_control", "");
        return Slide3_url_control;
    }


}
