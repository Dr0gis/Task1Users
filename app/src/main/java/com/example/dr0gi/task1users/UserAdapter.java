package com.example.dr0gi.task1users;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

// Adapter for recycler view
public class UserAdapter extends RecyclerView.Adapter<UserHolder> {

    private List<User> usersList;
    private MainActivity mainActivity;

    public UserAdapter(List<User> usersList, MainActivity mainActivity) {
        this.usersList = usersList;
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
        User user = usersList.get(position);
        holder.bindData(user, position);
    }

    @Override
    public int getItemCount() {
        return usersList.size();
    }
}
