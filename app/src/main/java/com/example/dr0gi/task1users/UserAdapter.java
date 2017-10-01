package com.example.dr0gi.task1users;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

// Adapter for recycler view
public class UserAdapter extends RecyclerView.Adapter<UserHolder> {

    private UsersController usersController;
    private MainActivity mainActivity;

    public UserAdapter(UsersController usersController, MainActivity mainActivity) {
        this.usersController = usersController;
        this.mainActivity = mainActivity;
    }

    @Override
    public UserHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater li = mainActivity.getLayoutInflater();
        View view = li.inflate(R.layout.list_item_user, parent, false);
        return new UserHolder(view, mainActivity);
    }

    @Override
    public void onBindViewHolder(UserHolder holder, int position) {
        User user = usersController.getItem(position);
        holder.bindData(user, position);
        holder.setContextMenu();
    }

    @Override
    public int getItemCount() {
        return usersController.getSize();
    }
}
