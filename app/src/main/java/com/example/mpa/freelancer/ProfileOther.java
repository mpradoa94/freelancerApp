package com.example.mpa.freelancer;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
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
    ArrayList<String> reviews;
    List<ParseUser> users;

    String User;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentActivity faActivity = (FragmentActivity) super.getActivity();
        RelativeLayout rLayout = (RelativeLayout) inflater.inflate(R.layout.activity_profile_other, container, false);

        recyclerViewSkills = (RecyclerView) rLayout.findViewById(R.id.list_skills);
        recyclerViewReviews = (RecyclerView) rLayout.findViewById(R.id.list_reviews);

        mLayoutManager = new LinearLayoutManager(super.getActivity());
        mLayoutManager2 = new LinearLayoutManager(super.getActivity());

        recyclerViewSkills.setLayoutManager(mLayoutManager);
        recyclerViewReviews.setLayoutManager(mLayoutManager2);

        name = (TextView) rLayout.findViewById(R.id.header_name);
        occupation = (TextView) rLayout.findViewById(R.id.header_occupation);

        //Make the profile image round
        ImageView profileImg = (ImageView)rLayout.findViewById(R.id.profile_image);
        //get bitmap of the image
        Bitmap imageBitmap = BitmapFactory.decodeResource(getResources(),  R.drawable.default_user);
        RoundedBitmapDrawable roundedBitmapDrawable= RoundedBitmapDrawableFactory.create(getResources(), imageBitmap);
        //roundedBitmapDrawable.setCornerRadius(100.0f);
        roundedBitmapDrawable.setCircular(true);
        roundedBitmapDrawable.setAntiAlias(true);
        profileImg.setImageDrawable(roundedBitmapDrawable);

        //Getting user email from the list
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

        if(users != null){
            Log.e("User", users.get(0).toString());
            String namee = users.get(0).getUsername().toString();
            name.setText(namee);
            Object oc = users.get(0).get("ocupation");
            Object sk = users.get(0).get("skills");
            Object rw = users.get(0).get("reviews");
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
            if(rw != null){
                reviews = (ArrayList<String>) rw;
            }
            else {
                ArrayList<String> list = new ArrayList<>();
                list.add("No reviews yet");
                reviews = list;
            }

            mAdapter = new SkillsAdapter(skills);
            mAdapter2 = new ReviewsAdapter(reviews);

            recyclerViewSkills.setAdapter(mAdapter);
            recyclerViewReviews.setAdapter(mAdapter2);

        }

        return rLayout;
    }
}
