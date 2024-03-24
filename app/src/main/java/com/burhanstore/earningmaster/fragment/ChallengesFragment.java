package com.burhanstore.earningmaster.fragment;

import static com.burhanstore.earningmaster.helper.PrefManager.user_main_points;

import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.adgatemedia.sdk.classes.AdGateMedia;
import com.adgatemedia.sdk.network.OnOfferWallLoadFailed;
import com.adgatemedia.sdk.network.OnOfferWallLoadSuccess;
import com.ayetstudios.publishersdk.AyetSdk;
import com.burhanstore.earningmaster.R;
import com.burhanstore.earningmaster.activity.FyberActivity;
import com.burhanstore.earningmaster.activity.IronSourceActivity;
import com.burhanstore.earningmaster.activity.PollfishActivity;
import com.burhanstore.earningmaster.helper.AppController;
import com.burhanstore.earningmaster.util.Ads_ID_Controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by Burhan store
 */
public class ChallengesFragment extends Fragment {

    private ImageView adGateMedia_Image, Pollfish_Image, Fyber_Image_section, ironSource_Image, OfferToro_Image_section;
    private TextView adGateMedia_Text, adGateMedia_Coin, Pollfish_Text, Pollfish_Coin, Fyber_Text, Fyber_Coin, user_points_text_view, ironSource_Coin, ironSource_Text, OfferToro_Coin, OfferToro_Text;
    private Button ironSource_Btn, Pollfish_Btn, Fyber_Btn, OfferToro_Btn, adGateMedia_Btn;
    private Context activity;
    private Dialog dialogadGate;
    String ADGET_WALL_id;
    private Ads_ID_Controller ads_id_controller;

    public ChallengesFragment() {

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = context;
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
        View view = inflater.inflate(R.layout.fragment_challenges, container, false);

        ads_id_controller = new Ads_ID_Controller(activity);

        Pollfish_Text = view.findViewById(R.id.Pollfish_Text);
        Pollfish_Image = view.findViewById(R.id.Pollfish_Image);
        Pollfish_Coin = view.findViewById(R.id.Pollfish_Coin);
        Fyber_Image_section = view.findViewById(R.id.Fyber_Image_section);
        Fyber_Text = view.findViewById(R.id.Fyber_Text);
        Fyber_Coin = view.findViewById(R.id.Fyber_Coin);
        user_points_text_view = view.findViewById(R.id.user_points_text_view);
        ironSource_Image = view.findViewById(R.id.ironSource_Image);
        ironSource_Coin = view.findViewById(R.id.ironSource_Coin);
        ironSource_Text = view.findViewById(R.id.ironSource_Text);
        Pollfish_Btn = view.findViewById(R.id.Pollfish_Btn);
        Fyber_Btn = view.findViewById(R.id.Fyber_Btn);
        ironSource_Btn = view.findViewById(R.id.ironSource_Btn);
        adGateMedia_Btn = view.findViewById(R.id.adGateMedia_Btn);
        adGateMedia_Coin = view.findViewById(R.id.adGateMedia_Coin);
        adGateMedia_Image = view.findViewById(R.id.adGateMedia_Image);
        adGateMedia_Text = view.findViewById(R.id.adGateMedia_Text);
        OfferToro_Coin = view.findViewById(R.id.OfferToro_Coin);
        OfferToro_Image_section = view.findViewById(R.id.OfferToro_Image_section);
        OfferToro_Text = view.findViewById(R.id.OfferToro_Text);
        OfferToro_Btn = view.findViewById(R.id.OfferToro_Btn);


        Pollfish_Text.setText("Pollfish");
        Pollfish_Image.setImageResource(R.drawable.pollfish_ic);
        Pollfish_Coin.setText("+");

        Fyber_Text.setText("Fyber");
        Fyber_Coin.setText("+");
        Fyber_Image_section.setImageResource(R.drawable.fyber_ic);

        ironSource_Image.setImageResource(R.drawable.ironsource_ic);
        ironSource_Coin.setText("+");
        ironSource_Text.setText("ironSource");

        adGateMedia_Text.setText("adGateMedia");
        adGateMedia_Coin.setText("+");
        adGateMedia_Image.setImageResource(R.drawable.adgate_media);

        OfferToro_Text.setText("ayeT-Studios");
        OfferToro_Coin.setText("+");
        OfferToro_Image_section.setImageResource(R.drawable.ayetstudios_ic);

        //date
        TextView text_view_date_activity = view.findViewById(R.id.text_view_date_activity);
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
        text_view_date_activity.setText(dateFormat.format(date));

        user_points_text_view.setText("0");
        user_main_points(user_points_text_view);
        AppController.getInstance();

        onClickView();
        ADGET_WALL_id = ads_id_controller.getadGateMediaWallID();


        return view;
    }

    private void onClickView() {
        Pollfish_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(activity, PollfishActivity.class);
                startActivity(i);
            }
        });
        Fyber_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(activity, FyberActivity.class);
                startActivity(i);
            }
        });
        ironSource_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(activity, IronSourceActivity.class);
                startActivity(i);
            }
        });

        adGateMedia_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adGateMediaShow();
            }
        });

        OfferToro_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AyetStudioOffer();
            }
        });

    }

    private void adGateMediaShow() {
        //Loading Dialog
        dialogadGate = new Dialog(activity);
        dialogadGate.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogadGate.setContentView(R.layout.loading_dialog);
        dialogadGate.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogadGate.show();

        final HashMap<String, String> subids = new HashMap<String, String>();
        subids.put("s2", "my sub id");
        AdGateMedia.getInstance().setDebugMode(true);
        AdGateMedia adGateMedia = AdGateMedia.getInstance();
        adGateMedia.loadOfferWall(activity,
                ADGET_WALL_id,
                AppController.getInstance().getId(),
                subids,
                new OnOfferWallLoadSuccess() {
                    @Override
                    public void onOfferWallLoadSuccess() {
                        dialogadGate.dismiss();
                        // dialogadGate you can call adGateMedia.showOfferWall();
                        AdGateMedia.getInstance().showOfferWall(activity,
                                new AdGateMedia.OnOfferWallClosed() {
                                    @Override
                                    public void onOfferWallClosed() {
                                        // Here you handle the 'Offer wall has just been closed' event
                                    }
                                });
                    }
                },
                new OnOfferWallLoadFailed() {
                    @Override
                    public void onOfferWallLoadFailed(String reason) {
                        // Here you handle the errors with provided reason
                        dialogadGate.dismiss();

                    }
                });
    }

    private void AyetStudioOffer() {

        if (AyetSdk.isInitialized()) {
            AyetSdk.showOfferwall((Application) activity.getApplicationContext(), activity.getString(R.string.ayet_adslot));

        } else {
            Toast.makeText(activity, "Failed to load offerwall try again later.", Toast.LENGTH_SHORT).show();
        }
        AyetSdk.showOfferwall((Application) activity.getApplicationContext(), activity.getString(R.string.ayet_adslot));

    }
}