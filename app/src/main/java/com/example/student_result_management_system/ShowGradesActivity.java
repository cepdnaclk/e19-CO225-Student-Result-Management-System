package com.example.student_result_management_system;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

public class ShowGradesActivity extends AppCompatActivity {

    private ImageButton backNavigator2;
    private TextView course1, course2, course3, course4, course5, course6;
    private TextView grade1, grade2, grade3, grade4, grade5, grade6;
    private String studentID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_grades);

        backNavigator2 = findViewById(R.id.backNav2);

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
                        Object value = childSnapshot.getValue();

                        // Set the key as the text for the corresponding TextView
                        switch (i) {
                            case 1:
                                course1.setText(key);
                                grade1.setText(value.toString());
                                break;
                            case 2:
                                course2.setText(key);
                                grade2.setText(value.toString());
                                break;
                            case 3:
                                course3.setText(key);
                                grade3.setText(value.toString());
                                break;
                            case 4:
                                course4.setText(key);
                                grade4.setText(value.toString());
                                break;
                            case 5:
                                course5.setText(key);
                                grade5.setText(value.toString());
                                break;
                            case 6:
                                course6.setText(key);
                                grade6.setText(value.toString());
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
                Toast.makeText(ShowGradesActivity.this, "Error in Accessing the Database", Toast.LENGTH_SHORT).show();
            }
        });




        // navigate to student homepage..
        backNavigator2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
