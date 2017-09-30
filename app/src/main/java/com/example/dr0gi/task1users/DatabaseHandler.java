package com.example.dr0gi.task1users;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    private static final int DATABASE_VERSION = 1; // Database Version
    private static final String DATABASE_NAME = "usersDB.db"; // Database Name
    private static final String TABLE_USERS = "usersInfo";  // Users table name

    // Contacts Table Columns names
    private static final String KEY_ID = "_id";
    private static final String KEY_NAME = "name";
    private static final String KEY_SURNAME = "surname";
    private static final String KEY_BIRTHDAY = "birthday";
    private static final String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS +
    "(" +
        KEY_ID + " INTEGER PRIMARY KEY," +
        KEY_NAME + " TEXT," +
        KEY_SURNAME + " TEXT," +
        KEY_BIRTHDAY + " TEXT" +
    ")";

    public interface OnDBOperationCompleted<T>{
        void onSuccess(T result);
        void onError();
    }

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USERS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);

        // Create tables again
        onCreate(db);
    }

    /*-----------------------------------------------------------
    -------All CRUD(Create, Read, Update, Delete) Operations-----
    -----------------------------------------------------------*/

    // Adding new user
    public long addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, user.getName()); // User Name
        values.put(KEY_SURNAME, user.getSurname()); // User Surname
        values.put(KEY_BIRTHDAY, user.getBirthdayStr()); // User Birthday (String)

        // Inserting Row
        long result = db.insert(TABLE_USERS, null, values);

        db.close();

        return result;
    }

    // Getting single user
    public User getUser(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(
            TABLE_USERS,
            new String[] { KEY_ID, KEY_NAME, KEY_SURNAME, KEY_BIRTHDAY },
            KEY_ID + "=?",
            new String[] { String.valueOf(id) },
            null,
            null,
            null,
            null
        );

        User user = new User();

        if (cursor != null) {
            cursor.moveToFirst();

            user.setID(Integer.parseInt(cursor.getString(0)));
            user.setName(cursor.getString(1));
            user.setSurname(cursor.getString(2));
            user.setBirthdayStr(cursor.getString(3));
            cursor.close();
        }

        db.close();
        // return user
        return user;
    }

    // Getting All Users
    public List<User> getAllUsers() {
        List<User> usersList = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_USERS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                int id = Integer.parseInt(cursor.getString(0));
                String name = cursor.getString(1);
                String surname = cursor.getString(2);
                String birthday = cursor.getString(3);
                User user = new User(id, name, surname, birthday);

                // Adding user to list
                usersList.add(user);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close(); // Closing database connection

        // return users list
        return usersList;
    }

    // Getting Users Count
    public int getUsersCount() {
        String countQuery = "SELECT COUNT(id) FROM " + TABLE_USERS;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(countQuery, null);
        int result = cursor.getInt(0);
        cursor.close();

        db.close(); // Closing database connection

        // return count
        return result;
    }

    // Updating single user
    public int updateUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, user.getName());
        values.put(KEY_SURNAME, user.getSurname());
        values.put(KEY_BIRTHDAY, user.getBirthdayStr());

        int result = db.update(
                TABLE_USERS,
                values,
                KEY_ID + " = ?",
                new String[] { String.valueOf(user.getID()) }
        );

        db.close();

        // updating row
        return result;
    }

    // Deleting single user
    public int deleteUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(
            TABLE_USERS,
            KEY_ID + " = ?",
            new String[] { String.valueOf(user.getID()) }
        );
        db.close();
        return result;
    }

}
