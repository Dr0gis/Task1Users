package com.example.dr0gi.task1users;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.List;

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

    public boolean removeItemById(int id) {
        int i = 0;
        for (User item: usersList) {
            if (item.getID() == id) {
                usersList.remove(i);
                new removeItemByIdThread().execute(item);
                return true;
            }
            ++i;
        }
        return false;

    }

    public void updateItem(User item, DatabaseHandler.OnDBOperationCompleted<Integer> listener) {
        for (User i : usersList) {
            if (i.getID() == item.getID()) {
                i.setUser(item);
                new updateItemThread(listener).execute(item);
            }
        }
    }

    public void addItem(User item) {
        DatabaseHandler db = new DatabaseHandler(context);

        int id = (int) db.addUser(item);
        item.setID(id);
        usersList.add(item);

        db.close();
    }

    public int getLastIndex() {
        return usersList.size() - 1;
    }

    public int getSize() {
        return usersList.size();
    }

    private class removeItemByIdThread extends AsyncTask<User, Void, Void> {

        @Override
        protected void onPreExecute() {
            Toast.makeText(context, "Задача запущена", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected Void doInBackground(User... params) {
            DatabaseHandler db = new DatabaseHandler(context);
            db.deleteUser(params[0]);
            db.close();
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            Toast.makeText(context, "Задача завершена", Toast.LENGTH_SHORT).show();
        }

    }

    private class updateItemThread extends AsyncTask<User, Integer, Integer> {

        private DatabaseHandler.OnDBOperationCompleted<Integer> listener;

        updateItemThread(DatabaseHandler.OnDBOperationCompleted<Integer> listener){
            this.listener = listener;
        }

        @Override
        protected void onPreExecute() {
            Toast.makeText(context, "Задача запущена", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected Integer doInBackground(User... params) {
            DatabaseHandler db = new DatabaseHandler(context);
            int result = db.updateUser(params[0]);
            db.close();
            return result;
        }

        @Override
        protected void onPostExecute(Integer result) {
            Toast.makeText(context, "Задача завершена", Toast.LENGTH_SHORT).show();
            if (result>0){
                listener.onSuccess(result);
            }else {
                listener.onError();
            }
        }

    }
}
