package com.burhanstore.earningmaster.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

public class Ads_Controller {

    private Context context;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @SuppressLint("CommitPrefEdits")
    public Ads_Controller(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences("Ads_Controller", Context.MODE_PRIVATE);
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

    public void dataStore(String collect_reward_ads, String Open_Reward_ads, String Everyday_gift_ads,
                          String Scratch_win_ads1,
                          String Scratch_win_ads2,
                          String Scratch_win_ads3,
                          String TicTAcToe_Ads,
                          String gold_reward_ads,
                          String king_pot_ads,
                          String pay_earn_gift_ads,
                          String daily_check_reward_ads,
                          String spin_reward_ads,
                          String Banner_Ads,
                          String Int_Ads,
                          String signup_bonus,
                          String Refer_Point,
                          String Contact_us_email,
                          String spin_Interstitial_ads,
                          String collect_Interstitial_ads,
                          String Open_Interstitial_ads,
                          String Everyday_gift_Interstitial_ads,
                          String Scratch_win_ads1_Interstitial,
                          String Scratch_win_ads2_Interstitial,
                          String Scratch_win_ads3_Interstitial,
                          String TicTAcToe_Interstitial_Ads,
                          String BannerAdsControl,
                          String privacypolicyurl

    ) {
        editor.putString("collect_reward_ads", collect_reward_ads);
        editor.putString("Open_Reward_ads", Open_Reward_ads);
        editor.putString("Everyday_gift_ads", Everyday_gift_ads);
        editor.putString("Scratch_win_ads1", Scratch_win_ads1);
        editor.putString("Scratch_win_ads2", Scratch_win_ads2);
        editor.putString("Scratch_win_ads3", Scratch_win_ads3);
        editor.putString("TicTAcToe_Ads", TicTAcToe_Ads);
        editor.putString("gold_reward_ads", gold_reward_ads);
        editor.putString("king_pot_ads", king_pot_ads);
        editor.putString("pay_earn_gift_ads", pay_earn_gift_ads);
        editor.putString("daily_check_reward_ads", daily_check_reward_ads);
        editor.putString("spin_reward_ads", spin_reward_ads);
        editor.putString("Banner_Ads", Banner_Ads);
        editor.putString("Int_Ads", Int_Ads);
        editor.putString("signup_bonus", signup_bonus);
        editor.putString("Refer_Point", Refer_Point);
        editor.putString("Contact_us_email", Contact_us_email);
        editor.putString("spin_Interstitial_ads", spin_Interstitial_ads);
        editor.putString("collect_Interstitial_ads", collect_Interstitial_ads);
        editor.putString("Open_Interstitial_ads", Open_Interstitial_ads);
        editor.putString("Everyday_gift_Interstitial_ads", Everyday_gift_Interstitial_ads);
        editor.putString("Scratch_win_ads1_Interstitial", Scratch_win_ads1_Interstitial);
        editor.putString("Scratch_win_ads2_Interstitial", Scratch_win_ads2_Interstitial);
        editor.putString("Scratch_win_ads3_Interstitial", Scratch_win_ads3_Interstitial);
        editor.putString("TicTAcToe_Interstitial_Ads", TicTAcToe_Interstitial_Ads);
        editor.putString("BannerAdsControl", BannerAdsControl);
        editor.putString("privacypolicyurl", privacypolicyurl);

        editor.commit();
    }

    public String getCollect_reward_ads() {
        String collect_reward_ads = sharedPreferences.getString("collect_reward_ads", "");
        return "Startapp";

    }

    public String getOpen_Reward_ads() {
        String Open_Reward_ads = sharedPreferences.getString("Open_Reward_ads", "");
        return "Startapp";

    }

    public String getEveryday_gift_ads() {
        String Everyday_gift_ads = sharedPreferences.getString("Everyday_gift_ads", "");
        return "Startapp";

    }

    public String getScratch_win_ads1() {
        String Scratch_win_ads1 = sharedPreferences.getString("Scratch_win_ads1", "");
        return "Startapp";//Scratch_win_ads1;
    }

    public String getScratch_win_ads2() {
        String Scratch_win_ads2 = sharedPreferences.getString("Scratch_win_ads2", "");
        return "Startapp";//Scratch_win_ads2;
    }

    public String getScratch_win_ads3() {
        //String Scratch_win_ads3 = sharedPreferences.getString("Scratch_win_ads3", "");
        return "Startapp";//Scratch_win_ads3;
    }

    public String getTicTAcToe_Ads() {
        String TicTAcToe_Ads = sharedPreferences.getString("TicTAcToe_Ads", "");
        return "Startapp";//TicTAcToe_Ads;
    }

    public String getGold_reward_ads() {
        String gold_reward_ads = sharedPreferences.getString("gold_reward_ads", "");
        return "Startapp";
    }

    public String getKing_pot_ads() {
        String king_pot_ads = sharedPreferences.getString("king_pot_ads", "");
        return "Startapp";//king_pot_ads;
    }

    public String getPay_earn_gift_ads() {
        String pay_earn_gift_ads = sharedPreferences.getString("pay_earn_gift_ads", "");
        return "Startapp";//pay_earn_gift_ads;
    }

    public String getDaily_check_reward_ads() {
        String daily_check_reward_ads = sharedPreferences.getString("daily_check_reward_ads", "");
        return "Startapp";

    }

    public String getSpin_reward_ads() {
        String spin_reward_ads = sharedPreferences.getString("spin_reward_ads", "");
        return "Startapp";
    }

    public String getBanner_Ads() {
        String Banner_Ads = sharedPreferences.getString("Banner_Ads", "");
        return "Startapp";
    }

    public String getInt_AdsApp() {
        String Int_Ads = sharedPreferences.getString("Int_Ads", "");
        return "Startapp";

    }

    public String getSignup_bonus() {
        String signup_bonus = sharedPreferences.getString("signup_bonus", "");
        return signup_bonus;
    }

    public String getRefer_Point() {
        String Refer_Point = sharedPreferences.getString("Refer_Point", "");
        return Refer_Point;
    }

    public String getContact_us_email() {
        String Contact_us_email = sharedPreferences.getString("Contact_us_email", "");
        return Contact_us_email;
    }

    public String getSpin_Interstitial_ads() {
        String spin_Interstitial_ads = sharedPreferences.getString("spin_Interstitial_ads", "");
        return "Startapp";

    }

    public String getCollect_Interstitial_ads() {
        String collect_Interstitial_ads = sharedPreferences.getString("collect_Interstitial_ads", "");
        return "Startapp";

    }

    public String getOpen_Interstitial_ads() {
        String Open_Interstitial_ads = sharedPreferences.getString("Open_Interstitial_ads", "");
        return "Startapp";

    }

    public String getEveryday_gift_Interstitial_ads() {
        String Everyday_gift_Interstitial_ads = sharedPreferences.getString("Everyday_gift_Interstitial_ads", "");
        return "Startapp";

    }

    public String getScratch_win_ads1_Interstitial() {
        String Scratch_win_ads1_Interstitial = sharedPreferences.getString("Scratch_win_ads1_Interstitial", "");
        return Scratch_win_ads1_Interstitial;
    }

    public String getScratch_win_ads2_Interstitial() {
        String Scratch_win_ads2_Interstitial = sharedPreferences.getString("Scratch_win_ads2_Interstitial", "");
        return "Startapp";

    }

    public String getScratch_win_ads3_Interstitial() {
        String Scratch_win_ads3_Interstitial = sharedPreferences.getString("Scratch_win_ads3_Interstitial", "");
        return "Startapp";

    }

    public String getTicTAcToe_Interstitial_Ads() {
        String TicTAcToe_Interstitial_Ads = sharedPreferences.getString("TicTAcToe_Interstitial_Ads", "");
        return "Startapp";

    }

    public String getBannerAdsControl() {
        String BannerAdsControl = sharedPreferences.getString("BannerAdsControl", "");
        return "Startapp";

    }

    public String getprivacypolicyurl() {
        String privacypolicyurl = sharedPreferences.getString("privacypolicyurl", "");
        return "Startapp";
    }


}
