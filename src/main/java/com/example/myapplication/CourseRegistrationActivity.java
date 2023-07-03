package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class CourseRegistrationActivity extends AppCompatActivity {

    private EditText registrationNumber;
    private EditText courseCode;
    private EditText courseName;
    private EditText courseCredit;
    private EditText coordinatorName;

    DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference dataRef = rootRef.child("Field_administrator");


        private DatabaseReference coursesRef;
        private DatabaseReference fieldAdminRef;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_course_registration);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);


            courseCode = findViewById(R.id.courseCode);
            courseName = findViewById(R.id.courseName);
            courseCredit = findViewById(R.id.courseCredits);
            coordinatorName = findViewById(R.id.courseCoordinator);

            DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
            coursesRef = rootRef.child("Courses");
            fieldAdminRef = rootRef.child("Field_administrator").child("fa1"); // Update the child key with the appropriate value
            System.out.println(fieldAdminRef.child("registrationNumber").toString());

            fieldAdminRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        String regNo = dataSnapshot.child("registrationNumber").getValue(String.class);
                        registrationNumber.setText(regNo);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Handle the error as per your requirement
                }
            });
        }

        public void onSaveButtonClicked(View view) {
            String code = courseCode.getText().toString().trim();
            String name = courseName.getText().toString().trim();
            String credit = courseCredit.getText().toString().trim();
            String coordinator = coordinatorName.getText().toString().trim();


            // Generate a unique key for the course
            String courseKey = coursesRef.push().getKey();

            // Create a Course object with the provided details
            Course course = new Course(code, name, credit, coordinator);

            // Save the course under the "courses" node with the generated key
            coursesRef.child(courseKey).setValue(course)
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(this, "Course saved successfully", Toast.LENGTH_SHORT).show();
                        // Perform any other necessary actions after successful course registration
                        finish();
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(this, "Failed to save course: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        // Handle the error as per your requirement
                    });
        }
}