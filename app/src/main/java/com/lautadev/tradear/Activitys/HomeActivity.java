package com.lautadev.tradear.Activitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.lautadev.tradear.R;


public class HomeActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.home) {
                    Intent qrIntent = new Intent(HomeActivity.this, HomeActivity.class);
                    startActivity(qrIntent);
                    return true;
                } else if (itemId == R.id.additem) {
                    Intent qrIntent = new Intent(HomeActivity.this, AddItemActivity.class);
                    startActivity(qrIntent);
                    return true;
                } else if (itemId == R.id.profile) {
                    Intent opcionesIntent = new Intent(HomeActivity.this, ProfileActivity.class);
                    startActivity(opcionesIntent);
                    return true;
                }
                return false;
            }
        });
    }
}