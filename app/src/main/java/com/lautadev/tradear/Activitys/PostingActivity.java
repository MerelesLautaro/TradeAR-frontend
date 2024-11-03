package com.lautadev.tradear.Activitys;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.lautadev.tradear.R;
import com.lautadev.tradear.dto.ItemDTO;
import com.lautadev.tradear.network.RetrofitClient;
import com.lautadev.tradear.repository.ItemAPIClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostingActivity extends AppCompatActivity {
    private Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.posting_activity);

        btnBack = findViewById(R.id.btn_back);

        // Recuperar el ID del item desde SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        long itemId = sharedPreferences.getLong("selectedItemId", -1);

        if (itemId != -1) {
            loadItemDetails(itemId);
        }

        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(PostingActivity.this, ProfileActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void loadItemDetails(long itemId) {
        ItemAPIClient itemAPIClient = RetrofitClient.getClient().create(ItemAPIClient.class);
        Call<ItemDTO> call = itemAPIClient.findItem(itemId);
        call.enqueue(new Callback<ItemDTO>() {
            @Override
            public void onResponse(Call<ItemDTO> call, Response<ItemDTO> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ItemDTO item = response.body();
                    updateUI(item);
                }
            }

            @Override
            public void onFailure(Call<ItemDTO> call, Throwable t) {
                Log.e("PostingActivity", "Error en la llamada a la API", t);
            }
        });
    }

    private void updateUI(ItemDTO itemDTO) {
        // Referencias a las vistas
        ImageView imagePost = findViewById(R.id.image_post);
        TextView textDescription = findViewById(R.id.text_description);
        TextView textDate = findViewById(R.id.text_date);
        TextView textNameItem = findViewById(R.id.text_name_item);

        // Cargar la imagen utilizando Glide
        Glide.with(this)
                .load(itemDTO.getLink())
                .into(imagePost);

        textNameItem.setText(itemDTO.getName());
        textDescription.setText(itemDTO.getDescription());
        textDate.setText(itemDTO.getDate());

    }
}
