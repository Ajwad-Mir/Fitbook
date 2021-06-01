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
import com.google.firebase.firestore.DocumentSnapshot;

import org.jetbrains.annotations.NotNull;

public class WorkoutAdapter extends FirestoreRecyclerAdapter<Workout, WorkoutAdapter.WorkoutViewHolder> {
    private OnItemClickListener listener;
    public WorkoutAdapter(@NonNull @NotNull FirestoreRecyclerOptions<Workout> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull @NotNull WorkoutAdapter.WorkoutViewHolder workoutViewHolder, int i, @NonNull @NotNull Workout workout) {
        workoutViewHolder.WorkoutName.setText(workout.getname());
        workoutViewHolder.WorkoutDateCreated.setText(workout.getdate());
        workoutViewHolder.WorkoutCreatedBy.setText(workout.getcreatorname());
    }

    @NonNull
    @NotNull
    @Override
    public WorkoutAdapter.WorkoutViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.workout_listitem,parent,false);
        return new WorkoutAdapter.WorkoutViewHolder(v);
    }

    public void delete(int adapterPosition) {
        getSnapshots().getSnapshot(adapterPosition).getReference().collection("Exercise").document().delete();
        getSnapshots().getSnapshot(adapterPosition).getReference().delete();
    }

    public class WorkoutViewHolder extends RecyclerView.ViewHolder {
        public TextView WorkoutName, WorkoutDateCreated, WorkoutCreatedBy;
        public WorkoutViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            WorkoutName = itemView.findViewById(R.id.UserName_Output);
            WorkoutDateCreated = itemView.findViewById(R.id.TotalDistance_Output);
            WorkoutCreatedBy = itemView.findViewById(R.id.WorkoutCreator_Output);

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
        void onItemClick(DocumentSnapshot dss, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener)
    {
        this.listener = listener;
    }
}
