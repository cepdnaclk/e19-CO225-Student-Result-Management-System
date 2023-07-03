package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AdminInquiryActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AdminInquiryAdapter adminInquiryAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inquiries);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Create an instance of the InquiryAdapter
        adminInquiryAdapter = new AdminInquiryAdapter(getApplicationContext(), new ArrayList<>());
        recyclerView.setAdapter(adminInquiryAdapter);

        DatabaseReference inquiriesRef = FirebaseDatabase.getInstance().getReference().child("Field_Administrator").child("fa1").child("messages");
        inquiriesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Inquiry> inquiryList = new ArrayList<>();

                for (DataSnapshot inquirySnapshot : dataSnapshot.getChildren()) {
                    Inquiry inquiry = inquirySnapshot.getValue(Inquiry.class);
                    inquiryList.add(inquiry);
                }

                // Update the inquiryAdapter with the new data
                adminInquiryAdapter.adminSetInquiryList(inquiryList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle the error
            }
        });
    }
}