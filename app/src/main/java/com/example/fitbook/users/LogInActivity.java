package com.example.fitbook.users;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.fitbook.Homepage;
import com.example.fitbook.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LogInActivity extends AppCompatActivity {

    private Button LogInButton;
    private EditText LogInEmailAddress, LogInPass;
    private CheckBox LogInRememberMe;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        LogInButton = findViewById(R.id.LogIn);
        LogInEmailAddress = findViewById(R.id.logInEmail);
        LogInPass = findViewById(R.id.LogInPass);
        LogInRememberMe = findViewById(R.id.LogInRememberMe);
        mAuth = FirebaseAuth.getInstance();
        LogInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String Email = LogInEmailAddress.getText().toString();
                final String Pass = LogInPass.getText().toString();
                mAuth.signInWithEmailAndPassword(Email,Pass)
                .addOnCompleteListener(LogInActivity.this, new OnCompleteListener<AuthResult>()
                {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("LogInActivity", "signInWithEmail:success");
                            if(LogInRememberMe.isChecked())
                            {
                                FirebaseUser user = mAuth.getCurrentUser();
                            }
                            Intent intent = new Intent(LogInActivity.this, Homepage.class);
                            startActivity(intent);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("LogInActivity", "signInWithEmail:failure", task.getException());
                            Toast.makeText(LogInActivity.this, "Your Email or Password is incorrect.",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
    }
}