package com.example.dr0gi.task1users;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;

import static com.example.dr0gi.task1users.MainActivity.EDIT_MESSAGE;

// Class for keeping links on elements widget, class used only for adapter
public class UserHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {

    private TextView mUserNameTextView;
    private TextView mUserAgeTextView;
    private User mUser;
    private int index;
    private MainActivity mainActivity;

    public UserHolder(View itemView, MainActivity mainActivity) {
        super(itemView);
        mUserNameTextView = (TextView) itemView.findViewById(R.id.userNameView);
        mUserAgeTextView = (TextView) itemView.findViewById(R.id.userAgeView);
        this.mainActivity = mainActivity;
        itemView.setOnCreateContextMenuListener(this);
    }

    // Contex menu / Items - Edit, Remove
    @Override
    public void onCreateContextMenu(ContextMenu menu, View itemView, ContextMenu.ContextMenuInfo menuInfo) {
        MenuInflater inflater = mainActivity.getMenuInflater();
        inflater.inflate(R.menu.menu_edit, menu);

        final int length = menu.size();
        for (int index = 0; index < length; index++) {
            final MenuItem menuItem = menu.getItem(index);
            menuItem.setOnMenuItemClickListener(this);
        }

    }

    // Contex menu / Items - Edit, Remove
    @Override
    public boolean onMenuItemClick(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.edit_option:
                mainActivity.openEditActivity(EDIT_MESSAGE, index);
                return true;

            case R.id.remove_option:
                mainActivity.removeItem(index);
                return true;

            default:
                return false;
        }

    }

    // A method that links references with data
    public void bindData(User user, int index) {
        mUser = user;
        this.index = index;

        StringBuilder sb = new StringBuilder(mUser.getName());
        sb.append(" ");
        sb.append(mUser.getSurname());
        mUserNameTextView.setText(sb.toString());

        sb = new StringBuilder(mUser.getAge());
        sb.append(" y/o");
        mUserAgeTextView.setText(sb.toString());
    }



}
