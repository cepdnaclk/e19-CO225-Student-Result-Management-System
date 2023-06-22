package com.example.student_result_management_system;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SignupActivity extends AppCompatActivity {
    private EditText userName;
    private EditText password;
    private EditText confirmPass;
    private ImageButton backNavigator;
    private Button SignupBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        userName = findViewById(R.id.username);
        password = findViewById(R.id.password);
        confirmPass = findViewById(R.id.ConfirmPassword);
        backNavigator = findViewById(R.id.backnav);
        SignupBtn = findViewById(R.id.Signup);

        backNavigator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // create an Intent to navigate to a desired activity..
                Intent intent = new Intent(SignupActivity.this, loginActivity.class);

                // Starting the activity.
                startActivity(intent);
            }
        });

        SignupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uName, pswd, cPswd;

                uName = userName.getText().toString();
                pswd = password.getText().toString();
                cPswd = confirmPass.getText().toString();

                if (TextUtils.isEmpty(uName)){
                    Toast.makeText(SignupActivity.this, "Enter the username", Toast.LENGTH_SHORT).show();
                }
                if (TextUtils.isEmpty(pswd)){
                    Toast.makeText(SignupActivity.this, "Enter the Password", Toast.LENGTH_SHORT).show();
                }
                if (TextUtils.isEmpty(cPswd)){
                    Toast.makeText(SignupActivity.this, "Enter the Confirmation password", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}
