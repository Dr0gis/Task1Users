package com.example.dr0gi.task1users;

import android.content.Context;

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
    //private DatabaseHandler db;
    private Context context;

    public UsersController(Context context) {
        this.context = context;
        DatabaseHandler db = new DatabaseHandler(context);

        usersList = db.getAllUsers();

        db.close();
        /*SimpleDateFormat dt = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
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
        }*/
    }

    private void updateUsersList() {
        DatabaseHandler db = new DatabaseHandler(context);

        usersList = db.getAllUsers();

        db.close();
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

    /*public void removeItem(int index) {
        usersList.remove(index);
    }*/

    public void removeItemById(int id) {
        DatabaseHandler db = new DatabaseHandler(context);

        int i = 0;
        for (User item: usersList) {
            if (item.getID() == id) {
                usersList.remove(i);
                db.deleteUser(item);
                return;
            }
            ++i;
        }

        db.close();
    }

    public void setItem(User item) {
        DatabaseHandler db = new DatabaseHandler(context);

        for (User i : usersList) {
            if (i.getID() == item.getID()) {
                i.setUser(item);
                db.updateUser(item);
            }
        }

        db.close();
    }

    public void addItem(User item) {
        DatabaseHandler db = new DatabaseHandler(context);

        int id = (int) db.addUser(item);
        item.setID(id);
        usersList.add(item);
        //updateUsersList();

        db.close();
    }

    public int getLastIndex() {
        return usersList.size() - 1;
    }

    public int getSize() {
        return usersList.size();
    }
}
