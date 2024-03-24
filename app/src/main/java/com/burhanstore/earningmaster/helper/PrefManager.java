package com.burhanstore.earningmaster.helper;

import static com.burhanstore.earningmaster.util.Just_base.addPoint;
import static com.burhanstore.earningmaster.helper.Constatnt.ACCESS_KEY;
import static com.burhanstore.earningmaster.helper.Constatnt.ACCESS_Value;
import static com.burhanstore.earningmaster.helper.Constatnt.ACCOUNT_STATE_ENABLED;
import static com.burhanstore.earningmaster.helper.Constatnt.API;
import static com.burhanstore.earningmaster.helper.Constatnt.Base_Url;
import static com.burhanstore.earningmaster.helper.Constatnt.DAILY_CHECKIN_API;
import static com.burhanstore.earningmaster.helper.Constatnt.DAILY_TYPE;
import static com.burhanstore.earningmaster.helper.Constatnt.GET_USER;
import static com.burhanstore.earningmaster.helper.Constatnt.ID;
import static com.burhanstore.earningmaster.helper.Constatnt.SPIN_TYPE;
import static com.burhanstore.earningmaster.helper.Constatnt.USERNAME;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import com.burhanstore.earningmaster.R;
import com.burhanstore.earningmaster.activity.MainActivity;
import com.burhanstore.earningmaster.activity.SignupActivity;
import com.burhanstore.earningmaster.activity.SplashActivity;


import org.json.JSONObject;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


public class PrefManager {
    static Context context;
    static AppCompatActivity activity;

    public PrefManager(Context context) {
        this.context = context;
    }

    public static void setWindowFlag(Activity activity, final int bits, boolean on) {
        Window win = activity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    public static void saveAdmob(String ads_id, String banner, String full, String reward) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("Admob", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("ads_id", ads_id);
        editor.putString("banner", banner);
        editor.putString("full", full);
        editor.putString("reward", reward);

        editor.commit();
    }

    public static String getAds_id(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("Admob", Context.MODE_PRIVATE);
        return sharedPreferences.getString("ads_id", "");
    }

    public static String getBanner(AppCompatActivity activity) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences("Admob", Context.MODE_PRIVATE);
        return sharedPreferences.getString("banner", "fsdafda");
    }

