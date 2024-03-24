package com.burhanstore.earningmaster.activity;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Process;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.multidex.BuildConfig;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.adcolony.sdk.AdColony;
import com.adcolony.sdk.AdColonyAppOptions;
import com.ayetstudios.publishersdk.AyetSdk;
import com.ayetstudios.publishersdk.interfaces.DeductUserBalanceCallback;
import com.ayetstudios.publishersdk.interfaces.UserBalanceCallback;
import com.ayetstudios.publishersdk.messages.SdkUserBalance;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.burhanstore.earningmaster.BaseUrl;
import com.burhanstore.earningmaster.adapter.OfferToro_Adapter;
import com.burhanstore.earningmaster.fragment.ChallengesFragment;
import com.burhanstore.earningmaster.fragment.HomeFragment;
import com.burhanstore.earningmaster.fragment.ProfileFragment;
import com.burhanstore.earningmaster.fragment.VideoAdsFragment;
import com.burhanstore.earningmaster.models.OfferToro_Model;
import com.burhanstore.earningmaster.models.offers_model;
import com.burhanstore.earningmaster.util.Ads_ID_Controller;
import com.burhanstore.earningmaster.util.Constant;
import com.burhanstore.earningmaster.util.FraudPrevention_Controller;
import com.burhanstore.earningmaster.util.RootChecker;
import com.burhanstore.earningmaster.util.SomeEarn_Controller;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.startapp.sdk.adsbase.StartAppAd;
import com.startapp.sdk.adsbase.StartAppSDK;
import com.burhanstore.earningmaster.R;
import com.burhanstore.earningmaster.helper.AppController;

