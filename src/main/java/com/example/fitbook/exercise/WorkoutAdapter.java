package com.example.fitbook.exercise;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitbook.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class WorkoutAdapter extends FirestoreRecyclerAdapter<Workout,WorkoutAdapter.WorkoutViewHolder> {
    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public WorkoutAdapter(@NonNull FirestoreRecyclerOptions<Workout> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull WorkoutAdapter.WorkoutViewHolder workoutViewHolder, int i, @NonNull Workout workout) {
        workoutViewHolder.Workout_Name.setText(workout.getworkoutname());
        workoutViewHolder.Workout_Reps.setText(Integer.toString(workout.getworkoutreps()));
    }

    @NonNull
    @Override
    public WorkoutAdapter.WorkoutViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.workout_listitem,parent,false);
        return new WorkoutViewHolder(v);
    }

    public class WorkoutViewHolder extends RecyclerView.ViewHolder {
        TextView Workout_Name, Workout_Reps;
        public WorkoutViewHolder(@NonNull View itemView) {
            super(itemView);
            Workout_Name = itemView.findViewById(R.id.WorkoutName_Output);
            Workout_Reps = itemView.findViewById(R.id.WorkoutReps_Output);
        }
    }
}
