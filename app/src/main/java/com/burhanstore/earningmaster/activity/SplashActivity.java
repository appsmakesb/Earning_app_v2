package com.burhanstore.earningmaster.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.multidex.BuildConfig;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Process;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.burhanstore.earningmaster.helper.AppController;
import com.burhanstore.earningmaster.helper.JsonRequest;
import com.burhanstore.earningmaster.util.Ads_ID_Controller;
import com.burhanstore.earningmaster.util.FraudPrevention_Controller;
import com.burhanstore.earningmaster.util.RootChecker;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.tasks.OnFailureListener;
import com.google.android.play.core.tasks.OnSuccessListener;
import com.google.android.play.core.tasks.Task;
import com.burhanstore.earningmaster.R;
import com.burhanstore.earningmaster.util.Ads_Controller;
import com.burhanstore.earningmaster.util.Payment_Controller;
import com.burhanstore.earningmaster.util.SomeEarn_Controller;
import com.burhanstore.earningmaster.BaseUrl;
import com.burhanstore.earningmaster.util.Constant;
import com.burhanstore.earningmaster.util.Helper;
import com.burhanstore.earningmaster.util.TinyDB;
import com.startapp.sdk.adsbase.StartAppAd;
import com.startapp.sdk.adsbase.StartAppSDK;

import org.json.JSONObject;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static com.burhanstore.earningmaster.helper.Constatnt.ACCESS_KEY;
import static com.burhanstore.earningmaster.helper.Constatnt.ACCESS_Value;
import static com.burhanstore.earningmaster.helper.Constatnt.ACCOUNT_STATE_ENABLED;
import static com.burhanstore.earningmaster.helper.Constatnt.API;
import static com.burhanstore.earningmaster.helper.Constatnt.Base_Url;
import static com.burhanstore.earningmaster.helper.Constatnt.GET_USER;
import static com.burhanstore.earningmaster.helper.Constatnt.ID;
import static com.google.android.play.core.install.model.AppUpdateType.IMMEDIATE;

public class SplashActivity extends AppCompatActivity {
    boolean LOGIN = false;
    private AppUpdateManager appUpdateManager;
    public static final int RC_APP_UPDATE = 101;
    String user_name = null;

    Ads_Controller ads_controller;
    SomeEarn_Controller someEarn_controller;
    Payment_Controller payment_controller;
    private TinyDB tinydb;
    Ads_ID_Controller ads_id_controller;
    FraudPrevention_Controller fraudPrevention_controller;

    // GPSTracker class
    private ConnectivityManager connectivityManager;
    private NetworkInfo activeNetwork;

    //app Clone
    private static final String DUAL_APP_ID_999 = "apps.clone.cloud.multiple.space.bit64";
    private static final int APP_PACKAGE_DOT_COUNT = 3; // number of dots present in package name
    private static final char DOT = '.';
    //app Clone

    private static int SPLASH_TIME_OUT = 5000;

    Activity context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        context = this;

        ads_id_controller = new Ads_ID_Controller(this);
        fraudPrevention_controller = new FraudPrevention_Controller(this);

        StartAppSDK.init(this, ads_id_controller.getStartaApp_app_id(), true);
        StartAppSDK.setTestAdsEnabled(BuildConfig.DEBUG);
        StartAppAd.disableSplash();
        StartAppAd.enableConsent(this, false);
        StartAppSDK.setTestAdsEnabled(false);

        // algoratham();


        String is_login = Constant.getString(context, Constant.IS_LOGIN);
        if (is_login.equals("true")) {
            LOGIN = true;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            appUpdateManager = AppUpdateManagerFactory.create(this);
            UpdateApp();
        } else {
            algoratham();
        }


        //some
        ads_controller = new Ads_Controller(this);
        someEarn_controller = new SomeEarn_Controller(this);
        payment_controller = new Payment_Controller(this);
        tinydb = new TinyDB(this);
        fetchAppData();

        //
        //Fraud & Prevention

        //Block Rooted Devices
        if (fraudPrevention_controller.getBlockRootedDevices().equals("Yes")) {
            //root
            RootChecker.isDeviceRooted();
            if (RootChecker.isDeviceRooted()) {
                String dialogText = getResources().getString(R.string.Rooted_Devices_dialog_Text);
                showBlockedDialog(dialogText);
            }
        }

