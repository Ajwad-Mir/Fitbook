package com.example.fitbook.exercise;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.fitbook.R;

public class ExerciseFinishActivity extends AppCompatActivity {

    private Button BackToMainBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_finish);

        BackToMainBtn = findViewById(R.id.GoBackBtn);

        BackToMainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ExerciseFinishActivity.this, ExerciseHomeListActivity.class);
                startActivity(intent);
            }
        });
    }
}