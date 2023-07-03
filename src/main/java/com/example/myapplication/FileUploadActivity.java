package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import java.io.IOException;
import java.io.InputStream;

public class FileUploadActivity extends AppCompatActivity {

    private Button uploadButton;
    private Uri fileUri;
    private DatabaseReference databaseReference;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_upload);

        // Initialize Firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("uploads");
        FirebaseStorage storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        uploadButton = findViewById(R.id.uploadButton);
        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFilePicker();
            }
        });
    }

    private void openFilePicker() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            fileUri = data.getData();
            uploadFile();
        }
    }

    private void uploadFile() {
        if (fileUri != null) {
            try {
                InputStream inputStream = getContentResolver().openInputStream(fileUri);
                if (inputStream != null) {
                    // Upload the file to Firebase Realtime Database
                    String fileName = "file_" + System.currentTimeMillis();
                    databaseReference.child(fileName).setValue(inputStream.toString());
                    Toast.makeText(this, "File uploaded successfully", Toast.LENGTH_SHORT).show();
                }
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Failed to upload file", Toast.LENGTH_SHORT).show();
            }
        }
    }
}