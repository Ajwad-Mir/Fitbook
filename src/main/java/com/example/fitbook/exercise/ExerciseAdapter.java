package com.example.fitbook.exercise;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitbook.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;

public class ExerciseAdapter  extends FirestoreRecyclerAdapter<Exercise, ExerciseAdapter.ExerciseViewHolder> {

    public OnItemClickListener listener;
    public ExerciseAdapter(@NonNull FirestoreRecyclerOptions<Exercise> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ExerciseViewHolder exerciseViewHolder, int i, @NonNull Exercise exercise) {
        exerciseViewHolder.ExerciseName.setText(exercise.getname());
        exerciseViewHolder.ExerciseDate.setText(exercise.getdate());
        exerciseViewHolder.ExerciseCreator.setText(exercise.getcreatorname());
    }

    @NonNull
    @Override
    public ExerciseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.exercise_listitem,parent,false);
        return new ExerciseViewHolder(view);
    }

    public class ExerciseViewHolder extends RecyclerView.ViewHolder {
        TextView ExerciseID,ExerciseName,ExerciseDate,ExerciseCreator;
        public ExerciseViewHolder(@NonNull View itemView) {
            super(itemView);
            ExerciseName = itemView.findViewById(R.id.WorkoutName_Output);
            ExerciseDate = itemView.findViewById(R.id.WorkoutReps_Output);
            ExerciseCreator = itemView.findViewById(R.id.exercisecreator_Output);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION && listener != null)
                    {
                        listener.onItemClick(getSnapshots().getSnapshot(position),position);
                    }
                }
            });
        }
    }

    public interface OnItemClickListener
    {
        void onItemClick(DocumentSnapshot dss,int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener)
    {
        this.listener = listener;
    }
}
