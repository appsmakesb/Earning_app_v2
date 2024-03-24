package com.burhanstore.earningmaster.activity;

import static android.content.ContentValues.TAG;

import static com.burhanstore.earningmaster.helper.Constatnt.ACCESS_KEY;
import static com.burhanstore.earningmaster.helper.Constatnt.ACCESS_Value;
import static com.burhanstore.earningmaster.helper.Constatnt.Base_Url;
import static com.burhanstore.earningmaster.helper.Constatnt.LEADER;

import android.app.Activity;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.burhanstore.earningmaster.BaseUrl;
import com.burhanstore.earningmaster.R;
import com.burhanstore.earningmaster.adapter.LeaderboardAdapter;
import com.burhanstore.earningmaster.helper.AppController;
import com.burhanstore.earningmaster.helper.JsonRequest;
import com.burhanstore.earningmaster.models.Leaderboard_model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class LeaderBoardActivity extends AppCompatActivity {


    private Activity activity;
    RecyclerView list;
    List<Leaderboard_model> model = new ArrayList<>();
    LeaderboardAdapter adapter;
    private TextView text1, text2, text3;
    CircleImageView leader_one_img, leader_two_img, leader_three_img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leader_board);
        activity = this;

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Leaderboard");
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_baseline_back));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        leader_one_img = findViewById(R.id.leader_user_one_img);
        text1 = findViewById(R.id.text1);
        leader_two_img = findViewById(R.id.leader_two_img);
        text2 = findViewById(R.id.text2);
        text3 = findViewById(R.id.text3);
        leader_three_img = findViewById(R.id.leader_three_img);
        list = findViewById(R.id.RecyclerView);

        getquizlist();
    }


    public void getquizlist() {
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
                params.put(LEADER, LEADER);
                return params;
            }
        };
        // Adding request to volley request queue
        AppController.getInstance().addToRequestQueue(stringRequest);
    }


    private void parseJsonFeed(JSONObject response) {
        try {


            JSONArray feedArray = response.getJSONArray("data");
            model.clear();
            int count = 0;
            for (int i = 0; i < feedArray.length(); i++) {
                JSONObject feedObj = (JSONObject) feedArray.get(i);
                count++;
                String name = (feedObj.getString("name"));
                String image = (feedObj.getString("image"));
                String points = (feedObj.getString("points"));
                String tag = "#" + count;


                if (activity == null) {
                    return;
                } else {
                    if (count == 1) {
                        Glide.with(activity).load(BaseUrl.ADMIN_URL + "api/images/" + image)
                                .apply(new RequestOptions().placeholder(R.drawable.app_icon))
                                .into(leader_one_img);

                        text1.setText(name);
                    } else if (count == 2) {
                        Glide.with(activity).load(BaseUrl.ADMIN_URL + "api/images/" + image)
                                .apply(new RequestOptions().placeholder(R.drawable.app_icon))
                                .into(leader_two_img);
                        text2.setText(name);
                    } else if (count == 3) {
                        Glide.with(activity).load(BaseUrl.ADMIN_URL + "api/images/" + image)
                                .apply(new RequestOptions().placeholder(R.drawable.app_icon))
                                .into(leader_three_img);
                        text3.setText(name);
                    } else {
                        Leaderboard_model item = new Leaderboard_model(name, points, image, tag);
                        model.add(item);
                    }
                }


            }

            adapter = new LeaderboardAdapter(model, activity);
            list.setLayoutManager(new LinearLayoutManager(activity));
            list.setAdapter(adapter);


        } catch (JSONException e) {
            e.printStackTrace();
            //  listView.setVisibility(View.GONE);
            Toast.makeText(activity, e.getMessage(), Toast.LENGTH_LONG).show();

        }
    }

    private void setLeader(TextView tag, CircleImageView circleImageView, TextView name_txt, String name, String img, String tage) {
        Glide.with(activity).load(img)
                .apply(new RequestOptions().placeholder(R.drawable.app_icon))
                .into(circleImageView);
        tag.setText(tage);
        name_txt.setText(name);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
