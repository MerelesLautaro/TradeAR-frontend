package com.lautadev.tradear.Adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
    private Context context;

    public ImageGridAdapter(Context context, List<ItemDTO> itemList) {
        this.context = context;
        this.itemList = itemList;
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

        holder.itemView.setOnClickListener(v -> {
            // Guardar el ID del item en SharedPreferences
            SharedPreferences sharedPreferences = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putLong("selectedItemId", item.getId());
            editor.apply();

            // Redirigir a PostingActivity
            Intent intent = new Intent(context, PostingActivity.class);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_post);
        }
    }
}
