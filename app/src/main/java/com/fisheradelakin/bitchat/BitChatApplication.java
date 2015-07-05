package com.fisheradelakin.bitchat;

import android.app.Application;

import com.parse.Parse;

/**
 * Created by Fisher on 7/4/15.
 */
public class BitChatApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);

        Parse.initialize(this, "YzjLNd1pC2jA7GS6MLIzwAxuWI6Qh09uezNRqZ8N", "0jZ3t9ID6N3mWrUzlPaHFxLHuhH3bx8dbdA9yeSc");

    }
}
