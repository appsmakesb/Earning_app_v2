package com.burhanstore.earningmaster.fragment;

import static android.content.ContentValues.TAG;
import static android.content.Context.CLIPBOARD_SERVICE;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.burhanstore.earningmaster.BaseUrl;
import com.burhanstore.earningmaster.activity.LoginActivity;
import com.burhanstore.earningmaster.helper.AppController;
import com.burhanstore.earningmaster.R;
import com.burhanstore.earningmaster.helper.JsonRequest;

import static com.burhanstore.earningmaster.helper.Constatnt.ACCESS_KEY;
import static com.burhanstore.earningmaster.helper.Constatnt.ACCESS_Value;
import static com.burhanstore.earningmaster.helper.Constatnt.Base_Url;
import static com.burhanstore.earningmaster.helper.PrefManager.user_main_points;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class ProfileFragment extends Fragment {
    private View view;
    private Context activity;
    private ImageView Profile_section;
    private LinearLayout UserLogut;
    private TextView pointU, DeviceId, TotalRefer, email_pf_edit_text, UserCountry, date_registered, your_refer_code, User_name;

    public ProfileFragment() {

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = context;
    }

    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();
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

        view = inflater.inflate(R.layout.fragment_profile, container, false);


        Profile_section = view.findViewById(R.id.Profile_section);
        pointU = view.findViewById(R.id.Upoints);
        DeviceId = view.findViewById(R.id.DeviceId);
        TotalRefer = view.findViewById(R.id.TotalRefer);
        email_pf_edit_text = view.findViewById(R.id.email_pf_edit_text);
        UserCountry = view.findViewById(R.id.UserCountry);
        date_registered = view.findViewById(R.id.member_since);
        your_refer_code = view.findViewById(R.id.your_refer_code);
        User_name = view.findViewById(R.id.User_name);
        UserLogut = view.findViewById(R.id.UserLogut);


        intData();
        settings();


        return view;
    }

    private void intData() {
        //
        Glide.with(getContext()).load(BaseUrl.ADMIN_URL + "api/images/" + AppController.getInstance().getProfile())
                .apply(new RequestOptions().placeholder(R.drawable.app_icon))
                .into(Profile_section);
        pointU.setText("0");
        user_main_points(pointU);

        DeviceId.setText(AppController.getInstance().getdevice());
        TotalRefer.setText(AppController.getInstance().gettotal_ref());
        email_pf_edit_text.setText(AppController.getInstance().getEmail());
        UserCountry.setText(AppController.getInstance().getUserCountry());
        date_registered.setText(AppController.getInstance().getdate_registered());
        your_refer_code.setText(AppController.getInstance().getRefercodee());
        User_name.setText(AppController.getInstance().getFullname());

        your_refer_code.setOnClickListener(view -> {
            ClipboardManager clipboard = (ClipboardManager) getContext().getSystemService(CLIPBOARD_SERVICE);
            if (clipboard != null) {
                ClipData clip = ClipData.newPlainText("Referral Link", AppController.getInstance().getRefercodee());
                clipboard.setPrimaryClip(clip);
                Toast.makeText(getContext(), getString(R.string.ref_code_copied), Toast.LENGTH_SHORT).show();
            }
        });
        UserLogut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppController.getInstance().removeData();
                AppController.getInstance().setId("0");
                AppController.getInstance().setUsername("0");
                AppController.getInstance().readData();
                Toast.makeText(getActivity(), "Logout Successfully.!!", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getActivity(), LoginActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            }
        });


    }

    public void settings() {
        // showpDialog();
        JsonRequest stringRequest = new JsonRequest(Request.Method.POST,
                Base_Url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                VolleyLog.d(TAG, "Response: " + response.toString());
                if (response != null) {
                    parseJsonFeed(response);


                    // Toast.makeText(MainActivity.this,response.toString(),Toast.LENGTH_LONG).show();
                }
                //hidepDialog();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getContext(), error.toString(), Toast.LENGTH_LONG).show();
                //  hidepDialog();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put(ACCESS_KEY, ACCESS_Value);
                params.put("reward_count", AppController.getInstance().getId());
                params.put("user", AppController.getInstance().getUsername());
                return params;
            }
        };
        // Adding request to volley request queue
        AppController.getInstance().addToRequestQueue(stringRequest);
    }

    private void parseJsonFeed(JSONObject response) {
        try {
            TextView re = view.findViewById(R.id.redeemBtn);
            re.setText(response.getString("re"));
            TextView total = view.findViewById(R.id.Total_Earn);
            total.setText(response.getString("earn"));


        } catch (JSONException e) {
            e.printStackTrace();
            //  listView.setVisibility(View.GONE);
            Toast.makeText(getContext(), AppController.getInstance().getUsername(), Toast.LENGTH_SHORT).show();

            Toast.makeText(getContext(), e.toString(), Toast.LENGTH_LONG).show();

        }
    }


}