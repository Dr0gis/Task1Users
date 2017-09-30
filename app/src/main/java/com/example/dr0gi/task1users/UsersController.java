package com.example.dr0gi.task1users;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.List;

// Class for keeping and create data (List Users)
public class UsersController {
    private List<User> usersList;
    private DatabaseHandler db;
    //private Context context;

    public UsersController(DatabaseHandler db) {
        //this.context = context;
        this.db = db;
        usersList = db.getAllUsers();
    }

    /*public int getPositionItem(int id) {
        int k = 0;
        for (User i : usersList) {
            if (i.getID() == id) {
                return k;
            }
            ++k;
        }
        return -1;
    }*/

    public void addItem(User item, DatabaseHandler.OnDBOperationCompleted<Long> listener) {
        new addItemThread(listener).execute(item);
    }

    public void removeItem(User user, DatabaseHandler.OnDBOperationCompleted<Integer> listener) {
        new removeItemThread(listener).execute(user);
    }

    public void updateItem(User item, DatabaseHandler.OnDBOperationCompleted<Integer> listener) {
        new updateItemThread(listener).execute(item);
    }

    public void updateUserList(DatabaseHandler.OnDBOperationCompleted<Boolean> listener) {
        new updateUserListThread(listener).execute();
    }


    //For MainActivity (scroll to item)
    public int getLastIndex() {
        return usersList.size() - 1;
    }

    //For UserAdapter
    public int getSize() {
        return usersList.size();
    }
    public User getItem(int index) {
        return usersList.get(index);
    }


    private class addItemThread extends AsyncTask<User, Void, Long> {

        private DatabaseHandler.OnDBOperationCompleted<Long> listener;

        addItemThread(DatabaseHandler.OnDBOperationCompleted<Long> listener){
            this.listener = listener;
        }

        @Override
        protected Long doInBackground(User... params) {
            User user = params[0];
            long result = db.addUser(user);
            return result;
        }

        @Override
        protected void onPostExecute(Long result) {
            if (result != 0) {
                listener.onSuccess(result);
            }
            else {
                listener.onError();
            }
        }

    }

    private class removeItemThread extends AsyncTask<User, Void, Integer> {

        private DatabaseHandler.OnDBOperationCompleted<Integer> listener;

        removeItemThread(DatabaseHandler.OnDBOperationCompleted<Integer> listener) {
            this.listener = listener;
        }

        @Override
        protected Integer doInBackground(User... params) {
            User user = params[0];
            int result = db.deleteUser(user);
            return result;
        }

        @Override
        protected void onPostExecute(Integer result) {
            if (result > 0) {
                listener.onSuccess(result);
            }
            else {
                listener.onError();
            }
        }

    }

    private class updateItemThread extends AsyncTask<User, Void, Integer> {

        private DatabaseHandler.OnDBOperationCompleted<Integer> listener;

        updateItemThread(DatabaseHandler.OnDBOperationCompleted<Integer> listener){
            this.listener = listener;
        }

        @Override
        protected Integer doInBackground(User... params) {
            User user = params[0];
            int result = db.updateUser(user);
            return result;
        }

        @Override
        protected void onPostExecute(Integer result) {
            if (result > 0) {
                listener.onSuccess(result);
            }
            else {
                listener.onError();
            }
        }

    }

    private class updateUserListThread extends AsyncTask<Void, Void, Boolean> {

        private DatabaseHandler.OnDBOperationCompleted<Boolean> listener;

        updateUserListThread(DatabaseHandler.OnDBOperationCompleted<Boolean> listener) {
            this.listener = listener;
        }

        @Override
        protected Boolean doInBackground(Void... unused) {
            usersList = db.getAllUsers();
            return true;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (result) {
                listener.onSuccess(result);
            }
            else {
                listener.onError();
            }
        }

    }
}
