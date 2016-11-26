package com.example.mpa.freelancer;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseUser;

public class MenuActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        //Set the search as selected in the beginning
        navigationView.setCheckedItem(R.id.nav_search);
        //Load the fragment
        search searchFragment = new search();
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.contentMenu, searchFragment, searchFragment.getTag()).commit();
        navigationView.setNavigationItemSelectedListener(this);

        //Get nav header view
        View navHeaderView = navigationView.inflateHeaderView(R.layout.nav_header_menu);
        //Get text to put user name
        TextView name = (TextView) navHeaderView.findViewById(R.id.textNameMenu);
        ParseUser currentUser = ParseUser.getCurrentUser();
        if(currentUser != null) {
            String namee = currentUser.getUsername();
            name.setText(namee);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_search) {
            search searchFragment = new search();
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.contentMenu, searchFragment, searchFragment.getTag()).commit();
        } else if (id == R.id.nav_profile) {
            Profile profileFragment = new Profile();
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.contentMenu, profileFragment, profileFragment.getTag()).commit();
        } else if (id == R.id.nav_edit_profile) {
            EditProfile editProfileFragment = new EditProfile();
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.contentMenu, editProfileFragment, editProfileFragment.getTag()).commit();
        }
        else if (id == R.id.nav_logout) {
            ParseUser.logOut();
            ParseUser currentUser = ParseUser.getCurrentUser();
            invalidateOptionsMenu();
            Toast.makeText(getApplicationContext(), "Good bye...", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(MenuActivity.this, login.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
