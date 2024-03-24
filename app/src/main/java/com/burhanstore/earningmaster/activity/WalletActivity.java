package com.burhanstore.earningmaster.activity;

import static android.content.ContentValues.TAG;
import static com.burhanstore.earningmaster.helper.Constatnt.ACCESS_KEY;
import static com.burhanstore.earningmaster.helper.Constatnt.ACCESS_Value;
import static com.burhanstore.earningmaster.helper.Constatnt.REWARD;
import static com.burhanstore.earningmaster.helper.PrefManager.user_main_points;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.burhanstore.earningmaster.R;
import com.burhanstore.earningmaster.adapter.WalletReward_adapter;
import com.burhanstore.earningmaster.helper.AppController;
import com.burhanstore.earningmaster.helper.Constatnt;
import com.burhanstore.earningmaster.helper.JsonRequest;
import com.burhanstore.earningmaster.models.Reward_model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WalletActivity extends AppCompatActivity {

    private WalletActivity activity;
    RecyclerView wallet_reward_list;
    WalletReward_adapter reward_adapter;
    private List<Reward_model> reward_listt = new ArrayList<>();
    LinearLayout trans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(" Redeem Points ");
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_baseline_back));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        activity = this;

        wallet_reward_list = findViewById(R.id.reward_list);
        //date
        TextView text_view_date_activity = findViewById(R.id.text_view_date_activity);
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
        text_view_date_activity.setText(dateFormat.format(date));

        TextView points = findViewById(R.id.user_points_textView);
        points.setText("0");
        user_main_points(points);

        getWalletData();

    }

    public void getWalletData() {

        JsonRequest stringRequest = new JsonRequest(Request.Method.POST,
                Constatnt.Base_Url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                VolleyLog.d(TAG, "Response: " + response.toString());
                if (response != null) {
                    parseWalletData(response);
                    //Toast.makeText(getActivity(),response.toString(),Toast.LENGTH_LONG).show();
                }
                //hidepDialog();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(activity, error.getMessage(), Toast.LENGTH_LONG).show();
                //  hidepDialog();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put(ACCESS_KEY, ACCESS_Value);
                params.put(REWARD, REWARD);
                params.put("id", AppController.getInstance().getId());
                return params;
            }
        };
        // Adding request to volley request queue
        AppController.getInstance().addToRequestQueue(stringRequest);
    }


    private void parseWalletData(JSONObject response) {
        try {


            int coi = Integer.parseInt(AppController.getInstance().getPoints());

            JSONArray feedArray = response.getJSONArray("data");
            reward_listt.clear();

            for (int i = 0; i < feedArray.length(); i++) {
                JSONObject feedObj = (JSONObject) feedArray.get(i);
                JSONArray array = (feedObj.getJSONArray("amounts"));
                Integer id = (feedObj.getInt("id"));
                String name = (feedObj.getString("name"));
                String image = (feedObj.getString("image"));
                String symbol = (feedObj.getString("symbol"));
                String hint = (feedObj.getString("hint"));
                String input_type = (feedObj.getString("input_type"));
                Integer minimum = (feedObj.getInt("minimum"));
                //Integer amount = (feedObj.getInt("amount"));
                String details = (feedObj.getString("details"));
                String arr = array.toString();

                Reward_model item = new Reward_model(name, image, symbol, hint, input_type, id, details, minimum, arr);
                reward_listt.add(item);

            }

            reward_adapter = new WalletReward_adapter(reward_listt, activity);

            wallet_reward_list.setHasFixedSize(true);
            wallet_reward_list.setLayoutManager(new LinearLayoutManager(activity));
            wallet_reward_list.setAdapter(reward_adapter);
            wallet_reward_list.setVisibility(View.VISIBLE);

        } catch (JSONException e) {
            e.printStackTrace();
            //  listView.setVisibility(View.GONE);
            Toast.makeText(activity, e.getMessage(), Toast.LENGTH_LONG).show();

        }
    }

}