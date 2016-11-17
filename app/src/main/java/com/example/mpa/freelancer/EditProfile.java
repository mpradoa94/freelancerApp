package com.example.mpa.freelancer;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.ParseUser;

import java.util.ArrayList;

public class EditProfile extends AppCompatActivity {
    private RecyclerView recyclerViewSkills;
    private RecyclerView.Adapter mAdapter;

    Context context;

    TextView name;
    TextView occupation;
    ImageButton btnSkills;
    ImageButton btnOccupation;
    Button saveProfile;
    ArrayList<String> skills;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        context = getApplicationContext();
        recyclerViewSkills = (RecyclerView) findViewById(R.id.list_skills);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewSkills.setLayoutManager(layoutManager);

        name = (TextView) findViewById(R.id.header_name);
        occupation = (TextView) findViewById(R.id.header_occupation);
        btnSkills = (ImageButton) findViewById(R.id.edit_skills_list);
        btnOccupation = (ImageButton) findViewById(R.id.edit_occupation);
        saveProfile = (Button) findViewById(R.id.btnSave);

        //Make the profile image round
        ImageView profileImg = (ImageView)findViewById(R.id.profile_image);
        //get bitmap of the image
        Bitmap imageBitmap = BitmapFactory.decodeResource(getResources(),  R.drawable.default_user);
        RoundedBitmapDrawable roundedBitmapDrawable= RoundedBitmapDrawableFactory.create(getResources(), imageBitmap);
        roundedBitmapDrawable.setCircular(true);
        roundedBitmapDrawable.setAntiAlias(true);
        profileImg.setImageDrawable(roundedBitmapDrawable);

        final ParseUser currentUser = ParseUser.getCurrentUser();
        if(currentUser != null) {
            String namee = currentUser.getUsername();
            name.setText(namee);
            Object oc = currentUser.get("ocupation");
            Object sk = currentUser.get("skills");
            if(oc != null) {
                String ocupationn = oc.toString();
                occupation.setText(ocupationn);
            }
            if(sk != null){
                skills = (ArrayList<String>)sk;
            }

            mAdapter = new SkillsAdapter(skills);
            recyclerViewSkills.setAdapter(mAdapter);

            btnOccupation.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(EditProfile.this);

                    builder.setTitle("Edit occupation");
                    final EditText TextInput = new EditText(context);
                    TextInput.setTextColor(Color.parseColor("#000000"));
                    builder.setView(TextInput);

                    builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            currentUser.put("ocupation", TextInput.getText().toString());
                            occupation.setText(TextInput.getText().toString());
                            currentUser.saveEventually();
                        }
                    });

                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                        }
                    });

                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            });

            btnSkills.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent intent = new Intent(EditProfile.this, EditSkills.class);
                    startActivity(intent);
                }
            });

            saveProfile.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent intent = new Intent(EditProfile.this, Profile.class);
                    startActivity(intent);
                }
            });
        }
    }
}
