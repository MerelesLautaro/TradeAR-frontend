package com.lautadev.tradear.Activitys;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.lautadev.tradear.R;

public class MessageActivity extends AppCompatActivity {

    private Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_activity);

        btnBack = findViewById(R.id.btn_back);

        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(MessageActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        });
    }
}
