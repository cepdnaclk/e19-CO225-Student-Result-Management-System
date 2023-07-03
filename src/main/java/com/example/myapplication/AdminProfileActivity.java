package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class AdminProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_profile);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView faName = findViewById(R.id.yourName);
        TextView faBatch = findViewById(R.id.yourBatch);
        TextView faField = findViewById(R.id.yourField);
        TextView faRegNo = findViewById(R.id.yourRegistrationNumber);
        TextView faId = findViewById(R.id.yourID);
        CardView editButton = findViewById(R.id.AdminEditButton);
        CardView changeRole = findViewById(R.id.adminChangeRole);

        // Get a reference to the root node of the database
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();

// Specify the child node or path from where you want to retrieve the data
        DatabaseReference dataRef = rootRef.child("Field_Administrator");




// Add a ValueEventListener to listen for data changes
        dataRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    String name = userSnapshot.child("name").getValue(String.class);
                    String batch = userSnapshot.child("batch").getValue(String.class);
                    String field = userSnapshot.child("field").getValue(String.class);
                    String regNo = userSnapshot.child("registrationNumber").getValue(String.class);
                    String id = userSnapshot.child("id").getValue(String.class);

                    faName.setText(name);
                    faBatch.setText(batch);
                    faField.setText(field);
                    faRegNo.setText(regNo);
                    faId.setText(id);




                    // TODO: Use the retrieved name and age values in your app
                }

                changeRole.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(AdminProfileActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                });


                editButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Create an intent to open the EditProfileActivity
                        Intent intent = new Intent(AdminProfileActivity.this, AdminEditProfile.class);

                        // Pass the necessary data to the EditProfileActivity
                        intent.putExtra("name", faName.getText().toString());
                        intent.putExtra("batch", faBatch.getText().toString());
                        intent.putExtra("field", faField.getText().toString());
                        intent.putExtra("regNo", faRegNo.getText().toString());
                        intent.putExtra("id", faId.getText().toString());

                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // This method is called if there is an error retrieving the data.
                // Handle the error as per your requirement.
            }
        });
    }
}