package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditProfileActivity extends AppCompatActivity {



        private DatabaseReference databaseReference;
        private EditText nameEditText, departmentEditText, instituteEditText, courseEditText, qualificationsEditText;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_edit_profile);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            // Get a reference to the root node of the database
            databaseReference = FirebaseDatabase.getInstance().getReference();

            nameEditText = findViewById(R.id.nameEditText);
            departmentEditText = findViewById(R.id.departmentEditText);
            instituteEditText = findViewById(R.id.instituteEditText);
            courseEditText = findViewById(R.id.courseEditText);
            qualificationsEditText = findViewById(R.id.qualificationsEditText);

            // Retrieve the passed data from the intent extras
            Intent intent = getIntent();
            String name = intent.getStringExtra("name");
            String department = intent.getStringExtra("department");
            String institute = intent.getStringExtra("institute");
            String course = intent.getStringExtra("course");
            String qualifications = intent.getStringExtra("qualifications");

            nameEditText.setText(name);
            departmentEditText.setText(department);
            instituteEditText.setText(institute);
            courseEditText.setText(course);
            qualificationsEditText.setText(qualifications);


            CardView saveButton = findViewById(R.id.saveButton);
            saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    saveProfileData();
                }
            });
        }

        private void saveProfileData() {
            String name = nameEditText.getText().toString();
            String department = departmentEditText.getText().toString();
            String institute = instituteEditText.getText().toString();
            String course = courseEditText.getText().toString();
            System.out.println(qualificationsEditText.getText().toString());
            String qualifications = qualificationsEditText.getText().toString();

            // Create a new child node under "Course_Coordinator" with a unique ID
            DatabaseReference profileRef = databaseReference.child("Course_Coordinator").child("cc1");

            // Set the profile details as key-value pairs
            profileRef.child("name").setValue(name);
            profileRef.child("department").setValue(department);
            profileRef.child("institute").setValue(institute);
            profileRef.child("course").setValue(course);
            profileRef.child("qualifications").setValue(qualifications);

            // Finish the activity and return to the ProfileActivity
            finish();
        }




//        // Populate the edit fields with the existing profile details
//        EditText nameEditText = findViewById(R.id.nameEditText);
//        EditText departmentEditText = findViewById(R.id.departmentEditText);
//        EditText instituteEditText = findViewById(R.id.instituteEditText);
//        EditText courseEditText = findViewById(R.id.courseEditText);
//        EditText qualificationsEditText = findViewById(R.id.qualificationsEditText);
//

//    }
}