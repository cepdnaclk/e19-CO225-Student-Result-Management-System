package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdminEditProfile extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private EditText nameEditText, batchEditText, fieldEditText, regNoEditText, idEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_edit_profile);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Get a reference to the root node of the database
        databaseReference = FirebaseDatabase.getInstance().getReference();

        nameEditText = findViewById(R.id.adminNameEditText);
        batchEditText = findViewById(R.id.adminBatchEditText);
        fieldEditText = findViewById(R.id.adminFieldEditText);
        regNoEditText = findViewById(R.id.adminRegistrationNumberEditText);
        idEditText = findViewById(R.id.adminIdEditText);

        // Retrieve the passed data from the intent extras
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String batch = intent.getStringExtra("batch");
        String field = intent.getStringExtra("field");
        String regNo = intent.getStringExtra("regNo");
        String id = intent.getStringExtra("id");

        nameEditText.setText(name);
        batchEditText.setText(batch);
        fieldEditText.setText(field);
        regNoEditText.setText(regNo);
        idEditText.setText(id);


        CardView saveButton = findViewById(R.id.adminSaveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveProfileData();
            }
        });
    }

    private void saveProfileData() {
        String name = nameEditText.getText().toString();
        String adminBatch = batchEditText.getText().toString();
        String adminField = fieldEditText.getText().toString();
        String adminRegNo = regNoEditText.getText().toString();
        System.out.println(idEditText.getText().toString());
        String adminId = idEditText.getText().toString();

        // Create a new child node under "Course_Coordinator" with a unique ID
        DatabaseReference profileRef = databaseReference.child("Field_Administrator").child("fa1");

        // Set the profile details as key-value pairs
        profileRef.child("name").setValue(name);
        profileRef.child("batch").setValue(adminBatch);
        profileRef.child("field").setValue(adminField);
        profileRef.child("registrationNumber").setValue(adminRegNo);
        profileRef.child("id").setValue(adminId);
        Toast.makeText(this, "Profile saved successfully", Toast.LENGTH_SHORT).show();
        // Finish the activity and return to the ProfileActivity
        finish();
    }
    }
