package com.example.dr0gi.task1users;

import android.os.AsyncTask;

import java.util.List;

// Class for keeping and create data (List Users)
public class UsersController {
    private List<User> usersList;
    private DatabaseHandler db;

    public UsersController(DatabaseHandler db) {
        this.db = db;
        usersList = db.getAllUsers();
    }

    public void addItem(User item, DatabaseHandler.OnDBOperationCompleted<Long> listener) {
        new AddItemAsync(listener).execute(item);
    }

    public void removeItem(User user, DatabaseHandler.OnDBOperationCompleted<Integer> listener) {
        new RemoveItemAsync(listener).execute(user);
    }

    public void updateItem(User item, DatabaseHandler.OnDBOperationCompleted<Integer> listener) {
        new UpdateItemAsync(listener).execute(item);
    }

    public void updateUserList(DatabaseHandler.OnDBOperationCompleted<Boolean> listener) {
        new UpdateUserListAsync(listener).execute();
    }

    public List<User> getUsersList() {
        return usersList;
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


    private class AddItemAsync extends AsyncTask<User, Void, Long> {

        private DatabaseHandler.OnDBOperationCompleted<Long> listener;

        AddItemAsync(DatabaseHandler.OnDBOperationCompleted<Long> listener){
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

    private class RemoveItemAsync extends AsyncTask<User, Void, Integer> {

        private DatabaseHandler.OnDBOperationCompleted<Integer> listener;

        RemoveItemAsync(DatabaseHandler.OnDBOperationCompleted<Integer> listener) {
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

    private class UpdateItemAsync extends AsyncTask<User, Void, Integer> {

        private DatabaseHandler.OnDBOperationCompleted<Integer> listener;

        UpdateItemAsync(DatabaseHandler.OnDBOperationCompleted<Integer> listener){
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

    private class UpdateUserListAsync extends AsyncTask<Void, Void, Boolean> {

        private DatabaseHandler.OnDBOperationCompleted<Boolean> listener;

        UpdateUserListAsync(DatabaseHandler.OnDBOperationCompleted<Boolean> listener) {
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
