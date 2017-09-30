package com.example.dr0gi.task1users;

import android.app.Application;

/**
 * Created by dr0gi on 27.09.2017.
 */

public class MyApplication extends Application {

    private DatabaseHandler db;

    @Override
    public void onCreate() {
        super.onCreate();
        db = new DatabaseHandler(MyApplication.this);
    }

    public DatabaseHandler getDataBaseConnect() {
        if (db == null) {
            db = new DatabaseHandler(MyApplication.this);
        }
        return db;
    }
}
