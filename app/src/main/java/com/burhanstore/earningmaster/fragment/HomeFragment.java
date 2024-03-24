package com.burhanstore.earningmaster.fragment;

import static com.burhanstore.earningmaster.helper.AppController.hidepDialog;
import static com.burhanstore.earningmaster.helper.PrefManager.User_Add_Coins;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.burhanstore.earningmaster.BaseUrl;
import com.burhanstore.earningmaster.R;
import com.burhanstore.earningmaster.activity.CollectRewardActivity;
import com.burhanstore.earningmaster.activity.EarningHistoryActivity;
import com.burhanstore.earningmaster.activity.EverydayGiftActivity;
import com.burhanstore.earningmaster.activity.GoldRewardActivity;
import com.burhanstore.earningmaster.activity.KingPotActivity;
import com.burhanstore.earningmaster.activity.MainActivity;
import com.burhanstore.earningmaster.activity.OpenRewardActivity;
import com.burhanstore.earningmaster.activity.PayEarnGiftActivity;
import com.burhanstore.earningmaster.activity.ReferAndEarnActivity;
import com.burhanstore.earningmaster.activity.ScratchActivity;
import com.burhanstore.earningmaster.activity.TictactoeMainActivity;
import com.burhanstore.earningmaster.activity.CompleteSurveyActivity;
import com.burhanstore.earningmaster.activity.TransactionsHistoryActivity;
import com.burhanstore.earningmaster.activity.WalletActivity;
import com.burhanstore.earningmaster.helper.AppController;
import com.burhanstore.earningmaster.helper.JsonRequest;
import com.burhanstore.earningmaster.util.Ads_Controller;
import com.burhanstore.earningmaster.util.SomeEarn_Controller;
import com.burhanstore.earningmaster.util.Constant;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class HomeFragment extends Fragment {

    private RelativeLayout ReferBtn;
    private CardView WalletHistoryBtn, Earning_History, ScratchToWinBtn, CompleteSurveyBtn, CollectRewardBtn, WalletBtn, dailyCheckIn, OpenRewardBtn, TicTac, everydayGiftBtn, GoldReward, KingPot, PayEarnGift;
    private Context activity;
    SomeEarn_Controller someEarn_controller;
    private TextView daily_check_in_textViewa;
    private String uid;
    Ads_Controller ads_controller;

    public HomeFragment() {

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = context;
    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        ReferBtn = view.findViewById(R.id.ReferBtn);
        WalletBtn = view.findViewById(R.id.WalletBtn);
        dailyCheckIn = view.findViewById(R.id.daily_check_in);
        CollectRewardBtn = view.findViewById(R.id.CollectRewardBtn);
        CompleteSurveyBtn = view.findViewById(R.id.CompleteSurveyBtn);
        OpenRewardBtn = view.findViewById(R.id.OpenRewardBtn);
        everydayGiftBtn = view.findViewById(R.id.everydayGiftBtn);
        daily_check_in_textViewa = view.findViewById(R.id.daily_check_in_textView);
        GoldReward = view.findViewById(R.id.GoldReward);
        KingPot = view.findViewById(R.id.KingPot);
        TicTac = view.findViewById(R.id.TicTac);
        PayEarnGift = view.findViewById(R.id.PayEarnGift);
        ScratchToWinBtn = view.findViewById(R.id.ScratchToWinBtn);
        Earning_History = view.findViewById(R.id.Earning_History);
        WalletHistoryBtn = view.findViewById(R.id.WalletHistoryBtn);

        onClick();

        someEarn_controller = new SomeEarn_Controller(getContext());
        daily_check_in_textViewa.setText(someEarn_controller.getDaily_check());
        ads_controller = new Ads_Controller(getContext());

        //Slider Image
        ImageView Slide1 = view.findViewById(R.id.Slide1);
        ImageView Slide2 = view.findViewById(R.id.Slide2);
        ImageView Slide3 = view.findViewById(R.id.Slide3);

        Glide.with(this).load(someEarn_controller.getSlide_image1()).into(Slide1);
        Glide.with(this).load(someEarn_controller.getSlide_image2()).into(Slide2);
        Glide.with(this).load(someEarn_controller.getSlide_image3()).into(Slide3);

        Slide1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (someEarn_controller.getSlide1_url_control().equals("Url")) {
                    Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(someEarn_controller.getSlide1_openurl()));
                    startActivity(i);
                }
            }
        });
        Slide2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (someEarn_controller.getSlide2_url_control().equals("Url")) {
                    Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(someEarn_controller.getSlide2_openurl()));
                    startActivity(i);
                }
            }
        });
        Slide3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (someEarn_controller.getSlide3_url_control().equals("Url")) {
                    Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(someEarn_controller.getSlide3_openurl()));
                    startActivity(i);
                }
            }
        });


        // //Vungle
        //        Constant.initVungle((Activity) activity);
        //        Constant.loadAdVungle((Activity) activity);
        //
        //        //Adcolony
        //        Constant.initRewardedAdAdColony((Activity) activity);

        return view;
    }

    private void onClick() {
        CollectRewardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(activity, CollectRewardActivity.class);
                startActivity(i);
            }
        });

        CompleteSurveyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(activity, CompleteSurveyActivity.class);
                startActivity(i);
            }
        });
        OpenRewardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(activity, OpenRewardActivity.class);
                startActivity(i);
            }
        });
        TicTac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(activity, TictactoeMainActivity.class);
                startActivity(i);
            }
        });
        everydayGiftBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(activity, EverydayGiftActivity.class);
                startActivity(i);
            }
        });
        KingPot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(activity, KingPotActivity.class);
                startActivity(i);
            }
        });
        GoldReward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(activity, GoldRewardActivity.class);
                startActivity(i);
            }
        });
        PayEarnGift.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(activity, PayEarnGiftActivity.class);
                startActivity(i);
            }
        });
        ReferBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent policyintent = new Intent(activity, ReferAndEarnActivity.class);
                startActivity(policyintent);
            }
        });

        WalletBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent policyintent = new Intent(activity, WalletActivity.class);
                startActivity(policyintent);
            }
        });

        dailyCheckIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkDaily();
            }
        });

        ScratchToWinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(activity, ScratchActivity.class);
                startActivity(i);
            }
        });

        Earning_History.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(activity, EarningHistoryActivity.class);
                startActivity(i);
            }
        });

        WalletHistoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(activity, TransactionsHistoryActivity.class);
                startActivity(i);
            }
        });


    }

    private void checkDaily() {
        if (Constant.isNetworkAvailable(activity)) {

            String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
            Log.e("TAG", "onClick: Current Date" + currentDate);
            String last_date = Constant.getString(activity, Constant.LAST_DATE);
            Log.e("TAG", "onClick: last_date Date" + last_date);
            if (last_date.equals("")) {
                Constant.setString(activity, Constant.LAST_DATE, currentDate);

                String DAILY_TYPE = "Daily Checking Reward";
                User_Add_Coins(activity, daily_check_in_textViewa.getText().toString(), DAILY_TYPE);

                showDialogOfPoints(Integer.parseInt(daily_check_in_textViewa.getText().toString()));
                if (getActivity() == null) {
                    return;
                }

                //
                String work_dateDate = Constant.getString(activity, Constant.LAST_DATE);
                UserWorkUpdateDate(work_dateDate);
                //

            } else {
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                try {
                    Date pastDAte = sdf.parse(last_date);
                    Date currentDAte = sdf.parse(currentDate);
                    long diff = currentDAte.getTime() - pastDAte.getTime();
                    long difference_In_Days = (diff / (1000 * 60 * 60 * 24)) % 365;
                    Log.e("TAG", "onClick: Days Diffrernce" + difference_In_Days);
                    if (difference_In_Days > 0) {
                        Constant.setString(activity, Constant.LAST_DATE, currentDate);

                        String DAILY_TYPE = "Daily Checking Reward";
                        User_Add_Coins(activity, daily_check_in_textViewa.getText().toString(), DAILY_TYPE);

                        showDialogOfPoints(Integer.parseInt(daily_check_in_textViewa.getText().toString()));
                        if (getActivity() == null) {
                            return;
                        }
                        //
                        String work_dateDate = Constant.getString(activity, Constant.LAST_DATE);
                        UserWorkUpdateDate(work_dateDate);
                        //

                    } else {
                        showDialogOfPoints(0);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        } else {
            Constant.showInternetErrorDialog(activity, getResources().getString(R.string.internet_connection_of_text));
        }
    }

    public void showDialogOfPoints(int points) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.show_points_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);

        ImageView imageView = dialog.findViewById(R.id.points_image);
        TextView title_text = dialog.findViewById(R.id.title_text_points);
        TextView points_text = dialog.findViewById(R.id.points);
        AppCompatButton add_btn = dialog.findViewById(R.id.add_btn);

        if (points == Integer.parseInt(daily_check_in_textViewa.getText().toString())) {

            title_text.setText(getResources().getString(R.string.you_win));
            points_text.setVisibility(View.VISIBLE);
            points_text.setText(String.valueOf(points));
            add_btn.setText(getResources().getString(R.string.account_add_to_wallet));
            //  Video Ads
            String typeOfAds = ads_controller.getDaily_check_reward_ads();
            switch (typeOfAds) {
                case "Admob":
                    Constant.showRewardedAdmobAds((Activity) activity);
                    break;
                case "Facebook":
                    Constant.showRewardedFacebookAds((Activity) activity);
                    break;
                case "Vungle":
                    Constant.showRewardedVungleAds((Activity) activity);
                    break;

                case "UnityAds":

                    break;
                case "AdColony":
                    Constant.ShowAdcolonyAds((Activity) activity);

                    break;
                case "Startapp":
                    Constant.StartappRewardedVideo((Activity) activity);

                    break;
                case "applovin":
                    Constant.ApplovinShowAds((Activity) activity);

                    break;
            }

        } else {
            imageView.setImageResource(R.drawable.banner_gift);
            title_text.setText(getResources().getString(R.string.today_checkin_over));
            points_text.setVisibility(View.GONE);
            add_btn.setText(getResources().getString(R.string.ok_text));
        }
        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
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
                params.put("get_user_data_dailych_date", "any");
                params.put("id", AppController.getInstance().getId());
                params.put("UpdateWork", data);

                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(jsonReq);
    }


}