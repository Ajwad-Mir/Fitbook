package com.example.fitbook.exercise;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;

import com.example.fitbook.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class ExerciseCreateNewListActivity extends AppCompatActivity {

    private static final String TAG = "ExerciseCreateNewList";
    private EditText ExerciseName,ExerciseID;
    private Button AddWorkout;
    private FirebaseFirestore mFirestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_create_new_list);
        ExerciseName = findViewById(R.id.AddExerciseName);
        ExerciseID = findViewById(R.id.AddExerciseID);
        AddWorkout = findViewById(R.id.AddWorkoutBtn);
        mFirestore = FirebaseFirestore.getInstance();

        AddWorkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date CurrentDate = Calendar.getInstance().getTime();
                String dateFormat = DateFormat.getDateInstance(DateFormat.DATE_FIELD).format(CurrentDate);
                String Name = ExerciseName.getText().toString();
                String ExID = ExerciseID.getText().toString();
               if(Name.length() <= 0)
               {
                   ExerciseName.setError("Exercise Name is Required");
                   ExerciseName.requestFocus();
               }
               else
               {
                   mFirestore.collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                           .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                       @Override
                       public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                          if(task.isSuccessful())
                          {
                              DocumentSnapshot dss = task.getResult();
                              if(dss.exists())
                              {
                                  String CreatorName = dss.getString("displayName");
                                  String CreatorUID = dss.getString("user_UID");

                                  Intent intent = new Intent(ExerciseCreateNewListActivity.this, ExerciseAddWorkoutActivity.class);
                                  intent.putExtra("EXID",ExID);
                                  intent.putExtra("EXNAME",Name);
                                  intent.putExtra("EXDATE",dateFormat);
                                  intent.putExtra("EXCREATORNAME",CreatorName);
                                  intent.putExtra("EXCREATORUID",CreatorUID);
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