package com.example.fitbook.shoes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Button;
import android.widget.Toast;

import com.example.fitbook.R;
import com.example.fitbook.SettingsActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class ShoesAddListActivity extends AppCompatActivity {

    private EditText ShoeName;
    private Spinner ShoeBrand;
    private Button AddShoeBtn;
    private FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shoes_add_list);

        ShoeName = findViewById(R.id.ShoeName_Input);
        ShoeBrand = findViewById(R.id.ShoeBrand_Dropdown);
        AddShoeBtn = findViewById(R.id.AddShoeBtn);

        ArrayAdapter<String> ShoeList = new ArrayAdapter<String>(ShoesAddListActivity.this,
                android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.shoes_brand));
        ShoeList.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ShoeBrand.setAdapter(ShoeList);
        final String[] Brand = {""};
        final String[] Shoe_name = {""};

        ShoeBrand.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Brand[0] = (String) parent.getItemAtPosition(position);
                if(position > 0)
                {
                    Toast.makeText(ShoesAddListActivity.this, "Selected : " + Brand[0], Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        AddShoeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Shoe_name[0] = ShoeName.getText().toString();
                Shoes newShoe = new Shoes(Shoe_name[0],Brand[0],0.0f);
                mFirestore.collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .collection("shoes").add(newShoe).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(ShoesAddListActivity.this, "Shoe Added Successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(ShoesAddListActivity.this, SettingsActivity.class);
                        startActivity(intent);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ShoesAddListActivity.this, "Shoe Failed To Add", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}