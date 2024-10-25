package com.lautadev.tradear.Activitys;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.lautadev.tradear.Adapters.GalleryAdapter;
import com.lautadev.tradear.R;
import com.lautadev.tradear.dto.ItemDTO;
import com.lautadev.tradear.dto.UserSecDTO;
import com.lautadev.tradear.model.UserSec;
import com.lautadev.tradear.network.RetrofitClient;
import com.lautadev.tradear.repository.ItemAPIClient;
import com.lautadev.tradear.repository.UserSecAPIClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private UserSecAPIClient userSecAPIClient;

    private ItemAPIClient itemAPIClient;
    private GalleryAdapter adapter;
    private RecyclerView recyclerView;
    private List<ItemDTO> itemList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.home) {
                return true;
            } else if (itemId == R.id.additem) {
                Intent qrIntent = new Intent(HomeActivity.this, AddItemActivity.class);
                startActivity(qrIntent);
                return true;
            } else if (itemId == R.id.profile) {
                Intent opcionesIntent = new Intent(HomeActivity.this, ProfileActivity.class);
                startActivity(opcionesIntent);
                return true;
            }
            return false;
        });

        // Recuperar el EMAIL del usuario desde SharedPreferences
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        String email = sharedPreferences.getString("EMAIL",null);
        loadGallery(email);
    }

    public void loadGallery(String email) {
        userSecAPIClient = RetrofitClient.getClient().create(UserSecAPIClient.class);

        // Obtener entidad del usuario por email
        Log.d("API Call", "URL: " + "http://10.0.2.2:8080/userSec/get/findByEmail?email=" + email);
        userSecAPIClient.findUserByEmail(email).enqueue(new Callback<UserSecDTO>() {
            @Override
            public void onResponse(Call<UserSecDTO> call, Response<UserSecDTO> response) {
                System.out.println("response: "+response);
                if (response.isSuccessful() && response.body() != null) {
                    UserSecDTO user = response.body();
                    Long userId = user.getId();

                    // Almacenar el ID en SharedPreferences
                    SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putLong("userId", userId);
                    editor.apply();

                    // Obtener ítems que no pertenecen al usuario
                    loadItemsNotBelongingToUser(userId);
                } else {
                    Log.e("HomeActivity", "Error al obtener el usuario: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<UserSecDTO> call, Throwable t) {
                Log.e("HomeActivity", "Error en la llamada al servidor", t);
            }
        });
    }

    private void loadItemsNotBelongingToUser(Long userId) {

        itemAPIClient = RetrofitClient.getClient().create(ItemAPIClient.class);
        itemAPIClient.indItemsNotBelongingToUser(userId).enqueue(new Callback<List<ItemDTO>>() {
            @Override
            public void onResponse(Call<List<ItemDTO>> call, Response<List<ItemDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    itemList = response.body();
                    setupRecyclerView();
                } else {
                    Log.e("HomeActivity", "Error al obtener los ítems: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<ItemDTO>> call, Throwable t) {
                Log.e("HomeActivity", "Error en la llamada al servidor", t);
            }
        });
    }

    private void setupRecyclerView() {
        recyclerView = findViewById(R.id.recyclerView);
        adapter = new GalleryAdapter(itemList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }
}