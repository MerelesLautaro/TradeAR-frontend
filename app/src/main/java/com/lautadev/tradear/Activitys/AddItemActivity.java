package com.lautadev.tradear.Activitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.lautadev.tradear.R;

public class AddItemActivity extends AppCompatActivity {

    private Button btnBack;

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        btnBack = findViewById(R.id.btn_back);

        bottomNavigationView = findViewById(R.id.bottom_navigation);


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddItemActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.home) {
                    Intent homeIntent = new Intent(AddItemActivity.this, HomeActivity.class);
                    startActivity(homeIntent);
                    return true;
                } else if (itemId == R.id.additem) {
                    Intent opcionesIntent = new Intent(AddItemActivity.this, AddItemActivity.class);
                    startActivity(opcionesIntent);
                    return true;
                } else if (itemId == R.id.profile) {
                    Intent opcionesIntent = new Intent(AddItemActivity.this, ProfileActivity.class);
                    startActivity(opcionesIntent);
                    return true;
                }
                return false;
            }
        });
    }
}