        //Block Vpn Access
        if (fraudPrevention_controller.getBlockVpnAccess().equals("Yes")) {
            //vpn detector
            connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            activeNetwork = connectivityManager.getActiveNetworkInfo();
            checkNetworkAndVPN();
            if (is_VPN_connected()) {
                String dialogText = getResources().getString(R.string.vpn_dialog_text);
                showBlockedDialog(dialogText);
            }
            //vpn detector
        }

        //App Cloning
        if (fraudPrevention_controller.getAppCloning().equals("Yes")) {
            //app Clone
            checkAppCloning();
            //app Clone

        }  //
        //Fraud & Prevention

        //Block Rooted Devices
        if (fraudPrevention_controller.getBlockRootedDevices().equals("Yes")) {
            //root
            RootChecker.isDeviceRooted();
            if (RootChecker.isDeviceRooted()) {
                String dialogText = getResources().getString(R.string.Rooted_Devices_dialog_Text);
                showBlockedDialog(dialogText);
            }
        }

        //Block Vpn Access
        if (fraudPrevention_controller.getBlockVpnAccess().equals("Yes")) {
            //vpn detector
            connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            activeNetwork = connectivityManager.getActiveNetworkInfo();
            checkNetworkAndVPN();
            if (is_VPN_connected()) {
                String dialogText = getResources().getString(R.string.vpn_dialog_text);
                showBlockedDialog(dialogText);
            }
            //vpn detector
        }

