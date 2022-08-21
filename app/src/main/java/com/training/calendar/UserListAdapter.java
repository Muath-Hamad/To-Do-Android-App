package com.training.calendar;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.graphics.Paint;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.SwitchCompat;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.MyViewHolder> {
    private Context context;
    private List<User> userList;
    private List<CategoryData> categoryDataList = new ArrayList<>();
    private ArrayAdapter<String> adapterItems; //for category drop-down list
    private AutoCompleteTextView autoCompleteTextView;
    private String Category;
    private AppDatabase DB;
    private int test;
    private SwitchCompat dateSwitch;
    private boolean hasDate;
    private CardView sCard ,eCard;
    private  Button btDateStart , btDateEnd , btTimeStart , btTimeEnd;
    private boolean Caller , UpdateTime = false;
    private DatePickerDialog datePickerDialog;
    private int sDay = -1 ,sMonth ,sYear, eDay = -1 ,eMonth ,eYear , hour , minute;
    private long newUpdatedstartD , newUpdatedEndD , newUpdatedStartTime , newUpdatedEndTime;



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
    private void initSwitchListener() {

        dateSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean Checked) {
                if (Checked){
                    hasDate = true;
                    sCard.setVisibility(View.VISIBLE);
                    eCard.setVisibility(View.VISIBLE);
                }else{
                    hasDate = false;
                    sCard.setVisibility(View.INVISIBLE);
                    eCard.setVisibility(View.INVISIBLE);
                }
            }
        });
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
                hasDate = d.hasDate;


                Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.activity_update_task);
                autoCompleteTextView = dialog.findViewById(R.id.auto_complete_text_Update);
                initDropDownList();
                int width = WindowManager.LayoutParams.MATCH_PARENT;
                int height = WindowManager.LayoutParams.WRAP_CONTENT;
                dialog.getWindow().setLayout(width, height);
                dialog.show();
                EditText titleUpd = dialog.findViewById(R.id.titleUpdate);
                EditText descUp = dialog.findViewById(R.id.descUpdate);
                Button btUpdate = dialog.findViewById(R.id.updateBTN);
                dateSwitch = dialog.findViewById(R.id.DateSwitchUpdate);
                dateSwitch.setChecked(d.hasDate);
                initSwitchListener();
                sCard =dialog.findViewById(R.id.StartCardUpdate);
                eCard =dialog.findViewById(R.id.EndCardUpdate);
