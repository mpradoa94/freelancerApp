package com.example.mpa.freelancer;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.parse.ParseObject;
import com.parse.ParseUser;

public class AddReview extends Fragment {

    ParseUser currentUser;
    String User;

    EditText review;
    RatingBar rating;

    float numStars;

    Button cancel;
    Button save;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final FragmentActivity faActivity = (FragmentActivity) super.getActivity();
        RelativeLayout rLayout = (RelativeLayout) inflater.inflate(R.layout.activity_add_review, container, false);

        //Getting user id from previous fragment
        Bundle bundle = getArguments();
        User = bundle.getString("Id");

        //Getting the xml elements
        rating = (RatingBar) rLayout.findViewById(R.id.ratingBar);
        review = (EditText) rLayout.findViewById(R.id.text_review);

        save = (Button) rLayout.findViewById(R.id.btnSave);
        cancel = (Button) rLayout.findViewById(R.id.btnCancel);
        currentUser = ParseUser.getCurrentUser();

        rating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar ratingBar, float rate,
                                        boolean fromUser) {
                numStars = rate;
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Only save the review if there is something on the edit text of review
                if (review.getText().length() > 0) {
                    //Create new review object
                    ParseObject reviewObj = new ParseObject("Reviews");
                    //Add the id of the user that is being reviewed to the new review object
                    reviewObj.put("UserId", User);
                    if(currentUser != null){
                        //Set the reviewer name to the name of the current user
                        reviewObj.put("ReviewerName", currentUser.getUsername());
                    }
                    reviewObj.put("Review", review.getText().toString());
                    reviewObj.put("Rating", Math.round(numStars));
                    reviewObj.saveInBackground();
                }
                //Go back to the previous fragment
                getFragmentManager().popBackStack();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Go back to the previous fragment
                getFragmentManager().popBackStack();
            }
        });

        return rLayout;
    }
}
