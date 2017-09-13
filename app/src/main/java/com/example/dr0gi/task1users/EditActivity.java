package com.example.dr0gi.task1users;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class EditActivity extends AppCompatActivity {

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        Intent intent = getIntent();
        int message = intent.getFlags();

        // Fill data in edit text
        switch (message) {
            case MainActivity.ADD_MESSAGE:
                user = new User();
                break;

            case MainActivity.EDIT_MESSAGE:
                user = (User) intent.getSerializableExtra(MainActivity.ITEM_USER);
                break;

            default:
                user = new User();
                break;
        }

        EditText editTextName = (EditText) findViewById(R.id.editTextName);
        EditText editTextSurname = (EditText) findViewById(R.id.editTextSurname);
        EditText editTextBirthday = (EditText) findViewById(R.id.editTextBirthday);

        editTextName.setText(user.getName());
        editTextSurname.setText(user.getSurname());

        DateFormat df = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
        Date birthday = user.getBirthday();
        editTextBirthday.setText(df.format(birthday));
    }

    // Menu save / Items - Save (Create new User or Edit old)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_save, menu);
        return true;
    }

    // Menu save / Items - Save (Create new User or Edit old)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(this, MainActivity.class);
        String name = ((EditText) findViewById(R.id.editTextName)).getText().toString();
        String surname = ((EditText) findViewById(R.id.editTextSurname)).getText().toString();
        String birthdayStr = ((EditText) findViewById(R.id.editTextBirthday)).getText().toString();

        Date dateBirthday = new Date();
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);

        try {
            dateBirthday = dateFormat.parse(birthdayStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        User newUser = new User(user.getID(), name, surname, dateBirthday);

        switch (item.getItemId()) {

            case R.id.button_save:
                intent.putExtra(MainActivity.ITEM_USER, newUser);
                setResult(RESULT_OK, intent);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
