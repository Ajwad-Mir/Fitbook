package com.example.fitbook.chats;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.fitbook.R;
import com.example.fitbook.users.Users;
import com.example.fitbook.users.useradapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.Objects;

public class ChatsSelectUserActivity extends AppCompatActivity {

    private RecyclerView userlist;
    private useradapter useradapter;
    private Button StartChat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chats_select_user);
        userlist = findViewById(R.id.Userlist_recyclerview);

        Setup();
    }

    private void Setup() {
        Query QB = FirebaseFirestore.getInstance().collectionGroup("users").whereNotEqualTo("user_UID", Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid());
        FirestoreRecyclerOptions<Users> options = new FirestoreRecyclerOptions.Builder<Users>()
                .setQuery(QB,Users.class).build();
        useradapter = new useradapter(options);
        userlist.setHasFixedSize(true);
        userlist.setLayoutManager(new LinearLayoutManager(this));
        userlist.setAdapter(useradapter);

        useradapter.setonuserclicklistener(new useradapter.onuserclicklistener() {
            @Override
            public void onuserclick(DocumentSnapshot dss, int position) {
                Intent intent = new Intent(ChatsSelectUserActivity.this, ChatsMessagingActivity.class);
                intent.putExtra("UID1",dss.getId());
                intent.putExtra("UID2",FirebaseAuth.getInstance().getCurrentUser().getUid());
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        useradapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        useradapter.stopListening();
    }
}