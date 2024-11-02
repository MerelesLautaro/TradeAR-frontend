package com.lautadev.tradear.Adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.lautadev.tradear.Activitys.PostingActivity;
import com.lautadev.tradear.R;
import com.lautadev.tradear.dto.ItemDTO;

import java.util.List;

public class ImageGridAdapter extends RecyclerView.Adapter<ImageGridAdapter.ViewHolder> {
    private List<ItemDTO> itemList;
    private List<ItemDTO> selectedItems;
    private Context context;
    private boolean shouldRedirect;


    public ImageGridAdapter(Context context, List<ItemDTO> itemList, boolean shouldRedirect, List<ItemDTO> selectedItems) {
        this.context = context;
        this.itemList = itemList;
        this.shouldRedirect = shouldRedirect;
        this.selectedItems = selectedItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ItemDTO item = itemList.get(position);
        Glide.with(holder.imageView.getContext()).load(item.getLink()).into(holder.imageView);

        holder.selectedIcon = holder.itemView.findViewById(R.id.icon_selected);

        // Establecer el estado de selección
        if (selectedItems.contains(item)) {
            holder.selectedIcon.setVisibility(View.VISIBLE); // Mostrar el ícono si está seleccionado
        } else {
            holder.selectedIcon.setVisibility(View.GONE); // Ocultar el ícono si no está seleccionado
        }

        holder.itemView.setSelected(selectedItems.contains(item));

        holder.itemView.setOnClickListener(v -> {
            if (selectedItems.contains(item)) {
                selectedItems.remove(item); // Deseleccionar
            } else {
                selectedItems.add(item); // Seleccionar
            }

            // Log para verificar la lista de items seleccionados
            Log.d("ImageGridAdapter", "Items seleccionados: " + selectedItems.toString());

            notifyItemChanged(position); // Actualizar el estado de este item

            // Guardar el ID del item en SharedPreferences
            SharedPreferences sharedPreferences = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putLong("selectedItemId", item.getId());
            editor.apply();

            // Redirigir a PostingActivity solo si shouldRedirect es verdadero
            if (shouldRedirect) {
                Intent intent = new Intent(context, PostingActivity.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public List<ItemDTO> getSelectedItems() {
        return selectedItems;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        ImageView selectedIcon;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_post);
            selectedIcon = itemView.findViewById(R.id.icon_selected); // Obtener referencia al ícono
        }
    }
}
