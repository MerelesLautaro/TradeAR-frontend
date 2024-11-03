package com.lautadev.tradear.Activitys;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.lautadev.tradear.Adapters.MessageAdapter;
import com.lautadev.tradear.R;
import com.lautadev.tradear.dto.ExchangeDTO;
import com.lautadev.tradear.dto.MessageDTO;
import com.lautadev.tradear.network.RetrofitClient;
import com.lautadev.tradear.repository.ExchangeAPIClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatActivity extends AppCompatActivity {

    private Button btnBack;
    private long exchangeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_activity);

        btnBack = findViewById(R.id.btn_back);

        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(ChatActivity.this, MessageActivity.class);
            startActivity(intent);
            finish();
        });

        exchangeId = getIntent().getLongExtra("EXCHANGE_ID", -1);

        if (exchangeId != -1) {
            loadChat(exchangeId);
        }
    }

    private void loadChat(long exchangeId){
        ExchangeAPIClient exchangeAPIClient  = RetrofitClient.getClient().create(ExchangeAPIClient.class);
        Call<ExchangeDTO> call = exchangeAPIClient.findExchange(exchangeId);

        call.enqueue(new Callback<ExchangeDTO>() {
            @Override
            public void onResponse(Call<ExchangeDTO> call, Response<ExchangeDTO> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ExchangeDTO exchangeDTO = response.body();
                    updateUI(exchangeDTO);
                }
            }

            @Override
            public void onFailure(Call<ExchangeDTO> call, Throwable t) {
                Log.e("ChatActivity", "Error en la llamada a la API", t);
            }
        });
    }

    private void updateUI(ExchangeDTO exchangeDTO){
        ImageView pictureItemRequested = findViewById(R.id.pictureItemRequested);
        TextView nameItemRequested = findViewById(R.id.nameItemRequested);
        TextView nameReceivingUser = findViewById(R.id.nameReceivingUser);

        Glide.with(this)
                .load(exchangeDTO.getItemRequested().get(0).getLink())
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(100)))
                .into(pictureItemRequested);

        String itemNameRequested = exchangeDTO.getItemRequested().get(0).getName();
        nameItemRequested.setText(itemNameRequested);
        nameReceivingUser.setText(exchangeDTO.getReceivingUser().getName()+" "+exchangeDTO.getReceivingUser().getLastname());

        List<MessageDTO> messages = exchangeDTO.getChat().getMessages();

        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        Long userId = sharedPreferences.getLong("userId", -1);

        // Configura el RecyclerView
        RecyclerView recyclerViewMessages = findViewById(R.id.recyclerViewMessages);
        MessageAdapter adapter = new MessageAdapter(messages,userId);
        recyclerViewMessages.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewMessages.setAdapter(adapter);
    }
}
