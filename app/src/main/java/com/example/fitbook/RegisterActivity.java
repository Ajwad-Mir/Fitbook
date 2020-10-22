package com.example.fitbook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = "RegisterActivity";
    private Button RegisterBtn;
    private EditText RegisterEmail, RegisterPassword, RegisterPassConfirm;

    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        RegisterBtn = findViewById(R.id.Register);
        RegisterEmail = findViewById(R.id.RegisterEmail);
        RegisterPassword = findViewById(R.id.RegisterPassword);
        mAuth = FirebaseAuth.getInstance();
        RegisterBtn.setOnClickListener(v -> {
            final String Email = RegisterEmail.getText().toString();
            final String Password = RegisterPassword.getText().toString();
            ValidateForm(Email,Password);
        });
    }

    private void ValidateForm(String Email, String Password)
    {
        if(Email.isEmpty())
        {
            if(Password.isEmpty())
            {
                RegisterPassword.setError("Enter Password");
                RegisterPassword.requestFocus();
            }
            RegisterEmail.setError("Missing Email");
            RegisterEmail.requestFocus();
        }
        else
        {
            if(Password.isEmpty())
            {
                RegisterPassword.setError("Enter Password");
                RegisterPassword.requestFocus();
            }
            else
            {
                mAuth.createUserWithEmailAndPassword(Email,Password)
                .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>()
                {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent intent = new Intent(RegisterActivity.this, Homepage.class);
                            startActivity(intent);
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }
    }
}