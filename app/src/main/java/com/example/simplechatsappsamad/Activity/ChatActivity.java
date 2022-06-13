package com.example.simplechatsappsamad.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;

import com.example.simplechatsappsamad.Adapter.MessagesAdapter;
import com.example.simplechatsappsamad.Adapter.UsersAdapter;
import com.example.simplechatsappsamad.Model.Message;
import com.example.simplechatsappsamad.R;
import com.example.simplechatsappsamad.databinding.ActivityChatBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;

public class ChatActivity extends AppCompatActivity {

    ActivityChatBinding binding;
    ArrayList<Message> messagesarraylist;
    MessagesAdapter messagesAdapter;
    String senderRoom, RecieverRoom;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        database = FirebaseDatabase.getInstance();
        messagesarraylist = new ArrayList<>();

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));



        //data send
        String name = getIntent().getStringExtra("name");
        String recieveruid = getIntent().getStringExtra("uid");

        String senderUid = FirebaseAuth.getInstance().getUid();
        senderRoom = senderUid + recieveruid;
        RecieverRoom = recieveruid + senderUid;

        binding.sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String messagetxt = binding.messageBox.getText().toString();
                Date date = new Date();
                Message message = new Message(messagetxt, senderUid, date.getTime());
                binding.messageBox.setText("");


                database.getReference().child("chats")
                        .child(senderRoom)
                        .child("message")
                        .push()
                        .setValue(message).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                        database.getReference().child("chats")
                                .child(RecieverRoom)
                                .child("message")
                                .push()
                                .setValue(message).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {

                            }
                        });

                    }
                });

            }
        });


        //data recive
        database.getReference().child("chats")
                .child(senderRoom)
                .child("message")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        messagesarraylist.clear();
                        for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                            Message message =dataSnapshot.getValue( Message.class);
                            messagesarraylist.add(message);
                        }
                        messagesAdapter = new MessagesAdapter(getApplicationContext(), messagesarraylist);
                        binding.recyclerView.setAdapter(messagesAdapter);
                        messagesAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        getSupportActionBar().setTitle(name);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();


    }
}