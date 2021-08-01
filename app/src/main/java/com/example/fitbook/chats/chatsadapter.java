package com.example.fitbook.chats;

import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitbook.R;
import com.example.fitbook.users.useradapter;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

public class chatsadapter extends FirestoreRecyclerAdapter<chats,chatsadapter.chatsviewholder> {
    private onchatclicklistener listener;

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public chatsadapter(@NonNull @NotNull FirestoreRecyclerOptions<chats> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull @NotNull chatsadapter.chatsviewholder chatsviewholder, int i, @NonNull @NotNull chats chats) {
                FirebaseFirestore.getInstance().collection("users").document(chats.getchatroomsenderuid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String Name = documentSnapshot.getString("displayName");
                chatsviewholder.messageUser.setText(Name);
            }
        });
    }

    @NonNull
    @NotNull
    @Override
    public chatsadapter.chatsviewholder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.chatuser_listitem,parent,false);
        return new chatsadapter.chatsviewholder(v);
    }

    public class chatsviewholder extends RecyclerView.ViewHolder {
        public TextView messageUser;
        public chatsviewholder(@NonNull @NotNull View itemView) {
            super(itemView);
            messageUser = itemView.findViewById(R.id.UserNameText);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION && listener != null)
                    {
                        listener.onchatclick(getSnapshots().getSnapshot(position),position);
                    }
                }
            });
        }
    }


    public interface onchatclicklistener
    {
        public void onchatclick(DocumentSnapshot dss, int position);
    }
    public void setonchatclicklistener(chatsadapter.onchatclicklistener listener)
    {
        this.listener = listener;
    }
}
