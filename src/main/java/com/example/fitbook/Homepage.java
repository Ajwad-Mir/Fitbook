package com.example.fitbook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.fitbook.exercise.ExerciseHomeListActivity;
import com.example.fitbook.musicplayer.MusicPlayerSongList;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class Homepage extends AppCompatActivity {

    private Button AboutUsBtn, MusicPlayerBtn,ExerciseBtn,DietsBtn,LeaderboardBtn;
    private FirebaseAuth mAuth;
    private FirebaseFirestore mFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        AboutUsBtn = findViewById(R.id.AboutUsBtn);
        MusicPlayerBtn = findViewById(R.id.MusicPlayerBtn);
        ExerciseBtn = findViewById(R.id.ExercisesBtn);
        DietsBtn = findViewById(R.id.DietsBtn);
        LeaderboardBtn = findViewById(R.id.LeaderboardBtn);
        mAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();

        AboutUsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Homepage.this, AboutUsActivity.class);
                startActivity(intent);
            }
        });
        MusicPlayerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Homepage.this, MusicPlayerSongList.class);
                startActivity(intent);
            }
        });
        ExerciseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Homepage.this, ExerciseHomeListActivity.class);
                startActivity(intent);
            }
        });
//        DietsBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Homepage.this, DietsListActivity.class);
//                startActivity(intent);
//            }
//        });
//        LeaderboardBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Homepage.this, LeaderboardActivity.class);
//                startActivity(intent);
//            }
//        });
    }
    @Override
    public void onBackPressed() {
        Toast.makeText(this, "The back button will not work at this screen", Toast.LENGTH_SHORT).show();
    }

    //Options Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home_options, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.settings:
                Intent intent = new Intent(Homepage.this, SettingsActivity.class);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mFirestore.collection("users")
                .document(Objects.requireNonNull(mAuth.getCurrentUser()).getUid()).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful())
                {
                    DocumentSnapshot dss = task.getResult();
                    if(dss != null)
                    {
                        Boolean checkdarkmode = dss.getBoolean("nightMode");
                        if(checkdarkmode == Boolean.FALSE)
                        {
                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                        }
                        else
                        {
                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                        }
                    }
                    else
                    {
                        Toast.makeText(Homepage.this,"Field Not Exist", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }
}