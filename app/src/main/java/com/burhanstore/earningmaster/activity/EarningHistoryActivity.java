package com.burhanstore.earningmaster.activity;

import static com.burhanstore.earningmaster.helper.AppController.hidepDialog;
import static com.burhanstore.earningmaster.helper.AppController.showApp_Dialog;
import static com.burhanstore.earningmaster.helper.Constatnt.ACCESS_KEY;
import static com.burhanstore.earningmaster.helper.Constatnt.ACCESS_Value;
import static com.burhanstore.earningmaster.helper.Constatnt.API;
import static com.burhanstore.earningmaster.helper.Constatnt.Base_Url;
import static com.burhanstore.earningmaster.helper.Constatnt.POINTS;
import static com.burhanstore.earningmaster.helper.Constatnt.USERNAME;
import static com.burhanstore.earningmaster.helper.Constatnt.USER_TRACKER;
import static com.burhanstore.earningmaster.helper.PrefManager.user_main_points;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.burhanstore.earningmaster.R;
import com.burhanstore.earningmaster.adapter.Earning_History;
import com.burhanstore.earningmaster.helper.AppController;
import com.burhanstore.earningmaster.models.Model_History;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EarningHistoryActivity extends AppCompatActivity {

    Earning_History adapter;
    RecyclerView earning_history;
    private List<Model_History> historyList_app = new ArrayList<>();
    private Activity activity;
    private TextView points;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_earning_history);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_baseline_back));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        earning_history = findViewById(R.id.RecyclerView);
        activity = this;
        HistoryData();
        userPoint();
    }

    private void userPoint() {
        points = findViewById(R.id.user_points_text_view);
        points.setText("0");
        user_main_points(points);
    }

    private void HistoryData() {
        showApp_Dialog();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Base_Url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response1) {

                        try {
                            JSONObject response = new JSONObject(response1);
                            if (response.getString("error").equalsIgnoreCase("false")) {
                                JSONArray jsonArray = response.getJSONArray("data");
                                historyList_app.clear();
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    String desc = jsonObject.getString("type");
                                    String date = jsonObject.getString("date");
                                    String time = jsonObject.getString("time");
                                    String coin = jsonObject.getString(POINTS);

                                    Model_History model = new Model_History(desc, date, coin, time);
                                    historyList_app.add(model);
                                }
                                adapter = new Earning_History(historyList_app);
                                earning_history.setLayoutManager(new LinearLayoutManager(activity));
                                earning_history.setAdapter(adapter);
                                hidepDialog();

                            } else {
                                if (response.getString("message").equalsIgnoreCase("No Earning History found!")) {
                                    Toast.makeText(activity, response.getString("message"), Toast.LENGTH_LONG).show();
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(activity, e.toString(), Toast.LENGTH_LONG).show();
                        }
                    }


                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(activity, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();


                params.put(ACCESS_KEY, ACCESS_Value);
                params.put(USER_TRACKER, API);
                params.put(USERNAME, AppController.getInstance().getUsername());

                return params;
            }

        };
        AppController.getInstance().getRequestQueue().getCache().clear();
        AppController.getInstance().addToRequestQueue(stringRequest);
    }

}