package com.example.mpa.freelancer;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Profile extends AppCompatActivity {
    private RecyclerView recyclerViewSkills;
    private RecyclerView.Adapter mAdapter;

    Context context;

    TextView name;
    TextView occupation;
    ArrayList<String> skills;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        context = getApplicationContext();
        recyclerViewSkills = (RecyclerView) findViewById(R.id.list_skills);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewSkills.setLayoutManager(layoutManager);
        recyclerViewSkills.setNestedScrollingEnabled(false);

        name = (TextView) findViewById(R.id.header_name);
        occupation = (TextView) findViewById(R.id.header_occupation);

        //Make the profile image round
        ImageView profileImg = (ImageView)findViewById(R.id.profile_image);
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
            if(sk != null){
                skills = (ArrayList<String>)sk;
            }

            mAdapter = new SkillsAdapter(skills);
            recyclerViewSkills.setAdapter(mAdapter);

            CardView layout = (CardView)findViewById(R.id.top_container);

            Button newButton = new Button(this);
            newButton.setText("Edit Profile");
            newButton.setTextColor(0xffffffff);
            newButton.setBackgroundColor(0xff70dc68);
            layout.addView(newButton);
            ViewGroup.LayoutParams params = newButton.getLayoutParams();
            params.height = 100;
            params.width = 300;
            newButton.setLayoutParams(params);

            newButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent intent = new Intent(Profile.this, EditProfile.class);
                    startActivity(intent);
                }
            });

;        }

        /* Query test using parse

        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> users, ParseException e) {
                if(e == null){
                    Toast.makeText(Profile.this, "Profiles: " + users.get(0).getString("email"), Toast.LENGTH_LONG).show();
                    String nombreusr;
                    nombreusr = user.getString("username");
                    name.setText(nombreusr);
                }
                else{
                    e.printStackTrace();
                }
            }
        });*/


    }


}
