package com.example.dr0gi.task1users;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class EditActivity extends AppCompatActivity {

    private int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        Intent intent = getIntent();
        int message = intent.getFlags();

        switch (message) {
            case MainActivity.ADD_MESSAGE:

                break;

            case MainActivity.EDIT_MESSAGE:
                index = intent.getIntExtra(MainActivity.INDEX, 0);

                EditText editTextName = (EditText) findViewById(R.id.editTextName);
                EditText editTextSurname = (EditText) findViewById(R.id.editTextSurname);
                EditText editTextBirthday = (EditText) findViewById(R.id.editTextBirthday);

                editTextName.setText(CloneFactory.getCloneList().get(index).getName());
                editTextSurname.setText(CloneFactory.getCloneList().get(index).getSurname());

                DateFormat df = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
                Date birthday = CloneFactory.getCloneList().get(index).getBirthday();

                editTextBirthday.setText(df.format(birthday));
                break;

            default:
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_save, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(this, MainActivity.class);
        EditText editTextName = (EditText) findViewById(R.id.editTextName);
        EditText editTextSurname = (EditText) findViewById(R.id.editTextSurname);
        EditText editTextBirthday = (EditText) findViewById(R.id.editTextBirthday);

        switch (item.getItemId()) {

            case R.id.button_save:
                intent.putExtra(MainActivity.INDEX, index);
                intent.putExtra(MainActivity.NAME_USER, editTextName.getText().toString());
                intent.putExtra(MainActivity.SURNAME_USER, editTextSurname.getText().toString());
                intent.putExtra(MainActivity.BIRTHDAY_USER, editTextBirthday.getText().toString());
                setResult(RESULT_OK, intent);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
