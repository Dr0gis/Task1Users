package com.example.dr0gi.task1users;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ScrollView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private UserAdapter usersAdapter; // Adapter
    private RecyclerView usersRecyclerView; // List Users UI
    private UsersController usersController; // Controller keeping users list

    public static final int ADD_MESSAGE = 1; // Code message for open activity
    public static final int EDIT_MESSAGE = 2; // Code message for open activity
    public static final String ITEM_USER = "com.example.dr0gi.task1users.ITEM_USER";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        usersRecyclerView = (RecyclerView) findViewById(R.id.recyclerViewUsers);
        usersRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        usersController = new UsersController(getApplicationContext());
        usersAdapter = new UserAdapter(usersController, this);
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
                openEditActivity(ADD_MESSAGE);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // Result after EditActivity Edit or Add items to Users
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            User newUser = (User) data.getSerializableExtra(ITEM_USER);

            switch (requestCode) {
                case (ADD_MESSAGE):
                    itemAdd(newUser);
                    break;

                case (EDIT_MESSAGE):
                    itemEdit(newUser);
                    break;

                default:
                    break;
            }

            usersAdapter.notifyDataSetChanged();
        }
    }

    // Add item to Users
    private void itemAdd(User newUser) {
        usersController.addItem(newUser);
        usersRecyclerView.smoothScrollToPosition(usersController.getLastIndex());
        usersAdapter.notifyItemInserted(usersController.getLastIndex());
    }

    // Edit item to Users
    private void itemEdit(User newUser) {
        usersController.setItem(newUser);
        int index = usersController.getPositionItem(newUser.getID());
        usersRecyclerView.smoothScrollToPosition(index);
        usersAdapter.notifyDataSetChanged();
    }

    // Remove item to Users
    public void removeItem(int index, int id) {
        usersController.removeItemById(id);
        usersAdapter.notifyItemRemoved(index);
    }

    // Open EditActivity for Edit or Add items to Users
    public void openEditActivity(int codeMessage, User user) {
        Intent intent = new Intent(this, EditActivity.class);
        intent.setFlags(codeMessage);
        intent.putExtra(ITEM_USER, user);
        startActivityForResult(intent, codeMessage);
    }
    public void openEditActivity(int codeMessage) {
        Intent intent = new Intent(this, EditActivity.class);
        intent.setFlags(codeMessage);
        startActivityForResult(intent, codeMessage);
    }
}
