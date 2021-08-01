package com.example.fitbook.chats;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitbook.R;
import com.example.fitbook.diets.DietsDayListActivity;
import com.example.fitbook.diets.DietsHomeListActivity;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.Objects;

public class ChatsHomeListActivity extends AppCompatActivity {

    private static final String TAG = "ChatsHomeListActivity";
    private RecyclerView chatsuser_Recyclerview;
    private chatsadapter chatsadapter;
    private Button createnewchatbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chats_home_list);
        chatsuser_Recyclerview = findViewById(R.id.chatuser_Recyclerview);
        createnewchatbtn = findViewById(R.id.createnewchatbtn);
        Setup();
        createnewchatbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChatsHomeListActivity.this, ChatsSelectUserActivity.class);
                startActivity(intent);
            }
        });
    }

    private void Setup() {
        Query QB = FirebaseFirestore.getInstance().collection("users")
                .document(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid())
                .collection("chats");
        FirestoreRecyclerOptions<chats> options = new FirestoreRecyclerOptions.Builder<chats>()
                .setQuery(QB,chats.class).build();
        chatsadapter = new chatsadapter(options);
        chatsuser_Recyclerview.setHasFixedSize(true);
        chatsuser_Recyclerview.setLayoutManager(new LinearLayoutManager(this));
        chatsuser_Recyclerview.setAdapter(chatsadapter);

        chatsadapter.setonchatclicklistener(new chatsadapter.onchatclicklistener() {
            @Override
            public void onchatclick(DocumentSnapshot dss, int position) {
                Intent intent = new Intent(ChatsHomeListActivity.this, ChatsMessagingActivity.class);
                String ID = dss.getId();
                String UID1 = ID.substring(0,28);
                String UID2 = ID.substring(29);
                intent.putExtra("UID1",UID1);
                intent.putExtra("UID2",UID2);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        chatsadapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        chatsadapter.stopListening();
    }
}