package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView ccName = findViewById(R.id.profileName);
        TextView ccDepartment = findViewById(R.id.yourDepartment);
        TextView ccInstitute = findViewById(R.id.yourInstitute);
        TextView ccCourse = findViewById(R.id.yourCourse);

        // Get a reference to the root node of the database
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();

// Specify the child node or path from where you want to retrieve the data
        DatabaseReference dataRef = rootRef.child("Course_Coordinator");

// Add a ValueEventListener to listen for data changes
        dataRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    String name = userSnapshot.child("name").getValue(String.class);
                    String department = userSnapshot.child("department").getValue(String.class);
                    String institute = userSnapshot.child("institute").getValue(String.class);
                    String course = userSnapshot.child("course").getValue(String.class);
                    System.out.println(name);
                    System.out.println(department);
                    ccName.setText(name);
                    ccDepartment.setText(department);
                    ccInstitute.setText(institute);
                    ccCourse.setText(course);


                    // TODO: Use the retrieved name and age values in your app
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // This method is called if there is an error retrieving the data.
                // Handle the error as per your requirement.
            }
        });
    }
}