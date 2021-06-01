package com.example.fitbook.workout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitbook.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import org.jetbrains.annotations.NotNull;

public class ExerciseAdapter extends FirestoreRecyclerAdapter<Exercise,ExerciseAdapter.ExerciseViewHolder> {

    public ExerciseAdapter(@NonNull @NotNull FirestoreRecyclerOptions<Exercise> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull @NotNull ExerciseAdapter.ExerciseViewHolder exerciseViewHolder, int i, @NonNull @NotNull Exercise exercise) {
        exerciseViewHolder.Exercise_Name.setText(exercise.getexercisename());
        exerciseViewHolder.Exercise_Reps.setText(String.valueOf(exercise.getexercisereps()));
    }

    @NonNull
    @Override
    public ExerciseAdapter.ExerciseViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.exercise_listitem,parent,false);
        return new ExerciseViewHolder(v);
    }

    public static class ExerciseViewHolder extends RecyclerView.ViewHolder {
        public TextView Exercise_Name, Exercise_Reps;
        public ExerciseViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            Exercise_Name = itemView.findViewById(R.id.ExerciseName_Output);
            Exercise_Reps = itemView.findViewById(R.id.ExerciseReps_Output);
        }
    }
}
