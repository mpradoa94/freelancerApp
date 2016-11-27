package com.example.mpa.freelancer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class Profile extends Fragment {
    private RecyclerView recyclerViewSkills;
    private RecyclerView recyclerViewReviews;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter2;
    private RecyclerView.LayoutManager mLayoutManager2;

    List<ParseObject> reviews;

    TextView name;
    TextView occupation;

    ArrayList<String> skills;

    RatingBar ratings;
    int ratingAvg;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentActivity faActivity = (FragmentActivity) super.getActivity();
        RelativeLayout rLayout = (RelativeLayout) inflater.inflate(R.layout.activity_profile, container, false);

        recyclerViewSkills = (RecyclerView) rLayout.findViewById(R.id.list_skills);
        recyclerViewReviews = (RecyclerView) rLayout.findViewById(R.id.list_reviews);

        mLayoutManager = new LinearLayoutManager(super.getActivity());
        mLayoutManager2 = new LinearLayoutManager(super.getActivity());

        recyclerViewSkills.setLayoutManager(mLayoutManager);
        recyclerViewReviews.setLayoutManager(mLayoutManager2);

        name = (TextView) rLayout.findViewById(R.id.header_name);
        occupation = (TextView) rLayout.findViewById(R.id.header_occupation);

        ratings = (RatingBar) rLayout.findViewById(R.id.header_rating_bar);

        ratingAvg = 0;

        //Make the profile image round
        ImageView profileImg = (ImageView)rLayout.findViewById(R.id.profile_image);
        //get bitmap of the image
        Bitmap imageBitmap = BitmapFactory.decodeResource(getResources(),  R.drawable.default_user);
        RoundedBitmapDrawable roundedBitmapDrawable= RoundedBitmapDrawableFactory.create(getResources(), imageBitmap);
        //roundedBitmapDrawable.setCornerRadius(100.0f);
        roundedBitmapDrawable.setCircular(true);
        roundedBitmapDrawable.setAntiAlias(true);
        profileImg.setImageDrawable(roundedBitmapDrawable);


        ParseUser currentUser = ParseUser.getCurrentUser();
        if(currentUser != null){
            String namee = currentUser.getUsername();
            name.setText(namee);
            Object oc = currentUser.get("ocupation");
            Object sk = currentUser.get("skills");

            if(oc != null) {
                String ocupationn = oc.toString();
                occupation.setText(ocupationn);
            }
            else {
                occupation.setText("");
            }
            if(sk != null){
                skills = (ArrayList<String>)sk;
            }
            else {
                ArrayList<String> list = new ArrayList<>();
                list.add("No skills");
                skills = list;
            }

            ParseQuery<ParseObject> query = ParseQuery.getQuery("Reviews");
            query.whereEqualTo("UserId", currentUser);
            try {
                reviews = query.find();
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if(reviews != null){
                mAdapter2 = new ReviewsAdapter(reviews);
                if(reviews.size() > 0) {
                    for (int i = 0; i < reviews.size(); i++) {
                        ratingAvg += (int) reviews.get(i).get("Rating");
                    }
                    ratingAvg = Math.round(ratingAvg / reviews.size());
                }
            }

            ratings.setRating(ratingAvg);
            mAdapter = new SkillsAdapter(skills);

            recyclerViewSkills.setAdapter(mAdapter);
            recyclerViewReviews.setAdapter(mAdapter2);

            mAdapter = new SkillsAdapter(skills);
            recyclerViewSkills.setAdapter(mAdapter);

            CardView layout = (CardView)rLayout.findViewById(R.id.top_container);

        }

        return rLayout;
    }
}
