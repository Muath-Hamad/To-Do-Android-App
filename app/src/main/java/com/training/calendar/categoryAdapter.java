package com.training.calendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
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
        //holder.iconDis.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_people));
        displayColor(data, holder);
        displayIcon(data,holder);



        holder.catBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent h = new Intent(context, Task_View.class);
                h.putExtra("EXTRA_CATname",holder.cTitle.getText());
                h.putExtra("EXTRA_isCAT",true);
                context.startActivity(h);
            }
        });

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
            new AlertDialog.Builder(context)
                    .setTitle("Delete Category")
                    .setMessage("Deleting this category will delete each task inside it.")

                    // Specifying a listener allows you to take an action before dismissing the dialog.
                    // The dialog is automatically dismissed when a dialog button is clicked.
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            CategoryData Pos = categoryDataList.get(holder.getAdapterPosition());
                            String Cat = holder.cTitle.getText().toString();
                            DB.userDao().deleteByCategory(Cat);
                            // Delete category from DB
                            DB.categoryDao().delete(Pos);
                            // Notify when data is deleted
                            int position = holder.getAdapterPosition();
                            categoryDataList.remove(position);
                            notifyItemRemoved(position);
                            notifyItemRangeChanged(position , categoryDataList.size());
                        }
                    })

                    // A null listener allows the button to dismiss the dialog and take no further action.
                    .setNegativeButton(android.R.string.no, null)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();


        }
        });

    }

    @Override
    public int getItemCount() {
        return categoryDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView cTitle;
        ImageView btEdit , btDelete , iconDis, colorDis;
        LinearLayout catBTN;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cTitle = itemView.findViewById(R.id.categoryRowTitle);
            btEdit = itemView.findViewById(R.id.catgEditBTN);
            btDelete = itemView.findViewById(R.id.catgDeleteBTN);
            colorDis = itemView.findViewById(R.id.catgColorDisplay);
            catBTN = itemView.findViewById(R.id.CatBTN);
            iconDis = itemView.findViewById(R.id.catgIconDisplay);


        }
    }
    public void displayColor(CategoryData data,ViewHolder holder){
        String color = data.getNewColor();
        if(color.equalsIgnoreCase("blue"))
            holder.colorDis.setColorFilter(context.getColor(R.color.blue));
        else if(color.equalsIgnoreCase("black"))
            holder.colorDis.setColorFilter(context.getColor(R.color.black));
        else if(color.equalsIgnoreCase("red"))
            holder.colorDis.setColorFilter(context.getColor(R.color.red));
        else if(color.equalsIgnoreCase("teal"))
            holder.colorDis.setColorFilter(context.getColor(R.color.teal_200));
        else if(color.equalsIgnoreCase("purple"))
            holder.colorDis.setColorFilter(context.getColor(R.color.purple_500));
        else if(color.equalsIgnoreCase("gray"))
            holder.colorDis.setColorFilter(context.getColor(R.color.gray));
        else if(color.equalsIgnoreCase("green"))
            holder.colorDis.setColorFilter(context.getColor(R.color.green));
        else if(color.equalsIgnoreCase("maroon"))
            holder.colorDis.setColorFilter(context.getColor(R.color.maroon));
    }
    public void displayIcon(CategoryData data , ViewHolder holder){
        String icon = data.getIcon();
        if(icon.equalsIgnoreCase("people"))
            holder.iconDis.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_people));
        else if(icon.equalsIgnoreCase("pcOnDesk"))
            holder.iconDis.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_pc_on_desk));
        else if(icon.equalsIgnoreCase("dumbbell"))
            holder.iconDis.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_dumbbell));
        else if(icon.equalsIgnoreCase("home"))
            holder.iconDis.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_home));
        else if(icon.equalsIgnoreCase("homeAddress"))
            holder.iconDis.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_home_address));
        else if(icon.equalsIgnoreCase("homeOffice"))
            holder.iconDis.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_home_office));
        else if(icon.equalsIgnoreCase("work"))
            holder.iconDis.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_work));
        else if(icon.equalsIgnoreCase("briefcase"))
            holder.iconDis.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_briefcase));

    }
}
