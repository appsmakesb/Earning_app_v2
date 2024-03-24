package com.burhanstore.earningmaster.activity;

import static com.burhanstore.earningmaster.helper.AppController.hidepDialog;
import static com.burhanstore.earningmaster.helper.AppController.initpDialog;
import static com.burhanstore.earningmaster.helper.AppController.showApp_Dialog;
import static com.burhanstore.earningmaster.helper.Constatnt.ACCESS_KEY;
import static com.burhanstore.earningmaster.helper.Constatnt.ACCESS_Value;
import static com.burhanstore.earningmaster.helper.Constatnt.API;
import static com.burhanstore.earningmaster.helper.Constatnt.Base_Url;
import static com.burhanstore.earningmaster.helper.PrefManager.user_main_points;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.burhanstore.earningmaster.R;
import com.burhanstore.earningmaster.adapter.ActivityListAdapter;
import com.burhanstore.earningmaster.helper.AppController;
import com.burhanstore.earningmaster.helper.JsonRequest;
import com.burhanstore.earningmaster.models.ActivityItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NotificationActivity extends AppCompatActivity {
    private RecyclerView listView;
    ActivityListAdapter adapter;
    private List<ActivityItem> historyList = new ArrayList<>();
    private View root_view;
    TextView nodata;
    int RecyclerViewItemPosition;
    View view;
    private LinearLayoutManager linearLayoutManager;
    ProgressDialog Asycdialog;
    ArrayList<String> get_date;
    ArrayList<String> get_title;
    ArrayList<String> get_message;
    AppCompatActivity getContext;
    Activity activity;
    private TextView user_points_text_view;

    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_activity);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_baseline_back));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        listView = (RecyclerView) findViewById(R.id.listview);
        linearLayoutManager = new LinearLayoutManager(getContext);
        nodata = (TextView) findViewById(R.id.nodata);
        user_points_text_view = (TextView) findViewById(R.id.user_points_text_view);
        get_date = new ArrayList<>();
        get_title = new ArrayList<>();
        get_message = new ArrayList<>();
        listView.setLayoutManager(linearLayoutManager);
        initpDialog(this);
        ActivityData();
        activity = this;

        setText();

        listView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {

            GestureDetector gestureDetector = new GestureDetector(getContext, new GestureDetector.SimpleOnGestureListener() {

                @Override
                public boolean onSingleTapUp(MotionEvent motionEvent) {

                    return true;
                }

            });

            @Override
            public boolean onInterceptTouchEvent(RecyclerView Recyclerview, MotionEvent motionEvent) {

                view = Recyclerview.findChildViewUnder(motionEvent.getX(), motionEvent.getY());

                if (view != null && gestureDetector.onTouchEvent(motionEvent)) {
                    //Getting RecyclerView Clicked Item value.
                    RecyclerViewItemPosition = Recyclerview.getChildAdapterPosition(view);
                    Notice_details(get_title.get(RecyclerViewItemPosition), get_message.get(RecyclerViewItemPosition), get_date.get(RecyclerViewItemPosition));
                    // Showing RecyclerView Clicked Item value using Toast.
                    // Toast.makeText(getActivity(), Game_url_arrey.get(RecyclerViewItemPosition), Toast.LENGTH_LONG).show();
                }

                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView Recyclerview, MotionEvent motionEvent) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });


    }

    private void setText() {
        user_points_text_view.setText("0");
        user_main_points(user_points_text_view);
    }

    public void Notice_details(String get_title, String get_message, String get_date) {

        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.show_notification_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);

        ImageView imageView = dialog.findViewById(R.id.points_image);
        TextView title_text = dialog.findViewById(R.id.title_text_points);
        TextView date = dialog.findViewById(R.id.date);
        AppCompatButton add_btn = dialog.findViewById(R.id.add_btn);

        imageView.setImageResource(R.drawable.notification);
        title_text.setText(get_message);
        date.setText(get_date);
        add_btn.setText(activity.getResources().getString(R.string.ok_text));

        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(get_title));
                startActivity(i);
                dialog.dismiss();
            }
        });
        dialog.show();


    }

    public void ActivityData() {
        showApp_Dialog();
        JsonRequest stringRequest = new JsonRequest(Request.Method.POST,
                Base_Url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                VolleyLog.d("TAG", "Response: " + response.toString());
                if (response != null) {
                    parseJsonFeed(response);

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //t.makeText(getActivity(), error.toString(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put(ACCESS_KEY, ACCESS_Value);
                params.put("activity", API);
                return params;
            }

        };
        // Adding request to volley request queue
        AppController.getInstance().addToRequestQueue(stringRequest);
    }

    private void parseJsonFeed(JSONObject response) {
        try {
            JSONArray feedArray = response.getJSONArray("feed");

            if (response.getJSONArray("feed") == response.getJSONArray("feed")) {
                historyList.clear();
                for (int i = 0; i < feedArray.length(); i++) {
                    JSONObject feedObj = (JSONObject) feedArray.get(i);
                    get_title.add(feedObj.getString("title"));
                    get_date.add(feedObj.getString("date"));
                    get_message.add(feedObj.getString("message"));
                    String title = feedObj.getString("title");
                    String message = feedObj.getString("message");
                    String date = feedObj.getString("date");
                    ActivityItem model = new ActivityItem(title, message, date);
                    historyList.add(model);
                }
                adapter = new ActivityListAdapter(historyList);
                listView.setVisibility(View.VISIBLE);
                listView.setAdapter(adapter);
                hidepDialog();
            } else {

                listView.setVisibility(View.GONE);
                nodata.setVisibility(View.VISIBLE);
            }

        } catch (JSONException e) {
            e.printStackTrace();
            hidepDialog();
        }
    }

    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {

        super.onPause();
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}





