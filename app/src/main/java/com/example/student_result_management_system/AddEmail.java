package com.example.student_result_management_system;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddEmail extends AppCompatActivity {

    private Button addBtn;
    private EditText etMail;
    private ImageButton backNavigator;
    private String studentID;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addmail);

        addBtn = findViewById(R.id.button);
        etMail = findViewById(R.id.mail);
        backNavigator = findViewById(R.id.backNavigator);
        // Retrieve the studentId from extras which is coming from the login page.
        Bundle extras = getIntent().getExtras();

        if(extras != null)
            studentID = extras.getString("StudentID");

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // creating a database instance..
                DatabaseReference dreff = FirebaseDatabase.getInstance().getReference("Student").child("student: "+studentID);

                dreff.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists())
                        {
                            // Access the specific student node
                            DatabaseReference studentRef = snapshot.getRef();

                            // Update the "Email-personal(optional)" field with the new email value
                            studentRef.child("Email-personal(optional)").setValue(etMail.getText().toString())
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            // Email updated successfully
                                            Toast.makeText(AddEmail.this, "Email added successfully.", Toast.LENGTH_SHORT).show();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            // Error occurred while updating the email
                                            Toast.makeText(AddEmail.this, "Error in adding the email.", Toast.LENGTH_SHORT).show();
                                        }
                                    });

                        }
                        else {
                            Toast.makeText(AddEmail.this, "Student not found!.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // Handle the error..
                        Toast.makeText(AddEmail.this, "Error in adding the mail.", Toast.LENGTH_SHORT).show();
                    }
                });
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
