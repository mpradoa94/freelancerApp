package com.example.mpa.freelancer;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProfileOther extends Fragment {
    private RecyclerView recyclerViewSkills;
    private RecyclerView recyclerViewReviews;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter2;
    private RecyclerView.LayoutManager mLayoutManager2;

    TextView name;
    TextView occupation;
    ArrayList<String> skills;

    List<ParseObject> reviews;
    List<ParseUser> users;

    Button addReview;
    int ratingAvg;

    String User;

    RatingBar ratings;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentActivity faActivity = (FragmentActivity) super.getActivity();
        RelativeLayout rLayout = (RelativeLayout) inflater.inflate(R.layout.activity_profile_other, container, false);

        try {
            InputMethodManager input = (InputMethodManager) getActivity()
                    .getSystemService(Activity.INPUT_METHOD_SERVICE);
            input.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
        }catch(Exception e) {
            e.printStackTrace();
        }

        ratingAvg = 0;

        recyclerViewSkills = (RecyclerView) rLayout.findViewById(R.id.list_skills);
        recyclerViewReviews = (RecyclerView) rLayout.findViewById(R.id.list_reviews);

        mLayoutManager = new LinearLayoutManager(super.getActivity());
        mLayoutManager2 = new LinearLayoutManager(super.getActivity());

        recyclerViewSkills.setLayoutManager(mLayoutManager);
        recyclerViewReviews.setLayoutManager(mLayoutManager2);

        name = (TextView) rLayout.findViewById(R.id.header_name);
        occupation = (TextView) rLayout.findViewById(R.id.header_occupation);

        addReview = (Button) rLayout.findViewById(R.id.addReviewbtn);

        ratings = (RatingBar) rLayout.findViewById(R.id.header_rating_bar);

        //Make the profile image round
        ImageView profileImg = (ImageView)rLayout.findViewById(R.id.profile_image);
        //get bitmap of the image
        Bitmap imageBitmap = BitmapFactory.decodeResource(getResources(),  R.drawable.default_user);
        RoundedBitmapDrawable roundedBitmapDrawable= RoundedBitmapDrawableFactory.create(getResources(), imageBitmap);
        //roundedBitmapDrawable.setCornerRadius(100.0f);
        roundedBitmapDrawable.setCircular(true);
        roundedBitmapDrawable.setAntiAlias(true);
        profileImg.setImageDrawable(roundedBitmapDrawable);

        //Getting user id from the list
        Bundle bundle = getArguments();
        User = bundle.getString("Id");

        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereEqualTo("objectId", User);
        //query.selectKeys(Arrays.asList("username", "ocupation", "skills", "reviews"));
        try {
            users = query.find();
            Log.e("User", users.get(0).toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        ParseQuery<ParseObject> query2 = ParseQuery.getQuery("Reviews");
        query2.whereEqualTo("UserId", User);
        try {
            reviews = query2.find();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if(users != null){
            Log.e("User", users.get(0).toString());
            String namee = users.get(0).getUsername().toString();
            name.setText(namee);
            Object oc = users.get(0).get("ocupation");
            Object sk = users.get(0).get("skills");


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
            if(reviews != null) {
                mAdapter2 = new ReviewsAdapter(reviews);
                for (int i = 0; i < reviews.size(); i++){
                    ratingAvg += (int) reviews.get(i).get("Rating");
                }
                ratingAvg = Math.round(ratingAvg/reviews.size());
                ratings.setRating(ratingAvg);
            }

            mAdapter = new SkillsAdapter(skills);

            recyclerViewSkills.setAdapter(mAdapter);
            recyclerViewReviews.setAdapter(mAdapter2);

        }

        addReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle user = new Bundle();
                user.putString("Id", User);

                Fragment reviewFragment = new AddReview();
                reviewFragment.setArguments(user);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.contentMenu, reviewFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        return rLayout;
    }

}
