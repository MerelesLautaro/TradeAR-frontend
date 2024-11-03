package com.lautadev.tradear.Adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.lautadev.tradear.Activitys.ChatActivity;
import com.lautadev.tradear.R;
import com.lautadev.tradear.dto.ExchangeDTO;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder> {

    private List<ExchangeDTO> exchangeDTOS;

    public ChatAdapter(List<ExchangeDTO> exchangeDTOS) {
        this.exchangeDTOS = exchangeDTOS;
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item, parent, false);
        return new ChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        ExchangeDTO exchangeDTO = exchangeDTOS.get(position);
        String itemName = exchangeDTO.getItemRequested().get(0).getName();
        holder.nameItemRequested.setText(itemName);
        holder.nameReceivingUser.setText(exchangeDTO.getReceivingUser().getName()+" "+exchangeDTO.getReceivingUser().getLastname());
        holder.exchangeStatus.setText(exchangeDTO.getStatus().getName());
        Glide.with(holder.pictureItemRequested.getContext())
                .load(exchangeDTO.getItemRequested().get(0).getLink())
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(100)))
                .into(holder.pictureItemRequested);

        // Pasar el chat seleccionado a la otra activity
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(holder.itemView.getContext(), ChatActivity.class);
            intent.putExtra("EXCHANGE_ID", exchangeDTO.getId());
            holder.itemView.getContext().startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return exchangeDTOS.size();
    }

    public void updateChats(List<ExchangeDTO> exchangeDTOS) {
        this.exchangeDTOS.clear();
        this.exchangeDTOS.addAll(exchangeDTOS);
        notifyDataSetChanged();
    }

    static class ChatViewHolder extends RecyclerView.ViewHolder {
        ImageView pictureItemRequested;
        TextView nameItemRequested;
        TextView nameReceivingUser;
        TextView exchangeStatus;

        public ChatViewHolder(@NonNull View itemView) {
            super(itemView);
            pictureItemRequested = itemView.findViewById(R.id.pictureItemRequested);
            nameItemRequested = itemView.findViewById(R.id.nameItemRequested);
            nameReceivingUser = itemView.findViewById(R.id.nameReceivingUser);
            exchangeStatus = itemView.findViewById(R.id.exchangeStatus);
        }
    }
}
