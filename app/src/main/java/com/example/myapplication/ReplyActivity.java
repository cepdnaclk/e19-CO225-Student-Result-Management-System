package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ReplyActivity extends AppCompatActivity {

    private EditText messageTitle;
    private EditText messageBody;

    private DatabaseReference messagesRef;
    private DatabaseReference coordinatorRef;
    private String sender = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reply);

        messageTitle = findViewById(R.id.titleEditText);
        messageBody = findViewById(R.id.messageEditText);

        messagesRef = FirebaseDatabase.getInstance().getReference("Field_Administrator/fa1/messages");
        coordinatorRef = FirebaseDatabase.getInstance().getReference("Course_Coordinator/cc1/name");

        coordinatorRef.addValueEventListener(new ValueEventListener() {
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

    public void onSendButtonClicked(View view) {
        String title = messageTitle.getText().toString().trim();
        String body = messageBody.getText().toString().trim();

        // Generate a new unique key for the message
        String messageId = messagesRef.push().getKey();

        // Create a new Message object
        Inquiry message = new Inquiry(sender, title, body);

        // Save the message to the database under the generated key
        messagesRef.child(messageId).setValue(message)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(ReplyActivity.this, "Reply sent successfully", Toast.LENGTH_SHORT).show();
                        // Clear the input fields
                        messageTitle.setText("");
                        messageBody.setText("");
                        finish();
                    } else {
                        Toast.makeText(ReplyActivity.this, "Failed to reply, try again later", Toast.LENGTH_SHORT).show();
                    }
                });
    }

}