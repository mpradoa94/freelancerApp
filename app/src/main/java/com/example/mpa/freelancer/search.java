package com.example.mpa.freelancer;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
        RelativeLayout rLayout = (RelativeLayout) inflater.inflate(R.layout.activity_search, container, false);

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

        ItemClickSupport.addTo(mRecyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                Bundle user = new Bundle();
                user.putString("Id", users.get(position).getObjectId());

                Fragment profileOtherFragment = new ProfileOther();
                profileOtherFragment.setArguments(user);
                FragmentManager manager = getFragmentManager();
                manager.beginTransaction()
                        .replace(R.id.contentMenu, profileOtherFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        return rLayout;
    }

}
