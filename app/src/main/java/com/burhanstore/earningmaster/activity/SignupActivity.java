package com.burhanstore.earningmaster.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import static com.burhanstore.earningmaster.util.Activity_otp.randomAlphaNumeric;
import static com.burhanstore.earningmaster.helper.AppController.hidepDialog;
import static com.burhanstore.earningmaster.helper.AppController.isConnected;
import static com.burhanstore.earningmaster.helper.AppController.showApp_Dialog;
import static com.burhanstore.earningmaster.helper.Constatnt.ACCESS_KEY;
import static com.burhanstore.earningmaster.helper.Constatnt.ACCESS_Value;
import static com.burhanstore.earningmaster.helper.Constatnt.ACCOUNT_STATE_ENABLED;
import static com.burhanstore.earningmaster.helper.Constatnt.API;
import static com.burhanstore.earningmaster.helper.Constatnt.Base_Url;
import static com.burhanstore.earningmaster.helper.Constatnt.USER_LOGIN;


import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.burhanstore.earningmaster.R;
import com.burhanstore.earningmaster.helper.AppController;
import com.burhanstore.earningmaster.helper.JsonRequest;
import com.burhanstore.earningmaster.util.Ads_Controller;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;

public class SignupActivity extends AppCompatActivity {

    private EditText name_edit_text, email_edit_text, password_edit_text, reffreal_code_edit_text;
    private TextView login_text;
    private AppCompatButton sign_up_button;
    private Context mContext;
    private ACProgressFlower alertDialog;
    private ImageView back;
    Ads_Controller ads_controller;

    //

    EditText phone;
    Button submitBtn;
    int account_status = 0;
    public static AlertDialog dialogwindow;
    private static final String TAG = "LoginActivity";
    String email;
    String username;
    String name;
    Uri profile;
    int simple_otp = 1227;
    String spinner_code, new_phone;
    CheckBox check1, check2, check3;
    int c1 = 0, c2, c3;
    String code = "";
    ImageView login, fb_btn;
    GoogleSignInClient googleSignInClient;
    private static final int RC_SIGN_IN = 1001;
    public static String user = "league", password = "123456", msisdn = "", sid = "LEAGUE", msg = " for Verification. Khelo family Welcomes You!", fl = "0", gwid = "2";
    String UserName, UserEmail, UserPassword, UserCountry;

    String androidId = " ";
    TextView text;
    GoogleSignInClient mGoogleSignInClient;
    private ConstraintLayout googleLoginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mContext = this;
        alertDialog = new ACProgressFlower.Builder(mContext)
                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                .themeColor(Color.WHITE)
                .text("Please Wait...")
                .fadeColor(Color.DKGRAY).build();
        onInitView();

        //
        code = randomAlphaNumeric(8);
        submitBtn = findViewById(R.id.submitBtn);
        googleLoginBtn = findViewById(R.id.googleLoginBtn);

