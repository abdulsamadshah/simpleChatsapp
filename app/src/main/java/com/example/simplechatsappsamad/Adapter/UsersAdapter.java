package com.example.simplechatsappsamad.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.example.simplechatsappsamad.Activity.ChatActivity;
import com.example.simplechatsappsamad.Model.User;
import com.example.simplechatsappsamad.R;
import com.example.simplechatsappsamad.databinding.RowConversationBinding;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.dynamic.IFragmentWrapper;

import java.util.ArrayList;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UsersViewHolder> {

    Context context;
    ArrayList<User> users;

    public UsersAdapter(Context context, ArrayList<User> users) {
        this.context = context;
        this.users = users;
    }

    @NonNull
    @Override
    public UsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view=LayoutInflater.from(context).inflate(R.layout.row_conversation,parent,false);

        return new UsersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UsersViewHolder holder, int position) {
        User user = users.get(position);
        holder.binding.username.setText(user.getName());

        Glide.with(context).load(user.getProfileImage())
                .placeholder(R.drawable.avatar)
                .into(holder.binding.profile);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context,ChatActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                intent.putExtra("name",user.getName());
                intent.putExtra("uid",user.getUid());
                context.startActivity(intent);
            }
        });



//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent=new Intent(context, ChatActivity.class);
//
//                Toast.makeText(context, "item is clicked", Toast.LENGTH_SHORT).show();
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
//                context.startActivity(intent);
//            }
//        });

//        String senderId = FirebaseAuth.getInstance().getUid();
//
//        String senderRoom = senderId + user.getUid();
//
//        FirebaseDatabase.getInstance().getReference()
//                .child("chats")
//                .child(senderRoom)
//                .addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        if(snapshot.exists()) {
//                            String lastMsg = snapshot.child("lastMsg").getValue(String.class);
//                            long time = snapshot.child("lastMsgTime").getValue(Long.class);
//                            SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm a");
//                            holder.binding.msgTime.setText(dateFormat.format(new Date(time)));
//                            holder.binding.lastMsg.setText(lastMsg);
//                        } else {
//                            holder.binding.lastMsg.setText("Tap to chat");
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });




//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(context, ChatActivity.class);
//                intent.putExtra("name", user.getName());
//                intent.putExtra("image", user.getProfileImage());
//                intent.putExtra("uid", user.getUid());
//                intent.putExtra("token", user.getToken());
//                context.startActivity(intent);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class UsersViewHolder extends RecyclerView.ViewHolder {

        RowConversationBinding binding;

        public UsersViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = RowConversationBinding.bind(itemView);



        }
    }

}
