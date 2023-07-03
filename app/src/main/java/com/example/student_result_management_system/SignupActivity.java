package com.example.student_result_management_system;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class SignupActivity extends AppCompatActivity {
    // Defining variable of layout components..
    private EditText userName;
    private EditText password;
    private EditText confirmPass;
    private ImageButton backNavigator;
    private Button SignupBtn;

    // definition of database reference;
    private DatabaseReference reff;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // get the values of the signup ui components..
        userName = findViewById(R.id.username);
        password = findViewById(R.id.password);
        confirmPass = findViewById(R.id.ConfirmPassword);
        backNavigator = findViewById(R.id.backnav);
        SignupBtn = findViewById(R.id.Signup);


        backNavigator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });

        SignupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String uName, pswd, cPswd, email;

                uName = userName.getText().toString();
                pswd = password.getText().toString();
                cPswd = confirmPass.getText().toString();
                email = uName + "@eng.pdn.ac.lk";


                if (TextUtils.isEmpty(uName)){
                    Toast.makeText(SignupActivity.this, "Enter the username", Toast.LENGTH_SHORT).show();
                }
                if (TextUtils.isEmpty(pswd)){
                    Toast.makeText(SignupActivity.this, "Enter the Password", Toast.LENGTH_SHORT).show();
                }
                if (TextUtils.isEmpty(cPswd)){
                    Toast.makeText(SignupActivity.this, "Enter the Confirmation password", Toast.LENGTH_SHORT).show();
                }

                if (!pswd.equals(cPswd))
                {
                    Toast.makeText(SignupActivity.this, "Confirmation Password is different", Toast.LENGTH_SHORT).show();
                }else{

                    // creating the reference of the database, and a child class students.
                    reff = FirebaseDatabase.getInstance().getReference().child("Student");
                    // Perform the database query
                    Query query = reff.orderByChild("Email(eng)").equalTo(email);
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                // Email exists in the database
                                // Perform the desired operations here

                                // Create user in firebase authentication
                                FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, pswd)
                                        .addOnCompleteListener(task -> {
                                            if (task.isSuccessful()){
                                                // User creation in Firebase Authentication successful
                                                FirebaseUser user = task.getResult().getUser();
                                                String userId = user.getUid();

                                                // store the password in the specific instance of the db.
                                                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                                                    // Get the reference to the child node
                                                    DatabaseReference childRef = childSnapshot.getRef();

                                                    // Update the password field with the new value
                                                    childRef.child("password").setValue(pswd)
                                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                @Override
                                                                public void onSuccess(Void aVoid) {
                                                                    // Password updated successfully
                                                                    // Perform any additional operations here
                                                                    Toast.makeText(SignupActivity.this, "Sign-up successful!", Toast.LENGTH_SHORT).show();
                                                                }
                                                            })
                                                            .addOnFailureListener(new OnFailureListener() {
                                                                @Override
                                                                public void onFailure(@NonNull Exception e) {
                                                                    // Failed to update password
                                                                    // Handle the error
                                                                    Toast.makeText(SignupActivity.this, "sign-up failed.", Toast.LENGTH_SHORT).show();
                                                                }
                                                            });
                                                }

                                            }
                                            else{
                                                Toast.makeText(SignupActivity.this, "SignUp failed.!", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            } else {
                                // Email does not exist in the database
                                // Perform other operations here
                                Toast.makeText(SignupActivity.this, "invalid username", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            // Handle the error
                        }
                    });

                }

            }
        });


    }
}
