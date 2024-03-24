package com.burhanstore.earningmaster.activity;

import static com.burhanstore.earningmaster.util.Constant.hideKeyboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.burhanstore.earningmaster.util.AppConfig;
import com.burhanstore.earningmaster.R;
import com.burhanstore.earningmaster.BaseUrl;
import com.burhanstore.earningmaster.util.Constant;
import com.burhanstore.earningmaster.util.CustomVolleyJsonRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;
import in.aabhasjindal.otptextview.OTPListener;
import in.aabhasjindal.otptextview.OtpTextView;

public class ForgotActivity extends AppCompatActivity {

    private TextInputEditText email_EditText, change_password_edit_text;
    private AppCompatButton reset_btn, change_password_btn;
    private Context mContext;
    private ACProgressFlower alertDialog;
    private TextView header_text, resend_text;
    private OtpTextView otp_edit_text;
    private String type;
    private ImageView back;
    private RelativeLayout baseLyt;
    private LinearLayout password_change_lyt;
    private String OTP;
    private TextInputLayout emailLyt;
    private LinearLayout otp_lyt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);

        mContext = this;
        email_EditText = findViewById(R.id.reset_email_edit_text);
        reset_btn = findViewById(R.id.reset_password_btn);
        header_text = findViewById(R.id.login_txt_forgot);
        otp_edit_text = findViewById(R.id.otpEditText);
        resend_text = findViewById(R.id.resend_otp);
        baseLyt = findViewById(R.id.otp_base_lyt);
        emailLyt = findViewById(R.id.email_lyt);
        otp_lyt = findViewById(R.id.otp_lyt);

        password_change_lyt = findViewById(R.id.reset_password_lyt);
        change_password_edit_text = findViewById(R.id.new_password);
        baseLyt = findViewById(R.id.otp_base_lyt);
        change_password_btn = findViewById(R.id.change_password_btn);

        alertDialog = new ACProgressFlower.Builder(mContext)
                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                .themeColor(Color.WHITE)
                .text("Please Wait...")
                .fadeColor(Color.DKGRAY).build();
        back = findViewById(R.id.back_img_forgot);
        if (ForgotActivity.this.getIntent() != null) {
            type = ForgotActivity.this.getIntent().getStringExtra("type");
        }
        if (type == null) {
            header_text.setText(getResources().getString(R.string.app_forgot_password));
        } else {
            header_text.setText(getResources().getString(R.string.app_change_password));
        }
        onClick();

    }

    private void onClick() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ForgotActivity.this.onBackPressed();
            }
        });
        resend_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard(ForgotActivity.this);
                showProgressDialog();
                Random rnd = new Random();
                int number = rnd.nextInt(999999);
                resetPasswordEmail(email_EditText.getText().toString(), String.format("%06d", number));
            }
        });

        otp_edit_text.setOtpListener(new OTPListener() {
            @Override
            public void onInteractionListener() {


            }

            @Override
            public void onOTPComplete(String otp) {
                if (otp.equalsIgnoreCase(OTP)) {
                    baseLyt.setVisibility(View.GONE);
                    password_change_lyt.setVisibility(View.VISIBLE);
                } else {
                    Constant.showToastMessage(mContext, "Enter Correct Otp...");
                }
            }
        });

        change_password_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (change_password_edit_text.getText().toString().length() == 0) {
                    change_password_edit_text.setError(getResources().getString(R.string.signup_enter_password));
                    change_password_edit_text.requestFocus();
                } else if (change_password_edit_text.getText().toString().length() < 6) {
                    change_password_edit_text.setError(getResources().getString(R.string.signup_enter_valid_password));
                    change_password_edit_text.requestFocus();
                } else {
                    changePasswordMethod(change_password_edit_text.getText().toString());
                }
            }
        });

        reset_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Constant.isNetworkAvailable(mContext)) {
                    String Email = email_EditText.getText().toString();
                    if (Email.length() == 0) {
                        email_EditText.setError(getResources().getString(R.string.account_enter_email));
                        email_EditText.requestFocus();
                    } else if (!Constant.isValidEmailAddress(Email)) {
                        email_EditText.setError(getResources().getString(R.string.signup_enter_valid_email));
                        email_EditText.requestFocus();
                    } else {
                        reset_btn.setEnabled(false);
                        hideKeyboard(ForgotActivity.this);
                        showProgressDialog();
                        Random rnd = new Random();
                        int number = rnd.nextInt(999999);
                        resetPasswordEmail(Email, String.format("%06d", number));
                    }
                } else {
                    Constant.showInternetErrorDialog(mContext, getResources().getString(R.string.internet_connection_of_text));
                }
            }
        });

    }

    private void changePasswordMethod(String password) {
        showProgressDialog();

        String tag_json_obj = "json_login_req";
        Map<String, String> params = new HashMap<String, String>();
        params.put("rest_pass", "any");
        params.put("email", email_EditText.getText().toString());
        params.put("pass", password);

        CustomVolleyJsonRequest jsonObjReq = new CustomVolleyJsonRequest(Request.Method.POST,
                BaseUrl.RESET_PASSWORD_APP, params, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d("TAG", response.toString());

                try {
                    hideProgressDialog();
                    boolean status = response.getBoolean("status");
                    if (status) {
                        hideProgressDialog();
                        Constant.showToastMessage(mContext, "Password Reset");
                        if (ForgotActivity.this == null) {
                            return;
                        }
                        ForgotActivity.this.onBackPressed();
                    } else {
                        Constant.showToastMessage(mContext, "Password Not Updated Please Check your email");
                    }
                } catch (JSONException e) {
                    hideProgressDialog();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                VolleyLog.d("TAG", "Error: " + error.getMessage());
                hideProgressDialog();
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    Constant.showToastMessage(mContext, getResources().getString(R.string.internet_connection_slow));
                }
            }
        });
        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(
                1000 * 20,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppConfig.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
    }

    private void resetPasswordEmail(String email, final String otp) {
        showProgressDialog();

        String tag_json_obj = "json_login_req";
        Map<String, String> params = new HashMap<String, String>();
        params.put("recover", "anything");
        params.put("email", email);
        params.put("otp", otp);
        Log.e("TAG", "resetPasswordEmail: " + params);

        CustomVolleyJsonRequest jsonObjReq = new CustomVolleyJsonRequest(Request.Method.POST,
                BaseUrl.FORGOT_PASSWORD_APP, params, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d("TAG", response.toString());

                try {
                    hideProgressDialog();
                    boolean status = response.getBoolean("status");
                    if (status) {
                        hideProgressDialog();
                        Constant.showToastMessage(mContext, "Otp Sent to your email check your email");
                        emailLyt.setVisibility(View.GONE);
                        otp_lyt.setVisibility(View.VISIBLE);
                        resend_text.setVisibility(View.VISIBLE);
                        reset_btn.setEnabled(false);
                        OTP = otp;
                    } else {
                        Constant.showToastMessage(mContext, "This Email is Not Registered");
                    }
                } catch (JSONException e) {
                    hideProgressDialog();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                VolleyLog.d("TAG", "Error: " + error.getMessage());
                hideProgressDialog();
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    Constant.showToastMessage(mContext, getResources().getString(R.string.internet_connection_slow));
                }
            }
        });
        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(
                1000 * 20,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppConfig.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);

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