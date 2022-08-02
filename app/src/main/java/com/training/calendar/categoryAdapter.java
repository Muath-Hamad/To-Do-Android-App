package com.training.calendar;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class categoryAdapter extends RecyclerView.Adapter<categoryAdapter.ViewHolder> {

private List<CategoryData> categoryDataList;
private Activity context;
private AppDatabase DB;

    public categoryAdapter(List<CategoryData> categoryDataList, Activity context) {
        this.categoryDataList = categoryDataList;
        this.context = context;
        //this.DB = DB;
        notifyDataSetChanged();

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row_category , parent , false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // initialize CategorData
        CategoryData data = categoryDataList.get(position);
        // initialize DB
        DB = AppDatabase.getDbInstance(context);
        // set text
        holder.cTitle.setText(data.getTitle());
        // set action for edit button
        holder.btEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // has no action for now

                CategoryData data = categoryDataList.get(holder.getAdapterPosition());
                int sID = data.getID();
                String sTitle = data.getTitle();
                int sColor = data.getColor();


                Intent i = new Intent(context , Create_Category.class);
                i.putExtra("EXTRA_CATEGORY_ID", sID);
                i.putExtra("EXTRA_CATEGORY_TITLE", sTitle);
                i.putExtra("EXTRA_CATEGORY_COLOR", sColor);
                i.putExtra("EXTRA_CATEGORY_UPDATE", true);

                context.startActivity(i);


            }
        });
        // set action for delete button
        holder.btDelete.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            CategoryData Pos = categoryDataList.get(holder.getAdapterPosition());
            // Delete category from DB
            DB.categoryDao().delete(Pos);
            // Notify when data is deleted
            int position = holder.getAdapterPosition();
            categoryDataList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position , categoryDataList.size());

        }
        });

    }

    @Override
    public int getItemCount() {
        return categoryDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView cTitle;
        ImageView btEdit , btDelete;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cTitle = itemView.findViewById(R.id.categoryRowTitle);
            btEdit = itemView.findViewById(R.id.catgEditBTN);
            btDelete = itemView.findViewById(R.id.catgDeleteBTN);


        }
    }
}
