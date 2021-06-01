package com.example.fitbook.workout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class WorkoutStartActivity extends AppCompatActivity {

    private TextView ExerciseName, ExerciseReps;
    private Button NextBtn,FinishBtn;
    private FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_start);
        ExerciseName = findViewById(R.id.ExerciseName_DisplayOutput);
        ExerciseReps = findViewById(R.id.ExerciseReps_DisplayOutput);
        NextBtn = findViewById(R.id.NextBtn);
        FinishBtn = findViewById(R.id.FinishBtn);


        String UID = getIntent().getExtras().getString("UID");
        String Key = getIntent().getExtras().getString("ID");

        StartExercise(UID,Key);
    }

    private void StartExercise(String uid, String key) {
        List<String> Output = new ArrayList<>();
        mFirestore.collection("users").document(uid)
                .collection("Workout").document(key)
                .collection("Exercise").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                int count = 0;
                int[] index = {0};
                QuerySnapshot querySnapshot = task.getResult();
                for(QueryDocumentSnapshot queryDocumentSnapshot: querySnapshot)
                {
                    Output.add(count,queryDocumentSnapshot.getId());
                    count++;
                }
                if(count == 0 || count == 1)
                {
                    NextBtn.setVisibility(View.INVISIBLE);
                    FinishBtn.setVisibility(View.VISIBLE);
                    mFirestore.collection("users").document(uid)
                            .collection("Workout").document(key)
                            .collection("Exercise").document(Output.get(index[0]))
                            .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull @NotNull Task<DocumentSnapshot> task) {
                            ExerciseName.setText(task.getResult().getString("exercisename"));
                            ExerciseReps.setText(String.valueOf(task.getResult().get("exercisereps")));
                        }
                    });
                    return;
                }
                else if (count > 1)
                {
                    mFirestore.collection("users").document(uid)
                            .collection("Workout").document(key)
                            .collection("Exercise").document(Output.get(index[0]))
                            .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull @NotNull Task<DocumentSnapshot> task) {
                            ExerciseName.setText(task.getResult().getString("exercisename"));
                            ExerciseReps.setText(String.valueOf(task.getResult().get("exercisereps")));
                        }
                    });

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
                                public void onComplete(@NonNull @NotNull Task<DocumentSnapshot> task) {
                                    ExerciseName.setText(task.getResult().getString("exercisename"));
                                    ExerciseName.setText(String.valueOf(task.getResult().get("exercisereps")));
                                }
                            });

                            index[0]++;
                            if(index[0] >= finalCount)
                            {
                                NextBtn.setVisibility(View.INVISIBLE);
                                FinishBtn.setVisibility(View.VISIBLE);
                                return;
                            }
                        }
                    });
                }
            }
        });
        FinishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WorkoutStartActivity.this, WorkoutFinishActivity.class);
                startActivity(intent);
            }
        });
    }
}