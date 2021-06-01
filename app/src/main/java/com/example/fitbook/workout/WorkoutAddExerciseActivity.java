package com.example.fitbook.workout;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.fitbook.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class WorkoutAddExerciseActivity extends AppCompatActivity {

    private EditText ExerciseName, ExerciseReps;
    private Button AddNewExercise,SaveWorkoutList;
    private FirebaseFirestore mFirestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_add_exercise);

        ExerciseName = findViewById(R.id.ExerciseName_CreateInput);
        ExerciseReps = findViewById(R.id.ExerciseReps_CreateInput);
        AddNewExercise = findViewById(R.id.AddAnotherExerciseBtn);
        SaveWorkoutList = findViewById(R.id.SaveThisWorkoutBtn);
        mFirestore = FirebaseFirestore.getInstance();

        AddNewExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String WxID = getIntent().getExtras().getString("WXID");
                String Name = getIntent().getExtras().getString("WXNAME");
                String dateFormat = getIntent().getExtras().getString("WXDATE");
                String CreatorName = getIntent().getExtras().getString("WXCREATORNAME");
                String CreatorUID = getIntent().getExtras().getString("WXCREATORUID");

                String Exercise_Name = ExerciseName.getText().toString();
                int Exercise_Reps = Integer.parseInt(ExerciseReps.getText().toString());

                Workout WKL = new Workout(WxID,Name,dateFormat,CreatorName,CreatorUID);
                mFirestore.collection("users").document(CreatorUID)
                        .collection("Workout").document(WxID).set(WKL).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                    }
                });
                Exercise EXL = new Exercise(WxID,Exercise_Name,Exercise_Reps);
                mFirestore.collection("users").document(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()). getUid())
                .collection("Workout").document(WxID)
                .collection("Exercise").add(EXL)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(WorkoutAddExerciseActivity.this,"Exercise Added Successfully",Toast.LENGTH_LONG).show();
                    }
                });

                ExerciseName.setText("");
                ExerciseReps.setText("");
            }
        });
        SaveWorkoutList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(WorkoutAddExerciseActivity.this, "Exercise List Saved Successfully", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(WorkoutAddExerciseActivity.this, WorkoutHomeListActivity.class);
                startActivity(intent);
            }
        });
    }
}