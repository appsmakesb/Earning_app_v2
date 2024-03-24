package com.burhanstore.earningmaster.activity;

import static android.content.ContentValues.TAG;
import static com.burhanstore.earningmaster.helper.Constatnt.ACCESS_KEY;
import static com.burhanstore.earningmaster.helper.Constatnt.ACCESS_Value;
import static com.burhanstore.earningmaster.helper.Constatnt.Base_Url;
import static com.burhanstore.earningmaster.helper.PrefManager.user_main_points;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
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
import com.burhanstore.earningmaster.adapter.r_Adapter;
import com.burhanstore.earningmaster.helper.AppController;
import com.burhanstore.earningmaster.helper.JsonRequest;
import com.burhanstore.earningmaster.models.r_Model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TransactionsHistoryActivity extends AppCompatActivity {

    private Activity activity;
    private TextView points;
    RecyclerView list;
    r_Adapter R_adapter;
    private List<r_Model> gameModel = new ArrayList<>();
    NestedScrollView scroll;
    LinearLayout no;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transactions_history);

        activity = this;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_baseline_back));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        list = findViewById(R.id.RecyclerView);
        getlist();
        userPoint();
    }

    private void userPoint() {
        points = findViewById(R.id.user_points_text_view);
        points.setText("0");
        user_main_points(points);
    }

    public void getlist() {
        // showpDialog();
        JsonRequest stringRequest = new JsonRequest(Request.Method.POST,
                Base_Url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                VolleyLog.d(TAG, "Response: " + response.toString());
                if (response != null) {
                    parseJsonFeed(response);
                    //Toast.makeText(getActivity(),response.toString(),Toast.LENGTH_LONG).show();
                }
                //hidepDialog();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(activity, error.toString(), Toast.LENGTH_LONG).show();
                //  hidepDialog();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put(ACCESS_KEY, ACCESS_Value);
                params.put("redeem_check", AppController.getInstance().getId());
                return params;
            }
        };
        // Adding request to volley request queue
        AppController.getInstance().addToRequestQueue(stringRequest);
    }

    private void parseJsonFeed(JSONObject response) {
        try {

            JSONArray feedArray = response.getJSONArray("data");
            if (!(feedArray.length() == 0)) {

            }
            gameModel.clear();
            for (int i = 0; i < feedArray.length(); i++) {
                JSONObject feedObj = (JSONObject) feedArray.get(i);
                Integer id = (feedObj.getInt("id"));
                String u_id = (feedObj.getString("u_id"));
                String package_name = (feedObj.getString("package_name"));
                String p_details = (feedObj.getString("p_details"));
                String coins_used = (feedObj.getString("coins_used"));
                String symbol = (feedObj.getString("symbol"));
                String amount = (feedObj.getString("amount"));
                String time = (feedObj.getString("time"));
                String date = (feedObj.getString("date"));
                String status = (feedObj.getString("status"));
                String package_id = (feedObj.getString("package_id"));
                String image = (feedObj.getString("image"));

                r_Model item = new r_Model(package_name, p_details, coins_used, symbol, amount, date, time, status, package_id, image);
                gameModel.add(item);
            }
            R_adapter = new r_Adapter(gameModel, activity);

            list.setLayoutManager(new LinearLayoutManager(activity));
            list.setAdapter(R_adapter);

        } catch (JSONException e) {
            e.printStackTrace();
            //  listView.setVisibility(View.GONE);
            //  Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();

        }
    }

}