package com.burhanstore.earningmaster.util;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.multidex.MultiDex;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.facebook.ads.AdSettings;
import com.facebook.ads.AudienceNetworkAds;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;


public class AppConfig extends Application {
    private static AppConfig mInstance;
    public static final String TAG = AppConfig.class.getSimpleName();

    private RequestQueue mRequestQueue;
    Ads_Controller ads_controller;

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        ads_controller = new Ads_Controller(mInstance);

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        String typeOfAds = ads_controller.getInt_AdsApp();
        switch (typeOfAds) {
            case "Facebook":
                AudienceNetworkAds.initialize(mInstance);
                AdSettings.addTestDevice("ad3f6f42-76a5-460a-a607-4d58eccee585");
                break;
            case "Admob":
                MobileAds.initialize(this, new OnInitializationCompleteListener() {
                    @Override
                    public void onInitializationComplete(InitializationStatus initializationStatus) {
                        Log.e("TAG", "onInitializationComplete: Initialize Successfully...");
                    }
                });
                break;
        }
    }

    public static Context getContext() {
        return mInstance;
    }


    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public static synchronized AppConfig getInstance() {
        return mInstance;
    }

}
