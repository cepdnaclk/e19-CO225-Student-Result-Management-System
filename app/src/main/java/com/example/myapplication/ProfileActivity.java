package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
        TextView ccQualifications = findViewById(R.id.profileQualifications);
        CardView editButton = findViewById(R.id.editButton);

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
                    String qualifications = userSnapshot.child("qualifications").getValue(String.class);
                    System.out.println(name);
                    System.out.println(department);
                    System.out.println(course);
                    ccName.setText(name);
                    ccDepartment.setText(department);
                    ccInstitute.setText(institute);
                    ccCourse.setText(course);
                    ccQualifications.setText(qualifications);



                    // TODO: Use the retrieved name and age values in your app
                }


                editButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Create an intent to open the EditProfileActivity
                        Intent intent = new Intent(ProfileActivity.this, EditProfileActivity.class);

                        // Pass the necessary data to the EditProfileActivity
                        intent.putExtra("name", ccName.getText().toString());
                        intent.putExtra("department", ccDepartment.getText().toString());
                        intent.putExtra("institute", ccInstitute.getText().toString());
                        intent.putExtra("course", ccCourse.getText().toString());
                        intent.putExtra("qualifications", ccQualifications.getText().toString());

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