package com.example.student_result_management_system;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

    // declaration of Firebase Authentication..
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_home);

        // definition of layout component variables;
        regNumber1 = findViewById(R.id.regNum);
        regNumber2 = findViewById(R.id.student);
        tvLogout = findViewById(R.id.tvLogout);

        // Initializing Firebase Authentication..
        mAuth = FirebaseAuth.getInstance();

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
                    String regnumber = dataSnapshot.child("regNumber").getValue(String.class);
                    regNumber1.setText("E/19/"+regnumber.substring(3,6)+":  ");
                    regNumber2.setText(" E/19/"+regnumber.substring(3,6));
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
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null)
        {
            startActivity(new Intent(StudentHomeActivity.this, loginActivity.class));
            finish();
        }
    }
}
