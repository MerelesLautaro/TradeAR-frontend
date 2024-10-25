package com.lautadev.tradear.Activitys;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.lautadev.tradear.Adapters.SectionsPagerAdapter;
import com.lautadev.tradear.R;
import com.lautadev.tradear.dto.UserSecDTO;
import com.lautadev.tradear.network.RetrofitClient;
import com.lautadev.tradear.repository.UserSecAPIClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    private TextView txtUsernameOptions, txtPosting, txtUsername;

    private ImageView imgProfile;

    private Button btnMessages;

    private UserSecAPIClient userSecAPIClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.home) {
                    Intent homeIntent = new Intent(ProfileActivity.this, HomeActivity.class);
                    startActivity(homeIntent);
                    return true;
                } else if (itemId == R.id.additem) {
                    Intent qrIntent = new Intent(ProfileActivity.this, AddItemActivity.class);
                    startActivity(qrIntent);
                    return true;
                } else if (itemId == R.id.profile) {
                    return true;
                }
                return false;
            }
        });

        imgProfile = findViewById(R.id.imgProfile);

        // Recuperar la URL picture desde SharedPreferences
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        String profilePictureUrl = sharedPreferences.getString("url_picture", null);

        // Cargar la imagen de perfil usando Glide
        loadProfileImage(profilePictureUrl);

        // Configuración del ViewPager y TabLayout
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(sectionsPagerAdapter);

        TabLayout tabs = findViewById(R.id.tabLayout);
        tabs.setupWithViewPager(viewPager);

        // Personalizar los tabs
        setupTab(tabs.getTabAt(0), "Inventario", R.drawable.ic_inventory);
        setupTab(tabs.getTabAt(1), "Guardados", R.drawable.ic_bookmark);

        userSecAPIClient = RetrofitClient.getClient().create(UserSecAPIClient.class);

        // Recuperar el ID del usuario desde SharedPreferences
        Long userId = sharedPreferences.getLong("userId", -1); // -1 es el valor por defecto si no se encuentra
        if(userId  != null){

            Call<UserSecDTO> userSecDTOCall = userSecAPIClient.findUser(userId);
            txtUsernameOptions = findViewById(R.id.txtUsernameOptions);
            txtUsername = findViewById(R.id.txtUsername);

            userSecDTOCall.enqueue(new Callback<UserSecDTO>() {
                @Override
                public void onResponse(Call<UserSecDTO> call, Response<UserSecDTO> response) {
                    if (response.isSuccessful() && response.body() != null){
                        UserSecDTO userSecDTO = response.body();
                        txtUsernameOptions.setText(userSecDTO.getName()+"."+userSecDTO.getLastname());
                        txtUsername.setText(userSecDTO.getName()+"  "+userSecDTO.getLastname());
                    } else {
                        Toast.makeText(ProfileActivity.this, "Error trying to find user data", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<UserSecDTO> call, Throwable t) {
                    Toast.makeText(ProfileActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    private void setupTab(TabLayout.Tab tab, String title, int iconResId) {
        if (tab != null) {
            // Inflar el layout personalizado
            View tabView = LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
            TextView tabText = tabView.findViewById(R.id.tabText);
            ImageView tabIcon = tabView.findViewById(R.id.tabIcon);

            tabText.setText(title);
            tabIcon.setImageResource(iconResId);

            // Asignar la vista personalizada al tab
            tab.setCustomView(tabView);
        }
    }

    private void loadProfileImage(String url) {
        if (url != null && imgProfile != null) {
            Glide.with(this)
                    .load(url)
                    .placeholder(R.mipmap.ic_default_profile) // Imagen de carga
                    .error(R.mipmap.ic_default_profile) // Imagen de error
                    .circleCrop()
                    .into(imgProfile);
        } else {
            imgProfile.setImageResource(R.mipmap.ic_default_profile); // Imagen por defecto
            Log.e("ProfileActivity", "URL o ImageView son nulos.");
        }
    }
}