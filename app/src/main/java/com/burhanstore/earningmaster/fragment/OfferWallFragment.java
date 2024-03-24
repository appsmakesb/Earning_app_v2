package com.burhanstore.earningmaster.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.fyber.requesters.OfferWallRequester;
import com.burhanstore.earningmaster.R;

import butterknife.BindView;

public class OfferWallFragment extends FyberFragment {
    private static final String TAG = OfferWallFragment.class.getSimpleName();
    private Dialog dialogLoading;

    @BindView(R.id.offer_wall_button)
    Button offer_wall_button;

    public OfferWallFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_offerwall, container, false);

        setButtonToSuccessState();
        offer_wall_button = view.findViewById(R.id.offer_wall_button);
        offer_wall_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestOrShowAd();
            }
        });

        return view;
    }


    /*
     * ** Code to perform a an Offer Wall request **
     */

    @Override
    protected void performRequest() {
        //Unless the device is not supported, OfferWallRequester will always return an Intent.
        //However, for consistency reasons Offer Wall has the same callback as other ad formats

        //Requesting the offer wall
        OfferWallRequester
                .create(this)
                .request(getActivity());
    }

    /*
     * ** FyberFragment methods **
     */


    @Override
    protected int getRequestCode() {
        return OFFERWALL_REQUEST_CODE;
    }

    /*
     * ** Offer wall specific state methods **
     */


}
