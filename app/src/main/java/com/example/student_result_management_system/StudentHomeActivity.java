package com.example.student_result_management_system;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.ktx.Firebase;

import org.w3c.dom.Text;

public class StudentHomeActivity extends AppCompatActivity {
    // Declaration of layout component variables..
    private  TextView regNumber1, regNumber2, tvLogout;
    private String studentID;
    private RelativeLayout personalInfo, GPATracker, AddGrades, ShowGrades;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_home);

        // definition of layout component variables;
        regNumber1 = findViewById(R.id.regNum);
        regNumber2 = findViewById(R.id.student);
        tvLogout = findViewById(R.id.tvLogout);

        personalInfo = findViewById(R.id.profileLayout);
        GPATracker = findViewById(R.id.gpaTrackerLayout);
        AddGrades = findViewById(R.id.AddGrades);
        ShowGrades = findViewById(R.id.ShowGrades);

        // Retrieve the stored student data from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String regnumber = sharedPreferences.getString("RegNum", "");

        // Update the TextViews with the retrieved data
        regNumber1.setText(regnumber);
        regNumber2.setText(regnumber);

        // Retrieve the studentId from extras which is coming from the login page.
        Bundle extras = getIntent().getExtras();

        if(extras != null)
            studentID = extras.getString("StudentID");

        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("Student").child("student: "+studentID); // Accessing the node Student to get the specific student details.

        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    // Retrieve the regnumber from the dataSnapshot
                    String regnumber = dataSnapshot.child("RegNum").getValue(String.class);

                    // Save the student details in SharedPreferences
                    SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("RegNum", regnumber);
                    editor.apply();

                    regNumber1.setText("E/19/"+regnumber.substring(5,8)+":  ");
                    regNumber2.setText(" E/19/"+regnumber.substring(5,8));
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(StudentHomeActivity.this, "Can't retrieve Data.", Toast.LENGTH_SHORT).show();
            }
        });

        tvLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // create an Intent to navigate to a desired activity..
                Intent intent = new Intent(StudentHomeActivity.this, loginActivity.class);

                // Clear the activity stack and start the desired activity as a new task
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                // Starting the activity.
                startActivity(intent);
            }
        });

        // Redirecting to student profile info..
        personalInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // create an Intent to navigate to a desired activity..
                Intent intent = new Intent(StudentHomeActivity.this, personalInfoActivity.class);
                intent.putExtra("RegistrationNumber",studentID);
                // Starting the activity.
                startActivity(intent);
            }
        });

        // Redirecting to GPA Tracker..
        GPATracker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // create an Intent to navigate to a desired activity..
                Intent intent = new Intent(StudentHomeActivity.this, gpaCalculatorActivity.class);
                intent.putExtra("RegistrationNumber",studentID);
                // Starting the activity.
                startActivity(intent);
            }
        });

        // Redirecting to Add Grades..
        AddGrades.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // create an Intent to navigate to a desired activity..
                Intent intent = new Intent(StudentHomeActivity.this, AddGradesActivity.class);
                intent.putExtra("RegistrationNumber",studentID);
                // Starting the activity.
                startActivity(intent);
            }
        });

        // Redirecting to Show Grades..
        ShowGrades.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // create an Intent to navigate to a desired activity..
                Intent intent = new Intent(StudentHomeActivity.this, ShowGradesActivity.class);
                intent.putExtra("RegistrationNumber",studentID);
                // Starting the activity.
                startActivity(intent);
            }
        });
    }

}
