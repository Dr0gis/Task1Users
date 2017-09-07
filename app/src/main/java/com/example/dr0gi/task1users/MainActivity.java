package com.example.dr0gi.task1users;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private PersonAdapter mAdapter;
    private RecyclerView mRecyclerView;

    public static final int ADD_MESSAGE = 1;
    public static final int EDIT_MESSAGE = 2;
    public static final String INDEX = "com.example.dr0gi.task1users.INDEX";
    public static final String NAME_USER = "com.example.dr0gi.task1users.NAME_USER";
    public static final String SURNAME_USER = "com.example.dr0gi.task1users.SURNAME_USER";
    public static final String BIRTHDAY_USER = "com.example.dr0gi.task1users.BIRTHDAY_USER";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerViewUsers);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<CloneFactory.User> userList = CloneFactory.getCloneList();
        mAdapter = new PersonAdapter(userList);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

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

    private void itemAdd(String name, String surname, String birthday) {
        Date date = new Date(90, 1, 1);
        CloneFactory.User newUser = new CloneFactory.User(name, surname, date);

        CloneFactory.getCloneList().add(newUser);
    }

    private void itemEdit(String name, String surname, String birthday, int index) {
        Date date = new Date(90, 1, 1);
        CloneFactory.User user = CloneFactory.getCloneList().get(index);
        user.setName(name);
        user.setSurname(surname);
        user.setBirthday(date);
    }

    private void removeItem(int index) {
        CloneFactory.getCloneList().remove(index);
        mAdapter.notifyDataSetChanged();
    }

    public void openEditActivity(int codeMessage, int index) {
        Intent intent = new Intent(this, EditActivity.class);
        intent.setFlags(codeMessage);
        intent.putExtra(INDEX, index);
        startActivityForResult(intent, codeMessage);
    }

    /*Класс PersonHolder занят тем, что держит на готове ссылки на элементы виджетов,
    которые он с радостью наполнит данными из объекта модели в методе bindCrimе.
    Этот класс используется только адаптером в коде ниже, адаптер дёргает его и поручает
    грязную работу по заполнению виджетов*/
    private class PersonHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {

        private TextView mUserNameTextView;
        private TextView mUserAgeTextView;
        private CloneFactory.User mUser;
        private int index;

        public PersonHolder(View itemView) {
            super(itemView);
            mUserNameTextView = (TextView) itemView.findViewById(R.id.userNameView);
            mUserAgeTextView = (TextView) itemView.findViewById(R.id.userAgeView);

            itemView.setOnCreateContextMenuListener(this);
        }

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

        //Метод, связывающий ранее добытые в конструкторе ссылки с данными модели
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

    private class PersonAdapter extends RecyclerView.Adapter<PersonHolder> {

        private List<CloneFactory.User> mUser;

        public PersonAdapter(List<CloneFactory.User> users) {
            mUser = users;
        }

        @Override
        public PersonHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater li = getLayoutInflater();
            View view = li.inflate(R.layout.list_item_user, parent, false);
            return new PersonHolder(view);

        }

        @Override
        public void onBindViewHolder(PersonHolder holder, int position) {
            CloneFactory.User user = mUser.get(position);
            holder.bindCrime(user, position);
        }

        @Override
        public int getItemCount() {
            return mUser.size();
        }
    }
}
