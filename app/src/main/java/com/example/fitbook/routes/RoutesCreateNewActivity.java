package com.example.fitbook.routes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.fitbook.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Objects;

public class RoutesCreateNewActivity extends AppCompatActivity {
    private static final String TAG = "RoutesCreateNewActivity";
    private EditText RouteIDText,RouteNameText;
    private Spinner ShoeSpinner;
    private Button continueBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routes_create_new);


        RouteIDText = findViewById(R.id.RouteID_Createinput);
        RouteNameText = findViewById(R.id.RouteName_Createinput);
        ShoeSpinner = findViewById(R.id.Shoe_Selector);
        continueBtn = findViewById(R.id.ContinueRoute_CreateBtn);

        final FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();
        final FirebaseAuth mAuth = FirebaseAuth.getInstance();
        ArrayList<String> ShoeNameList = new ArrayList<>();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ShoeNameList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ShoeSpinner.setAdapter(adapter);

        mFirestore.collection("users").document(Objects.requireNonNull(mAuth.getCurrentUser()).getUid())
                .collection("shoes").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                if(task.isSuccessful())
                {
                    for(QueryDocumentSnapshot qds: task.getResult())
                    {
                        ShoeNameList.add(qds.getString("shoe_name"));
                    }
                    adapter.notifyDataSetChanged();
                }
            }
        });

        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String RouteID = RouteIDText.getText().toString();
                String RouteName = RouteNameText.getText().toString();
                String RouteShoe = ShoeSpinner.getSelectedItem().toString();


                Intent intent = new Intent(RoutesCreateNewActivity.this, RoutesRecordActivity.class);

                intent.putExtra("RouteID",RouteID);
                intent.putExtra("RouteName",RouteName);
                intent.putExtra("RouteShoe",RouteShoe);
                startActivity(intent);
            }
        });
    }
}