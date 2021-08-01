package com.example.fitbook.routes;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fitbook.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class RoutesFinishActivity extends AppCompatActivity {

    private static final String TAG = "RoutesFinishActivity";
    public TextView DistanceTravelled,StepsTaken,ShoeSelected,TimeElapsed;
    public Button GoBackBtn;
    private FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routes_finish);
        DistanceTravelled = findViewById(R.id.DistanceTravelledResult);
        StepsTaken = findViewById(R.id.StepsTakenResult);
        TimeElapsed = findViewById(R.id.TimeElaspedResult);
        ShoeSelected = findViewById(R.id.ShoeSelectedResult);
        GoBackBtn = findViewById(R.id.RouteGoBackBtn);

        String RouteID = getIntent().getStringExtra("RouteID");
        String RouteName = getIntent().getStringExtra("RouteName");
        String Shoe = getIntent().getStringExtra("RouteShoe");
        String Distance = getIntent().getExtras().get("DistanceTravelled").toString();
        String Steps = getIntent().getExtras().get("StepsTaken").toString();
        String Time = getIntent().getExtras().get("TimeElapsed").toString();
        String FormatDistance = String.format("%.2f",Distance);
        DistanceTravelled.setText("Distance Travelled: " + FormatDistance);
        StepsTaken.setText("Steps Taken: " + Steps);
        TimeElapsed.setText("Time Elasped: " + Time);
        ShoeSelected.setText("Shoe Selected: " + Shoe);
        GoBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Routes newRoute = new Routes(RouteID,RouteName,FormatDistance,Time,Steps);
                mFirestore.collection("users").document(Objects.requireNonNull(mAuth.getCurrentUser()).getUid())
                        .collection("routes").document(RouteID).set(newRoute).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                    }
                });
                Intent intent = new Intent(RoutesFinishActivity.this,RoutesHomeListActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "The back button will not work at this screen", Toast.LENGTH_SHORT).show();
    }
}