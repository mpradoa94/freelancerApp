package com.example.mpa.freelancer;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class search extends Fragment {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    ProgressDialog progressDialog;

    List<ParseUser> users;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentActivity faActivity = (FragmentActivity) super.getActivity();
        // Replace LinearLayout by the type of the root element of the layout you're trying to load
        RelativeLayout rLayout = (RelativeLayout) inflater.inflate(R.layout.activity_search, container, false);
        // Of course you will want to faActivity and llLayout in the class and not this method to access them in the rest of

        mRecyclerView = (RecyclerView) rLayout.findViewById(R.id.users_recycler_view);
        mLayoutManager = new LinearLayoutManager(super.getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        users = new ArrayList<>();

        final ParseUser currentUser = ParseUser.getCurrentUser();

        progressDialog = ProgressDialog.show(super.getActivity(), "", "Loading users...", true);

        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereNotEqualTo("email", currentUser.getEmail().toString());
        query.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> userObjects, ParseException error) {
                if (userObjects != null) {
                    for (int i = 0; i < userObjects.size(); i++) {
                        users.add(userObjects.get(i));
                    }
                }
                //Toast.makeText(getActivity(), "Users loaded", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });

        if (users != null) {
            mAdapter = new UsersAdapter(users);
            mRecyclerView.setAdapter(mAdapter);
        }

        return rLayout;
    }

}
