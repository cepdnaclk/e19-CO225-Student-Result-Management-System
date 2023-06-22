package com.example.student_result_management_system;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class loginActivity extends AppCompatActivity {

    TextView editTextusername,editTextpassword;
    Button loginBtn;
    TextView tvRedirect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextusername = findViewById(R.id.username);
        editTextpassword = findViewById(R.id.password);
        loginBtn = findViewById(R.id.Login);
        tvRedirect = findViewById(R.id.signUp_link);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username, password;
                username = String.valueOf(editTextusername.getText());
                password = String.valueOf(editTextpassword.getText());

                if (TextUtils.isEmpty(username)){
                    Toast.makeText(loginActivity.this, "Enter the username", Toast.LENGTH_LONG).show();
                    return;
                }

                if (TextUtils.isEmpty(password)){
                    Toast.makeText(loginActivity.this, "Enter the Password", Toast.LENGTH_LONG).show();
                    return;
                }


                if (username.startsWith("e")) {
                    // Redirect to student_home page
                    startActivity(new Intent(loginActivity.this, StudentHomeActivity.class));
                    finish();
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
