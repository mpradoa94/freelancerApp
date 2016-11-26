package com.example.mpa.freelancer;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

//Adapter for recycleView
public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ViewHolder> {
    private ArrayList<String> items;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item has an image, the user name and occupation
        public TextView textReview;

        public Bitmap imageBitmap;
        public RoundedBitmapDrawable roundedBitmapDrawable;

        public ViewHolder(View v) {
            super(v);
            textReview = (TextView) v.findViewById(R.id.review_text);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ReviewsAdapter(ArrayList<String> items) {
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
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_review, parent, false);
        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        //Make the profile image round
        holder.textReview.setText(items.get(position));
    }
}