        //google
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        googleLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });
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

    private void onInitView() {

        //
        //country
        TelephonyManager teleMgr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        String localeCountry = teleMgr.getNetworkCountryIso();
        Locale l = new Locale("", localeCountry);
        UserCountry = l.getDisplayCountry();
        //


    }

    // Check app User
    public void check_app_user(String e) {
        showApp_Dialog();
        if (isConnected(SignupActivity.this)) {
            JsonRequest jsonReq = new JsonRequest(Request.Method.POST, Base_Url, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            //Toast.makeText(Activity_Login.this,response.toString(),Toast.LENGTH_LONG).show();
                            try {
                                if (response.getString("error").equalsIgnoreCase("false")) {
                                    // Toast.makeText(Activity_Login.this,response.getString("message"),Toast.LENGTH_LONG).show();
                                    account_status = 1;
                                    appSign(email);

                                } else if (response.getString("error").equalsIgnoreCase("true")) {
                                    //Toast.makeText(Activity_Login.this,response.getString("message"),Toast.LENGTH_LONG).show();
                                    account_status = 2;
                                    appSignup("0000000000", username, name, email, profile);
                                }
                                /*Intent intent = new Intent(Activity_Login.this,Activity_otp.class);
                                intent.putExtra("status",""+account_status);
                                intent.putExtra("phone",new_phone);
                                intent.putExtra("otp",""+simple_otp);
                                startActivity(intent);
                                finish();
                                hidepDialog();*/
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(getApplicationContext(), "Check User " + e.toString(), Toast.LENGTH_LONG).show();
                                hidepDialog();
                                hideProgressDialog();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    //Toast.makeText(getApplicationContext(), getText(R.string.error_data_loading), Toast.LENGTH_LONG).show();
                    Toast.makeText(getApplicationContext(), "Error in Check User " + error.toString(), Toast.LENGTH_LONG).show();
                    hidepDialog();
                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put(ACCESS_KEY, ACCESS_Value);
                    params.put("user_check", API);
                    params.put("phone", e);
                    Log.e("Error", "phone :" + new_phone);
                    return params;
                }
            };
            AppController.getInstance().addToRequestQueue(jsonReq);
        }
    }

    public void appSign(final String enter_phone) {

        if (isConnected(SignupActivity.this)) {
            showApp_Dialog();
            JsonRequest jsonReq = new JsonRequest(Request.Method.POST, Base_Url, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            if (AppController.getInstance().authorize(response)) {
                                if (AppController.getInstance().getState().equals(ACCOUNT_STATE_ENABLED)) {

                                    try {

                                        if (response.getString("error").equalsIgnoreCase("true")) {
                                            Toast.makeText(getApplicationContext(), response.getString("message"), Toast.LENGTH_SHORT).show();
                                        } else if (response.getString("error").equalsIgnoreCase("false")) {
                                            // Toast.makeText(getApplicationContext(), response.getString("message"), Toast.LENGTH_SHORT).show();
                                            //   chkref(SignupActivity.this);
                                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            startActivity(intent);
                                            finish();
                                        }
                                    } catch (JSONException e) {

                                        e.printStackTrace();

                                    }

                                } else {
                                    AppController.getInstance().logout(SignupActivity.this);
                                    Toast.makeText(getApplicationContext(), getText(R.string.msg_account_blocked), Toast.LENGTH_SHORT).show();
                                }

                            } else {
                                Toast.makeText(getApplicationContext(), "Something Gone Wrong", Toast.LENGTH_SHORT).show();
                            }

                            hidepDialog();
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), getText(R.string.error_data_loading), Toast.LENGTH_LONG).show();
                    hidepDialog();
                }
            }) {

                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put(ACCESS_KEY, ACCESS_Value);
                    params.put(USER_LOGIN, API);
                    params.put("phone", enter_phone);
                    //   AppController.getInstance().setPassword("12345");
                    //   AppController.getInstance().getBadge();
                    return params;
                }
            };
            AppController.getInstance().getRequestQueue().getCache().clear();
            AppController.getInstance().addToRequestQueue(jsonReq);
        }

    }

    public void appSignup(final String enter_phone, final String username, final String name, final String email, final Uri pro) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {

            androidId = Settings.Secure.getString(
                    getContentResolver(),
                    Settings.Secure.ANDROID_ID);
            if (androidId == null) {
                androidId = " ";
            }
        } else {
            final TelephonyManager mTelephony = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            if (mTelephony.getDeviceId() != null) {
                androidId = mTelephony.getDeviceId();
                if (androidId == null) {
                    androidId = " ";
                }
            } else {
                androidId = Settings.Secure.getString(
                        getContentResolver(),
                        Settings.Secure.ANDROID_ID);
                if (androidId == null) {
                    androidId = " ";
                }
            }


        }
        if (isConnected(SignupActivity.this)) {
            showApp_Dialog();
            JsonRequest jsonReq = new JsonRequest(Request.Method.POST, Base_Url, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            try {
                                if (response.getString("error").equalsIgnoreCase("true")) {
                                    Toast.makeText(getApplicationContext(), response.getString("message"), Toast.LENGTH_LONG).show();
                                } else if (response.getString("error").equalsIgnoreCase("false")) {
                                    appSign(email);
                                    // Toast.makeText(Activity_Login.this,response.getString("message"),Toast.LENGTH_LONG).show();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();

                            }
                            hidepDialog();
                            hideProgressDialog();
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                    hidepDialog();
                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put(ACCESS_KEY, ACCESS_Value);
                    params.put("user_signup", API);
                    params.put("phone", enter_phone);
                    params.put("username", username);
                    params.put("name", name);
                    params.put("email", email);
                    params.put("refer", code);
                    params.put("device", androidId);
                    params.put("image", String.valueOf(pro));
                    params.put("UserCountry", UserCountry);
                    return params;
                }
            };
            AppController.getInstance().addToRequestQueue(jsonReq);
        }
    }

    public void submit() {
        if (account_status == 0) {
            rand();
            if (phone.getText().length() < 10) {
                phone.setError("Please Input Valid Phone Number");
            } else {
                new_phone = phone.getText().toString();
                // otp_send(new_phone,simple_otp,getApplicationContext());
                check_app_user(new_phone);
            }
        }
    }

    public void rand() {
        Random rand = new Random();
        int resRandom = rand.nextInt((9999 - 100) + 1) + 10;
        simple_otp = resRandom;
        //Toast.makeText(Activity_Login.this,simple_otp+"",Toast.LENGTH_LONG).show();
    }

    //

    //google
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resulrCode, Intent data) {
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
        super.onActivityResult(requestCode, resulrCode, data);
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            googleSignIn();
        } catch (ApiException e) {
            e.printStackTrace();
        }
    }

    private void googleSignIn() {

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(SignupActivity.this);

        name = acct.getDisplayName();
        email = acct.getEmail();
        profile = acct.getPhotoUrl();
        Toast.makeText(mContext, "aaa" + acct.getPhotoUrl(), Toast.LENGTH_SHORT).show();

        // Toast.makeText(Activity_Login.this,"user: "+user.getDisplayName(),Toast.LENGTH_LONG).show();

        String newString = name.replace(" ", "_");
        username = newString + "2296";

        check_app_user(email);
    }

}