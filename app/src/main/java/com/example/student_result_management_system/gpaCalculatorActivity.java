package com.example.student_result_management_system;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;

public class gpaCalculatorActivity extends AppCompatActivity {

    private TextView currentGpa, neededGpa;
    private EditText targetGpa;
    private ImageButton backNavigator;
    private Button gpaCalculate;
    private String studentID;
    private double cgpa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gpa_calculator);

        currentGpa = findViewById(R.id.cgpa);
        neededGpa = findViewById(R.id.wantedGpa);
        gpaCalculate = findViewById(R.id.CalculateButton);
        backNavigator = findViewById(R.id.backNavigator);
        targetGpa = findViewById(R.id.targetgpa);

        // Retrieve the stored student data from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String cgpaString = sharedPreferences.getString("CurrentGpa", "");

        // Update the TextViews with the retrieved data
        currentGpa.setText(cgpaString);

        // Retrieve the studentId from extras which is coming from the login page.
        Bundle extras = getIntent().getExtras();

        if(extras != null)
            studentID = extras.getString("RegistrationNumber");

        // creating a database instance..
        DatabaseReference reff = FirebaseDatabase.getInstance().getReference("Student").child("student: "+studentID);

        reff.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    // Access the specific student details
                     cgpa = snapshot.child("CurrentGpa").getValue(Double.class);

                    // Save the student details in SharedPreferences
                    SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("CurrentGpa", String.valueOf(cgpa));
                    editor.apply();

                    // Update the TextViews with the retrieved data
                    currentGpa.setText(String.valueOf(cgpa));
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle the error..
                Toast.makeText(gpaCalculatorActivity.this, "Error in accessing the database..", Toast.LENGTH_SHORT).show();
            }
        });

        // calculations.......
        gpaCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double NeededGPA, GOAL;

                try {
                    GOAL = Double.parseDouble(targetGpa.getText().toString());
                } catch (NumberFormatException e) {
                    // Handle the exception if the input cannot be parsed to a double
                    GOAL = 0.0; // Default value if the input is not a valid double
                }

                NeededGPA = 2*GOAL - cgpa;

                // Round NeededGPA to 4 decimal places
                DecimalFormat decimalFormat = new DecimalFormat("#.####");
                String roundedNeededGpa = decimalFormat.format(NeededGPA);

                if (NeededGPA>4.0)
                {
                    neededGpa.setText("It is not possible to ascertain the target GPA for the current semester at this time.");
                }else{
                    neededGpa.setText("You need to get "+roundedNeededGpa+" GPA to gain the target GPA.");
                }

                // Clear the targetGpa EditText
                targetGpa.getText().clear();
            }
        });


        // navigate to student homepage..
        backNavigator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }
}
