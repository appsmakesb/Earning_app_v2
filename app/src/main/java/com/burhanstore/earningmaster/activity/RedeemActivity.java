package com.burhanstore.earningmaster.activity;

import static com.burhanstore.earningmaster.helper.PrefManager.user_main_points;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.burhanstore.earningmaster.R;
import com.burhanstore.earningmaster.adapter.WalletRedeem_adapter;
import com.burhanstore.earningmaster.models.Redeem_model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RedeemActivity extends AppCompatActivity {

    private RedeemActivity activity;
    private List<Redeem_model> model = new ArrayList<>();
    RecyclerView list;
    WalletRedeem_adapter adapter;
    ImageView back;
    LinearLayout history;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redeem);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(" ");
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_baseline_back));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        activity = this;

        list = findViewById(R.id.RecyclerView);
        Intent i = getIntent();

        TextView points = findViewById(R.id.user_points_textView);
        user_main_points(points);

        try {
            JSONArray array = new JSONArray(i.getStringExtra("arry"));
            for (int index = 0; index < array.length(); index++) {
                JSONObject feedObj = (JSONObject) array.get(index);
                //  Integer id = (feedObj.getInt("id"));
                String coins = (feedObj.getString("coins"));
                String amount = (feedObj.getString("amount"));
                String amount_id = (feedObj.getString("id"));
                String image = i.getStringExtra("image");
                String input = i.getStringExtra("input");
                String hint = i.getStringExtra("hint");
                String title = i.getStringExtra("title");
                String symb = i.getStringExtra("symb");
                String id = i.getStringExtra("id");
                String type = i.getStringExtra("type");
                String details = i.getStringExtra("details");
                //GameModel item = new GameModel(id,title,image,game_link);
                //gameModel.add(item);
                Redeem_model item = new Redeem_model(image, coins, amount, symb, input, hint, title, id, type, details, amount_id);
                model.add(item);
            }
            adapter = new WalletRedeem_adapter(model, RedeemActivity.this);
            list.setLayoutManager(new GridLayoutManager(this, 3));
            list.setAdapter(adapter);

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}