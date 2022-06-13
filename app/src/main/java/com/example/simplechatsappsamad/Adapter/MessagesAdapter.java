package com.example.simplechatsappsamad.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.simplechatsappsamad.Model.Message;
import com.example.simplechatsappsamad.R;
import com.example.simplechatsappsamad.databinding.ItemReceiveBinding;
import com.example.simplechatsappsamad.databinding.ItemSendBinding;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class MessagesAdapter extends RecyclerView.Adapter {

    Context context;
    ArrayList<Message> messages;

    final int ITEM_SEND = 1;
    final int ITEM_RECIEVE = 2;


    public MessagesAdapter(Context context, ArrayList<Message> messages) {
        this.context = context;
        this.messages = messages;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == ITEM_SEND) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_send, parent, false);
            return new SentViewholder(view);
        } else {
            View views = LayoutInflater.from(context).inflate(R.layout.item_receive, parent, false);
            return new RecieverViewholder(views);
        }
    }

    @Override
    public int getItemViewType(int position) {

        Message message = messages.get(position);
        if (FirebaseAuth.getInstance().getUid().equals(message.getSenderId())) {
            return ITEM_SEND;
        } else {
            return ITEM_RECIEVE;
        }

    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        Message message = messages.get(position);

        if (holder.getClass() == SentViewholder.class) {
            SentViewholder viewholder = (SentViewholder) holder;
            viewholder.binding.message.setText(message.getMessage());

        } else {
            RecieverViewholder recieverViewholder = (RecieverViewholder) holder;
            recieverViewholder.binding.message.setText(message.getMessage());

        }


    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public class SentViewholder extends RecyclerView.ViewHolder {

        ItemSendBinding binding;

        public SentViewholder(@NonNull View itemView) {
            super(itemView);
            binding = ItemSendBinding.bind(itemView);
        }
    }


    public class RecieverViewholder extends RecyclerView.ViewHolder {
        ItemReceiveBinding binding;

        public RecieverViewholder(@NonNull View itemView) {
            super(itemView);
            binding = ItemReceiveBinding.bind(itemView);
        }
    }
}
