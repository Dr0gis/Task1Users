package com.example.dr0gi.task1users;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private UserAdapter mAdapter; // Adapter
    private RecyclerView mRecyclerView; //List Users

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

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerViewUsers);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<CloneFactory.User> userList = CloneFactory.getCloneList();
        mAdapter = new UserAdapter(userList);
        mRecyclerView.setAdapter(mAdapter);
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

            mAdapter.notifyDataSetChanged();
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

        CloneFactory.User newUser = new CloneFactory.User(name, surname, date);

        CloneFactory.getCloneList().add(newUser);
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
        CloneFactory.User user = CloneFactory.getCloneList().get(index);
        user.setName(name);
        user.setSurname(surname);
        user.setBirthday(date);
    }

    // Remove item to Users
    private void removeItem(int index) {
        CloneFactory.getCloneList().remove(index);
        mAdapter.notifyDataSetChanged();
    }

    // Open EditActivity for Edit or Add items to Users
    public void openEditActivity(int codeMessage, int index) {
        Intent intent = new Intent(this, EditActivity.class);
        intent.setFlags(codeMessage);
        intent.putExtra(INDEX, index);
        startActivityForResult(intent, codeMessage);
    }

    // Class for keeping links on elements widget, class used only for adapter
    private class UserHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {

        private TextView mUserNameTextView;
        private TextView mUserAgeTextView;
        private CloneFactory.User mUser;
        private int index;

        public UserHolder(View itemView) {
            super(itemView);
            mUserNameTextView = (TextView) itemView.findViewById(R.id.userNameView);
            mUserAgeTextView = (TextView) itemView.findViewById(R.id.userAgeView);

            itemView.setOnCreateContextMenuListener(this);
        }

        // Contex menu / Items - Edit, Remove
        @Override
        public void onCreateContextMenu(ContextMenu menu, View itemView, ContextMenu.ContextMenuInfo menuInfo) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu_edit, menu);

            final int length = menu.size();
            for (int index = 0; index < length; index++) {
                final MenuItem menuItem = menu.getItem(index);
                menuItem.setOnMenuItemClickListener(this);
            }

        }

        // Contex menu / Items - Edit, Remove
        @Override
        public boolean onMenuItemClick(MenuItem item) {

            switch (item.getItemId()) {
                case R.id.edit_option:
                    openEditActivity(EDIT_MESSAGE, index);
                    return true;

                case R.id.remove_option:
                    removeItem(index);
                    return true;

                default:
                    return false;
            }

        }

        // A method that links references with data
        public void bindCrime(CloneFactory.User user, int index) {
            mUser = user;
            this.index = index;

            StringBuilder sb = new StringBuilder(mUser.getName());
            sb.append(" ");
            sb.append(mUser.getSurname());
            mUserNameTextView.setText(sb.toString());

            sb = new StringBuilder(calculateAge(mUser.getBirthday()).toString());
            sb.append(" y/o");
            mUserAgeTextView.setText(sb.toString());
        }

        // Calculate age by birthday
        private Integer calculateAge(final Date birthday) {
            Calendar dob = Calendar.getInstance();
            Calendar today = Calendar.getInstance();

            dob.setTime(birthday);
            dob.add(Calendar.DAY_OF_MONTH, -1);

            int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);
            if (today.get(Calendar.DAY_OF_YEAR) <= dob.get(Calendar.DAY_OF_YEAR)) {
                age--;
            }
            return age;
        }

    }

    // Adapter for recycler view
    private class UserAdapter extends RecyclerView.Adapter<UserHolder> {

        private List<CloneFactory.User> mUser;

        public UserAdapter(List<CloneFactory.User> users) {
            mUser = users;
        }

        @Override
        public UserHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater li = getLayoutInflater();
            View view = li.inflate(R.layout.list_item_user, parent, false);
            return new UserHolder(view);

        }

        @Override
        public void onBindViewHolder(UserHolder holder, int position) {
            CloneFactory.User user = mUser.get(position);
            holder.bindCrime(user, position);
        }

        @Override
        public int getItemCount() {
            return mUser.size();
        }
    }
}
