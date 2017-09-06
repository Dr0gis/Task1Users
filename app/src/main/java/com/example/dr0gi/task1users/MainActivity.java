package com.example.dr0gi.task1users;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by dr0gi on 05.09.2017.
 */

public class MainActivity extends AppCompatActivity {

    private PersonAdapter mAdapter;
    private RecyclerView mRecyclerView;

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

    /*Класс PersonHolder занят тем, что держит на готове ссылки на элементы виджетов,
    которые он с радостью наполнит данными из объекта модели в методе bindCrimе.
    Этот класс используется только адаптером в коде ниже, адаптер дёргает его и поручает
    грязную работу по заполнению виджетов*/
    private class PersonHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {

        private TextView mUserNameTextView;
        private TextView mUserAgeTextView;
        private CloneFactory.User mUser;

        public PersonHolder(View itemView) {
            super(itemView);
            mUserNameTextView = (TextView) itemView.findViewById(R.id.userNameView);
            mUserAgeTextView = (TextView) itemView.findViewById(R.id.userAgeView);

            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View itemView, ContextMenu.ContextMenuInfo menuInfo) {
            getMenuInflater().inflate(R.menu.menu_edit, menu);
        }

        //Метод, связывающий ранее добытые в конструкторе ссылки с данными модели
        public void bindCrime(CloneFactory.User user) {
            mUser = user;
            StringBuilder sb = new StringBuilder(mUser.getName());
            sb.append(" ");
            sb.append(mUser.getSurname());
            mUserNameTextView.setText(sb.toString());
            sb = new StringBuilder(calculateAge(mUser.getBirthday()).toString());
            sb.append(" y/o");
            mUserAgeTextView.setText(sb.toString());
        }

        private Integer calculateAge(final Date birthday)
        {
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
            holder.bindCrime(user);

        }

        @Override
        public int getItemCount() {
            return mUser.size();
        }
    }
}
