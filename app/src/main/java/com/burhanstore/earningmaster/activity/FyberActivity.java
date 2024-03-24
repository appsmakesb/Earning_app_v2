package com.burhanstore.earningmaster.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.multidex.BuildConfig;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;

import com.burhanstore.earningmaster.util.Ads_ID_Controller;
import com.fyber.Fyber;
import com.fyber.utils.FyberLogger;
import com.burhanstore.earningmaster.R;
import com.burhanstore.earningmaster.fragment.OfferWallFragment;

import butterknife.ButterKnife;

public class FyberActivity extends AppCompatActivity {

    private static final int DURATION_MILLIS = 300;
    private static final int DEGREES_360 = 360;
    private static final int DEGREES_0 = 0;
    private static final float PIVOT_X_VALUE = 0.5f;
    private static final float PIVOT_Y_VALUE = 0.5f;
    private static final String TAG = "FyberMainActivity";

    Fragment fragment;
    Ads_ID_Controller ads_id_controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_fyber);
        ButterKnife.bind(this);

        ads_id_controller = new Ads_ID_Controller(this);

//		enabling Fyber logs so that we can see what is going on the SDK level
        FyberLogger.enableLogging(BuildConfig.DEBUG);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragment = fragmentManager.findFragmentById(R.id.offer_wall_fragment_layout);
        if (fragment == null) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragment = new OfferWallFragment();
            fragmentTransaction.add(android.R.id.content, fragment);
            fragmentTransaction.commit();
        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            Fyber.Settings fyberSettings = Fyber
                    .with(ads_id_controller.getFyber_app_id(), this)
                    .withSecurityToken(ads_id_controller.getFyber_security_key())
                    .start();

            customiseFyberSettings(fyberSettings);

        } catch (IllegalArgumentException e) {
            Log.d(TAG, e.getLocalizedMessage());
        }
    }

    //User Settings to customise Fyber SDK behaviour
    private void customiseFyberSettings(Fyber.Settings fyberSettings) {
        fyberSettings.notifyUserOnReward(false)
                .closeOfferWallOnRedirect(true)
                .addParameter("myCustomParamKey", "myCustomParamValue")
                .setCustomUIString(Fyber.Settings.UIStringIdentifier.GENERIC_ERROR, "my custom generic error msg");
    }

    /*
     * ** Fyber SDK: other features **
     *
     * > this method shows you a couple of features from Fyber SDK that we left out of the sample app:
     * > report installs and rewarded actions (mainly for advertisers)
     * > control over which thread should the requester callback run on
     * > creating a new Requester from an existing Requester
     */

    // ** Animations **

    public static Animation getClockwiseAnimation() {
        AnimationSet animationSet = new AnimationSet(true);
        RotateAnimation rotateAnimation = new RotateAnimation(DEGREES_0, DEGREES_360, Animation.RELATIVE_TO_SELF, PIVOT_X_VALUE, Animation.RELATIVE_TO_SELF, PIVOT_Y_VALUE);
        rotateAnimation.setDuration(DURATION_MILLIS);
        animationSet.addAnimation(rotateAnimation);

        return animationSet;
    }

    public static Animation getCounterclockwiseAnimation() {
        AnimationSet animationSet = new AnimationSet(true);
        RotateAnimation rotateAnimation = new RotateAnimation(DEGREES_360, DEGREES_0, Animation.RELATIVE_TO_SELF, PIVOT_X_VALUE, Animation.RELATIVE_TO_SELF, PIVOT_Y_VALUE);
        rotateAnimation.setDuration(DURATION_MILLIS);
        animationSet.addAnimation(rotateAnimation);

        return animationSet;
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(FyberActivity.this, MainActivity.class);
        startActivity(i);
        finish();
    }
}
