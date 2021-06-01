package com.example.fitbook.shoes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitbook.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class ShoesAdapter extends FirestoreRecyclerAdapter<Shoes, ShoesAdapter.ShoesViewHolder> {
    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public ShoesAdapter(@NonNull FirestoreRecyclerOptions<Shoes> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ShoesAdapter.ShoesViewHolder shoesViewHolder, int i, @NonNull Shoes shoes) {
        shoesViewHolder.Name.setText(shoes.getshoe_name());
        shoesViewHolder.Brand.setText(shoes.getshoe_brand());
        shoesViewHolder.Distance.setText(String.valueOf(shoes.getshoe_distance()));
    }

    @NonNull
    @Override
    public ShoesAdapter.ShoesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.shoes_listitem, parent, false);
        return new ShoesViewHolder(v);
    }

    public class ShoesViewHolder extends RecyclerView.ViewHolder {
        public TextView Name,Brand,Distance;
        public ShoesViewHolder(@NonNull View itemView) {
            super(itemView);
            Name = itemView.findViewById(R.id.shoesName_Output);
            Brand = itemView.findViewById(R.id.shoesBrand_Output);
            Distance = itemView.findViewById(R.id.shoesDistance_Output);
        }
    }
}
