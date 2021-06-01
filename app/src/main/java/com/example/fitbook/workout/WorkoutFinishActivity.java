package com.example.fitbook.workout;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.fitbook.R;

public class WorkoutFinishActivity extends AppCompatActivity {

    private Button BackToMainBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_finish);
        BackToMainBtn = findViewById(R.id.GoBackBtn);

        BackToMainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WorkoutFinishActivity.this, WorkoutHomeListActivity.class);
                startActivity(intent);
            }
        });
    }
}