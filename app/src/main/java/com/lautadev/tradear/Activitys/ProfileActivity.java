package com.lautadev.tradear.Activitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.lautadev.tradear.Adapters.SectionsPagerAdapter;
import com.lautadev.tradear.R;

public class ProfileActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

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
                    Intent qrIntent = new Intent(ProfileActivity.this, ProfileActivity.class);
                    startActivity(qrIntent);
                    return true;
                }
                return false;
            }
        });

        // Configuración del ViewPager y TabLayout
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(sectionsPagerAdapter);

        TabLayout tabs = findViewById(R.id.tabLayout);
        tabs.setupWithViewPager(viewPager);

        // Personalizar los tabs
        setupTab(tabs.getTabAt(0), "Inventario", R.drawable.ic_inventory);
        setupTab(tabs.getTabAt(1), "Guardados", R.drawable.ic_bookmark);
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
}