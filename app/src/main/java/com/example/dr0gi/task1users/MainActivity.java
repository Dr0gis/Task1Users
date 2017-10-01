package com.example.dr0gi.task1users;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private UserAdapter usersAdapter; // Adapter
    private RecyclerView usersRecyclerView; // List Users UI
    private UsersController usersController; // Controller keeping users list

    public static final int ADD_MESSAGE = 1; // Code message for open activity
    public static final int EDIT_MESSAGE = 2; // Code message for open activity
    public static final String CODE_MESSAGE = "com.example.dr0gi.task1users.CODE_MESSAGE";
    public static final String ITEM_USER = "com.example.dr0gi.task1users.ITEM_USER";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        usersRecyclerView = (RecyclerView) findViewById(R.id.recyclerViewUsers);
        usersRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        MyApplication myApplication = (MyApplication) getApplicationContext();

        usersController = new UsersController(myApplication.getDataBaseConnect());
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

    //
    public class ContextMenuRecyclerView implements View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {

        private int index;

        public ContextMenuRecyclerView(int index) {
            this.index = index;
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            MenuInflater inflater = MainActivity.this.getMenuInflater();
            inflater.inflate(R.menu.menu_edit, menu);
            int length = menu.size();
            for (int index = 0; index < length; index++) {
                MenuItem menuItem = menu.getItem(index);
                menuItem.setOnMenuItemClickListener(this);
            }
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.edit_option:
                    openEditActivity(MainActivity.EDIT_MESSAGE, index);
                    return true;

                case R.id.remove_option:
                    removeItem(index);
                    return true;

                default:
                    return false;
            }
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
        usersController.addItem(newUser, new DatabaseHandler.OnDBOperationCompleted<Long>() {
            @Override
            public void onSuccess(Long result) {
                usersController.updateUserList(new DatabaseHandler.OnDBOperationCompleted<Boolean>() {
                    @Override
                    public void onSuccess(Boolean result) {
                        usersAdapter.notifyItemInserted(usersController.getLastIndex());
                        usersRecyclerView.smoothScrollToPosition(usersController.getLastIndex());
                    }

                    @Override
                    public void onError() {

                    }
                });
            }

            @Override
            public void onError() {

            }
        });
    }

    // Edit item to Users
    private void itemEdit(User newUser) {
        usersController.updateItem(newUser, new DatabaseHandler.OnDBOperationCompleted<Integer>() {
            @Override
            public void onSuccess(Integer result) {
                usersController.updateUserList(new DatabaseHandler.OnDBOperationCompleted<Boolean>() {
                    @Override
                    public void onSuccess(Boolean result) {
                        usersAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError() {

                    }
                });
            }

            @Override
            public void onError() {

            }
        });
    }

    // Remove item to Users
    public void removeItem(final int index) {
        User user = usersController.getItem(index);
        usersController.removeItem(user, new DatabaseHandler.OnDBOperationCompleted<Integer>() {
            @Override
            public void onSuccess(Integer result) {
                usersController.updateUserList(new DatabaseHandler.OnDBOperationCompleted<Boolean>() {
                    @Override
                    public void onSuccess(Boolean result) {
                        usersAdapter.notifyItemRemoved(index);
                    }

                    @Override
                    public void onError() {

                    }
                });

            }

            @Override
            public void onError() {

            }
        });
    }

    // Open EditActivity for Edit or Add items to Users
    public void openEditActivity(int codeMessage, int position) {
        User user = usersController.getItem(position);

        Intent intent = new Intent(this, EditActivity.class);

        intent.putExtra(CODE_MESSAGE, codeMessage);
        intent.putExtra(ITEM_USER, user);
        startActivityForResult(intent, codeMessage);
    }
    public void openEditActivity(int codeMessage) {
        Intent intent = new Intent(this, EditActivity.class);
        intent.putExtra(CODE_MESSAGE, codeMessage);
        startActivityForResult(intent, codeMessage);
    }
}
