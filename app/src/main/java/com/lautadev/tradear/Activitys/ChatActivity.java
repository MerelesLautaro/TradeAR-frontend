package com.lautadev.tradear.Activitys;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
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
import com.lautadev.tradear.dto.ChatDTO;
import com.lautadev.tradear.dto.ExchangeDTO;
import com.lautadev.tradear.dto.MessageDTO;
import com.lautadev.tradear.dto.SendMessageDTO;
import com.lautadev.tradear.dto.UserSecDTO;
import com.lautadev.tradear.model.Chat;
import com.lautadev.tradear.model.Message;
import com.lautadev.tradear.model.UserSec;
import com.lautadev.tradear.network.RetrofitClient;
import com.lautadev.tradear.repository.ChatAPIClient;
import com.lautadev.tradear.repository.ExchangeAPIClient;
import com.lautadev.tradear.repository.MessageAPIClient;
import com.lautadev.tradear.repository.UserSecAPIClient;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatActivity extends AppCompatActivity {

    private Button btnBack, btnSendMessage;

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

        btnSendMessage = findViewById(R.id.btnSendMessage);

        btnSendMessage.setOnClickListener(v -> {
            sendMessage();
        });

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

    private void sendMessage() {
        EditText inputTextMessage = findViewById(R.id.inputTextMessage);
        String messageContent = inputTextMessage.getText().toString().trim();

        if (messageContent.isEmpty()) {
            return;
        }

        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        Long userId = sharedPreferences.getLong("userId", -1);
        String timestamp = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault()).format(new Date());

        // Obtener el exchangeDTO para acceder al chat
        ExchangeAPIClient exchangeAPIClient = RetrofitClient.getClient().create(ExchangeAPIClient.class);
        Call<ExchangeDTO> exchangeCall = exchangeAPIClient.findExchange(exchangeId);

        exchangeCall.enqueue(new Callback<ExchangeDTO>() {
            @Override
            public void onResponse(Call<ExchangeDTO> call, Response<ExchangeDTO> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ExchangeDTO exchangeDTO = response.body();
                    long chatId = exchangeDTO.getChat().getId();

                    // Crear el DTO para enviar el mensaje
                    SendMessageDTO sendMessageDTO = new SendMessageDTO(
                            null,
                            messageContent,
                            timestamp,
                            userId,
                            chatId
                    );

                    // Llamar al endpoint para guardar el mensaje
                    MessageAPIClient messageAPIClient = RetrofitClient.getClient().create(MessageAPIClient.class);
                    Call<MessageDTO> messageCall = messageAPIClient.saveMessage(sendMessageDTO);

                    messageCall.enqueue(new Callback<MessageDTO>() {
                        @Override
                        public void onResponse(Call<MessageDTO> call, Response<MessageDTO> response) {
                            if (response.isSuccessful() && response.body() != null) {
                                // Limpiar el EditText
                                inputTextMessage.setText("");
                                loadChat(exchangeId);
                            } else {
                                Log.e("ChatActivity", "Error al guardar el mensaje");
                            }
                        }

                        @Override
                        public void onFailure(Call<MessageDTO> call, Throwable t) {
                            Log.e("ChatActivity", "Error al enviar el mensaje", t);
                        }
                    });
                } else {
                    Log.e("ChatActivity", "Error al obtener el exchange");
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
