package com.example.student_result_management_system;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
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

public class AddGradesActivity extends AppCompatActivity {

    private ImageButton backNavigator1;
    private TextView course1, course2, course3, course4, course5, course6;
    private EditText grade1, grade2, grade3, grade4, grade5, grade6;
    private Button AddGradeBtn;
    private String studentID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_grades);

        backNavigator1 = findViewById(R.id.backNav1);

        AddGradeBtn = findViewById(R.id.AddButton);

        course1 = findViewById(R.id.course1label);
        course2 = findViewById(R.id.course2label);
        course3 = findViewById(R.id.course3label);
        course4 = findViewById(R.id.course4label);
        course5 = findViewById(R.id.course5label);
        course6 = findViewById(R.id.course6label);

        grade1 = findViewById(R.id.course1Grade);
        grade2 = findViewById(R.id.course2Grade);
        grade3 = findViewById(R.id.course3Grade);
        grade4 = findViewById(R.id.course4Grade);
        grade5 = findViewById(R.id.course5Grade);
        grade6 = findViewById(R.id.course6Grade);


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
                    DataSnapshot courseGradesSnapshot = snapshot.child("course_grades");
                    // Iterate over the child nodes
                    int i = 1;
                    for (DataSnapshot childSnapshot : courseGradesSnapshot.getChildren()) {
                        String key = childSnapshot.getKey();
//                        Object value = childSnapshot.getValue();

                        // Set the key as the text for the corresponding TextView
                        switch (i) {
                            case 1:
                                course1.setText(key);
                                break;
                            case 2:
                                course2.setText(key);
                                break;
                            case 3:
                                course3.setText(key);
                                break;
                            case 4:
                                course4.setText(key);
                                break;
                            case 5:
                                course5.setText(key);
                                break;
                            case 6:
                                course6.setText(key);
                                break;
                            // Add more cases for other course TextView components if needed
                            default:
                                // Handle additional courses or add extra logic
                                break;
                        }
                        i++;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle the error..
                Toast.makeText(AddGradesActivity.this, "Error in Accessing the Database", Toast.LENGTH_SHORT).show();
            }
        });


        AddGradeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                reff.child("course_grades").child("CO224").setValue(grade1.getText().toString());
                reff.child("course_grades").child("CO225").setValue(grade2.getText().toString());
                reff.child("course_grades").child("CO226").setValue(grade3.getText().toString());
                reff.child("course_grades").child("EE285").setValue(grade4.getText().toString());
                reff.child("course_grades").child("EM212").setValue(grade5.getText().toString());
                reff.child("course_grades").child("EM215").setValue(grade6.getText().toString());
                Toast.makeText(AddGradesActivity.this, "Grades are added into the system..", Toast.LENGTH_SHORT).show();

                grade1.getText().clear();
                grade2.getText().clear();
                grade3.getText().clear();
                grade4.getText().clear();
                grade5.getText().clear();
                grade6.getText().clear();

            }
        });


        // navigate to student homepage..
        backNavigator1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
