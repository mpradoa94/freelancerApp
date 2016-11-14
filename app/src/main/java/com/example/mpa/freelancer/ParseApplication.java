package com.example.mpa.freelancer;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseUser;

/**
 * Created by JuanPablo on 14/11/2016.
 */

public class ParseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("vy6ZcwiJIdQmApaVslMdJXkeiK9xbNH7Ni35ATWQ")
                .clientKey("KaNrovlikvnVHzJoXvcL98uqUQYe7wEYeDo9wQsX")
                .build());
    }

}
