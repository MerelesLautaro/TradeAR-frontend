package com.lautadev.tradear;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Obtener datos del Intent
        String personName = getIntent().getStringExtra("personName");

        // Mostrar los datos
        TextView nameTextView = findViewById(R.id.nameTextView);
        nameTextView.setText("Welcome, " + personName);
    }
}