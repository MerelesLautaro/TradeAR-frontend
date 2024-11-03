package com.lautadev.tradear.Activitys;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lautadev.tradear.Adapters.ChatAdapter;
import com.lautadev.tradear.R;
import com.lautadev.tradear.dto.ExchangeDTO;
import com.lautadev.tradear.network.RetrofitClient;
import com.lautadev.tradear.repository.ExchangeAPIClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MessageActivity extends AppCompatActivity {

    private Button btnBack;
    private RecyclerView recyclerView;
    private ChatAdapter chatAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_activity);

        btnBack = findViewById(R.id.btn_back);
        recyclerView = findViewById(R.id.chatRecycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        chatAdapter = new ChatAdapter(new ArrayList<>());
        recyclerView.setAdapter(chatAdapter);

        fetchExchanges();

        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(MessageActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void fetchExchanges() {
        // Recuperar el EMAIL del usuario desde SharedPreferences
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        String email = sharedPreferences.getString("EMAIL",null);

        ExchangeAPIClient exchangeAPIClient = RetrofitClient.getClient().create(ExchangeAPIClient.class);
        Call<List<ExchangeDTO>> call = exchangeAPIClient.myExchanges(email);

        call.enqueue(new Callback<List<ExchangeDTO>>() {
            @Override
            public void onResponse(Call<List<ExchangeDTO>> call, Response<List<ExchangeDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    chatAdapter.updateChats(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<ExchangeDTO>> call, Throwable t) {
                Log.e("MessageActivity", "Error en la llamada a la API", t);
            }
        });
    }
}
