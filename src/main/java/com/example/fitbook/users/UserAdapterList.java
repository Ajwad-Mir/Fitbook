package com.example.fitbook.users;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitbook.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class UserAdapterList extends FirestoreRecyclerAdapter<Users, UserAdapterList.UsersViewHolder> {

    public UserAdapterList(@NonNull FirestoreRecyclerOptions<Users> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull UsersViewHolder usersViewHolder, int i, @NonNull Users users) {
        usersViewHolder.UserDisplayName_View.setText(users.getDisplayName());
        usersViewHolder.UserFirst_View.setText(users.getFirstName());
        usersViewHolder.UserLast_View.setText(users.getLastName());
    }

    @NonNull
    @Override
    public UsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.users_itemlist,parent,false);
        return new UsersViewHolder(view);
    }


    public static class UsersViewHolder extends RecyclerView.ViewHolder {
        TextView UserDisplayName_View,UserFirst_View,UserLast_View;
        public UsersViewHolder(@NonNull View itemView) {
            super(itemView);
            UserDisplayName_View = itemView.findViewById(R.id.WorkoutName_Output);
            UserFirst_View = itemView.findViewById(R.id.ExerciseDateCreated_Output);
            UserLast_View = itemView.findViewById(R.id.ExerciseCreatedBy_Output);
        }
    }
}
