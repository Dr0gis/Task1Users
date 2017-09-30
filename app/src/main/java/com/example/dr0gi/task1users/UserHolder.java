package com.example.dr0gi.task1users;

import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

// Class for keeping links on elements widget, class used only for adapter
public class UserHolder extends RecyclerView.ViewHolder {

    private TextView userNameTextView;
    private TextView userAgeTextView;
    private User user;
    public int index;
    private MainActivity mainActivity;

    public UserHolder(View itemView, MainActivity mainActivity) {
        super(itemView);
        userNameTextView = (TextView) itemView.findViewById(R.id.userNameView);
        userAgeTextView = (TextView) itemView.findViewById(R.id.userAgeView);
        this.mainActivity = mainActivity;
        itemView.setOnCreateContextMenuListener(mainActivity);
    }

    // A method that links references with data
    public void bindData(User user, int index) {
        this.user = user;
        this.index = index;

        StringBuilder sb = new StringBuilder(this.user.getName());
        sb.append(" ");
        sb.append(this.user.getSurname());
        userNameTextView.setText(sb.toString());

        sb = new StringBuilder(Integer.toString(this.user.getAge()));
        sb.append(" y/o");
        userAgeTextView.setText(sb.toString());
    }
}
