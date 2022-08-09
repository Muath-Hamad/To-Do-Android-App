package com.training.calendar;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.MyViewHolder> {
    private Context context;
    private List<User> userList;
    private AppDatabase DB;
    private int test;


    public UserListAdapter(Context context, List<User> userList) {
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
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_row, parent, false);


        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserListAdapter.MyViewHolder holder, int position) {
        User data = userList.get(position);
        DB = AppDatabase.getDbInstance(context);

        holder.taskName.setText(this.userList.get(position).taskName);
        holder.catDisplay.setText(this.userList.get(position).cat);

        if (data.getDone()){

            holder.taskName.setPaintFlags(holder.taskName.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
//            holder.taskDesc.setPaintFlags(holder.taskName.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
//            holder.catDisplay.setPaintFlags(holder.taskName.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }

        holder.DoneBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User instance = userList.get(holder.getAdapterPosition());
                int id = instance.uid;
                if (!holder.taskName.getPaint().isStrikeThruText()){
                    DB.userDao().setDone(id , true);
                    holder.taskName.setPaintFlags(holder.taskName.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
//                    holder.taskDesc.setPaintFlags(holder.taskName.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
//                    holder.catDisplay.setPaintFlags(holder.taskName.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                }else{
                    DB.userDao().setDone(id , false);
                    holder.taskName.setPaintFlags(holder.taskName.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
//                    holder.taskDesc.setPaintFlags(holder.taskName.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
//                    holder.catDisplay.setPaintFlags(holder.taskName.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                }
            }
        });


        holder.infoBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                User d = userList.get(holder.getAdapterPosition());
                int sID = d.uid;
                String sText = d.taskName;
                String sDesc = d.description;
                Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.activity_update_task);
                int width = WindowManager.LayoutParams.MATCH_PARENT;
                int height = WindowManager.LayoutParams.WRAP_CONTENT;
                dialog.getWindow().setLayout(width, height);
                dialog.show();
                EditText titleUpd = dialog.findViewById(R.id.titleUpdate);
                EditText descUp = dialog.findViewById(R.id.descUpdate);
                Button btUpdate = dialog.findViewById(R.id.updateBTN);
                ImageView deleteBTN = dialog.findViewById(R.id.taskDeleteBTN);
                titleUpd.setText(sText);
                descUp.setText(sDesc);
                btUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String uText = titleUpd.getText().toString();
                        String uDesc = descUp.getText().toString();
                        d.taskName = uText;
                        d.description = uDesc;
                        DB.userDao().Update(d);
                        notifyDataSetChanged();
                        dialog.dismiss();

                    }
                });
                deleteBTN.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        User Pos = userList.get(holder.getAdapterPosition());
                        // Delete category from DB
                        DB.userDao().delete(Pos);
                        // Notify when data is deleted
                        int position = holder.getAdapterPosition();
                        userList.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, userList.size());
                        dialog.dismiss();
                    }
                });



            }
        });

    }

    @Override
    public int getItemCount() {
        return this.userList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView taskName;
        TextView catDisplay;
        TextView taskDesc;
//        ImageView editBTN;
//        ImageView deleteBTN;
        ImageView DoneBTN;
        ImageView infoBTN;


        public MyViewHolder(View itemView) {
            super(itemView);
            taskName = itemView.findViewById(R.id.TaskName);
//            taskDesc = itemView.findViewById(R.id.description_Field);
            catDisplay = itemView.findViewById(R.id.Date);
//            editBTN = itemView.findViewById(R.id.taskEditBTN);
//            deleteBTN = itemView.findViewById(R.id.taskDeleteBTN);
            DoneBTN = itemView.findViewById(R.id.taskDoneBTN);
            infoBTN = itemView.findViewById(R.id.taskInfoBTN);


        }
    }
}
