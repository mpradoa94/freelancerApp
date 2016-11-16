package com.example.mpa.freelancer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import bolts.Task;

public class EditSkills extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    EditText editSkills;
    Button btnAddSkill;
    Button btnSave;
    Button btnCancel;
    ArrayList<String> skills;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_skills);

        editSkills = (EditText) findViewById(R.id.skill_input);
        btnAddSkill = (Button) findViewById(R.id.skill_submit);
        btnSave = (Button) findViewById(R.id.btnSave);
        btnCancel = (Button) findViewById(R.id.btnCancel);

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        skills = new ArrayList<String>();

        final ParseUser currentUser = ParseUser.getCurrentUser();
        if(currentUser != null) {
            Object s = currentUser.get("skills");
            if(s != null)
                skills = (ArrayList<String>)s;
        }

        mAdapter = new SkillsAdapter(skills);
        mRecyclerView.setAdapter(mAdapter);

        btnAddSkill.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (editSkills.getText().length() > 0){
                    skills.add(editSkills.getText().toString());
                    editSkills.setText("");
                    mAdapter.notifyItemInserted(skills.size() - 1);
                    Toast.makeText(EditSkills.this, "New skill", Toast.LENGTH_SHORT).show();

                }
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(skills != null){
                    currentUser.put("skills", skills);
                    currentUser.saveEventually();
                }
                Intent intent = new Intent(EditSkills.this, EditProfile.class);
                startActivity(intent);
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(EditSkills.this, EditProfile.class);
                startActivity(intent);
            }
        });

    }
}

