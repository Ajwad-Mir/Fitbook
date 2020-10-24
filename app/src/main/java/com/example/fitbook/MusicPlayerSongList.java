package com.example.fitbook;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.File;
import java.util.ArrayList;

public class MusicPlayerSongList extends AppCompatActivity {
    private ListView listView;
    private String[] items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player_song_list);
        listView = (ListView) findViewById(R.id.listView);
        Dexter.withContext(this)
                .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        display();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        // check for permanent denial of permission
                        if (response.isPermanentlyDenied()) {

                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();


    }

    public ArrayList<File> findSong(File root) {
        ArrayList<File> SongList = new ArrayList<>();
        File[] files = root.listFiles();
        for (File singleFile : files) {
            if (singleFile.isDirectory() && !singleFile.getName().equalsIgnoreCase("Android")
                    && !singleFile.getName().equalsIgnoreCase("LOST.DIR") && !singleFile.isHidden())
            {
                SongList.addAll(findSong(singleFile));
            }
            else
            {
                if (singleFile.getName().endsWith(".mp3") ||singleFile.getName().endsWith(".mp3"))
                {
                    SongList.add(singleFile);
                }
            }
        }
        return SongList;
    }

    void display() {
        final ArrayList<File> mySongs = findSong(Environment.getExternalStorageDirectory());
        items = new String[mySongs.size()];
        for (int i = 0; i < mySongs.size(); i++) {
            items[i] = mySongs.get(i).getName().replace(".mp3","")
                    .replace(".wav","").replace("_", " ");
        }
        ArrayAdapter<String> adp = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        listView.setAdapter(adp);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int
                    position, long l) {

                String songName = listView.getItemAtPosition(position).toString();
                Intent intent = new Intent(MusicPlayerSongList.this, MusicPlayerMainPlayerActivity.class)
                .putExtra("pos", position).putExtra("songs", mySongs).putExtra("songname", songName);
                startActivity(intent);
            }
        });
    }
}
