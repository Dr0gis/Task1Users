package com.example.dr0gi.task1users;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private UserAdapter usersAdapter; // Adapter
    private RecyclerView usersRecyclerView; //List Users
    private List<User> usersList;

    public static final int ADD_MESSAGE = 1; // Code message for open activity
    public static final int EDIT_MESSAGE = 2; // Code message for open activity
    public static final String INDEX = "com.example.dr0gi.task1users.INDEX"; // String for intent extra
    public static final String NAME_USER = "com.example.dr0gi.task1users.NAME_USER"; // String for intent extra
    public static final String SURNAME_USER = "com.example.dr0gi.task1users.SURNAME_USER"; // String for intent extra
    public static final String BIRTHDAY_USER = "com.example.dr0gi.task1users.BIRTHDAY_USER"; // String for intent extra

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        usersRecyclerView = (RecyclerView) findViewById(R.id.recyclerViewUsers);
        usersRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        usersList = UsersController.getUsersList();
        usersAdapter = new UserAdapter(usersList, this);
        usersRecyclerView.setAdapter(usersAdapter);
    }

    // Main menu / Items - Add
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    // Main menu / Items - Add
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.button_add:
                openEditActivity(ADD_MESSAGE, -1);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // Result after EditActivity Edit or Add items to Users
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            String name = data.getStringExtra(NAME_USER);
            String surname = data.getStringExtra(SURNAME_USER);
            String bitrthday = data.getStringExtra(BIRTHDAY_USER);

            switch (requestCode) {
                case (ADD_MESSAGE):
                    itemAdd(name, surname, bitrthday);
                    break;

                case (EDIT_MESSAGE):
                    int index = data.getIntExtra(INDEX, -1);
                    itemEdit(name, surname, bitrthday, index);
                    break;

                default:
                    break;
            }

            usersAdapter.notifyDataSetChanged();
        }
    }

    // Add item to Users
    private void itemAdd(String name, String surname, String birthday) {
        Date date = new Date();

        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);

        try {
            date = dateFormat.parse(birthday);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        User newUser = new User(name, surname, date);

        usersList.add(newUser);
    }

    // Edit item to Users
    private void itemEdit(String name, String surname, String birthday, int index) {
        Date date = new Date();

        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);

        try {
            date = dateFormat.parse(birthday);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        User user = usersList.get(index);
        user.setName(name);
        user.setSurname(surname);
        user.setBirthday(date);
    }

    // Remove item to Users
    public void removeItem(int index) {
        usersList.remove(index);
        usersAdapter.notifyDataSetChanged();
    }

    // Open EditActivity for Edit or Add items to Users
    public void openEditActivity(int codeMessage, int index) {
        Intent intent = new Intent(this, EditActivity.class);
        intent.setFlags(codeMessage);
        intent.putExtra(INDEX, index);
        startActivityForResult(intent, codeMessage);
    }
}
