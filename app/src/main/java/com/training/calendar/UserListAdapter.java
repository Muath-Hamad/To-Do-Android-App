package com.training.calendar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.MyViewHolder> {
    private Context context;
    private List<User> userList;

    public UserListAdapter(Context context) {
        this.context = context;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    @NonNull
    @Override
    public UserListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_row, parent,false);


        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserListAdapter.MyViewHolder holder, int position) {
        holder.taskName.setText(this.userList.get(position).taskName);
        holder.date.setText(this.userList.get(position).date);

    }

    @Override
    public int getItemCount() {
        return this.userList.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView taskName;
        TextView date;

        public MyViewHolder(View itemView) {
            super(itemView);
            taskName = itemView.findViewById(R.id.TaskName);
            date = itemView.findViewById(R.id.Date);

        }
    }
}
