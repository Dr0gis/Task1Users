package com.example.dr0gi.task1users;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

// Adapter for recycler view
public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserHolder> {

    private UsersClickListener listener;
    private List<User> users;
    private Context context;

    public interface UsersClickListener{
        void onUserLongClicked(View view, int pos);
    }

    public UserAdapter(Context context, List<User> users, UsersClickListener listener) {
        this.users = users;
        this.context = context;
        this.listener = listener;
    }

    @Override
    public UserHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater li = LayoutInflater.from(context);
        View view = li.inflate(R.layout.list_item_user, parent, false);
        return new UserHolder(view);
    }

    @Override
    public void onBindViewHolder(UserHolder holder, int position) {
        User user = users.get(position);
        holder.bindData(user);
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    class UserHolder extends RecyclerView.ViewHolder {
        private TextView userNameTextView;
        private TextView userAgeTextView;

        public UserHolder(View itemView) {
            super(itemView);
            userNameTextView = (TextView) itemView.findViewById(R.id.userNameView);
            userAgeTextView = (TextView) itemView.findViewById(R.id.userAgeView);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    listener.onUserLongClicked(v, getAdapterPosition());
                    return false;
                }
            });
        }

        // A method that links references with data
        public void bindData(User user) {
            StringBuilder sb = new StringBuilder(user.getName());
            sb.append(" ");
            sb.append(user.getSurname());
            userNameTextView.setText(sb.toString());

            sb = new StringBuilder(Integer.toString(user.getAge()));
            sb.append(" y/o");
            userAgeTextView.setText(sb.toString());
        }
    }
}
