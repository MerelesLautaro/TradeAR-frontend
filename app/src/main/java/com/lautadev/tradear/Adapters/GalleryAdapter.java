package com.lautadev.tradear.Adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.lautadev.tradear.R;
import com.lautadev.tradear.dto.ItemDTO;
import com.lautadev.tradear.utils.ItemCountManager;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ViewHolder> {
    private List<ItemDTO> itemList;
    public GalleryAdapter(List<ItemDTO> postList) {
        this.itemList = postList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gallery, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ItemDTO itemDTO = itemList.get(position);
        Glide.with(holder.itemView.getContext()).load(itemDTO.getLink()).into(holder.imagePost);
        holder.textUser.setText(itemDTO.getUserSecDTO().getName() + " " + itemDTO.getUserSecDTO().getLastname());
        holder.textDescription.setText(itemDTO.getDescription());
        holder.txtNameItem.setText(itemDTO.getName());

        // Formatear la fecha usando SimpleDateFormat
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy HH:mm", Locale.getDefault());
        String formattedDate = formatter.format(itemDTO.getDate());
        holder.textDate.setText(formattedDate);
    }

    @Override
    public int getItemCount() {
        ItemCountManager.getInstance().setItemCount(itemList.size());
        return itemList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imagePost;
        TextView textUser, textDescription, textDate, txtNameItem;

        public ViewHolder(View itemView) {
            super(itemView);
            imagePost = itemView.findViewById(R.id.image_post);
            textUser = itemView.findViewById(R.id.text_user);
            textDescription = itemView.findViewById(R.id.text_description);
            textDate = itemView.findViewById(R.id.text_date);
            txtNameItem = itemView.findViewById(R.id.text_name_item);
        }
    }
}