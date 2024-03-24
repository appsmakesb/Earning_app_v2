package com.burhanstore.earningmaster.activity;

import static android.content.ContentValues.TAG;
import static com.burhanstore.earningmaster.helper.AppController.hidepDialog;
import static com.burhanstore.earningmaster.helper.Constatnt.ACCESS_KEY;
import static com.burhanstore.earningmaster.helper.Constatnt.ACCESS_Value;
import static com.burhanstore.earningmaster.helper.Constatnt.Base_Url;
import static com.burhanstore.earningmaster.helper.PrefManager.user_main_points;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
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
import com.burhanstore.earningmaster.helper.AppController;
import com.burhanstore.earningmaster.helper.JsonRequest;
import com.burhanstore.earningmaster.R;
import com.burhanstore.earningmaster.BaseUrl;
import com.burhanstore.earningmaster.util.Constant;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;

public class ProfileActivity extends AppCompatActivity {

    private ImageView Profile_section;
    private LinearLayout UserLogut;
    private TextView pointU, DeviceId, TotalRefer, email_pf_edit_text, UserCountry, date_registered, your_refer_code, User_name;
    ProfileActivity activity;
    private ImageButton UploadImage;
    private final int PICK_IMAGE_REQUEST = 22;
    private Uri filePath;
    private String imageUrl = "";
    private AppCompatButton update_profile_btn;
    private String image;
    private Bitmap bitmap;
    private ACProgressFlower alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Profile");
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_baseline_back));

        activity = this;

        Profile_section = findViewById(R.id.Profile_section);
        pointU = findViewById(R.id.Upoints);
        DeviceId = findViewById(R.id.DeviceId);
        TotalRefer = findViewById(R.id.TotalRefer);
        email_pf_edit_text = findViewById(R.id.email_pf_edit_text);
        UserCountry = findViewById(R.id.UserCountry);
        date_registered = findViewById(R.id.member_since);
        your_refer_code = findViewById(R.id.your_refer_code);
        User_name = findViewById(R.id.User_name);
        UserLogut = findViewById(R.id.UserLogut);
        UploadImage = findViewById(R.id.UploadImage);
        update_profile_btn = findViewById(R.id.update_btn);

        intData();
        settings();


    }


    private void intData() {
        //
        Glide.with(activity).load(BaseUrl.ADMIN_URL + "api/images/" + AppController.getInstance().getProfile())
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
            ClipboardManager clipboard = (ClipboardManager) activity.getSystemService(CLIPBOARD_SERVICE);
            if (clipboard != null) {
                ClipData clip = ClipData.newPlainText("Referral Link", AppController.getInstance().getRefercodee());
                clipboard.setPrimaryClip(clip);
                Toast.makeText(activity, getString(R.string.ref_code_copied), Toast.LENGTH_SHORT).show();
            }
        });
        UserLogut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppController.getInstance().removeData();
                AppController.getInstance().setId("0");
                AppController.getInstance().setUsername("0");
                AppController.getInstance().readData();
                Toast.makeText(activity, "Logout Successfully.!!", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(activity, LoginActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            }
        });

        Codeass();


    }

    private void Codeass() {
        imageUrl = AppController.getInstance().getProfile();
        Glide.with(activity)
                .load(BaseUrl.LOAD_USER_IMAGE_APP + imageUrl)
                .centerCrop()
                .placeholder(R.drawable.user_ic)
                .into(Profile_section);
        UploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isStoragePermissionGranted()) {
                    SelectImage();
                }
            }
        });


        //
        update_profile_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog = new ACProgressFlower.Builder(activity)
                        .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                        .themeColor(Color.WHITE)
                        .text("Please Wait...")
                        .fadeColor(Color.DKGRAY).build();
                showProgressDialog();

                if (Constant.isNetworkAvailable(activity)) {
                    UserWorkUpdateDate();
                } else {
                    Constant.showInternetErrorDialog(activity, getResources().getString(R.string.internet_connection_of_text));
                }
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

                Toast.makeText(activity, error.toString(), Toast.LENGTH_LONG).show();
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
            TextView re = findViewById(R.id.redeemBtn);
            re.setText(response.getString("re"));
            TextView total = findViewById(R.id.Total_Earn);
            total.setText(response.getString("earn"));


        } catch (JSONException e) {
            e.printStackTrace();
            //  listView.setVisibility(View.GONE);
            Toast.makeText(activity, AppController.getInstance().getUsername(), Toast.LENGTH_SHORT).show();

            Toast.makeText(activity, e.toString(), Toast.LENGTH_LONG).show();

        }
    }

    public boolean isStoragePermissionGranted() {
        if (activity == null) {
            return false;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (activity.checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v("TAG", "Permission is granted");
                return true;
            } else {

                Log.v("TAG", "Permission is revoked");
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        } else {
            Log.v("TAG", "Permission is granted");
            return true;
        }
    }


    private void SelectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(
                Intent.createChooser(
                        intent,
                        "Select Image from here..."),
                PICK_IMAGE_REQUEST);
    }


    private void UserWorkUpdateDate() {
        JsonRequest jsonReq = new JsonRequest(Request.Method.POST, BaseUrl.UPDATE_PROFILE_APP, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        hideProgressDialog();
                        Toast.makeText(activity, getString(R.string.app_successfully), Toast.LENGTH_SHORT).show();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                hidepDialog();
                hideProgressDialog();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("update_profile", "any");
                params.put("id", AppController.getInstance().getId());
                params.put("name", AppController.getInstance().getUsername());

                if (image == null) {
                    params.put("img", "");
                } else {
                    params.put("img", image);
                }

                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(jsonReq);
    }


    @Override
    public void onActivityResult(int requestCode,
                                 int resultCode,
                                 Intent data) {

        super.onActivityResult(requestCode,
                resultCode,
                data);

        if (requestCode == PICK_IMAGE_REQUEST
                && resultCode == RESULT_OK
                && data != null
                && data.getData() != null) {

            filePath = data.getData();
            Uri selectedImage = data.getData();
            Log.e("TAG", "onActivityResult: filePath" + filePath);
            Log.e("TAG", "onActivityResult: selectedImage" + selectedImage);
            InputStream imageStream = null;
            try {
                imageStream = activity.getContentResolver().openInputStream(
                        selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            bitmap = BitmapFactory.decodeStream(imageStream);
            Log.e("TAG", "onActivityResult: bmp" + bitmap);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            byte[] byteArray = stream.toByteArray();
            try {
                stream.close();
                stream = null;
            } catch (IOException e) {

                e.printStackTrace();
            }
            try {
                Glide.with(activity)
                        .load(bitmap)
                        .centerCrop()
                        .placeholder(R.drawable.user_ic)
                        .into(Profile_section);
                image = Base64.encodeToString(byteArray, Base64.DEFAULT);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public void showProgressDialog() {
        if (alertDialog != null && !alertDialog.isShowing()) {
            alertDialog.show();
        }
    }

    public void hideProgressDialog() {
        if (alertDialog != null && alertDialog.isShowing()) {
            alertDialog.dismiss();
        }
    }

}