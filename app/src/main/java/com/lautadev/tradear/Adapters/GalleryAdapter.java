package com.lautadev.tradear.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.lautadev.tradear.Activitys.ExchangeActivity;
import com.lautadev.tradear.R;
import com.lautadev.tradear.dto.ItemDTO;

import java.util.List;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ViewHolder> {
    private List<ItemDTO> itemList;
    private Context context;

    public GalleryAdapter(Context context, List<ItemDTO> postList) {
        this.context = context;
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
        Glide.with(holder.itemView.getContext())
                .load(itemDTO.getUserSecDTO().getPictureUrl())
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(100)))
                .into(holder.imageProfile);
        Glide.with(holder.itemView.getContext()).load(itemDTO.getLink()).into(holder.imagePost);
        holder.textUser.setText(itemDTO.getUserSecDTO().getName() + " " + itemDTO.getUserSecDTO().getLastname());
        holder.textDescription.setText(itemDTO.getDescription());
        holder.txtNameItem.setText(itemDTO.getName());

        holder.textDate.setText(itemDTO.getDate());

        // Configurar el listener para el botón "btnExchange"
        holder.btnExchange.setOnClickListener(v -> {
            Intent intent = new Intent(context, ExchangeActivity.class);
            intent.putExtra("ITEM_ID", itemDTO.getId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imagePost, imageProfile;
        TextView textUser, textDescription, textDate, txtNameItem;
        Button btnExchange;

        public ViewHolder(View itemView) {
            super(itemView);
            imageProfile = itemView.findViewById(R.id.img_profile);
            imagePost = itemView.findViewById(R.id.image_post);
            textUser = itemView.findViewById(R.id.text_user);
            textDescription = itemView.findViewById(R.id.text_description);
            textDate = itemView.findViewById(R.id.text_date);
            txtNameItem = itemView.findViewById(R.id.text_name_item);
            btnExchange = itemView.findViewById(R.id.btnExchange);
        }
    }
}