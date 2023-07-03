package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class InquiryDetailsActivity extends AppCompatActivity {

    private TextView senderTextView;
    private TextView titleTextView;
    private TextView bodyTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inquiry_details);
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
        View uploadResultsCardView = findViewById(R.id.upload);

        // Set the inquiry details in the views
        senderTextView.setText(sender);
        titleTextView.setText(title);
        bodyTextView.setText(body);

        // Reply Results CardView
        View replyResultsCardView = findViewById(R.id.reply);
        replyResultsCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replyActivity();
            }
        });



        uploadResultsCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileUploadActivity();
            }
        });
    }


    private void replyActivity() {
        Intent intent_reply = new Intent(this, ReplyActivity.class);
        startActivity(intent_reply);
    }
    // Upload Results CardView




    private void openFileUploadActivity() {
        Intent intent = new Intent(this, FileUploadActivity.class);
        startActivity(intent);
    }
}
