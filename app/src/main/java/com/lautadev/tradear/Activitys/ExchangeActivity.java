package com.lautadev.tradear.Activitys;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.lautadev.tradear.R;
import com.lautadev.tradear.dto.ExchangeDTO;
import com.lautadev.tradear.dto.ItemDTO;
import com.lautadev.tradear.dto.UserSecDTO;
import com.lautadev.tradear.fragments.ProfileFragment;
import com.lautadev.tradear.model.Exchange;
import com.lautadev.tradear.network.RetrofitClient;
import com.lautadev.tradear.repository.ExchangeAPIClient;
import com.lautadev.tradear.repository.ItemAPIClient;
import com.lautadev.tradear.utils.OnItemsSelectedListener;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
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

        btnExchange.setOnClickListener(v -> {
            // Obtener el fragmento actual
            ProfileFragment profileFragment = (ProfileFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);
            if (profileFragment != null) {
                // Items del inventario del usuario seleccionados para el tradeo.
                List<ItemDTO> selectedItems = profileFragment.getSelectedItems();
                Log.d("ExchangeActivity", "Items seleccionados: " + selectedItems);

                createExchange(selectedItems,itemId);

            }
        });


    }

    private void createExchange(List<ItemDTO> selectedItems, Long itemId){

        ItemAPIClient itemAPIClient = RetrofitClient.getClient().create(ItemAPIClient.class);
        Call<ItemDTO> call = itemAPIClient.findItem(itemId);
        call.enqueue(new Callback<ItemDTO>() {
            @Override
            public void onResponse(Call<ItemDTO> call, Response<ItemDTO> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ItemDTO item = response.body();

                    List<ItemDTO> itemDTOSRequested = new ArrayList<>();
                    itemDTOSRequested.add(item);

                    ExchangeDTO exchangeDTO = null;
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                        exchangeDTO = new ExchangeDTO(
                                null,
                                LocalDateTime.now().toString(),
                                selectedItems,
                                itemDTOSRequested,
                                selectedItems.get(0).getUserSecDTO(),
                                item.getUserSecDTO(),
                                null,
                                null
                        );
                    }

                    saveExchange(exchangeDTO);
                }
            }

            @Override
            public void onFailure(Call<ItemDTO> call, Throwable t) {
                Log.e("ExchangeActivity", "Error en la llamada a la API", t);
            }
        });

    }

    private void saveExchange(ExchangeDTO exchangeDTO) {
        ExchangeAPIClient exchangeAPIClient = RetrofitClient.getClient().create(ExchangeAPIClient.class);
        Call<ExchangeDTO> call = exchangeAPIClient.saveExchange(exchangeDTO);
        call.enqueue(new Callback<ExchangeDTO>() {
            @Override
            public void onResponse(Call<ExchangeDTO> call, Response<ExchangeDTO> response) {
                if (response.isSuccessful()) {
                    Log.d("ExchangeActivity", "Intercambio creado exitosamente: " + response.body());
                } else {
                    Log.d("ExchangeDTO", "Exchange DTO JSON: " + new Gson().toJson(exchangeDTO));
                    Log.e("ExchangeActivity", "Error al crear el intercambio: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<ExchangeDTO> call, Throwable t) {
                Log.e("ExchangeActivity", "Error en la llamada a la API para crear el intercambio", t);
            }
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
                Log.e("ExchangeActivity", "Error en la llamada a la API", t);
            }
        });
    }

    private void updateUI(ItemDTO item) {
        ImageView imageUser = findViewById(R.id.img_profile);
        ImageView imagePost = findViewById(R.id.image_post);
        TextView textNameItem = findViewById(R.id.text_name_item);
        TextView textDescription = findViewById(R.id.text_description);
        TextView textDate = findViewById(R.id.text_date);
        TextView textUser = findViewById(R.id.text_user);

        // Cargar la imagen utilizando Glide
        Glide.with(this)
                        .load(item.getUserSecDTO().getPictureUrl())
                        .apply(RequestOptions.bitmapTransform(new RoundedCorners(100)))
                        .into(imageUser);

        Glide.with(this)
                .load(item.getLink())
                .into(imagePost);

        textUser.setText(item.getUserSecDTO().getName()+" "+item.getUserSecDTO().getLastname());
        textNameItem.setText(item.getName());
        textDescription.setText(item.getDescription());
       //SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
        //String formattedDate = formatter.format(item.getDate());
        textDate.setText(item.getDate());
    }

    @Override
    public void onItemsSelected(List<ItemDTO> selectedItems) {
        Log.d("ExchangeActivity", "Items seleccionados: " + selectedItems);
    }
}
