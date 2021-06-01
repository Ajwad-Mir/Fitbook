package com.example.fitbook.shoes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.fitbook.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class ShoesHomeListActivity extends AppCompatActivity {

    private RecyclerView Shoes_View;
    private ShoesAdapter Shoes_Adapter;
    private FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shoes_home_list);
        Shoes_View = findViewById(R.id.shoe_recyclerview);
        setup();
    }

    private void setup() {
        Query QB = mFirestore.collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .collection("shoes").orderBy("shoe_name",Query.Direction.ASCENDING);

        FirestoreRecyclerOptions<Shoes> options = new FirestoreRecyclerOptions.Builder<Shoes>()
                .setQuery(QB,Shoes.class).build();
        Shoes_Adapter = new ShoesAdapter(options);
        Shoes_View.setHasFixedSize(true);
        Shoes_View.setLayoutManager(new LinearLayoutManager(this));
        Shoes_View.setAdapter(Shoes_Adapter);

    }

    @Override
    protected void onStart() {
        super.onStart();
        Shoes_Adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Shoes_Adapter.stopListening();
    }
}
