package com.example.fitbook.workout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.fitbook.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

public class WorkoutCreateNewExerciseListActivity extends AppCompatActivity {

    private EditText WorkoutName, WorkoutID;
    private FirebaseFirestore mFirestore;
    private Button addExercisesBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_create_new_exercise_list);
        WorkoutID = findViewById(R.id.WorkoutID_CreateInput);
        WorkoutName = findViewById(R.id.WorkoutName_CreateInput);
        addExercisesBtn = findViewById(R.id.AddExercisesBtn);
        mFirestore =  FirebaseFirestore.getInstance();

        addExercisesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date CurrentDate = Calendar.getInstance().getTime();
                String dateFormat = DateFormat.getDateInstance(DateFormat.DATE_FIELD).format(CurrentDate);

                String Name = WorkoutName.getText().toString();
                String ID = WorkoutID.getText().toString();

                if(Name.length() <= 0)
                {
                    WorkoutName.setError("Workout Name is Required");
                    WorkoutName.requestFocus();
                }
                else if(ID.length() <= 0)
                {
                    WorkoutID.setError("Workout ID is Required");
                    WorkoutID.requestFocus();
                }
                else
                {
                    mFirestore.collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull @NotNull Task<DocumentSnapshot> task) {
                            if(task.isSuccessful())
                            {
                                DocumentSnapshot dss = task.getResult();
                                if(dss.exists())
                                {
                                    String CreatorName = dss.getString("displayName");
                                    String CreatorUID = dss.getString("user_UID");

                                    Intent intent = new Intent(WorkoutCreateNewExerciseListActivity.this, WorkoutAddExerciseActivity.class);
                                    intent.putExtra("WXID",ID);
                                    intent.putExtra("WXNAME",Name);
                                    intent.putExtra("WXDATE",dateFormat);
                                    intent.putExtra("WXCREATORNAME",CreatorName);
                                    intent.putExtra("WXCREATORUID",CreatorUID);
                                    startActivity(intent);
                                }
                            }
                        }
                    });
                }
            }
        });
    }
}