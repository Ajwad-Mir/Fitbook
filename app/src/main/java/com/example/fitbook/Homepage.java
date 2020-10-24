package com.example.fitbook;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.net.sip.SipSession;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.zip.Inflater;

public class Homepage extends AppCompatActivity {

    private Button AboutUsBtn, MusicPlayerBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        AboutUsBtn = findViewById(R.id.AboutUsBtn);
        MusicPlayerBtn = findViewById(R.id.MusicPlayerBtn);
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
            case R.id.Home_SignOut:
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(Homepage.this, MainActivity.class);
                startActivity(intent);
                finish();
                Toast.makeText(Homepage.this, "Logged Out", Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}