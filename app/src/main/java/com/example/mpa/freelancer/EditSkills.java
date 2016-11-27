package com.example.mpa.freelancer;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.parse.ParseUser;

import java.util.ArrayList;

public class EditSkills extends Fragment {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    EditText editSkills;
    Button btnAddSkill;
    Button btnSave;
    Button btnCancel;
    ArrayList<String> skills;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentActivity faActivity = (FragmentActivity) super.getActivity();
        RelativeLayout rLayout = (RelativeLayout) inflater.inflate(R.layout.activity_edit_skills, container, false);

        editSkills = (EditText) rLayout.findViewById(R.id.skill_input);
        btnAddSkill = (Button) rLayout.findViewById(R.id.skill_submit);
        btnSave = (Button) rLayout.findViewById(R.id.btnSave);
        btnCancel = (Button) rLayout.findViewById(R.id.btnCancel);

        mRecyclerView = (RecyclerView) rLayout.findViewById(R.id.my_recycler_view);

        mLayoutManager = new LinearLayoutManager(super.getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        skills = new ArrayList<String>();

        final ParseUser currentUser = ParseUser.getCurrentUser();
        if(currentUser != null) {
            Object s = currentUser.get("skills");
            if(s != null)
                skills = (ArrayList<String>)s;
        }

        mAdapter = new EditSkillsAdapter(skills);
        mRecyclerView.setAdapter(mAdapter);

        btnAddSkill.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (editSkills.getText().length() > 0){
                    skills.add(editSkills.getText().toString());
                    editSkills.setText("");
                    mAdapter.notifyItemInserted(skills.size() - 1);
                    Toast.makeText(getActivity(), "New skill", Toast.LENGTH_SHORT).show();

                }
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(skills != null){
                    currentUser.put("skills", skills);
                    currentUser.saveEventually();
                }
                // replace the current Fragment with the edit profile fragment and
                // push transaction onto the backstack to preserve the back button behavior
                Fragment editProfileFragment = new EditProfile();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.contentMenu, editProfileFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(getActivity(), "The skills were not saved", Toast.LENGTH_SHORT).show();
                // replace the current Fragment with the edit profile fragment and
                // push transaction onto the backstack to preserve the back button behavior
                Fragment editProfileFragment = new EditProfile();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.contentMenu, editProfileFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        return rLayout;
    }
}

