package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class AdminHomeActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle drawerToggle;

    public void onCourseRegistrationClicked(View view) {
        // Handle inquiries card click
        Intent intent = new Intent(this, CourseRegistrationActivity.class);
        startActivity(intent);
    }
    public void onInquiriesMessageClicked(View view) {
        // Handle inquiries card click
        Intent intent = new Intent(this, ReplyActivity.class);
        startActivity(intent);
    }



    public void onRequestResultsClicked(View view) {
        // Handle results card click
        Intent intent = new Intent(this, RequestResultsActivity.class);
        startActivity(intent);
    }

    public void onAdminProfileClicked(View view) {
        // Handle profile card click
        Intent intent = new Intent(this, AdminProfileActivity.class);
        startActivity(intent);
    }

    public void onAdminInquiriesClicked(View view) {
        // Handle profile card click
        Intent intent = new Intent(this, AdminInquiryActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout,R.string.open, R.string.close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.home:
                        Toast.makeText(AdminHomeActivity.this, "Home selected", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.contact:
                        Toast.makeText(AdminHomeActivity.this, "Contact selected", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.settings:
                        Toast.makeText(AdminHomeActivity.this, "Settings selected", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.about:
                        Toast.makeText(AdminHomeActivity.this, "About selected", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.logout:
                        Toast.makeText(AdminHomeActivity.this, "Logout selected", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.share:
                        Toast.makeText(AdminHomeActivity.this, "Share selected", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.rate_us:
                        Toast.makeText(AdminHomeActivity.this, "Rate us selected", Toast.LENGTH_SHORT).show();
                        break;
                }
                return false;
            }
        });
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
    }
}