package com.burhanstore.earningmaster.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.view.View;
import android.widget.ImageView;

import com.google.android.material.textfield.TextInputEditText;
import com.burhanstore.earningmaster.R;
import com.burhanstore.earningmaster.util.Ads_Controller;

public class ContactActivity extends AppCompatActivity {

    private TextInputEditText subject, message;
    private AppCompatButton btn;
    private ImageView back;
    Ads_Controller ads_controller;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        subject = findViewById(R.id.subject);
        message = findViewById(R.id.message);
        btn = findViewById(R.id.send_btn);
        back = findViewById(R.id.back_img_contact);
        intView();
        ads_controller = new Ads_Controller(this);

    }

    private void intView() {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (subject.getText().toString().length() == 0) {
                    subject.setError("Enter Subject");
                    subject.requestFocus();
                } else if (message.getText().toString().length() == 0) {
                    message.setError("Please Type Message");
                    message.requestFocus();
                } else {
                    if (ContactActivity.this == null) {
                        return;
                    }
                    String[] Email = {ads_controller.getContact_us_email()};
                    String Subject = subject.getText().toString();
                    String Message = message.getText().toString();
                    Intent intent = new Intent(Intent.ACTION_SENDTO);
                    intent.setData(Uri.parse("mailto:"));
                    intent.putExtra(Intent.EXTRA_EMAIL, Email);
                    intent.putExtra(Intent.EXTRA_SUBJECT, Subject);
                    intent.putExtra(Intent.EXTRA_TEXT, Message);
                    ContactActivity.this.startActivity(Intent.createChooser(intent, "Send Via"));
                }
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContactActivity.this == null) {
                    return;
                }
                ContactActivity.this.onBackPressed();
            }
        });

    }
}