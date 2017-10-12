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
        new AddItemAsync(usersList, listener).execute(item);
    }

    public void removeItem(User user, DatabaseHandler.OnDBOperationCompleted<Integer> listener) {
        new RemoveItemAsync(usersList, listener).execute(user);
    }

    public void updateItem(User item, DatabaseHandler.OnDBOperationCompleted<Integer> listener) {
        new UpdateItemAsync(usersList, listener).execute(item);
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
        private List<User> users;
        private User newUser;

        AddItemAsync(List<User> users, DatabaseHandler.OnDBOperationCompleted<Long> listener){
            this.users = users;
            this.listener = listener;
        }

        @Override
        protected Long doInBackground(User... params) {
            newUser = params[0];
            return db.addUser(newUser);
        }

        @Override
        protected void onPostExecute(Long result) {
            if (result != -1) {
                newUser.setID(result);
                users.add(newUser);
                listener.onSuccess(result);
            }
            else {
                listener.onError();
            }
        }

    }

    private class RemoveItemAsync extends AsyncTask<User, Void, Integer> {

        private DatabaseHandler.OnDBOperationCompleted<Integer> listener;
        private List<User> users;
        private User deletedUser;

        RemoveItemAsync(List<User> users, DatabaseHandler.OnDBOperationCompleted<Integer> listener) {
            this.listener = listener;
            this.users = users;
        }

        @Override
        protected Integer doInBackground(User... params) {
            deletedUser = params[0];
            return db.deleteUser(deletedUser);
        }

        @Override
        protected void onPostExecute(Integer result) {
            if (result != 0) {
                users.remove(deletedUser);
                listener.onSuccess(result);
            }
            else {
                listener.onError();
            }
        }

    }

    private class UpdateItemAsync extends AsyncTask<User, Void, Integer> {
        private DatabaseHandler.OnDBOperationCompleted<Integer> listener;
        private List<User> users;
        private User updatedUser;

        UpdateItemAsync(List<User> users, DatabaseHandler.OnDBOperationCompleted<Integer> listener){
            this.users = users;
            this.listener = listener;
        }

        @Override
        protected Integer doInBackground(User... params) {
            updatedUser = params[0];
            return db.updateUser(updatedUser);
        }

        @Override
        protected void onPostExecute(Integer result) {
            if (result != 0) {
                int i = 0;
                for (User user: users) {
                    if (user.getID() == updatedUser.getID()) {
                        break;
                    }
                    ++i;
                }
                users.set(i, updatedUser);
                listener.onSuccess(result);
            }
            else {
                listener.onError();
            }
        }

    }
}
