package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class ResultsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Batch Spinner
        Spinner batchSpinner = findViewById(R.id.batchSpinner);
        batchSpinner.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> batchAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.batch_array,
                android.R.layout.simple_spinner_item
        );
        batchAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        batchSpinner.setAdapter(batchAdapter);

        // Course Spinner
        Spinner courseSpinner = findViewById(R.id.courseSpinner);
        courseSpinner.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> courseAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.course_array,
                android.R.layout.simple_spinner_item
        );
        courseAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        courseSpinner.setAdapter(courseAdapter);

        // Semester Spinner
        Spinner semesterSpinner = findViewById(R.id.semesterSpinner);
        semesterSpinner.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> semesterAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.semester_array,
                android.R.layout.simple_spinner_item
        );
        semesterAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        semesterSpinner.setAdapter(semesterAdapter);

        // Upload Results CardView
        View uploadResultsCardView = findViewById(R.id.upload);
        uploadResultsCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileUploadActivity();
            }
        });


    }



    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        int spinnerId = parent.getId();
        String selectedItem = parent.getItemAtPosition(position).toString();
        String message = "";

        if (spinnerId == R.id.batchSpinner) {
            message = "Selected Batch: " + selectedItem;
        } else if (spinnerId == R.id.courseSpinner) {
            message = "Selected Course: " + selectedItem;
        } else if (spinnerId == R.id.semesterSpinner) {
            message = "Selected Semester: " + selectedItem;
        }

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // Do nothing
    }

    private void openFileUploadActivity() {
        Intent intent = new Intent(this, FileUploadActivity.class);
        startActivity(intent);
    }
}