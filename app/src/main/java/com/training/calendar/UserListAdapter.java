package com.training.calendar;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.MyViewHolder> {
    private Context context;
    private List<User> userList;
    private AppDatabase DB;

    public UserListAdapter(Context context , List<User> userList) {
        this.context = context;
        this.userList = userList;
        notifyDataSetChanged();
    }

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
        User data = userList.get(position);
        DB = AppDatabase.getDbInstance(context);

        holder.taskName.setText(this.userList.get(position).taskName);
        holder.taskDesc.setText(this.userList.get(position).description);
        holder.date.setText(this.userList.get(position).date);
        holder.deleteBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                User Pos = userList.get(holder.getAdapterPosition());
                // Delete category from DB
                DB.userDao().delete(Pos);
                // Notify when data is deleted
                int position = holder.getAdapterPosition();
                userList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position , userList.size());

            }
        });
        holder.editBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent h =  new Intent(context, Create_Event.class);
                context.startActivity(h);
            }
        });

    }

    @Override
    public int getItemCount() {
        return this.userList.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView taskName;
        TextView date;
        TextView taskDesc;
        ImageView editBTN;
        ImageView deleteBTN;


        public MyViewHolder(View itemView) {
            super(itemView);
            taskName = itemView.findViewById(R.id.TaskName);
            taskDesc = itemView.findViewById(R.id.description_Field);
            date = itemView.findViewById(R.id.Date);
            editBTN = itemView.findViewById(R.id.taskEditBTN);
            deleteBTN = itemView.findViewById(R.id.taskDeleteBTN);

        }
    }
}
