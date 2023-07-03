package com.example.student_result_management_system;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class personalInfoActivity extends AppCompatActivity {

    private ImageButton backNavigator3;
    private TextView Name, RegNumber, Batch, engMail, personalMail, EditNav;
    private String studentID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_info);

        // defining ui components..
        backNavigator3 = findViewById(R.id.backNav3);

        Name = findViewById(R.id.Name);
        RegNumber = findViewById(R.id.RegNumber);
        Batch = findViewById(R.id.Batch);
        engMail =findViewById(R.id.engMail);
        personalMail = findViewById(R.id.personalMail);
        EditNav = findViewById(R.id.edit);

        // Retrieve the stored student data from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String name = sharedPreferences.getString("Name", "");
        String regNum = sharedPreferences.getString("RegNum", "");
        String batch = sharedPreferences.getString("Batch", "");
        String email = sharedPreferences.getString("Email", "");
        String emailPersonal = sharedPreferences.getString("EmailPersonal", "");

        // Update the TextViews with the retrieved data
        Name.setText(name);
        RegNumber.setText(regNum);
        Batch.setText(batch);
        engMail.setText(email);
        personalMail.setText(emailPersonal);

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
                    String name = snapshot.child("Name").getValue(String.class);
                    String regNum = snapshot.child("RegNum").getValue(String.class);
                    String batch = snapshot.child("Batch").getValue(String.class);
                    String email = snapshot.child("Email(eng)").getValue(String.class);
                    String emailPersonal = snapshot.child("Email-personal(optional)").getValue(String.class);

                    // Save the student details in SharedPreferences
                    SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("Name", name);
                    editor.putString("RegNum", regNum);
                    editor.putString("Batch", batch);
                    editor.putString("Email", email);
                    editor.putString("EmailPersonal", emailPersonal);
                    editor.apply();

                    // Update the TextViews with the retrieved data
                    Name.setText(name);
                    RegNumber.setText(regNum);
                    Batch.setText(batch);
                    engMail.setText(email);
                    personalMail.setText(emailPersonal);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle the error..
                Toast.makeText(personalInfoActivity.this, "Couldn't load the data..", Toast.LENGTH_SHORT).show();
            }
        });


        // navigate to student homepage..
        backNavigator3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        EditNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // create an Intent to navigate to a desired activity..
                Intent intent = new Intent(personalInfoActivity.this, AddEmail.class);
                intent.putExtra("StudentID", studentID);
                // Starting the activity.
                startActivity(intent);
            }
        });


    }
}
