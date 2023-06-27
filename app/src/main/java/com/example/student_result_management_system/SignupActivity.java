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

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity {
    // Defining variable of layout components..
    private EditText userName;
    private EditText password;
    private EditText confirmPass;
    private ImageButton backNavigator;
    private Button SignupBtn;

    // definition of student object;
    private Student student;

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

        // creating a student instance..
        student = new Student();

        // creating the reference of the database, and a child class students.
        reff = FirebaseDatabase.getInstance().getReference().child("Student");

        backNavigator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // create an Intent to navigate to a desired activity..
                Intent intent = new Intent(SignupActivity.this, loginActivity.class);

                // Clear the activity stack and start the desired activity as a new task
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                // Starting the activity.
                startActivity(intent);
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
                    student.setRegNumber(uName);
                    student.setEmail(email);
                    student.setPassword(pswd);

                    // Create user in firebase authentication
                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, pswd)
                                    .addOnCompleteListener(task -> {
                                        if (task.isSuccessful()){
                                            // User creation in Firebase Authentication successful
                                            FirebaseUser user = task.getResult().getUser();
                                            String userId = user.getUid();

                                            // store additional information in the realtime Database.
                                            reff.child("student: "+uName).setValue(student);
                                            Toast.makeText(SignupActivity.this, "Sign-up successful!", Toast.LENGTH_SHORT).show();

                                        }
                                        else{
                                            Toast.makeText(SignupActivity.this, "SignUp failed.!", Toast.LENGTH_SHORT).show();
                                        }
                                    });

                }

            }
        });


    }
}
