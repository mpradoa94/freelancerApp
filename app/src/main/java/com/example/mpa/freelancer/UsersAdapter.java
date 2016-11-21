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

import java.util.List;

//Adapter for recycleView
public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.ViewHolder> {
    private List<ParseUser> items;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item has an image, the user name and occupation
        public TextView textName;
        public TextView textOcupation;
        public ImageView profileImg;

        public Bitmap imageBitmap;
        public RoundedBitmapDrawable roundedBitmapDrawable;

        public ViewHolder(View v) {
            super(v);
            textName = (TextView) v.findViewById(R.id.name);
            textOcupation = (TextView) v.findViewById(R.id.occupation);
            profileImg = (ImageView) v.findViewById(R.id.image);

            //get bitmap of the image to make rounded image
            imageBitmap = BitmapFactory.decodeResource(v.getResources(),  R.drawable.default_user);
            roundedBitmapDrawable= RoundedBitmapDrawableFactory.create(v.getResources(), imageBitmap);
            //roundedBitmapDrawable.setCornerRadius(100.0f);
            roundedBitmapDrawable.setCircular(true);
            roundedBitmapDrawable.setAntiAlias(true);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public UsersAdapter(List<ParseUser> items) {
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
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_user, parent, false);
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
        holder.profileImg.setImageDrawable(holder.roundedBitmapDrawable);
        holder.textName.setText("Name: "+ items.get(position).getUsername());
        holder.textOcupation.setText("Occupation: " + items.get(position).get("ocupation"));
    }
}
