package com.lautadev.tradear.Adapters;

import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.lautadev.tradear.R;
import com.lautadev.tradear.dto.MessageDTO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {

    private List<MessageDTO> messages;
    private Long userId;
    public MessageAdapter(List<MessageDTO> messages, Long userId)  {
        this.messages = messages;
        this.userId = userId;
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_message, parent, false);
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        MessageDTO message = messages.get(position);
        try {
            holder.bind(message,userId);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public static class MessageViewHolder extends RecyclerView.ViewHolder {
        private final TextView messageContentReceived;
        private final TextView messageContentSent;
        private final TextView messageTimeReceived;
        private final TextView messageTimeSent;
        private final LinearLayout messageBubbleReceived;
        private final LinearLayout messageBubbleSent;
        private final ImageView profileImageView;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            messageContentReceived = itemView.findViewById(R.id.messageContentReceived);
            messageContentSent = itemView.findViewById(R.id.messageContentSent);
            messageTimeReceived = itemView.findViewById(R.id.messageTimeReceived);
            messageTimeSent = itemView.findViewById(R.id.messageTimeSent);
            messageBubbleReceived = itemView.findViewById(R.id.messageBubbleReceived);
            messageBubbleSent = itemView.findViewById(R.id.messageBubbleSent);
            profileImageView = itemView.findViewById(R.id.profileImageView);
        }

        public void bind(MessageDTO message, Long userId) throws ParseException {
            String timestamp = message.getTimestamp(); // El timestamp como String
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
            SimpleDateFormat outputFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());

            try {
                Date date = inputFormat.parse(timestamp);
                String formattedTime = outputFormat.format(date);

                if (message.getSender().getId() == userId) {
                    messageBubbleSent.setVisibility(View.VISIBLE);
                    messageBubbleReceived.setVisibility(View.GONE);
                    messageTimeSent.setText(formattedTime);
                    messageContentSent.setText(message.getContent());
                } else {
                    messageBubbleReceived.setVisibility(View.VISIBLE);
                    messageBubbleSent.setVisibility(View.GONE);
                    messageTimeReceived.setText(formattedTime);
                    messageContentReceived.setText(message.getContent());
                    Glide.with(itemView.getContext())
                            .load(message.getSender().getPictureUrl())
                            .apply(RequestOptions.bitmapTransform(new RoundedCorners(100)))
                            .into(profileImageView);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
