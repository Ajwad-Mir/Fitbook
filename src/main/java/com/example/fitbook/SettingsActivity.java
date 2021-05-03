package com.example.fitbook;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreference;

import com.bumptech.glide.Glide;
import com.example.fitbook.shoes.ShoesAddListActivity;
import com.example.fitbook.shoes.ShoesHomeListActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Objects;

public class SettingsActivity extends AppCompatActivity {

    private static Context mContext;

    private FirebaseAuth mAuth;
    private FirebaseFirestore firestore;
    private StorageReference mStorageReference;

    private ImageView SettingsUserProfilePic;
    private TextView SettingsUserDisplayName,SettingsUserRealName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        mContext = this;

        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        mStorageReference = FirebaseStorage.getInstance().getReference();

        SettingsUserDisplayName = findViewById(R.id.SettingsDisplayName);
        SettingsUserProfilePic = findViewById(R.id.SettingsProfilePic);
        SettingsUserRealName = findViewById(R.id.SettingsRealName);

        firestore.collection("users").document(mAuth.getCurrentUser().getUid())
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String DisplayName = documentSnapshot.get("displayName").toString();
                String FullName = documentSnapshot.get("firstName").toString() + " " + documentSnapshot.get("lastName").toString();
                SettingsUserDisplayName.setText(DisplayName);
                SettingsUserRealName.setText(FullName);
                mStorageReference.child("Users").child(mAuth.getCurrentUser().getUid() + ".jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Glide.with(SettingsActivity.this)
                                .load(uri)
                                .centerCrop()
                                .placeholder(R.drawable.defavatar)
                                .into(SettingsUserProfilePic);
                    }
                });
            }
        });
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.settings, new SettingsFragment())
                    .commit();
        }
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent myIntent = new Intent(getApplicationContext(), Homepage.class);
        startActivityForResult(myIntent, 0);
        return true;
    }

    public static class SettingsFragment extends PreferenceFragmentCompat {
        private FirebaseAuth mAuth;
        private FirebaseFirestore firestore;
        private Preference Logout,ViewShoes,AddShoes;
        private SwitchPreference DarkMode;

        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.settings_preferences, rootKey);
            mAuth = FirebaseAuth.getInstance();
            firestore = FirebaseFirestore.getInstance();

            Logout = findPreference("key_logout");
            DarkMode = findPreference("key_darkmode");
            ViewShoes = findPreference("key_view_shoecollection");
            AddShoes = findPreference("key_add_shoecollection");


            ViewShoes.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    Intent intent = new Intent(mContext, ShoesHomeListActivity.class);
                    startActivity(intent);
                    return true;
                }
            });
            AddShoes.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    Intent intent = new Intent(mContext, ShoesAddListActivity.class);
                    startActivity(intent);
                    return true;
                }
            });
            DarkMode.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    final Boolean isnightmode = (Boolean) newValue;
                    if(!isnightmode)
                    {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                        firestore.collection("users").document(Objects.requireNonNull(mAuth.getCurrentUser()).getUid()).update("nightMode", false);
                    }
                    else
                    {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                        firestore.collection("users").document(Objects.requireNonNull(mAuth.getCurrentUser()).getUid()).update("nightMode",true);
                    }
                    return true;
                }
            });
            Logout.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    FirebaseAuth.getInstance().signOut();
                    Intent intent = new Intent(mContext,MainActivity.class);
                    startActivity(intent);
                    Toast.makeText(mContext, "Logged Out", Toast.LENGTH_LONG).show();
                    return true;
                }
            });

        }
    }

}