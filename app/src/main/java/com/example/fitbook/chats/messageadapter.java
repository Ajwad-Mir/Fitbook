package com.example.fitbook.chats;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitbook.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class messageadapter extends FirestoreRecyclerAdapter<message,messageadapter.messageviewholder> {

    public messageadapter(@NonNull @NotNull FirestoreRecyclerOptions<message> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull @NotNull messageadapter.messageviewholder messageviewholder, int i, @NonNull @NotNull message message) {
        FirebaseFirestore.getInstance().collection("users").document(message.getrecieveruid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String Username = documentSnapshot.getString("displayName");
                messageviewholder.sender.setText(Username);
                messageviewholder.message.setText(message.getmessagetext());
                messageviewholder.messagetime.setText(message.getmessagetime());
            }
        });
    }

    @NonNull
    @NotNull
    @Override
    public messageadapter.messageviewholder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_listitem,parent,false);
        return new messageadapter.messageviewholder(v);
    }

    public class messageviewholder extends RecyclerView.ViewHolder {
        public TextView messagetime,message,sender;
        public messageviewholder(@NonNull @NotNull View itemView) {
            super(itemView);
            sender = itemView.findViewById(R.id.sendertextout);
            message = itemView.findViewById(R.id.MessageOutput);
            messagetime = itemView.findViewById(R.id.MessageTimestampout);
        }
    }
}