        //App Cloning
        if (fraudPrevention_controller.getAppCloning().equals("Yes")) {
            //app Clone
            checkAppCloning();
            //app Clone

        }

    }


    public void fetchAppData() {
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, BaseUrl.APP_DATA_API_URL
                + "?user=" + Helper.getUserAccount(this)
                + "&did=" + Helper.getDeviceId(this)
                + "&" + BaseUrl.EXTRA_PARAMS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject data = new JSONObject(response);


                            if (!data.getBoolean("success")) {
                                //   sweetAlertDialog( data.getString("message"), "WARNING_TYPE", true );
                                return;
                            }

                            if (data != null) {

                                String collect_reward_ads = data.getString("collect_reward_ads");
                                String Open_Reward_ads = data.getString("Open_Reward_ads");
                                String Everyday_gift_ads = data.getString("Everyday_gift_ads");
                                String Scratch_win_ads1 = data.getString("Scratch_win_ads1");
                                String Scratch_win_ads2 = data.getString("Scratch_win_ads2");
                                String Scratch_win_ads3 = data.getString("Scratch_win_ads3");
                                String TicTAcToe_Ads = data.getString("TicTAcToe_Ads");
                                String gold_reward_ads = data.getString("gold_reward_ads");
                                String king_pot_ads = data.getString("king_pot_ads");
                                String pay_earn_gift_ads = data.getString("pay_earn_gift_ads");
                                String daily_check_reward_ads = data.getString("daily_check_reward_ads");
                                String spin_reward_ads = data.getString("spin_reward_ads");
                                String Banner_Ads = data.getString("Banner_Ads");
                                String Int_Ads = data.getString("Int_Ads");
                                String signup_bonus = data.getString("signup_bonus");
                                String Refer_Point = data.getString("Refer_Point");
                                String Contact_us_email = data.getString("Contact_us_email");
                                String spin_Interstitial_ads = data.getString("spin_Interstitial_ads");
                                String collect_Interstitial_ads = data.getString("collect_Interstitial_ads");
                                String Open_Interstitial_ads = data.getString("Open_Interstitial_ads");
                                String Everyday_gift_Interstitial_ads = data.getString("Everyday_gift_Interstitial_ads");
                                String Scratch_win_ads1_Interstitial = data.getString("Scratch_win_ads1_Interstitial");
                                String Scratch_win_ads2_Interstitial = data.getString("Scratch_win_ads2_Interstitial");
                                String Scratch_win_ads3_Interstitial = data.getString("Scratch_win_ads3_Interstitial");
                                String TicTAcToe_Interstitial_Ads = data.getString("TicTAcToe_Interstitial_Ads");
                                String BannerAdsControl = data.getString("BannerAdsControl");
                                String privacypolicyurl = data.getString("privacypolicyurl");

                                Ads_Controller ads_controller = new Ads_Controller(SplashActivity.this);
                                ads_controller.dataStore(collect_reward_ads,
                                        Open_Reward_ads,
                                        Everyday_gift_ads,
                                        Scratch_win_ads1,
                                        Scratch_win_ads2,
                                        Scratch_win_ads3,
                                        TicTAcToe_Ads,
                                        gold_reward_ads,
                                        king_pot_ads,
                                        pay_earn_gift_ads,
                                        daily_check_reward_ads,
                                        spin_reward_ads,
                                        Banner_Ads,
                                        Int_Ads,
                                        signup_bonus,
                                        Refer_Point,
                                        Contact_us_email,
                                        spin_Interstitial_ads,
                                        collect_Interstitial_ads,
                                        Open_Interstitial_ads,
                                        Everyday_gift_Interstitial_ads,
                                        Scratch_win_ads1_Interstitial,
                                        Scratch_win_ads2_Interstitial,
                                        Scratch_win_ads3_Interstitial,
                                        TicTAcToe_Interstitial_Ads,
                                        BannerAdsControl,
                                        privacypolicyurl
                                );


                                String BlockVpnAccess = data.getString("BlockVpnAccess");
                                String AppCloning = data.getString("AppCloning");
                                String BlockRootedDevices = data.getString("BlockRootedDevices");
                                String AutoBanRootedDevice = data.getString("AutoBanRootedDevice");

                                FraudPrevention_Controller fraudPrevention_controller = new FraudPrevention_Controller(SplashActivity.this);
                                fraudPrevention_controller.dataStore(BlockVpnAccess, AppCloning, BlockRootedDevices, AutoBanRootedDevice);


                                String Payment_section_Image1 = data.getString("Payment_section_Image1");
                                String Payment_section_Dollar_1 = data.getString("Payment_section_Dollar_1");
                                String Payment_section_CostCOint1 = data.getString("Payment_section_CostCOint1");
                                String Payment_section_Image2 = data.getString("Payment_section_Image2");
                                String Payment_section_Dollar_2 = data.getString("Payment_section_Dollar_2");
                                String Payment_section_CostCOint2 = data.getString("Payment_section_CostCOint2");
                                String Payment_section_Image3 = data.getString("Payment_section_Image3");
                                String Payment_section_Dollar_3 = data.getString("Payment_section_Dollar_3");
                                String Payment_section_CostCOint3 = data.getString("Payment_section_CostCOint3");
                                String Payment_section_Dollar_4 = data.getString("Payment_section_Dollar_4");
                                String Payment_section_Image4 = data.getString("Payment_section_Image4");
                                String Payment_section_CostCOint4 = data.getString("Payment_section_CostCOint4");
                                String Payment_section_Image5 = data.getString("Payment_section_Image5");
                                String Payment_section_Dollar_5 = data.getString("Payment_section_Dollar_5");
                                String Payment_section_CostCOint5 = data.getString("Payment_section_CostCOint5");
                                String Payment_section_Image6 = data.getString("Payment_section_Image6");
                                String Payment_section_Dollar_6 = data.getString("Payment_section_Dollar_6");
                                String Payment_section_CostCOint6 = data.getString("Payment_section_CostCOint6");
                                Payment_Controller payment_controller = new Payment_Controller(SplashActivity.this);
                                payment_controller.dataStore(Payment_section_Image1, Payment_section_Dollar_1,
                                        Payment_section_CostCOint1,
                                        Payment_section_Image2,
                                        Payment_section_Dollar_2,
                                        Payment_section_CostCOint2,
                                        Payment_section_Image3,
                                        Payment_section_Dollar_3,
                                        Payment_section_CostCOint3,
                                        Payment_section_Dollar_4,
                                        Payment_section_Image4,
                                        Payment_section_CostCOint4,
                                        Payment_section_Image5,
                                        Payment_section_Dollar_5,
                                        Payment_section_CostCOint5,
                                        Payment_section_Image6,
                                        Payment_section_Dollar_6,
                                        Payment_section_CostCOint6);


                                String CollectReward = data.getString("CollectReward");
                                String WatchVideo = data.getString("WatchVideo");
                                String OpenReward = data.getString("OpenReward");
                                String EverydayGifts_Count = data.getString("EverydayGifts_Count");
                                String CollectRewardCount = data.getString("CollectRewardCount");
                                String OpenRewardCount = data.getString("OpenRewardCount");
                                String SpinCount = data.getString("SpinCount");
                                String TicTacCount = data.getString("TicTacCount");
                                String TicTacReward = data.getString("TicTacReward");
                                String daily_check = data.getString("daily_check");
                                String GoldRewardPoint = data.getString("GoldRewardPoint");
                                String KingPotPoint = data.getString("KingPotPoint");
                                String PayEarnGift = data.getString("PayEarnGift");
                                String Pollfish_point = data.getString("Pollfish_point");
                                String Fyber_point = data.getString("Fyber_point");
                                String ironSource_point = data.getString("ironSource_point");
                                String Video_Ads_Available_Views = data.getString("Video_Ads_Available_Views");
                                String Vungle_Rewarded = data.getString("Vungle_Rewarded");
                                String UnityAds_Rewarded = data.getString("UnityAds_Rewarded");
                                String AppLovin_Rewarded = data.getString("AppLovin_Rewarded");
                                String AdColony_Rewarded = data.getString("AdColony_Rewarded");
                                String Admob_Rewarded = data.getString("Admob_Rewarded");
                                String Startapp_Rewarded = data.getString("Startapp_Rewarded");
                                String Facebook_Rewarded = data.getString("Facebook_Rewarded");
                                String Slide_image1 = data.getString("Slide_image1");
                                String Slide_image2 = data.getString("Slide_image2");
                                String Slide_image3 = data.getString("Slide_image3");
                                String CompleteSurvey_reward = data.getString("CompleteSurvey_reward");
                                String Additional_Scratch_Chances = data.getString("Additional_Scratch_Chances");
                                String Additional_Scratch_Point = data.getString("Additional_Scratch_Point");
                                String Extra_Scratch_Chances = data.getString("Extra_Scratch_Chances");
                                String Extra_Scratch_Point = data.getString("Extra_Scratch_Point");
                                String Great_Scratch_Chances = data.getString("Great_Scratch_Chances");
                                String Great_Scratch_Point = data.getString("Great_Scratch_Point");
                                String rewarded_and_interstitial_count = data.getString("rewarded_and_interstitial_count");
                                String Slide1_openurl = data.getString("Slide1_openurl");
                                String Slide1_url_control = data.getString("Slide1_url_control");
                                String Slide2_openurl = data.getString("Slide2_openurl");
                                String Slide2_url_control = data.getString("Slide2_url_control");
                                String Slide3_openurl = data.getString("Slide3_openurl");
                                String Slide3_url_control = data.getString("Slide3_url_control");
                                SomeEarn_Controller someEarn_controller = new SomeEarn_Controller(SplashActivity.this);
                                someEarn_controller.dataStore(CollectReward, WatchVideo, OpenReward, EverydayGifts_Count, CollectRewardCount, OpenRewardCount, SpinCount, TicTacCount, TicTacReward, daily_check, GoldRewardPoint, KingPotPoint, PayEarnGift, Pollfish_point, Fyber_point, ironSource_point, Video_Ads_Available_Views, Vungle_Rewarded, UnityAds_Rewarded, AppLovin_Rewarded, AdColony_Rewarded, Admob_Rewarded, Startapp_Rewarded, Facebook_Rewarded, Slide_image1, Slide_image2, Slide_image3, CompleteSurvey_reward, Additional_Scratch_Chances, Additional_Scratch_Point, Extra_Scratch_Chances, Extra_Scratch_Point, Great_Scratch_Chances, Great_Scratch_Point, rewarded_and_interstitial_count, Slide1_openurl, Slide1_url_control, Slide2_openurl, Slide2_url_control, Slide3_openurl, Slide3_url_control);

                                //real Time Ads data
                                String admob_app_id = "null";//data.getString("admob_app_id");
                                String admob_banner_id = "null";//data.getString("admob_banner_id");
                                String Admob_Interstitial_id = "null";// data.getString("Admob_Interstitial_id");
                                String Admob_rewarded_id = "null";//data.getString("Admob_rewarded_id");
                                String Facebook_app_id ="null";// data.getString("Facebook_app_id");
                                String Facebook_Interstitial_id = "null";//data.getString("Facebook_Interstitial_id");
                                String Facebook_rewarded_id = "null";//data.getString("Facebook_rewarded_id");
                                String Fyber_app_id ="null";// data.getString("Fyber_app_id");
                                String Fyber_security_key = "null";//data.getString("Fyber_security_key");
                                String Vungle_key = "null";//data.getString("Vungle_key");
                                String Vungle_InterstitialPlacementID = "null";//data.getString("Vungle_InterstitialPlacementID");
                                String Applovin_SDK_Key = "null";//data.getString("Applovin_SDK_Key");
                                String AdcolonyAPP_ID = "null";//data.getString("AdcolonyAPP_ID");
                                String AdcolonyREWARD_ZONE_ID = "null";//data.getString("AdcolonyREWARD_ZONE_ID");
                                String AdcolonyINT_ZONE_ID = "null";//data.getString("AdcolonyINT_ZONE_ID");
                                String IronSource_App_Key = "null";//data.getString("IronSource_App_Key");
                                String pollfish_key = "null";//data.getString("pollfish_key");
                                String Unity_Game_Id = "null";//data.getString("Unity_Game_Id");
                                String StartaApp_app_id = data.getString("StartaApp_app_id");
                                String Tapjoy_SDK_KEY = "null";//data.getString("Tapjoy_SDK_KEY");
                                String Tapjoy_PLACEMENT_OFFERWALL = "null";//data.getString("Tapjoy_PLACEMENT_OFFERWALL");
                                String adGateMediaWallID = "null";//data.getString("adGateMediaWallID");
                                String OneSignalAppID = data.getString("OneSignalAppID");

                                Ads_ID_Controller ads_id_controller = new Ads_ID_Controller(SplashActivity.this);
                                ads_id_controller.dataStore(
                                        admob_app_id,
                                        admob_banner_id,
                                        Admob_Interstitial_id,
                                        Admob_rewarded_id,
                                        Facebook_app_id,
                                        Facebook_Interstitial_id,
                                        Facebook_rewarded_id,
                                        Fyber_app_id,
                                        Fyber_security_key,
                                        Vungle_key,
                                        Vungle_InterstitialPlacementID,
                                        Applovin_SDK_Key,
                                        AdcolonyAPP_ID,
                                        AdcolonyREWARD_ZONE_ID,
                                        AdcolonyINT_ZONE_ID,
                                        IronSource_App_Key,
                                        pollfish_key,
                                        Unity_Game_Id,
                                        StartaApp_app_id,
                                        Tapjoy_SDK_KEY,
                                        Tapjoy_PLACEMENT_OFFERWALL,
                                        adGateMediaWallID,
                                        OneSignalAppID
                                );

                            }

                        } catch (Exception e) {

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //    showConnectionErrorDialog();
                return;
            }
        });
        queue.add(stringRequest);
    }


    public void UpdateApp() {
        try {
            Task<AppUpdateInfo> appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();
            appUpdateInfoTask.addOnSuccessListener(new OnSuccessListener<AppUpdateInfo>() {
                @Override
                public void onSuccess(AppUpdateInfo appUpdateInfo) {
                    if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                            && appUpdateInfo.isUpdateTypeAllowed(IMMEDIATE)) {
                        try {
                            appUpdateManager.startUpdateFlowForResult(
                                    appUpdateInfo, IMMEDIATE, context, RC_APP_UPDATE);
                            Log.e("TAG", "onCreate:startUpdateFlowForResult part activarte ");
                        } catch (IntentSender.SendIntentException e) {
                            e.printStackTrace();
                            algoratham();
                        }
                    } else {
                        Log.e("TAG", "onCreate:startUpdateFlowForResult else part activarte ");
                        algoratham();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(Exception e) {
                    e.printStackTrace();
                    Log.e("TAG", "onCreate:addOnFailureListener else part activarte ");
                    algoratham();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            algoratham();
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        context = this;
        appUpdateManager.getAppUpdateInfo().addOnSuccessListener(new OnSuccessListener<AppUpdateInfo>() {
            @Override
            public void onSuccess(AppUpdateInfo appUpdateInfo) {
                if (appUpdateInfo.updateAvailability() == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS) {
                    try {
                        appUpdateManager.startUpdateFlowForResult(
                                appUpdateInfo, IMMEDIATE, context, RC_APP_UPDATE);
                    } catch (IntentSender.SendIntentException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_APP_UPDATE) {
            if (resultCode != RESULT_OK) {
                algoratham();
            } else {
                algoratham();
            }
        }
    }


    // Fraud & Prevention

    //app Clone
    private void checkAppCloning() {
        String path = getFilesDir().getPath();
        if (path.contains(DUAL_APP_ID_999)) {
            killProcess();
        } else {
            int count = getDotCount(path);
            if (count > APP_PACKAGE_DOT_COUNT) {
                killProcess();
            }
        }
    }

    private int getDotCount(String path) {
        int count = 0;
        for (int i = 0; i < path.length(); i++) {
            if (count > APP_PACKAGE_DOT_COUNT) {
                break;
            }
            if (path.charAt(i) == DOT) {
                count++;
            }
        }
        return count;
    }

    private void killProcess() {
        finish();
        Process.killProcess(Process.myPid());
    }
    //app Clone

    //vpn detector
    public void checkNetworkAndVPN() {

        if (activeNetwork != null && activeNetwork.isConnected()) {

        } else {

            return;
        }
    }

    public Boolean is_VPN_connected() {
        return connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_VPN).isConnectedOrConnecting();
    }
    //vpn detector


    //end Fraud & Prevention


    //
    //vpn detector

    public void showBlockedDialog(String dialogText) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.show_app_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);

        ImageView imageView = dialog.findViewById(R.id.points_image);
        TextView title_text = dialog.findViewById(R.id.title_text_points);
        TextView points_text = dialog.findViewById(R.id.points);
        AppCompatButton add_btn = dialog.findViewById(R.id.add_btn);

        imageView.setImageResource(R.drawable.ic_close);
        title_text.setText(dialogText);
        points_text.setVisibility(View.GONE);
        add_btn.setText(" Ok ");

        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        dialog.show();
    }

    //end Fraud & Prevention


    //

    public void algoratham() {

        if (AppController.getInstance().isConnected((AppCompatActivity) context) && !(AppController.getInstance().getId().equals("0"))) {
            JsonRequest jsonReq = new JsonRequest(Request.Method.POST, Base_Url, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {


                            if (AppController.getInstance().authorize(response)) {

                                if (AppController.getInstance().getState().equalsIgnoreCase(ACCOUNT_STATE_ENABLED)) {

                                    Intent i = new Intent(context, MainActivity.class);
                                    i.putExtra("new_user", "old");
                                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(i);
                                    finish();


                                } else {

                                    Intent i = new Intent(context, LoginActivity.class);
                                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(i);
                                    finish();
                                    AppController.getInstance().logout((AppCompatActivity) context);

                                }

                            } else {
                                Intent i = new Intent(context, LoginActivity.class);
                                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(i);
                                finish();

                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Intent i = new Intent(context, LoginActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                    finish();


                }
            }) {

                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();

                    params.put(ACCESS_KEY, ACCESS_Value);
                    params.put(GET_USER, API);
                    params.put(ID, "" + AppController.getInstance().getId());

                    return params;
                }
            };

            AppController.getInstance().addToRequestQueue(jsonReq);

        } else {
            Intent i = new Intent(context, LoginActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
            finish();
        }


    }

    public boolean vpn() {
        String iface = "";
        try {
            for (NetworkInterface networkInterface : Collections.list(NetworkInterface.getNetworkInterfaces())) {
                if (networkInterface.isUp())
                    iface = networkInterface.getName();
                Log.d("DEBUG", "IFACE NAME: " + iface);
                if (iface.contains("tun") || iface.contains("ppp") || iface.contains("pptp")) {
                    return true;
                }
            }
        } catch (SocketException e1) {
            e1.printStackTrace();
        }

        return false;
    }

    public void FullScreencall() {
        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
            View v = this.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else if (Build.VERSION.SDK_INT >= 19) {
            //for new api versions.
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }
}