import static com.burhanstore.earningmaster.helper.PrefManager.user_main_points;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    private MainActivity activity;
    private NavigationView mNav;
    private TextView points_textView, user_points_text_view, UserName, UserEmail;
    static final float END_SCALE = 0.7f;
    private RelativeLayout contentView;
    FragmentManager fragmentManager;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ImageView UserProfilePic, LeaderBtn;
    private LinearLayout profile_layout, nav_leaderboard, Nav_wallet, Nav_Contact, Nav_Refer, Nav_Privacy, Nav_Share;
    TextView points, c_sub, name;
    private Boolean connectionErrorDialogShown = false;
    //AppLovin
    // private AppLovinInterstitialAdDialog appLovinInterstitialAd;
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

    String wallCode = "n6aVrg", userId = "1";
    int count = 0;
    Dialog dialog;
    Integer progress = 0;
    String p, res_game, res_offer;
    Boolean daily = false;
    RewardedAd mRe;
    ConstraintLayout main_l;
    private ViewPager2 viewPager2;
    LinearLayout pro_lin, offerwall_btn, video;
    ImageView wheel;
    Boolean is_offer = false, is_game = false;

    TextView game_t;
    RecyclerView game_list, offer_t;
    private List<OfferToro_Model> offerToro_model = new ArrayList<>();
    private OfferToro_Adapter offerToro_adapter;
    private Handler sliderHandler = new Handler();

    private List<offers_model> offers = new ArrayList<>();
    TextView pars;
    CircleImageView pro_img;

    Boolean is_offer_loaded = false;
    String offerwalls;
    LinearLayout spin, game_btn, task, game_more, more_offer;
    SomeEarn_Controller someEarn_controller;
    private CardView SpinBtn;


    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activity = this;
        someEarn_controller = new SomeEarn_Controller(this);

        drawerLayout = findViewById(R.id.drawer_layout);

        fraudPrevention_controller = new FraudPrevention_Controller(this);

        ImageView bottom_nav = findViewById(R.id.bottom_nav);
        bottom_nav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (drawerLayout.isDrawerOpen(GravityCompat.END)) {
                    drawerLayout.closeDrawer(GravityCompat.END);
                }
                drawerLayout.openDrawer(GravityCompat.END);
            }
        });

        points = findViewById(R.id.user_points_text_view);
        profile_layout = findViewById(R.id.profile_layout);
        SpinBtn = findViewById(R.id.SpinBtn);
        setPointsText();


        ads_id_controller = new Ads_ID_Controller(this);

        StartAppSDK.init(this, ads_id_controller.getStartaApp_app_id(), true);
        StartAppSDK.setTestAdsEnabled(BuildConfig.DEBUG);
        StartAppAd.disableSplash();
        StartAppAd.enableConsent(this, false);
        StartAppSDK.setTestAdsEnabled(false);
        StartAppSDK.enableReturnAds(false);

        //
        if (savedInstanceState == null) {
            fragmentManager = getSupportFragmentManager();
            HomeFragment homeFragment = new HomeFragment();
            fragmentManager.beginTransaction().replace(R.id.fragment_container, homeFragment)
                    .commit();
        }
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomnavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                switch (item.getItemId()) {
                    case R.id.Nav_home:
                        fragment = new HomeFragment();
                        break;
                    case R.id.Nav_Challenges:
                        fragment = new ChallengesFragment();
                        break;
                    case R.id.Nav_Video:
                        fragment = new VideoAdsFragment();
                        break;
                    case R.id.Nav_Setting:
                        fragment = new ProfileFragment();
                        break;

                }
                if (fragment != null) {
                    fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment)
                            .commit();
                } else {

                }
                return true;
            }
        });

        profile_layout = findViewById(R.id.profile_layout);
        nav_leaderboard = findViewById(R.id.nav_leaderboard);
        Nav_wallet = findViewById(R.id.Nav_wallet);
        Nav_Contact = findViewById(R.id.Nav_Contact);
        Nav_Refer = findViewById(R.id.Nav_Refer);
        Nav_Privacy = findViewById(R.id.Nav_Privacy);
        Nav_Share = findViewById(R.id.Nav_Share);
        LeaderBtn = findViewById(R.id.LeaderBtn);

        setonClick();

        TextView badge = findViewById(R.id.badge);

        int notification_count = Integer.parseInt(AppController.getInstance().getBadge());
        if (notification_count != 0) {
            badge.setText("" + notification_count);
            badge.setVisibility(View.VISIBLE);
        } else {
            badge.setVisibility(View.GONE);
        }

        final HashMap<String, String> subids = new HashMap<String, String>();
        subids.put("s2", "my sub id");

        //ayet
        AppController.getInstance();
        collectReward_data();
        videowall();
        OpenReward();
        KingPot();
        dailychh();
        Pay_Earn();
        GreatScratc();
        goldReward_data();
        AScratch();
        ExtraScratch();

        AyetSdk.init((Application) getApplicationContext(), AppController.getInstance().getId(), new UserBalanceCallback() { // UserBalanceCallback is optional if you want to manage balances on your servers
            @Override
            public void userBalanceChanged(SdkUserBalance sdkUserBalance) {
                Log.d("AyetSdk", "userBalanceChanged - available balance: " + sdkUserBalance.getAvailableBalance()); // this is the new total available balance for the user
            }

            @Override
            public void userBalanceInitialized(SdkUserBalance sdkUserBalance) {
                Log.d("AyetSdk", "SDK initialization successful");
                Log.d("AyetSdk", "userBalanceInitialized - available balance: " + sdkUserBalance.getAvailableBalance()); // this is the total available balance for the user
                Log.d("AyetSdk", "userBalanceInitialized - spent balance: " + sdkUserBalance.getSpentBalance()); // this is the total amount spent with "AyetSdk.deductUserBalance(..)"
                Log.d("AyetSdk", "userBalanceInitialized - pending balance: " + sdkUserBalance.getPendingBalance()); // this is the amount currently pending for conversion (e.g. user still has offer requirements to meet)
            }

            @Override
            public void initializationFailed() {
                Log.d("AyetSdk", "initializationFailed - please check APP API KEY & internet connectivity");
            }
        });

        int amount = 100;
        AyetSdk.deductUserBalance(MainActivity.this, amount, new DeductUserBalanceCallback() {
            @Override
            public void success() {

            }

            @Override
            public void failed() {
                Log.d("AyetSdk", "deductUserBalance - failed");
                // this usually means that the user does not have sufficient balance in his account
            }
        });

        //AppLovin
        //  appLovinInterstitialAd = AppLovinInterstitialAd.create(AppLovinSdk.getInstance(MainActivity.this), MainActivity.this);

        //AdColony
        AdColonyAppOptions appOptions = new AdColonyAppOptions()
                .setKeepScreenOn(true)
                .setPrivacyFrameworkRequired(AdColonyAppOptions.GDPR, true)
                .setPrivacyConsentString(AdColonyAppOptions.GDPR, String.valueOf(1));
        AdColony.configure(this, appOptions, ads_id_controller.getAdcolonyAPP_ID(), ads_id_controller.getAdcolonyREWARD_ZONE_ID());

        this.navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        IntNav();

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
    

        //App Cloning
        if (fraudPrevention_controller.getAppCloning().equals("Yes")) {
            //app Clone
            checkAppCloning();
            //app Clone

        }


        //Initializing the bottomNavigationView
        // AppController.transparentStatusAndNavigation(MainActivity.this);


        check_AppData();


    }

    private void collectReward_data() {

        if (AppController.getInstance().getcollect_reward_count().equals("")) {
            Constant.setString(activity, Constant.Collect_Reward_COUNT, someEarn_controller.getCollectRewardCount());
        }


        if (AppController.getInstance().getcollect_reward_count() != null) {
            Constant.setString(activity, Constant.Collect_Reward_COUNT, AppController.getInstance().getcollect_reward_count());
        }
        if (AppController.getInstance().getlast_date_collectreward() != null) {
            Constant.setString(activity, Constant.LAST_DATE_Collect_Reward, AppController.getInstance().getlast_date_collectreward());

        }

        if (AppController.getInstance().getcollect_reward_count().equals("0")) {
            String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
            if (currentDate.equals(AppController.getInstance().getlast_date_collectreward())) {
            } else {
                Constant.setString(activity, Constant.Collect_Reward_COUNT, someEarn_controller.getCollectRewardCount());
            }
        }


    }

    private void videowall() {

        if (AppController.getInstance().getvideo_ads_view_reward().equals("")) {
            Constant.setString(activity, Constant.Video_Ads_View_Reward_COUNT, someEarn_controller.getVideo_Ads_Available_Views());
        }


        if (AppController.getInstance().getvideo_ads_view_reward() != null) {
            Constant.setString(activity, Constant.Video_Ads_View_Reward_COUNT, AppController.getInstance().getvideo_ads_view_reward());
        }
        if (AppController.getInstance().getlast_date_video_ads_view_reward() != null) {
            Constant.setString(activity, Constant.LAST_DATE_Video_Ads_View_Reward, AppController.getInstance().getlast_date_video_ads_view_reward());

        }

        if (AppController.getInstance().getvideo_ads_view_reward().equals("0")) {
            String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
            if (currentDate.equals(AppController.getInstance().getlast_date_video_ads_view_reward())) {
            } else {
                Constant.setString(activity, Constant.Video_Ads_View_Reward_COUNT, someEarn_controller.getVideo_Ads_Available_Views());
            }
        }


    }

    private void OpenReward() {

        if (AppController.getInstance().getopen_reward().equals("")) {
            Constant.setString(activity, Constant.Open_Reward_COUNT, someEarn_controller.getOpenRewardCount());
        }


        if (AppController.getInstance().getopen_reward() != null) {
            Constant.setString(activity, Constant.Open_Reward_COUNT, AppController.getInstance().getopen_reward());
        }
        if (AppController.getInstance().getlast_date_open_reward() != null) {
            Constant.setString(activity, Constant.LAST_DATE_Open_Reward, AppController.getInstance().getlast_date_open_reward());

        }

        if (AppController.getInstance().getopen_reward().equals("0")) {
            String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
            if (currentDate.equals(AppController.getInstance().getlast_date_open_reward())) {
            } else {
                Constant.setString(activity, Constant.Open_Reward_COUNT, someEarn_controller.getOpenRewardCount());
            }
        }


    }

    private void GreatScratc() {

        if (AppController.getInstance().getgreat_scratch().equals("")) {
            Constant.setString(activity, Constant.Great_Scratch_COUNT, someEarn_controller.getGreat_Scratch_Chances());
        }


        if (AppController.getInstance().getgreat_scratch() != null) {
            Constant.setString(activity, Constant.Great_Scratch_COUNT, AppController.getInstance().getgreat_scratch());
        }
        if (AppController.getInstance().getlast_date_great_scratch() != null) {
            Constant.setString(activity, Constant.LAST_DATE_Great_SCRATCH, AppController.getInstance().getlast_date_great_scratch());

        }

        if (AppController.getInstance().getgreat_scratch().equals("0")) {
            String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
            if (currentDate.equals(AppController.getInstance().getlast_date_great_scratch())) {
            } else {
                Constant.setString(activity, Constant.Great_Scratch_COUNT, someEarn_controller.getGreat_Scratch_Chances());
            }
        }


    }

    private void goldReward_data() {

        if (AppController.getInstance().getgold_resdd_last() != null) {
            Constant.setString(activity, Constant.GoldReward_DATE, AppController.getInstance().getgold_resdd_last());

        }

    }

    private void KingPot() {

        if (AppController.getInstance().getking_tot_date() != null) {
            Constant.setString(activity, Constant.King_Pot_DATE, AppController.getInstance().getking_tot_date());

        }

    }

    private void dailychh() {

        if (AppController.getInstance().getdaily_last_date() != null) {
            Constant.setString(activity, Constant.LAST_DATE, AppController.getInstance().getdaily_last_date());

        }

    }

    private void Pay_Earn() {

        if (AppController.getInstance().getpay_earn_gift_date() != null) {
            Constant.setString(activity, Constant.Pay_Earn_Gift_DATE, AppController.getInstance().getpay_earn_gift_date());

        }

    }

    private void ExtraScratch() {

        if (AppController.getInstance().getextra_scratch().equals("")) {
            Constant.setString(activity, Constant.Extra_Scratch_COUNT, someEarn_controller.getExtra_Scratch_Chances());
        }


        if (AppController.getInstance().getextra_scratch() != null) {
            Constant.setString(activity, Constant.Extra_Scratch_COUNT, AppController.getInstance().getextra_scratch());
        }
        if (AppController.getInstance().getlast_date_extra_scratch() != null) {
            Constant.setString(activity, Constant.LAST_DATE_Extra_SCRATCH, AppController.getInstance().getlast_date_extra_scratch());

        }

        if (AppController.getInstance().getextra_scratch().equals("0")) {
            String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
            if (currentDate.equals(AppController.getInstance().getlast_date_extra_scratch())) {
            } else {
                Constant.setString(activity, Constant.Extra_Scratch_COUNT, someEarn_controller.getExtra_Scratch_Chances());
            }
        }


    }

    private void AScratch() {

        if (AppController.getInstance().getadditional_scratch().equals("")) {
            Constant.setString(activity, Constant.Additional_Scratch_COUNT, someEarn_controller.getAdditional_Scratch_Chances());
        }

        if (AppController.getInstance().getadditional_scratch() != null) {
            Constant.setString(activity, Constant.Additional_Scratch_COUNT, AppController.getInstance().getadditional_scratch());
        }
        if (AppController.getInstance().getlast_date_additional_scratch() != null) {
            Constant.setString(activity, Constant.LAST_DATE_Additional_SCRATCH, AppController.getInstance().getlast_date_additional_scratch());

        }

        if (AppController.getInstance().getadditional_scratch().equals("0")) {
            String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
            if (currentDate.equals(AppController.getInstance().getlast_date_additional_scratch())) {
            } else {
                Constant.setString(activity, Constant.Additional_Scratch_COUNT, someEarn_controller.getAdditional_Scratch_Chances());
            }
        }

    }


    private void setonClick() {

        RelativeLayout bell = findViewById(R.id.bell);
        bell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, NotificationActivity.class);
                startActivity(i);
            }
        });

        nav_leaderboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, LeaderBoardActivity.class);
                startActivity(i);
            }
        });
        profile_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(i);
            }
        });
        Nav_Contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, ContactActivity.class);
                startActivity(i);
            }
        });

        Nav_wallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, WalletActivity.class);
                startActivity(i);
            }
        });
        Nav_Refer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, ReferAndEarnActivity.class);
                startActivity(i);
            }
        });
        LeaderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, LeaderBoardActivity.class);
                startActivity(i);
            }
        });
        Nav_Privacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, WebViewActivity.class);
                startActivity(i);
            }
        });
        SpinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, SpinToWinActivity.class);
                startActivity(i);
            }
        });
        Nav_Share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, activity.getResources().getString(R.string.share_text) + "' " + AppController.getInstance().getRefercodee() + " '" + " Download Link = " + " https://play.google.com/store/apps/details?id=" + activity.getPackageName());
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
            }
        });

    }

    private void IntNav() {

        View headerview = navigationView.getHeaderView(0);
        this.UserName = (TextView) headerview.findViewById(R.id.UserName);
        this.UserEmail = (TextView) headerview.findViewById(R.id.UserEmail);
        this.UserProfilePic = (ImageView) headerview.findViewById(R.id.UserProfilePic);
        UserName.setText(AppController.getInstance().getFullname());
        UserEmail.setText(AppController.getInstance().getEmail());
        Glide.with(activity).load(BaseUrl.ADMIN_URL + "api/images/" + AppController.getInstance().getProfile())
                .apply(new RequestOptions().placeholder(R.drawable.user_ic))
                .into(UserProfilePic);


    }

    public void setPointsText() {
        points.setText("0");
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        showExitDialog();
    }


    private void showExitDialog() {
        RelativeLayout ExitDialog = findViewById(R.id.ExitDialog);
        ExitDialog.setVisibility(View.VISIBLE);

        TextView title_text = findViewById(R.id.title_text_points);
        TextView points_text = findViewById(R.id.points);
        AppCompatButton cancel_btn = findViewById(R.id.cancel_btn);
        AppCompatButton yes_btn = findViewById(R.id.add_btn);

        title_text.setText(getResources().getString(R.string.exit_text));
        points_text.setVisibility(View.GONE);
        cancel_btn.setVisibility(View.VISIBLE);
        cancel_btn.setText(getResources().getString(R.string.no));
        yes_btn.setText(getResources().getString(R.string.yes));

        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ExitDialog.setVisibility(View.GONE);
            }
        });

        yes_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ExitDialog.setVisibility(View.GONE);
                finish();
            }
        });
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

    public static void toast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    private void check_AppData() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            // this will request for permission when permission is not true
        } else {
            // Download code here
        }
    }

    @Override
    protected void onResume() {
        AppController.getInstance();
        user_main_points(points);
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        getWindow().clearFlags(android.view.WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }


}
