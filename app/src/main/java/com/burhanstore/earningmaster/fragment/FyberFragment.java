package com.burhanstore.earningmaster.fragment;

import android.content.Intent;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.fyber.ads.AdFormat;
import com.fyber.requesters.RequestCallback;
import com.fyber.requesters.RequestError;

public abstract class FyberFragment extends Fragment implements RequestCallback {

    protected static final int OFFERWALL_REQUEST_CODE = 8795;

    private boolean isRequestingState;
    protected Intent intent;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Do not create a new Fragment when the Activity is re-created such as orientation changes.
        setRetainInstance(true);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //resetting button and Intent
        setButtonToOriginalState();
        resetIntent();
    }

    /*
     * ** Fragment specific methods **
     */


    protected abstract int getRequestCode();

    protected abstract void performRequest();


    // when a button is clicked, request or show the ad according to Intent availability
    protected void requestOrShowAd() {
        //avoid requesting an ad when already requesting
        if (!isRequestingState()) {
            //if we already have an Intent, we start the ad Activity
            if (isIntentAvailable()) {
                //start the ad format specific Activity
                startActivityForResult(intent, getRequestCode());

            } else {
                setButtonToRequestingMode();
                //perform the ad request. Each Fragment has its own implementation.
                performRequest();
            }
        }
    }

    /*
     * ** RequestCallback methods **
     */

    @Override
    public void onAdAvailable(Intent intent) {
        resetRequestingState();
        this.intent = intent;
        //if you are using a general purpose requestCallback like this you might want to verify which adFormat will this Intent show.
        //You can use the AdFormat class to obtain an AdFormat from a given Intent. Then you can perform ad format specific actions e.g.:
        AdFormat adFormat = AdFormat.fromIntent(intent);
        if (adFormat == AdFormat.OFFER_WALL) {
            startActivityForResult(intent, OFFERWALL_REQUEST_CODE);
        } else {
            //we only animate the button if it is not an Offer Wall Intent.

            setButtonToSuccessState();
        }
    }

    @Override
    public void onAdNotAvailable(AdFormat adFormat) {
        resetRequestingState();
        resetIntent();
        resetButtonStateWithAnimation();
    }

    @Override
    public void onRequestError(RequestError requestError) {
        resetRequestingState();
        resetIntent();
        resetButtonStateWithAnimation();
    }
    /*
     * ** State helper methods **
     */

    private void resetRequestingState() {
        isRequestingState = false;
    }

    private void resetIntent() {
        intent = null;
    }

    protected boolean isIntentAvailable() {
        return intent != null;
    }

    protected boolean isRequestingState() {
        return isRequestingState;
    }

    /*
     * ** UI state helper methods **
     */

    private void resetButtonStateWithAnimation() {

    }

    protected void setButtonToRequestingMode() {

        isRequestingState = true;
    }

    protected void setButtonToSuccessState() {
    //    setButtonColorAndText(getButton(), getShowText(), getResources().getColor(R.color.buttonColorSuccess));
    }

    protected void setButtonToOriginalState() {
     //   setButtonColorAndText(getButton(), getRequestText(), getResources().getColor(R.color.colorPrimary));
    }

    private void setButtonColorAndText(Button button, String text, int color) {
        Drawable drawable = button.getBackground();
        ColorFilter filter = new LightingColorFilter(color, color);
        drawable.setColorFilter(filter);
        button.setText(text);
    }

}
