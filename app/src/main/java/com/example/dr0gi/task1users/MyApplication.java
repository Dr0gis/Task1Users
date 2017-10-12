package com.example.dr0gi.task1users;

import android.app.Application;

public class MyApplication extends Application {
    private DatabaseHandler db;
    private static MyApplication sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        db = new DatabaseHandler(MyApplication.this);
        sInstance = this;
    }

    public DatabaseHandler getDb(){
        return db;
    }

    public static MyApplication getInstance(){
       return sInstance;
    }

}