//         default state
                if (d.hasDate){
                    sCard.setVisibility(View.VISIBLE);
                    eCard.setVisibility(View.VISIBLE);
                }else{
                    sCard.setVisibility(View.INVISIBLE);
                    eCard.setVisibility(View.INVISIBLE);}
                initDatePicker();
                 btDateStart = dialog.findViewById(R.id.StartDateUpdate);
                 btDateEnd = dialog.findViewById(R.id.EndDateUpdate);
                 btTimeStart = dialog.findViewById(R.id.StartTimeUpdate);
                 btTimeEnd = dialog.findViewById(R.id.EndTimeUpdate);
                preloadDateAndTime(d.startTime,d.endTime);
                ImageView deleteBTN = dialog.findViewById(R.id.taskDeleteBTN);
                titleUpd.setText(sText);
                descUp.setText(sDesc);
                btDateStart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        openDatePicker1(btDateStart);
                    }
                });
                btDateEnd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        openDatePicker1(btDateEnd);
                    }
                });
                btTimeStart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        popTimePicker1(btTimeStart);
                    }
                });
                btTimeEnd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        popTimePicker1(btTimeEnd);
                    }
                });
                btUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String uText = titleUpd.getText().toString();
                        String uDesc = descUp.getText().toString();
                        d.taskName = uText;
                        d.description = uDesc;
                        d.setHasDate(hasDate);
                        if (hasDate && UpdateTime){
                            d.setStartDate(newUpdatedstartD);
                            d.setEndDate(newUpdatedEndD);
                            d.setStartTime(newUpdatedStartTime);
                            d.setEndTime(newUpdatedEndTime);
                            d.setTask(false);
                        }else{
                            d.setEndDate(-1);
                            d.setStartTime(-1);
                            d.setStartTime(newUpdatedStartTime);
                            d.setEndTime(newUpdatedEndTime);
                            d.setTask(true);
                        }
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

    private void preloadDateAndTime(long startTime, long endTime) {
        Calendar cal =Calendar.getInstance();
        cal.setTimeInMillis(startTime);
        int startYear = cal.get(Calendar.YEAR);
        int startMonth = cal.get(Calendar.MONTH);
        int startDay = cal.get(Calendar.DAY_OF_MONTH);
        int startHour = cal.get(Calendar.HOUR);
        int startMinute = cal.get(Calendar.MINUTE);

        Calendar cal1 =Calendar.getInstance();
        cal1.setTimeInMillis(endTime);
        int endYear = cal1.get(Calendar.YEAR);
        int endMonth = cal1.get(Calendar.MONTH);
        int endDay = cal1.get(Calendar.DAY_OF_MONTH);
        int endHour = cal1.get(Calendar.HOUR);
        int endMinute = cal1.get(Calendar.MINUTE);
        btDateStart.setText(makeDateString(startDay,startMonth+1,startYear));
        btDateEnd.setText(makeDateString(endDay,endMonth+1,endYear));
        btTimeStart.setText(String.format(Locale.getDefault(), "%02d:%02d",startHour ,startMinute));
        btTimeEnd.setText(String.format(Locale.getDefault(), "%02d:%02d",endHour ,endMinute));
    }

    private void initDropDownList() {

        // initialize DB
        DB = AppDatabase.getDbInstance(context);
        // store DB value in data list
        categoryDataList = DB.categoryDao().getAllC();

        String[] items = new String[categoryDataList.size()];
        int i =0;
        for (Object value: categoryDataList) {
            CategoryData data = categoryDataList.get(i);
            items[i] =  data.getTitle();
            i++;
        }
        adapterItems = new ArrayAdapter<String>(context,R.layout.category_list_item,items);
        autoCompleteTextView.setAdapter(adapterItems);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                Category = adapterView.getItemAtPosition(position).toString(); // store selection in item


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
        LinearLayout infoBTN;


        public MyViewHolder(View itemView) {
            super(itemView);
            taskName = itemView.findViewById(R.id.TaskName);
//            taskDesc = itemView.findViewById(R.id.description_Field);
            catDisplay = itemView.findViewById(R.id.Date);
//            editBTN = itemView.findViewById(R.id.taskEditBTN);
//            deleteBTN = itemView.findViewById(R.id.taskDeleteBTN);
            DoneBTN = itemView.findViewById(R.id.taskDoneBTN);
            infoBTN = itemView.findViewById(R.id.TaskBTN);


        }
    }
    public void openDatePicker1(View view){

        if (view.equals(btDateStart)){ // this will excute if the user clicks starts date picker
            Caller = true;
            datePickerDialog.show();
        }
        if (view.equals(btDateEnd)){// this will excute if the user clicks Ends date picker
            Caller = false;
            datePickerDialog.show();
        }

    }
    private void initDatePicker() {

        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                Calendar cal = Calendar.getInstance();

                if (Caller){
                    btDateStart.setText(makeDateString(day,month+1,year));
                    //startD = LocalDate.of(year , month,day);

                    cal.set(Calendar.YEAR , year); // this will help us set default value to Today's Date
                    cal.set(Calendar.MONTH , month);
                    cal.set(Calendar.DAY_OF_MONTH , day);
                    cal.set(Calendar.HOUR_OF_DAY ,0);
                    cal.set(Calendar.MINUTE , 0);
                    cal.set(Calendar.SECOND , 0);
                    newUpdatedstartD = cal.getTimeInMillis();

                    sDay = day; sMonth = month; sYear = year;
                }else {
                    btDateEnd.setText(makeDateString(day,month+1,year));
                    // endD = LocalDate.of(year , month ,day);

                    cal.set(Calendar.YEAR , year); // this will help us set default value to Today's Date
                    cal.set(Calendar.MONTH , month);
                    cal.set(Calendar.DAY_OF_MONTH , day);
                    cal.set(Calendar.HOUR_OF_DAY ,23);
                    cal.set(Calendar.MINUTE , 59);
                    cal.set(Calendar.SECOND , 59);
                    newUpdatedEndD = cal.getTimeInMillis();
                    eDay = day; eMonth = month; eYear = year;
                }
            }
        };
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR); // this will help us set default value to Today's Date
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;
        datePickerDialog = new DatePickerDialog(context,style,dateSetListener ,year ,month ,day ); // initializing the dialog
    }

    private String makeDateString(int day, int month, int year) {
        return getMonthFormat(month) + " " + day + " " + year;
    }
    private String getMonthFormat(int month) { // this method is to get the "String" value instead of Digital
        if (month == 1) return "JAN";
        if (month == 2) return "FEB";
        if (month == 3) return "MAR";
        if (month == 4) return "APR";
        if (month == 5) return "MAY";
        if (month == 6) return "JUN";
        if (month == 7) return "JUL";
        if (month == 8) return "AUG";
        if (month == 9) return "SEP";
        if (month == 10) return "OCT";
        if (month == 11) return "NOV";
        if (month == 12) return "DEC";
        //Default should never be reached
        return "JAN";
    }
    public void popTimePicker1(View view){
        if (sDay == -1){
            Toast toast = Toast.makeText(context ,"Please Enter a Date first",Toast.LENGTH_SHORT);
            toast.show();
        }else{
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                 hour = selectedHour;  minute = selectedMinute;
                if (view.equals(btTimeStart)){
                    btTimeStart.setText(String.format(Locale.getDefault(), "%02d:%02d",hour ,minute));

                        Calendar cal =Calendar.getInstance();
                        cal.set(Calendar.YEAR ,sYear); cal.set(Calendar.MONTH ,sMonth); cal.set(Calendar.DAY_OF_MONTH ,sDay); cal.set(Calendar.HOUR_OF_DAY ,selectedHour); cal.set(Calendar.MINUTE ,selectedMinute);
                        newUpdatedStartTime = cal.getTimeInMillis();

                }
                if (view.equals(btTimeEnd)){
                    btTimeEnd.setText(String.format(Locale.getDefault(), "%02d:%02d",hour ,minute));

                        Calendar cal =Calendar.getInstance();
                        cal.set(Calendar.YEAR ,eYear); cal.set(Calendar.MONTH ,eMonth); cal.set(Calendar.DAY_OF_MONTH ,eDay); cal.set(Calendar.HOUR_OF_DAY ,selectedHour); cal.set(Calendar.MINUTE ,selectedMinute);
                        newUpdatedEndTime = cal.getTimeInMillis();
                }
            }
        };
        int style = AlertDialog.THEME_HOLO_LIGHT; // to change the style of the dialog plug in this style as 2nd parameter in the following method

        TimePickerDialog timePickerDialog = new TimePickerDialog(context , onTimeSetListener , hour ,minute , true);
        timePickerDialog.setTitle("Select Time");
        timePickerDialog.show();}
    }
}
