package com.example.dr0gi.task1users;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

// Class for keeping and create data (List Users)
public class UsersController {
    private static final int COUNT_USERS = 100;
    private List<User> usersList;

    public UsersController() {
        usersList = new ArrayList<>(COUNT_USERS);
        SimpleDateFormat dt = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
        Date birthday = new Date();

        for (int i = 0; i < COUNT_USERS; i++) {
            if(i % 2 == 0) {
                try {
                    birthday = dt.parse("05/04/1990");
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                usersList.add(new User(i, "John", "Smith " + i, birthday));
            }
            else {
                try {
                    birthday = dt.parse("05/04/2000");
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                usersList.add(new User(i, "Ivan", "Ivanov " + i, birthday));
            }
        }
    }

    public User getItem(int index) {
        return usersList.get(index);
    }

    public int getPositionItem(int id) {
        int k = 0;
        for (User i : usersList) {
            if (i.getID() == id) {
                return k;
            }
            ++k;
        }
        return -1;
    }

    public void removeItem(int index) {
        usersList.remove(index);
    }

    public void setItem(User item) {
        for (User i : usersList) {
            if (i.getID() == item.getID()) {
                i.setUser(item);
            }
        }
    }

    public void addItem(User item) {
        usersList.add(item);
    }

    public int getLastIndex() {
        return usersList.size() - 1;
    }

    public int getSize() {
        return usersList.size();
    }
}
