package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class AdminInquiryDetailActivity extends AppCompatActivity {
    private TextView senderTextView;
    private TextView titleTextView;
    private TextView bodyTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_inquiry_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        // Retrieve the inquiry details from the intent
        Intent intent = getIntent();
        String sender = intent.getStringExtra("sender");
        String title = intent.getStringExtra("title");
        String body = intent.getStringExtra("body");

        // Find the views in the layout
        senderTextView = findViewById(R.id.senderTextView);
        titleTextView = findViewById(R.id.adminTitleTextView);
        bodyTextView = findViewById(R.id.bodyTextView);


        // Set the inquiry details in the views
        senderTextView.setText(sender);
        titleTextView.setText(title);
        bodyTextView.setText(body);

        // Reply Results CardView
        View replyResultsCardView = findViewById(R.id.reply);
        replyResultsCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adminReplyActivity();
            }
        });

    }

    private void adminReplyActivity() {
        Intent intent_reply = new Intent(this, AdminReplyActivity.class);
        startActivity(intent_reply);
    }
}