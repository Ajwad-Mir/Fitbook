package com.example.fitbook.routes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.fitbook.R;
import com.example.fitbook.workout.Workout;
import com.example.fitbook.workout.WorkoutAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class RoutesHomeListActivity extends AppCompatActivity {

    private RecyclerView routes_recyclerview;
    private Routesadapter routesadapter;
    private Button CreateNewRouteBtn;
    private FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routes_home_list);
        routes_recyclerview = findViewById(R.id.routes_recyclerview);
        CreateNewRouteBtn = findViewById(R.id.CreateNewRoute);
        Setup();
        CreateNewRouteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RoutesHomeListActivity.this, RoutesCreateNewActivity.class);
                startActivity(intent);
            }
        });
    }

    private void Setup() {
        Query QB = mFirestore.collectionGroup("routes");
        FirestoreRecyclerOptions<Routes> options = new FirestoreRecyclerOptions.Builder<Routes>()
                .setQuery(QB,Routes.class).build();

        routesadapter = new Routesadapter(options);
        routes_recyclerview.setHasFixedSize(true);
        routes_recyclerview.setLayoutManager(new LinearLayoutManager(this));
        routes_recyclerview.setAdapter(routesadapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        routesadapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        routesadapter.stopListening();
    }
}