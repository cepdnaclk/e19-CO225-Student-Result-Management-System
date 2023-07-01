package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class InquiriesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private InquiryAdapter inquiryAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inquiries);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Create an instance of the InquiryAdapter
        inquiryAdapter = new InquiryAdapter(getApplicationContext(), new ArrayList<>());
        recyclerView.setAdapter(inquiryAdapter);

        DatabaseReference inquiriesRef = FirebaseDatabase.getInstance().getReference().child("Course_Coordinator").child("cc1").child("inquiries");
        inquiriesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Inquiry> inquiryList = new ArrayList<>();

                for (DataSnapshot inquirySnapshot : dataSnapshot.getChildren()) {
                    Inquiry inquiry = inquirySnapshot.getValue(Inquiry.class);
                    inquiryList.add(inquiry);
                }

                // Update the inquiryAdapter with the new data
                inquiryAdapter.setInquiryList(inquiryList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle the error
            }
        });
    }
}
