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
        setContentView(R.layout.add_item_activity);

        btnBack = findViewById(R.id.btn_back);
        bottomNavigationView = findViewById(R.id.bottom_navigation);

        // Establecer el ítem seleccionado para este Activity
        bottomNavigationView.setSelectedItemId(R.id.additem);

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
                    finish();
                    return true;
                } else if (itemId == R.id.additem) {
                    return true;
                } else if (itemId == R.id.profile) {
                    Intent opcionesIntent = new Intent(AddItemActivity.this, ProfileActivity.class);
                    startActivity(opcionesIntent);
                    finish();
                    return true;
                }
                return false;
            }
        });
    }
}
