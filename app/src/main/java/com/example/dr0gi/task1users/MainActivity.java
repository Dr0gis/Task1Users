package com.example.dr0gi.task1users;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import static java.security.AccessController.getContext;

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

        List<CloneFactory.Person> personList = CloneFactory.getCloneList();
        mAdapter = new PersonAdapter(personList);
        mRecyclerView.setAdapter(mAdapter);

        /*SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS users (name TEXT, age INTEGER)");

        Cursor query = db.rawQuery("SELECT * FROM users;", null);



        TextView textView = (TextView) findViewById(R.id.textView);
        if(query.moveToFirst()){
            do{
                String name = query.getString(0);
                int age = query.getInt(1);
                textView.append("Name: " + name + " Age: " + age + "\n");
            }
            while(query.moveToNext());
        }
        query.close();
        db.close();*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /*Класс PersonHolder занят тем, что держит на готове ссылки на элементы виджетов,
    которые он с радостью наполнит данными из объекта модели в методе bindCrimе.
    Этот класс используется только адаптером в коде ниже, адаптер дёргает его и поручает
    грязную работу по заполнению виджетов*/
    private class PersonHolder extends RecyclerView.ViewHolder{

        private TextView mPersonNameTextView;
        private TextView mPersonAdressTextView;
        private TextView mPersonSexTextView;
        private TextView mPersonAgeTextView;
        private CloneFactory.Person mPerson;


        public PersonHolder(View itemView) {
            super(itemView);
            mPersonNameTextView = (TextView) itemView.findViewById(R.id.personNameView);
            mPersonAdressTextView = (TextView) itemView.findViewById(R.id.personAdressView);
            mPersonSexTextView = (TextView) itemView.findViewById(R.id.personSexView);
            mPersonAgeTextView = (TextView) itemView.findViewById(R.id.personAgeView);
        }
        //Метод, связывающий ранее добытые в конструкторе ссылки с данными модели
        public void bindCrime(CloneFactory.Person person) {
            mPerson = person;
            mPersonNameTextView.setText(mPerson.getName());
            mPersonAdressTextView.setText(mPerson.getAdress());
            mPersonAgeTextView.setText(""+mPerson.getAge());
            if(mPerson.isSex()){
                mPersonSexTextView.setText("Мужчина");
            }else {
                mPersonSexTextView.setText("Женщина");
            }

        }

    }

    //Наш адаптер, мост между фабрикой клонов и выводом их на экран.
    //Его методы будет дёргать LinearLayoutManager, назныченный вьюшке
    //RecyclerView в методе onCreate нашей активити
    private class PersonAdapter extends RecyclerView.Adapter<PersonHolder> {

        private List<CloneFactory.Person> mPersons;

        public PersonAdapter(List<CloneFactory.Person> persons) {
            mPersons = persons;
        }

        //Создаёт пустую вьюшку,оборачивает её в PersonHolder.
        //Дальше забота по наполнению этой вьюшки ложиться именно на объект PersonHolder'а
        @Override
        public PersonHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater li = getLayoutInflater();
            View view = li.inflate(R.layout.list_item_user, parent, false);
            return new PersonHolder(view);

        }

        //Дёргает метод холдера при выводе нового элемента списка на экран,
        //передавая ему актуальный объект модели для разбора и представления
        @Override
        public void onBindViewHolder(PersonHolder holder, int position) {
            CloneFactory.Person person = mPersons.get(position);
            holder.bindCrime(person);

        }

        //Возвращает размер хранилища моделей
        @Override
        public int getItemCount() {
            return mPersons.size();
        }
    }
}
