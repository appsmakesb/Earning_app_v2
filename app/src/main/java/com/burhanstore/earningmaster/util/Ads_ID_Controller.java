package com.burhanstore.earningmaster.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

public class Ads_ID_Controller {

    private Context context;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @SuppressLint("CommitPrefEdits")
    public Ads_ID_Controller(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences("Ads_ID_Controller", Context.MODE_PRIVATE);
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

    public void dataStore(String admob_app_id,
                          String admob_banner_id,
                          String Admob_Interstitial_id,
                          String Admob_rewarded_id,
                          String Facebook_app_id,
                          String Facebook_Interstitial_id,
                          String Facebook_rewarded_id,
                          String Fyber_app_id,
                          String Fyber_security_key,
                          String Vungle_key,
                          String Vungle_InterstitialPlacementID,
                          String Applovin_SDK_Key,
                          String AdcolonyAPP_ID,
                          String AdcolonyREWARD_ZONE_ID,
                          String AdcolonyINT_ZONE_ID,
                          String IronSource_App_Key,
                          String pollfish_key,
                          String Unity_Game_Id,
                          String StartaApp_app_id,
                          String Tapjoy_SDK_KEY,
                          String Tapjoy_PLACEMENT_OFFERWALL,
                          String adGateMediaWallID,
                          String OneSignalAppID) {
        editor.putString("admob_app_id", admob_app_id);
        editor.putString("admob_banner_id", admob_banner_id);
        editor.putString("Admob_Interstitial_id", Admob_Interstitial_id);
        editor.putString("Admob_rewarded_id", Admob_rewarded_id);
        editor.putString("Facebook_app_id", Facebook_app_id);
        editor.putString("Facebook_Interstitial_id", Facebook_Interstitial_id);
        editor.putString("Facebook_rewarded_id", Facebook_rewarded_id);
        editor.putString("Fyber_app_id", Fyber_app_id);
        editor.putString("Fyber_security_key", Fyber_security_key);
        editor.putString("Vungle_key", Vungle_key);
        editor.putString("Vungle_InterstitialPlacementID", Vungle_InterstitialPlacementID);
        editor.putString("Applovin_SDK_Key", Applovin_SDK_Key);
        editor.putString("AdcolonyAPP_ID", AdcolonyAPP_ID);
        editor.putString("AdcolonyREWARD_ZONE_ID", AdcolonyREWARD_ZONE_ID);
        editor.putString("AdcolonyINT_ZONE_ID", AdcolonyINT_ZONE_ID);
        editor.putString("IronSource_App_Key", IronSource_App_Key);
        editor.putString("pollfish_key", pollfish_key);
        editor.putString("Unity_Game_Id", Unity_Game_Id);
        editor.putString("StartaApp_app_id", StartaApp_app_id);
        editor.putString("Tapjoy_SDK_KEY", Tapjoy_SDK_KEY);
        editor.putString("Tapjoy_PLACEMENT_OFFERWALL", Tapjoy_PLACEMENT_OFFERWALL);
        editor.putString("adGateMediaWallID", adGateMediaWallID);
        editor.putString("OneSignalAppID", OneSignalAppID);
        editor.commit();
    }

    public String getAdmob_app_id() {
        String admob_app_id = sharedPreferences.getString("admob_app_id", "0");
        return admob_app_id;
    }

    public String getAdmob_banner_id() {
        String admob_banner_id = sharedPreferences.getString("admob_banner_id", "0");
        return admob_banner_id;
    }

    public String getAdmob_Interstitial_id() {
        String Admob_Interstitial_id = sharedPreferences.getString("Admob_Interstitial_id", "0");
        return Admob_Interstitial_id;
    }

    public String getAdmob_rewarded_id() {
        String Admob_rewarded_id = sharedPreferences.getString("Admob_rewarded_id", "0");
        return Admob_rewarded_id;
    }

    public String getFacebook_app_id() {
        String Facebook_app_id = sharedPreferences.getString("Facebook_app_id", "0");
        return Facebook_app_id;
    }

    public String getFacebook_Interstitial_id() {
        String Facebook_Interstitial_id = sharedPreferences.getString("Facebook_Interstitial_id", "0");
        return Facebook_Interstitial_id;
    }

    public String getFacebook_rewarded_id() {
        String Facebook_rewarded_id = sharedPreferences.getString("Facebook_rewarded_id", "0");
        return Facebook_rewarded_id;
    }

    public String getFyber_app_id() {
        String Fyber_app_id = sharedPreferences.getString("Fyber_app_id", "0");
        return Fyber_app_id;
    }

    public String getFyber_security_key() {
        String Fyber_security_key = sharedPreferences.getString("Fyber_security_key", "0");
        return Fyber_security_key;
    }

    public String getVungle_key() {
        String Vungle_key = sharedPreferences.getString("Vungle_key", "0");
        return Vungle_key;
    }

    public String getVungle_InterstitialPlacementID() {
        String Vungle_InterstitialPlacementID = sharedPreferences.getString("Vungle_InterstitialPlacementID", "0");
        return Vungle_InterstitialPlacementID;
    }

    public String getApplovin_SDK_Key() {
        String Applovin_SDK_Key = sharedPreferences.getString("Applovin_SDK_Key", "0");
        return Applovin_SDK_Key;
    }

    public String getAdcolonyAPP_ID() {
        String AdcolonyAPP_ID = sharedPreferences.getString("AdcolonyAPP_ID", "0");
        return AdcolonyAPP_ID;
    }

    public String getAdcolonyREWARD_ZONE_ID() {
        String AdcolonyREWARD_ZONE_ID = sharedPreferences.getString("AdcolonyREWARD_ZONE_ID", "0");
        return AdcolonyREWARD_ZONE_ID;
    }

    public String getAdcolonyINT_ZONE_ID() {
        String AdcolonyINT_ZONE_ID = sharedPreferences.getString("AdcolonyINT_ZONE_ID", "0");
        return AdcolonyINT_ZONE_ID;
    }

    public String getIronSource_App_Key() {
        String IronSource_App_Key = sharedPreferences.getString("IronSource_App_Key", "0");
        return IronSource_App_Key;
    }

    public String getpollfish_key() {
        String pollfish_key = sharedPreferences.getString("pollfish_key", "0");
        return pollfish_key;
    }

    public String getUnity_Game_Id() {
        String Unity_Game_Id = sharedPreferences.getString("Unity_Game_Id", "0");
        return Unity_Game_Id;
    }

    public String getStartaApp_app_id() {
        String StartaApp_app_id = sharedPreferences.getString("StartaApp_app_id", "0");
        return StartaApp_app_id;
    }

    public String getTapjoy_SDK_KEY() {
        String Tapjoy_SDK_KEY = sharedPreferences.getString("Tapjoy_SDK_KEY", "0");
        return Tapjoy_SDK_KEY;
    }

    public String getTapjoy_PLACEMENT_OFFERWALL() {
        String Tapjoy_PLACEMENT_OFFERWALL = sharedPreferences.getString("Tapjoy_PLACEMENT_OFFERWALL", "0");
        return Tapjoy_PLACEMENT_OFFERWALL;
    }

    public String getadGateMediaWallID() {
        String adGateMediaWallID = sharedPreferences.getString("adGateMediaWallID", "0");
        return adGateMediaWallID;
    }

    public String getOneSignalAppID() {
        String OneSignalAppID = sharedPreferences.getString("OneSignalAppID", "0");
        return OneSignalAppID;
    }

}
