package com.lautadev.tradear.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lautadev.tradear.Adapters.ImageGridAdapter;
import com.lautadev.tradear.R;
import com.lautadev.tradear.dto.ItemDTO;
import com.lautadev.tradear.network.RetrofitClient;
import com.lautadev.tradear.repository.ItemAPIClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment {

    private RecyclerView recyclerView;
    private ImageGridAdapter imageGridAdapter;
    private List<ItemDTO> itemList = new ArrayList<>();
    private List<ItemDTO> selectedItems = new ArrayList<>();
    private ItemAPIClient itemAPIClient;
    private boolean shouldRedirect;

    public ProfileFragment(boolean shouldRedirect) {
        this.shouldRedirect = shouldRedirect;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.inventory_fragment, container, false);

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));

        // Inicializar Retrofit
        itemAPIClient = RetrofitClient.getClient().create(ItemAPIClient.class);

        // Obtener el ID del usuario desde SharedPreferences
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
        Long userId = sharedPreferences.getLong("userId", -1);

        if (userId != -1) {
            loadItemsBelongingToUser(userId);
        }

        return view;
    }

    private void loadItemsBelongingToUser(Long userId) {
        Call<List<ItemDTO>> call = itemAPIClient.findItemsBelongingToUser(userId);
        call.enqueue(new Callback<List<ItemDTO>>() {
            @Override
            public void onResponse(Call<List<ItemDTO>> call, Response<List<ItemDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<ItemDTO> itemList = response.body();
                    updateUI(itemList);
                } else {
                    Log.e("ProfileFragment", "Error: respuesta no exitosa");
                }
            }

            @Override
            public void onFailure(Call<List<ItemDTO>> call, Throwable t) {
                Log.e("ProfileFragment", "Error en la llamada a la API", t);
            }
        });
    }

    private void updateUI(List<ItemDTO> itemList) {
        if (imageGridAdapter == null) {
            // Pasar false para shouldRedirect
            imageGridAdapter = new ImageGridAdapter(getContext(), itemList, shouldRedirect,selectedItems);
            recyclerView.setAdapter(imageGridAdapter);
        } else {
            // Actualiza la lista y notifica al adaptador
            imageGridAdapter.notifyDataSetChanged();
        }
    }
}
