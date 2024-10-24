package com.lautadev.tradear.Activitys;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.lautadev.tradear.Adapters.GalleryAdapter;
import com.lautadev.tradear.R;
import com.lautadev.tradear.model.Item;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class HomeActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.home) {
                    Intent qrIntent = new Intent(HomeActivity.this, HomeActivity.class);
                    startActivity(qrIntent);
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
            }
        });

        List<Item> itemList = new ArrayList<>();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            itemList.add(new Item(1L, LocalDateTime.now(), "https://th.bing.com/th/id/OIP.P8HogL0Ou9ASPj3VwDtNgQHaE8?rs=1&pid=ImgDetMain", "Usuario1", "Descripción de la imagen 1"));
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            itemList.add(new Item(2L, LocalDateTime.now(), "https://cdn1.smartprix.com/rx-iNhU4gypf-w420-h420/realme-tv-32-inch-hd.jpg", "Usuario2", "Descripción de la imagen 2"));
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            itemList.add(new Item(3L, LocalDateTime.now(), "https://sgfm.elcorteingles.es/SGFM/dctm/MEDIA03/202210/06/00194612200868____8__1200x1200.jpg", "Usuario1", "Descripción de la imagen 1"));
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            itemList.add(new Item(4L, LocalDateTime.now(), "https://th.bing.com/th/id/R.d8b107243b7d46a47e36531c3decdaf3?rik=duo5JnYzFzRnog&riu=http%3a%2f%2fwhirlpool-latam.s3.amazonaws.com%2fwp-content%2fuploads%2f2020%2f06%2fWRM39-E2.jpg&ehk=Alg2g9O%2bV0I9YhPt8dwfOYVm6wcmV9Hrbc0Gw6abSTo%3d&risl=&pid=ImgRaw&r=0", "Usuario2", "Descripción de la imagen 2"));
        }

        // Configura el RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        GalleryAdapter adapter = new GalleryAdapter(itemList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }
}