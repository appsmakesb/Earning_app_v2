package com.burhanstore.earningmaster.helper;

import static com.burhanstore.earningmaster.helper.Constatnt.ACCESS_KEY;
import static com.burhanstore.earningmaster.helper.Constatnt.ACCESS_Value;
import static com.burhanstore.earningmaster.helper.Constatnt.API;
import static com.burhanstore.earningmaster.helper.Constatnt.Base_Url;
import static com.burhanstore.earningmaster.helper.Constatnt.EMAIL;
import static com.burhanstore.earningmaster.helper.Constatnt.GET_USER;
import static com.burhanstore.earningmaster.helper.Constatnt.ID;
import static com.burhanstore.earningmaster.helper.Constatnt.NAME;
import static com.burhanstore.earningmaster.helper.Constatnt.POINTS;
import static com.burhanstore.earningmaster.helper.Constatnt.REFER_CODE;
import static com.burhanstore.earningmaster.helper.Constatnt.STATTUS;
import static com.burhanstore.earningmaster.helper.Constatnt.USERNAME;

import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.burhanstore.earningmaster.R;
import com.burhanstore.earningmaster.util.Ads_ID_Controller;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseApp;
import com.onesignal.OneSignal;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by Burhan store
 */

public class AppController extends Application {

    private RequestQueue mRequestQueue;
    private static AppController mInstance;
    public static final String TAG = AppController.class.getSimpleName();


    private SharedPreferences sharedPref;

    private String id, status, fcm_id;
    private String username, fullname, accessToken, email, ip_addr, points, phone, device, total_ref, UserCountry, collect_reward_count, spin_count, video_ads_view_reward, open_reward, great_scratch, extra_scratch, additional_scratch, last_date_additional_scratch, last_date_collectreward, last_date_spin, last_date_video_ads_view_reward, last_date_open_reward, last_date_great_scratch, gold_resdd_last, king_tot_date, daily_last_date, pay_earn_gift_date, last_date_extra_scratch, date_registered, password, refercodee, badge, image, rank;
    public static ProgressDialog pDialog;
    private ImageLoader mImageLoader;
    private Ads_ID_Controller ads_id_controller;


    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        sharedPref = this.getSharedPreferences(getString(R.string.settings_file), Context.MODE_PRIVATE);
        OneSignal.initWithContext(this);

