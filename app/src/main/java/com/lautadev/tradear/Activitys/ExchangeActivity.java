package com.lautadev.tradear.Activitys;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


import com.bumptech.glide.Glide;
import com.lautadev.tradear.R;
import com.lautadev.tradear.dto.ItemDTO;
import com.lautadev.tradear.fragments.ProfileFragment;
import com.lautadev.tradear.network.RetrofitClient;
import com.lautadev.tradear.repository.ItemAPIClient;
import com.lautadev.tradear.utils.OnItemsSelectedListener;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExchangeActivity extends AppCompatActivity implements OnItemsSelectedListener {

    private Button btnBack, btnCancel, btnExchange;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exchange_activity);

        btnBack = findViewById(R.id.btn_back);

        btnCancel = findViewById(R.id.btn_cancel);

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ExchangeActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        };

        btnBack.setOnClickListener(onClickListener);
        btnCancel.setOnClickListener(onClickListener);

        // llamada a ProfileFragment
        if (savedInstanceState == null) {
            ProfileFragment profileFragment = new ProfileFragment(false);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, profileFragment)
                    .commit();
        }

        Long itemId = getIntent().getLongExtra("ITEM_ID", -1);

        if (itemId != -1) {
            loadItemDetails(itemId);
        }

        btnExchange = findViewById(R.id.btn_tradear);

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

    private void updateUI(ItemDTO item) {
        ImageView imagePost = findViewById(R.id.image_post);
        TextView textNameItem = findViewById(R.id.text_name_item);
        TextView textDescription = findViewById(R.id.text_description);
        TextView textDate = findViewById(R.id.text_date);
        TextView textUser = findViewById(R.id.text_user);

        // Cargar la imagen utilizando Glide
        Glide.with(this)
                .load(item.getLink())
                .into(imagePost);

        textUser.setText(item.getUserSecDTO().getName()+" "+item.getUserSecDTO().getLastname());
        textNameItem.setText(item.getName());
        textDescription.setText(item.getDescription());
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
        String formattedDate = formatter.format(item.getDate());
        textDate.setText(formattedDate);
    }

    @Override
    public void onItemsSelected(List<ItemDTO> selectedItems) {
        Log.d("ExchangeActivity", "Items seleccionados: " + selectedItems);
    }
}
