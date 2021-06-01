package com.example.fitbook.users;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.fitbook.Homepage;
import com.example.fitbook.R;
import com.example.fitbook.leaderboard.Leaderboard;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.jetbrains.annotations.NotNull;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = "RegisterActivity";
    private final int PICK_IMAGE = 10000;
    private Button RegisterBtn;
    private EditText RegisterEmail, RegisterPassword, RegisterFirstName,RegisterLastName,RegisterDisplayName;
    private ImageView RegisterProfilePic;
    private Uri Image_uri;
    private FirebaseAuth mAuth;
    private StorageReference mStorageReference;
    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        RegisterBtn = findViewById(R.id.Register);
        RegisterFirstName = findViewById(R.id.RegisterFirstname);
        RegisterLastName = findViewById(R.id.RegisterLastname);
        RegisterDisplayName = findViewById(R.id.RegisterDisplayname);
        RegisterEmail = findViewById(R.id.RegisterEmail);
        RegisterPassword = findViewById(R.id.RegisterPassword);
        RegisterProfilePic = findViewById(R.id.RegisterProfilePic);

        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        mStorageReference = FirebaseStorage.getInstance().getReference();

        RegisterProfilePic.setOnClickListener(v -> SelectImage());

        RegisterBtn.setOnClickListener(v -> {
            final String Display_Name = RegisterDisplayName.getText().toString();
            final String First_Name = RegisterFirstName.getText().toString();
            final String Last_Name = RegisterLastName.getText().toString();
            final String Email_Address = RegisterEmail.getText().toString();
            final String Password = RegisterPassword.getText().toString();

            if(TextUtils.isEmpty(Email_Address))
            {
                RegisterEmail.setError("Email is required");
                RegisterEmail.requestFocus();
            }

            if(TextUtils.isEmpty(Display_Name))
            {
                RegisterDisplayName.setError("Display Name is required");
                RegisterDisplayName.requestFocus();
            }

            if(TextUtils.isEmpty(First_Name))
            {
                RegisterFirstName.setError("First Name is required");
                RegisterFirstName.requestFocus();
            }

            if(TextUtils.isEmpty(Last_Name))
            {
                RegisterLastName.setError("Last Name is required");
                RegisterLastName.requestFocus();
            }

            if(TextUtils.isEmpty(Password))
            {
                RegisterPassword.setError("Password is required");
                RegisterPassword.requestFocus();
            }

            mAuth.createUserWithEmailAndPassword(Email_Address,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful())
                    {
                        Users user = new Users(Display_Name,First_Name,Last_Name,Email_Address,
                                Image_uri.toString(),mAuth.getCurrentUser().getUid(),false,0.0f);
                        UploadImage();

                        firestore.collection("users")
                        .document(mAuth.getCurrentUser().getUid())
                        .set(user)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Leaderboard LDR = new Leaderboard(Display_Name,0.0f);
                                firestore.collection("Leaderboard")
                                .document(mAuth.getCurrentUser().getUid())
                                .set(LDR).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(RegisterActivity.this,"Account Created In Firestore", Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(RegisterActivity.this, Homepage.class);
                                        startActivity(intent);
                                    }
                                });
                            }
                        });

                    }
                    else
                    {
                        Log.e(TAG,"User Profile Failed to Create",task.getException());
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    Log.e(TAG,"User Profile Failed to Initialize",e);
                }
            });
        });
    }

    private void SelectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Image from here..."), PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            // Get the Uri of data
            Image_uri = data.getData();
            Glide.with(RegisterActivity.this)
                    .load(Image_uri)
                    .centerCrop()
                    .placeholder(R.drawable.defavatar)
                    .into(RegisterProfilePic);
        }
    }

    private void UploadImage()
    {
        if(Image_uri != null)
        {
            StorageReference ref = mStorageReference.child("Users").child(mAuth.getCurrentUser().getUid() + ".jpg");
            ref.putFile(Image_uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(RegisterActivity.this, "Image Uploaded!!", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(RegisterActivity.this, "Image Failed Uploaded!!", Toast.LENGTH_LONG).show();
                }
            });
        }
        else
        {
            Image_uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.drawable.defavatar);
            StorageReference ref = mStorageReference.child("Users").child(mAuth.getCurrentUser().getUid() + ".jpg");
            ref.putFile(Image_uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(RegisterActivity.this, "Image Uploaded!!", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull @NotNull Exception e) {
                    Toast.makeText(RegisterActivity.this, "Image Failed Uploaded!!", Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}