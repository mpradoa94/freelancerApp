package com.example.mpa.freelancer;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

//Adapter for recycleView
public class EditSkillsAdapter extends RecyclerView.Adapter<EditSkillsAdapter.ViewHolder> {
    private ArrayList<String> items;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string
        public TextView textSkill;
        public ImageButton deleteSkill;


        public ViewHolder(View v) {
            super(v);
            textSkill = (TextView) v.findViewById(R.id.skill);
            deleteSkill = (ImageButton) v.findViewById(R.id.deleteBtn);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public EditSkillsAdapter(ArrayList<String> items) {
        this.items = items;
    }

    @Override
    public int getItemCount(){
        try {
            return items.size();
        }catch (Exception e){
            return 0;
        }

    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.edit_layout_skill, parent, false);
        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.textSkill.setText(items.get(position));
        holder.deleteSkill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                items.remove(position);
                notifyDataSetChanged();
            }
        });
    }
}
