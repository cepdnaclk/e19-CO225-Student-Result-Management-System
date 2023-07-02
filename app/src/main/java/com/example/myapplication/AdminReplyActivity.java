package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AdminReplyActivity extends AppCompatActivity {


        private EditText messageTitle;
        private EditText messageBody;

        private DatabaseReference adminMessageRef;
        private DatabaseReference fieldAdminRef;
        private String sender = null;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_admin_reply);

            messageTitle = findViewById(R.id.adminTitleEditText);
            messageBody = findViewById(R.id.adminMessageEditText);

            adminMessageRef = FirebaseDatabase.getInstance().getReference("Course_Coordinator/cc1/inquiries");
            fieldAdminRef = FirebaseDatabase.getInstance().getReference("Field_Administrator/fa1/name");

            fieldAdminRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        String coordinatorName = dataSnapshot.getValue(String.class);
                        if (coordinatorName != null) {
                            // Set the course coordinator's name as the sender
                            sender = coordinatorName.trim();
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Handle any errors
                }
            });
        }

        public void onAdminSendButtonClicked(View view) {
            String title = messageTitle.getText().toString().trim();
            String body = messageBody.getText().toString().trim();

            // Generate a new unique key for the message
            String messageId = adminMessageRef.push().getKey();

            // Create a new Message object
            Inquiry message = new Inquiry(sender, title, body);

            // Save the message to the database under the generated key
            adminMessageRef.child(messageId).setValue(message)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(AdminReplyActivity.this, "Reply sent successfully", Toast.LENGTH_SHORT).show();
                            // Clear the input fields
                            messageTitle.setText("");
                            messageBody.setText("");
                            finish();
                        } else {
                            Toast.makeText(AdminReplyActivity.this, "Failed to reply, try again later", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
