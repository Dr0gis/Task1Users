package com.example.dr0gi.task1users;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


// Class for keeping and create data (List Users)
public abstract class UsersController {
    private static final int COUNT_USERS = 10;

    public static List<User> getUsersList() {
        List<User> usersList = new ArrayList<>(COUNT_USERS);
        SimpleDateFormat dt = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
        Date birthday = new Date();

        for (int i = 0; i < COUNT_USERS; i++) {
            if(i % 2 == 0) {
                try {
                    birthday = dt.parse("05/04/1990");
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                usersList.add(new User("John", "Smith " + i, birthday));
            }
            else {
                try {
                    birthday = dt.parse("05/04/2000");
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                usersList.add(new User("Ivan", "Ivanov " + i, birthday));
            }
        }

        return usersList;
    }
}
