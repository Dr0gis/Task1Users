package com.example.dr0gi.task1users;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.NumberPicker;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class EditActivity extends AppCompatActivity {

    private EditText editTextName;
    private EditText editTextSurname;
    private NumberPicker numberPickerBirthdayMonth;
    private NumberPicker numberPickerBirthdayDay;
    private NumberPicker numberPickerBirthdayYear;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        String titleActivity = "Profile info";
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(titleActivity);

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

        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextSurname = (EditText) findViewById(R.id.editTextSurname);
        numberPickerBirthdayMonth = (NumberPicker) findViewById(R.id.numberPickerBirthdayMonth);
        numberPickerBirthdayDay = (NumberPicker) findViewById(R.id.numberPickerBirthdayDay);
        numberPickerBirthdayYear = (NumberPicker) findViewById(R.id.numberPickerBirthdayYear);

        editTextName.setText(user.getName());
        editTextSurname.setText(user.getSurname());

        numberPickerBirthdayMonth.setMaxValue(12);
        numberPickerBirthdayMonth.setMinValue(1);
        numberPickerBirthdayDay.setMaxValue(31);
        numberPickerBirthdayDay.setMinValue(1);
        numberPickerBirthdayYear.setMaxValue(3000);
        numberPickerBirthdayYear.setMinValue(1500);

        Date birthday = user.getBirthday();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(birthday);
        numberPickerBirthdayMonth.setValue(calendar.get(Calendar.MONTH) + 1);
        numberPickerBirthdayDay.setValue(calendar.get(Calendar.DAY_OF_MONTH));
        numberPickerBirthdayYear.setValue(calendar.get(Calendar.YEAR));
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
        String name = editTextName.getText().toString();
        String surname = editTextSurname.getText().toString();

        StringBuilder birthdayStr = new StringBuilder(Integer.toString(numberPickerBirthdayMonth.getValue()));
        birthdayStr.append("/");
        birthdayStr.append(Integer.toString(numberPickerBirthdayDay.getValue()));
        birthdayStr.append("/");
        birthdayStr.append(Integer.toString(numberPickerBirthdayYear.getValue()));

        Date dateBirthday = new Date();
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);

        try {
            dateBirthday = dateFormat.parse(birthdayStr.toString());
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