    public static String getFull(AppCompatActivity activity) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences("Admob", Context.MODE_PRIVATE);
        return sharedPreferences.getString("full", "");
    }

    public static String getReward(AppCompatActivity activity) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences("Admob", Context.MODE_PRIVATE);
        return sharedPreferences.getString("reward", "");
    }

    public String getStatus() {
        SharedPreferences sharedPreferences = activity.getSharedPreferences("Admob", Context.MODE_PRIVATE);
        return sharedPreferences.getString("status", "");
    }

    public boolean reset_admob(AppCompatActivity activity) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences("Admob", Context.MODE_PRIVATE);
        boolean isAds_idEmpty = sharedPreferences.getString("ads_id", "").isEmpty();
        boolean isBannerEmpty = sharedPreferences.getString("banner", "").isEmpty();
        boolean isFullEmpty = sharedPreferences.getString("full", "").isEmpty();
        boolean isRewardEmpty = sharedPreferences.getString("reward", "").isEmpty();
        boolean isStatusEmpty = sharedPreferences.getString("status", "").isEmpty();

        return isAds_idEmpty || isBannerEmpty || isFullEmpty || isRewardEmpty || isStatusEmpty;
    }

    public static void User_Add_Coins(Context context, String coins, String from) {
        FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
        //  Coins_Dialog newFragment = new Coins_Dialog();
        Bundle args = new Bundle();
        args.putString("coins", coins);
        args.putString("from", from);
        //   newFragment.setArguments(args);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        //  transaction.add(android.R.id.content, newFragment).addToBackStack(null).commit();
        addPoint(context, coins, from);
    }

    public static void user_main_points(TextView t) {
        final String[] s = {"0"};
        JsonRequest stringRequest = new JsonRequest(Request.Method.POST, Base_Url, null,

                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.getString("error").equalsIgnoreCase("false")) {
                                t.setText(response.getString("points"));

                            } else {

                            }
                        } catch (Exception e) {
                            Toast.makeText(t.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put(ACCESS_KEY, ACCESS_Value);
                params.put("get_points", AppController.getInstance().getId());

                return params;
            }

        };

        AppController.getInstance().addToRequestQueue(stringRequest);


    }

    public static void algoratham(Context context) {
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
                                    context.startActivity(i);
                                    ((Activity) context).finish();


                                } else {

                                    Intent i = new Intent(context, SignupActivity.class);
                                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    context.startActivity(i);
                                    AppController.getInstance().logout((AppCompatActivity) context);
                                    ((Activity) context).finish();
                                }

                            } else {
                                Intent i = new Intent(context, SignupActivity.class);
                                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                context.startActivity(i);
                                ((Activity) context).finish();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Intent i = new Intent(context, SignupActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    context.startActivity(i);
                    ((Activity) context).finish();
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
            Intent i = new Intent(context, SignupActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            context.startActivity(i);
            ((Activity) context).finish();
        }


    }

    public static void A(Context context) {
        JsonRequest stringRequest = new JsonRequest(Request.Method.POST, Base_Url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.getString("error").equalsIgnoreCase("1")) {
                                if (response.getString("vpn").equals("1")) {
                                    if (vpn()) {
                                        Toast.makeText(context, "Please disconnect the vpn and reopen the app!", Toast.LENGTH_SHORT).show();
                                        ((Activity) context).finish();
                                    } else {
                                        algoratham(context);
                                    }
                                } else {
                                    algoratham(context);
                                }
                            }
                        } catch (Exception e) {
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put(ACCESS_KEY, ACCESS_Value);
                params.put("c", DAILY_TYPE);
                return params;
            }

        };

        AppController.getInstance().addToRequestQueue(stringRequest);

    }

    public static boolean vpn() {
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


    public static void check_n(Context context, Activity activity) {
        if (isConnected(context)) {

        } else {
            // Create the object of
            // AlertDialog Builder class
            AlertDialog.Builder builder
                    = new AlertDialog
                    .Builder(context);

            // Set the message show for the Alert time
            builder.setMessage("Please check your internet.");

            // Set Alert Title
            builder.setTitle("No connection!");
            builder.setCancelable(false);

            // Set Cancelable false
            // for when the user clicks on the outside
            // the Dialog Box then it will remain show
            builder.setCancelable(false);

            // Set the positive button with yes name
            // OnClickListener method is use of
            // DialogInterface interface.

            builder
                    .setPositiveButton(
                            "Retry",
                            new DialogInterface
                                    .OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which) {

                                    // When the user click yes button
                                    // then app will close
                                    dialog.dismiss();
                                    Intent i = new Intent(context, SplashActivity.class);
                                    context.startActivity(i);
                                    activity.finish();

                                }
                            });

            // Set the Negative button with No name
            // OnClickListener method is use
            // of DialogInterface interface.
            builder
                    .setNegativeButton(
                            "Exit",
                            new DialogInterface
                                    .OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which) {

                                    activity.finishAffinity();
                                    System.exit(0);

                                }
                            });

            // Create the Alert dialog
            AlertDialog alertDialog = builder.create();

            // Show the Alert Dialog box
            alertDialog.show();
        }
    }

    public static boolean isConnected(Context context) {
        boolean connected = false;
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo nInfo = cm.getActiveNetworkInfo();
            connected = nInfo != null && nInfo.isAvailable() && nInfo.isConnected();
            return connected;
        } catch (Exception e) {
            Log.e("Connectivity Exception", e.getMessage());
        }
        return connected;
    }


    public static void redeem_package(final Context contextt, String package_id, String p_details, String amount_id) {
        Dialog dialogg = new Dialog(contextt);
        dialogg.setContentView(R.layout.loading);
        dialogg.getWindow().setBackgroundDrawable(new ColorDrawable
                (Color.TRANSPARENT));
        dialogg.setCancelable(false);
        dialogg.show();
        JsonRequest stringRequest = new JsonRequest(Request.Method.POST, Base_Url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            if (response.getString("error").equalsIgnoreCase("false")) {
                                if (response.getString("message").equals("Not enough points available to redeem")) {
                                    dialogg.dismiss();
                                    Toast.makeText(contextt, response.getString("message"), Toast.LENGTH_SHORT).show();
                                } else {
                                    dialogg.dismiss();
                                   /* FragmentManager fragmentManager = ((FragmentActivity)contextt).getSupportFragmentManager();
                                    Done newFragment = new Done();
                                    Bundle args = new Bundle();
                                    args.putString("symbol", symbol);
                                    args.putString("amount", ""+ amount);
                                    newFragment.setArguments(args);
                                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                                    transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                                    transaction.add(android.R.id.content, newFragment).addToBackStack(null).commit();*/
                                    Toast.makeText(contextt, response.getString("message"), Toast.LENGTH_SHORT).show();
                                }


                            } else {
                                dialogg.dismiss();
                                Toast.makeText(contextt, response.getString("message"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            dialogg.dismiss();
                        }
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(contextt, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put(ACCESS_KEY, ACCESS_Value);
                params.put("redeem", package_id);
                params.put("id", AppController.getInstance().getId());
                params.put("p_details", p_details);
                params.put("amount_id", amount_id);
                return params;
            }

        };

        AppController.getInstance().addToRequestQueue(stringRequest);

    }

    public static void claim_points(Context context) {
        JsonRequest stringRequest = new JsonRequest(Request.Method.POST, Base_Url, null,

                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            if (response.getString("error").equalsIgnoreCase("false")) {
                                //daily = true;
                                String p = response.getString("points");
                                // c_sub.setText("Claim your daily "+p+" coins for free");
                                User_Add_Coins(context, p, "Daily checkin bonus");
                            } else {
                                Toast.makeText(context, "You've already claim your daily bonus!", Toast.LENGTH_LONG).show();
                            }
                        } catch (Exception e) {
                            // Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put(ACCESS_KEY, ACCESS_Value);
                params.put(DAILY_CHECKIN_API, API);
                params.put(USERNAME, AppController.getInstance().getUsername());
                // params.put(POINTS, "" + DAILY_POINT);
                params.put(SPIN_TYPE, DAILY_TYPE);
                return params;
            }

        };

        AppController.getInstance().addToRequestQueue(stringRequest);

    }

}