        ads_id_controller = new Ads_ID_Controller(this);
        OneSignal.setAppId(ads_id_controller.getOneSignalAppID());
        this.readData();
        FirebaseApp.initializeApp(this);
    }

    public static synchronized AppController getInstance() {
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }

    public Boolean signup_authorize(JSONObject authObj) {
        try {
            Boolean ret = false;
            if (authObj.getString("error").toString().equalsIgnoreCase("true")) {

                return false;
            }
            if (authObj.has("id")) {
                this.setId(authObj.getString(ID));

                this.saveData();


                JsonRequest jsonReq = new JsonRequest(Request.Method.POST, Base_Url, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {

                                if (AppController.getInstance().authorize(response)) {

                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }) {

                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();


                        System.out.println("send**" + AppController.getInstance().getId());
                        params.put(ACCESS_KEY, ACCESS_Value);
                        params.put(GET_USER, API);
                        params.put(ID, "" + AppController.getInstance().getId());

                        return params;
                    }
                };

                AppController.getInstance().addToRequestQueue(jsonReq);


                return true;
            } else {

                return false;

            }


        } catch (JSONException e) {

            e.printStackTrace();
            return false;
        }
    }


    public Boolean authorize(JSONObject authObj) {

        try {

            if (authObj.getString("error").equalsIgnoreCase("true")) {

                return false;
            }
            if (!authObj.has("data")) {

                return false;

            } else {
                JSONObject accountObj = authObj.getJSONObject("data");


                this.setId(accountObj.getString(ID));
                this.setFullname(accountObj.getString(NAME));
                this.setUsername(accountObj.getString(USERNAME));
                this.setEmail(accountObj.getString(EMAIL));
                this.setIp_addr(accountObj.getString("image"));
                this.setImage(accountObj.getString("image"));
                this.setState(accountObj.getString(STATTUS));
                this.setPoints(accountObj.getString(POINTS));
                this.setRefer(accountObj.getString(REFER_CODE));
                this.setPhone(accountObj.getString("phone"));
                this.setdevice(accountObj.getString("device"));
                this.settotal_ref(accountObj.getString("total_ref"));
                this.setUserCountry(accountObj.getString("UserCountry"));
                this.setcollect_reward_count(accountObj.getString("collect_reward_count"));
                this.setspin_count(accountObj.getString("spin_count"));
                this.setvideo_ads_view_reward(accountObj.getString("video_ads_view_reward"));
                this.setopen_reward(accountObj.getString("open_reward"));
                this.setgreat_scratch(accountObj.getString("great_scratch"));
                this.setextra_scratch(accountObj.getString("extra_scratch"));
                this.setadditional_scratch(accountObj.getString("additional_scratch"));
                this.setlast_date_additional_scratch(accountObj.getString("last_date_additional_scratch"));
                this.setlast_date_collectreward(accountObj.getString("last_date_collectreward"));
                this.setlast_date_spin(accountObj.getString("last_date_spin"));
                this.setlast_date_video_ads_view_reward(accountObj.getString("last_date_video_ads_view_reward"));
                this.setlast_date_open_reward(accountObj.getString("last_date_open_reward"));
                this.setlast_date_great_scratch(accountObj.getString("last_date_great_scratch"));
                this.setgold_resdd_last(accountObj.getString("gold_resdd_last"));
                this.setking_tot_date(accountObj.getString("king_tot_date"));
                this.setdaily_last_date(accountObj.getString("daily_last_date"));
                this.setpay_earn_gift_date(accountObj.getString("pay_earn_gift_date"));
                this.setlast_date_extra_scratch(accountObj.getString("last_date_extra_scratch"));
                this.setdate_registered(accountObj.getString("date_registered"));
                this.setBadge(accountObj.getString("badge"));
                this.setRank(authObj.getString("rank"));
                this.saveData();
                return true;
            }


        } catch (JSONException e) {

            e.printStackTrace();
            return false;
        }
    }


    public static Boolean isConnected(final AppCompatActivity activity) {
        Boolean check = false;
        ConnectivityManager ConnectionManager = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = ConnectionManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected() == true) {
            check = true;
        } else {
            Snackbar snackbar = Snackbar
                    .make(activity.findViewById(android.R.id.content), "No Network connection..!!", Snackbar.LENGTH_INDEFINITE)
                    .setAction("RETRY", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = activity.getIntent();
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            activity.startActivity(intent);
                        }
                    });
            snackbar.show();
        }
        return check;
    }

    public void logout(AppCompatActivity activity) {

        AppController.getInstance().removeData();
        AppController.getInstance().readData();
    }

    public static void initpDialog(AppCompatActivity activity) {

        pDialog = new ProgressDialog(activity);
        pDialog.setMessage(activity.getString(R.string.msg_loading));
        pDialog.setCancelable(true);
    }

    public static void showApp_Dialog() {
        try {
            if (!pDialog.isShowing())
                pDialog.show();
        } catch (Exception e) {
        }
    }

    public static void hidepDialog() {
        try {
            if (pDialog.isShowing())
                pDialog.dismiss();
        } catch (Exception e) {

        }

    }

    public void readData() {
        this.setId(sharedPref.getString(getString(R.string.settings_account_id), "0"));
    }

    public void saveData() {
        sharedPref.edit().putString(getString(R.string.settings_account_id), this.getId()).apply();
    }

    public Boolean readFCM() {


        return sharedPref.getBoolean("FCM", false);
    }

    public void saveFCM(Boolean fcm) {

        sharedPref.edit().putBoolean("FCM", fcm).apply();
    }

    public String readToken() {

        return sharedPref.getString("TOKEN", "");
    }

    public void saveToken(String fcm) {

        sharedPref.edit().putString("TOKEN", fcm).apply();
    }

    public void removeData() {

        sharedPref.edit().putString(getString(R.string.settings_account_id), "0").apply();

    }

    public String getId() {

        return this.id;
    }

    public void setId(String id) {

        this.id = id;
    }

    public void setState(String state) {

        this.status = state;
    }

    public String getState() {

        return this.status;
    }

    public String getPoints() {

        return this.points;
    }

    public void setPoints(String username) {

        this.points = username;
    }

    public void setPhone(String phone) {

        this.phone = phone;
    }

    public void setdevice(String device) {

        this.device = device;
    }

    public void settotal_ref(String total_ref) {

        this.total_ref = total_ref;
    }

    public void setUserCountry(String UserCountry) {

        this.UserCountry = UserCountry;
    }

    public void setcollect_reward_count(String collect_reward_count) {

        this.collect_reward_count = collect_reward_count;
    }

    public void setspin_count(String spin_count) {

        this.spin_count = spin_count;
    }

    public void setvideo_ads_view_reward(String video_ads_view_reward) {

        this.video_ads_view_reward = video_ads_view_reward;
    }


    public void setopen_reward(String open_reward) {

        this.open_reward = open_reward;
    }

    public void setgreat_scratch(String great_scratch) {

        this.great_scratch = great_scratch;
    }

    public void setextra_scratch(String extra_scratch) {

        this.extra_scratch = extra_scratch;
    }

    public void setadditional_scratch(String additional_scratch) {

        this.additional_scratch = additional_scratch;
    }

    public void setlast_date_additional_scratch(String last_date_additional_scratch) {

        this.last_date_additional_scratch = last_date_additional_scratch;
    }

    public void setlast_date_collectreward(String last_date_collectreward) {

        this.last_date_collectreward = last_date_collectreward;
    }

    public void setlast_date_spin(String last_date_spin) {

        this.last_date_spin = last_date_spin;
    }

    public void setlast_date_video_ads_view_reward(String last_date_video_ads_view_reward) {

        this.last_date_video_ads_view_reward = last_date_video_ads_view_reward;
    }

    public void setlast_date_open_reward(String last_date_open_reward) {

        this.last_date_open_reward = last_date_open_reward;
    }

    public void setlast_date_great_scratch(String last_date_great_scratch) {

        this.last_date_great_scratch = last_date_great_scratch;
    }

    public void setgold_resdd_last(String gold_resdd_last) {

        this.gold_resdd_last = gold_resdd_last;
    }

    public void setking_tot_date(String king_tot_date) {

        this.king_tot_date = king_tot_date;
    }

    public void setdaily_last_date(String daily_last_date) {

        this.daily_last_date = daily_last_date;
    }

    public void setpay_earn_gift_date(String pay_earn_gift_date) {

        this.pay_earn_gift_date = pay_earn_gift_date;
    }

    public void setlast_date_extra_scratch(String last_date_extra_scratch) {

        this.last_date_extra_scratch = last_date_extra_scratch;
    }


    public void setdate_registered(String date_registered) {

        this.date_registered = date_registered;
    }


    public void setBadge(String badge) {

        this.badge = badge;
    }

    public String getPhone() {

        return this.phone;
    }

    public String getdevice() {

        return this.device;
    }

    public String gettotal_ref() {

        return this.total_ref;
    }

    public String getUserCountry() {

        return this.UserCountry;
    }

    public String getcollect_reward_count() {

        return this.collect_reward_count;
    }

    public String getspin_count() {

        return this.spin_count;
    }

    public String getvideo_ads_view_reward() {

        return this.video_ads_view_reward;
    }

    public String getopen_reward() {

        return this.open_reward;
    }

    public String getgreat_scratch() {

        return this.great_scratch;
    }

    public String getextra_scratch() {

        return this.extra_scratch;
    }

    public String getadditional_scratch() {

        return this.additional_scratch;
    }

    public String getlast_date_additional_scratch() {

        return this.last_date_additional_scratch;
    }

    public String getlast_date_collectreward() {

        return this.last_date_collectreward;
    }

    public String getlast_date_spin() {

        return this.last_date_spin;
    }

    public String getlast_date_video_ads_view_reward() {

        return this.last_date_video_ads_view_reward;
    }

    public String getlast_date_open_reward() {

        return this.last_date_open_reward;
    }

    public String getlast_date_great_scratch() {

        return this.last_date_great_scratch;
    }

    public String getgold_resdd_last() {

        return this.gold_resdd_last;
    }

    public String getking_tot_date() {

        return this.king_tot_date;
    }

    public String getdaily_last_date() {

        return this.daily_last_date;
    }

    public String getpay_earn_gift_date() {

        return this.pay_earn_gift_date;
    }

    public String getlast_date_extra_scratch() {

        return this.last_date_extra_scratch;
    }

    public String getdate_registered() {

        return this.date_registered;
    }


    public String getBadge() {

        return this.badge;
    }

    public String getUsername() {

        return this.username;
    }

    public void setUsername(String username) {

        this.username = username;
    }

    public void setFullname(String fullname) {

        this.fullname = fullname;
    }

    public String getFullname() {

        return this.fullname;
    }

    public void setEmail(String email) {

        this.email = email;
    }

    public String getEmail() {

        return this.email;
    }

    public void setIp_addr(String ip_addr) {

        this.image = ip_addr;
    }

    public String getProfile() {

        return this.image;
    }

    public String setImage(String image) {

        return this.image;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }


    public String getRefercodee() {
        return refercodee;
    }

    public void setRefer(String password) {
        this.refercodee = password;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFcm_id() {
        return fcm_id;
    }

    public void setFcm_id(String fcm_id) {
        this.fcm_id = fcm_id;
    }


    public static void transparentStatusAndNavigation(AppCompatActivity context) {
        //make full transparent statusBar
        if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21) {
            setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, true, context);
        }
        if (Build.VERSION.SDK_INT >= 19) {
            context.getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            );
        }
        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, false, context);
            context.getWindow().setStatusBarColor(Color.TRANSPARENT);
            context.getWindow().setNavigationBarColor(Color.TRANSPARENT);
        }
    }

    private static void setWindowFlag(final int bits, boolean on, AppCompatActivity context) {
        Window win = context.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    //

    public static Context getContext() {
        return mInstance;
    }

}
