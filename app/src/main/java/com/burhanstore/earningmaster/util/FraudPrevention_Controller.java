package com.burhanstore.earningmaster.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

public class FraudPrevention_Controller {

    private Context context;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;


    @SuppressLint("CommitPrefEdits")
    public FraudPrevention_Controller(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences("FraudPrevention_Controller", Context.MODE_PRIVATE);
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


    public void dataStore(String BlockVpnAccess,
                          String AppCloning,
                          String BlockRootedDevices,
                          String AutoBanRootedDevice


    ) {
        editor.putString("BlockVpnAccess", BlockVpnAccess);
        editor.putString("AppCloning", AppCloning);
        editor.putString("BlockRootedDevices", BlockRootedDevices);
        editor.putString("AutoBanRootedDevice", AutoBanRootedDevice);

        editor.commit();
    }


    public String getBlockVpnAccess() {
        String BlockVpnAccess = sharedPreferences.getString("BlockVpnAccess", "0");
        return BlockVpnAccess;
    }
    public String getAppCloning() {
        String AppCloning = sharedPreferences.getString("AppCloning", "0");
        return AppCloning;
    }

    public String getBlockRootedDevices() {
        String BlockRootedDevices = sharedPreferences.getString("BlockRootedDevices", "0");
        return BlockRootedDevices;
    }
    public String getAutoBanRootedDevice() {
        String AutoBanRootedDevice = sharedPreferences.getString("AutoBanRootedDevice", "0");
        return AutoBanRootedDevice;
    }


}
