package com.example.student_result_management_system;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class loginActivity extends AppCompatActivity {
    // declaration of variables for login ui components.
    private EditText editTextusername, editTextpassword;
    private Button loginBtn;
    private TextView tvRedirect;

    // declaration of firebaseAuthentication object;
    private FirebaseAuth userAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // definition of ui components.
        editTextusername = findViewById(R.id.username);
        editTextpassword = findViewById(R.id.password);
        loginBtn = findViewById(R.id.Login);
        tvRedirect = findViewById(R.id.signUp_link);

        // Authentication object definition.;
        userAuth = FirebaseAuth.getInstance();


        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username, password, email;
                username = String.valueOf(editTextusername.getText());
                password = String.valueOf(editTextpassword.getText());
                email = username + "@eng.pdn.ac.lk";

                if (TextUtils.isEmpty(username)){
                    Toast.makeText(loginActivity.this, "Enter the username", Toast.LENGTH_LONG).show();
                    return;
                }
                if (TextUtils.isEmpty(password)){
                    Toast.makeText(loginActivity.this, "Enter the Password", Toast.LENGTH_LONG).show();
                    return;
                }

                if (username.startsWith("e")) {

                    // Call the signInWithEmailAndPassword method
                    userAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(loginActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Redirect to student_home page
                                        Toast.makeText(loginActivity.this, "Success!", Toast.LENGTH_SHORT).show();

                                        Intent intent = new Intent(loginActivity.this, StudentHomeActivity.class);
                                        intent.putExtra("StudentID", username);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        // Authentication failed
                                        Toast.makeText(loginActivity.this, "invalid username or password.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                } else if (username.startsWith("cc")) {
                    // Redirect to cc_home page
                    startActivity(new Intent(loginActivity.this, CoordinatorHomeActivity.class));
                    finish();
                } else if (username.startsWith("a")) {
                    // Redirect to cc_home page
                    startActivity(new Intent(loginActivity.this, AdministratorHomeActivity.class));
                    finish();
                } else {
                    // Invalid username
                    Toast.makeText(loginActivity.this, "Invalid username", Toast.LENGTH_LONG).show();
                }

            }

        });

        tvRedirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // create an Intent to navigate to a desired activity..
                Intent intent = new Intent(loginActivity.this, SignupActivity.class);
                // Starting the activity.
                startActivity(intent);
            }
        });

    }
}
