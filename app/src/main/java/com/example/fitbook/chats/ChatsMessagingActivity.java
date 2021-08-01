package com.example.fitbook.chats;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.fitbook.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class ChatsMessagingActivity extends AppCompatActivity {

    private RecyclerView messageRecyclerview;
    private messageadapter messageadapter;
    private ImageButton sendmessage;
    private EditText MessageTextBox;
    private FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chats_messaging);
        messageRecyclerview = findViewById(R.id.message_recyclerview);
        sendmessage = findViewById(R.id.SendButton);
        MessageTextBox = findViewById(R.id.MessageTextBox);

        String UID1 = getIntent().getStringExtra("UID1"); //UID of the user you want to send the message to
        String UID2 = getIntent().getStringExtra("UID2"); //UID of the sender
        setup(UID1,UID2);
        sendmessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Message = MessageTextBox.getText().toString();
                String Time = java.text.DateFormat.getTimeInstance().format(Calendar.getInstance().getTime());
                createnewchatroom(UID1,UID2);
                message newmessage = new message(UID1,UID2,Time,Message);
                mFirestore.collection("users")
                        .document(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid())
                        .collection("chats").document(UID1 + "_" + UID2)
                        .collection("messages").add(newmessage).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        mFirestore.collection("users")
                                .document(UID1)
                                .collection("chats").document(UID2 + "_" + UID1)
                                .collection("messages").add(newmessage).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(ChatsMessagingActivity.this,"Message Sent",Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                });
                MessageTextBox.setText("");
            }
        });

    }

    private void createnewchatroom(String UID1, String UID2) {
        chats newchat = new chats(UID1 + "_" + UID2, UID1,UID2);
        mFirestore.collection("users").document(UID2)
                .collection("chats").document(UID1 + "_" + UID2)
                .set(newchat).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {

            }
        });
        newchat = new chats(UID2 + "_" + UID1, UID2,UID1);
        mFirestore.collection("users").document(UID1)
                .collection("chats").document(UID2 + "_" + UID1)
                .set(newchat).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {

            }
        });
    }

    private void setup(String uid1, String uid2) {
        Query QB = mFirestore.collection("users").document(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid())
                .collection("chats").document(uid1 + "_" + uid2)
                .collection("messages").orderBy("messagetime",Query.Direction.ASCENDING);
        FirestoreRecyclerOptions<message> options = new FirestoreRecyclerOptions.Builder<message>()
                .setQuery(QB,message.class).build();
        messageadapter = new messageadapter(options);
        messageRecyclerview.setHasFixedSize(true);
        messageRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        messageRecyclerview.setAdapter(messageadapter);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ChatsMessagingActivity.this,ChatsHomeListActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        messageadapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        messageadapter.stopListening();
    }
}