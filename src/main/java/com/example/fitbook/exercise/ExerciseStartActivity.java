package com.example.fitbook.exercise;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.fitbook.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ExerciseStartActivity extends AppCompatActivity {

    private static final String TAG = "ExerciseStartActivity";
    private TextView Workoutname, WorkoutReps;
    private Button NextBtn,FinishBtn;
    private FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_start);
        Workoutname = findViewById(R.id.WorkoutName_OutputText);
        WorkoutReps = findViewById(R.id.WorkoutReps_OutputText);
        NextBtn = findViewById(R.id.NextBtn);
        FinishBtn = findViewById(R.id.FinishBtn);

        String UID = getIntent().getExtras().getString("UID");
        String Key = getIntent().getExtras().getString("ID");

        Work(UID,Key);
    }

    private void Work(String uid, String key) {
        View Newview = null;
        List<String> Output = new ArrayList<>();
        mFirestore.collection("users").document(uid)
                .collection("Exercise").document(key)
                .collection("Workout").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                int count = 0;
                QuerySnapshot querySnapshot = task.getResult();
                for(QueryDocumentSnapshot queryDocumentSnapshot: querySnapshot)
                {
                    Output.add(count,queryDocumentSnapshot.getId());
                    Log.v(TAG,"Data Added = " + Output.get(count));
                    count++;
                }
                int[] index = {0};
                mFirestore.collection("users").document(uid)
                        .collection("Exercise").document(key)
                        .collection("Workout").document(Output.get(index[0]))
                        .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        Workoutname.setText(task.getResult().getString("workoutname"));
                        WorkoutReps.setText(String.valueOf(task.getResult().get("workoutreps")));
                    }
                });

                //Button Presses And Changes Data Accordingly
                int finalCount = count;
                index[0]++;
                NextBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mFirestore.collection("users").document(uid)
                                .collection("Exercise").document(key)
                                .collection("Workout").document(Output.get(index[0]))
                                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                Workoutname.setText(task.getResult().getString("workoutname"));
                                WorkoutReps.setText(String.valueOf(task.getResult().get("workoutreps")));
                            }
                        });
                        index[0]++;
                        if(index[0] == finalCount)
                        {
                            NextBtn.setVisibility(View.INVISIBLE);
                            FinishBtn.setVisibility(View.VISIBLE);
                            return;
                        }
                    }
                });
            }
        });

        FinishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ExerciseStartActivity.this, ExerciseFinishActivity.class);
                startActivity(intent);
            }
        });
    }
}