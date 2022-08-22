package com.training.calendar;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class Task_View extends AppCompatActivity {
    private ImageView update;
    private ListView noteListView;
    private UserListAdapter userListAdapter;
    private String TodayDate;
    private AppDatabase db;
    private List<CategoryData> categoryDataList = new ArrayList<>();
    private ArrayAdapter<String> adapterItems; //for category drop-down list
    private AutoCompleteTextView autoCompleteTextView;
    private String Category;
    private SwitchCompat dateSwitch;
    private boolean hasDate;
    private CardView sCard ,eCard;
    private  Button btDateStart , btDateEnd , btTimeStart , btTimeEnd;
    private boolean Caller , UpdateTime = false;
    private DatePickerDialog datePickerDialog;
    private int sDay = -1 ,sMonth ,sYear, eDay = -1 ,eMonth ,eYear , hour , minute;
    private long newUpdatedstartD , newUpdatedEndD , newUpdatedStartTime , newUpdatedEndTime;


    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_view);
        initRecyclerView();
        loadUserList();
    }
    private void initRecyclerView(){
        RecyclerView recyclerView = findViewById(R.id.RecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        userListAdapter = new UserListAdapter(this);
        recyclerView.setAdapter(userListAdapter);
    }
    private void loadUserList(){
<<<<<<< HEAD
        AppDatabase db = AppDatabase.getDbInstance(this.getApplicationContext());
        List<User> userList = new ArrayList<>();
=======
        db = AppDatabase.getDbInstance(this.getApplicationContext());
        List<User> userList;
        Button TaskCatAdd= findViewById(R.id.testing123);
>>>>>>> a641330 (Successfully added categroy task creation and today's view task creation)

        if (getIntent().getBooleanExtra("EXTRA_isCAT",false)){
            String catname = getIntent().getStringExtra("EXTRA_CATname");
            userList = db.userDao().getByCategory(catname);
            TaskCatAdd.setVisibility(View.VISIBLE);
            TaskCatAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Dialog dialog = new Dialog(Task_View.this);
                    dialog.setContentView(R.layout.activity_update_task);
                    autoCompleteTextView = dialog.findViewById(R.id.auto_complete_text_Update);
                    initDropDownList();
                    int width = WindowManager.LayoutParams.MATCH_PARENT;
                    int height = WindowManager.LayoutParams.WRAP_CONTENT;
                    dialog.getWindow().setLayout(width, height);
                    dialog.show();
                    EditText titleUpd = dialog.findViewById(R.id.titleUpdate);
                    EditText descUp = dialog.findViewById(R.id.descUpdate);
                    Button save = dialog.findViewById(R.id.updateBTN);
                    AutoCompleteTextView category = dialog.findViewById(R.id.auto_complete_text_Update);
                    //category.setHint(cat);

                    dateSwitch = dialog.findViewById(R.id.DateSwitchUpdate);
                    initSwitchListener();
                    sCard =dialog.findViewById(R.id.StartCardUpdate);
                    eCard =dialog.findViewById(R.id.EndCardUpdate);
                    initDatePicker();
                    btDateStart = dialog.findViewById(R.id.StartDateUpdate);
                    btDateEnd = dialog.findViewById(R.id.EndDateUpdate);
                    btTimeStart = dialog.findViewById(R.id.StartTimeUpdate);
                    btTimeEnd = dialog.findViewById(R.id.EndTimeUpdate);
                    ImageView deleteBTN = dialog.findViewById(R.id.taskDeleteBTN);
                    deleteBTN.setVisibility(View.INVISIBLE);

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
                    save.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            User d = new User();
                            String uText = titleUpd.getText().toString();
                            String uDesc = descUp.getText().toString();
                            String uCategory = Category;
                            d.taskName = uText;
                            d.description = uDesc;
                            d.cat = uCategory;
                            d.setHasDate(hasDate);
                            if (hasDate && UpdateTime){
                                d.setStartDate(newUpdatedstartD);
                                d.setEndDate(newUpdatedEndD);
                                d.setStartTime(newUpdatedStartTime);
                                d.setEndTime(newUpdatedEndTime);
                            }else{
                                d.setEndDate(-1);
                                d.setStartTime(-1);
                                d.setStartTime(newUpdatedStartTime);
                                d.setEndTime(newUpdatedEndTime);
                            }
                            db.userDao().insertAll(d);
                            userListAdapter.notifyDataSetChanged();
                            //notifyItemRangeChanged(position, userList.size());
                            dialog.dismiss();
                        }
                    });
                }
            });
        }else if (getIntent().getBooleanExtra("EXTRA_TODAY",false)){ // if the caller is today button then today will be loaded in the list else all events will be loaded

            Calendar TodayTime = Calendar.getInstance();
            int TodayYear = TodayTime.get(Calendar.YEAR);
            int TodayDOY = TodayTime.get(Calendar.DAY_OF_YEAR);

            List<User> eventsList = db.userDao().getTodayLong(getTodayLong(),true);
            List<User>TasksList = db.userDao().getTasks(false); // getTasks() is Sql query in userDao
            for (User u:TasksList) {

                    Calendar taskTime = Calendar.getInstance();


                    taskTime.setTimeInMillis(u.getTaskDay());
                    int TaskYear = taskTime.get(Calendar.YEAR);
                    int TaskDOY = taskTime.get(Calendar.DAY_OF_YEAR);


                    if (TaskYear == TodayYear && TaskDOY == TodayDOY){ // this will compare today's year and day of year << with >> task's year and day of year
                        userList.add(u);
                    }

            }

            //userList = TasksList;
            userList.addAll(eventsList);
            TaskCatAdd.setVisibility(View.VISIBLE);
            TaskCatAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Dialog dialog = new Dialog(Task_View.this);
                    dialog.setContentView(R.layout.activity_update_task);
                    autoCompleteTextView = dialog.findViewById(R.id.auto_complete_text_Update);
                    initDropDownList();
                    int width = WindowManager.LayoutParams.MATCH_PARENT;
                    int height = WindowManager.LayoutParams.WRAP_CONTENT;
                    dialog.getWindow().setLayout(width, height);
                    dialog.show();
                    EditText titleUpd = dialog.findViewById(R.id.titleUpdate);
                    EditText descUp = dialog.findViewById(R.id.descUpdate);
                    Button save = dialog.findViewById(R.id.updateBTN);
                    AutoCompleteTextView category = dialog.findViewById(R.id.auto_complete_text_Update);
                    dateSwitch = dialog.findViewById(R.id.DateSwitchUpdate);
                    initSwitchListener();
                    sCard =dialog.findViewById(R.id.StartCardUpdate);
                    eCard =dialog.findViewById(R.id.EndCardUpdate);
                    initDatePicker();
                    btDateStart = dialog.findViewById(R.id.StartDateUpdate);
                    btDateEnd = dialog.findViewById(R.id.EndDateUpdate);
                    btTimeStart = dialog.findViewById(R.id.StartTimeUpdate);
                    btTimeEnd = dialog.findViewById(R.id.EndTimeUpdate);
                    ImageView deleteBTN = dialog.findViewById(R.id.taskDeleteBTN);
                    deleteBTN.setVisibility(View.INVISIBLE);

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
                    save.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            User d = new User();
                            String uText = titleUpd.getText().toString();
                            String uDesc = descUp.getText().toString();
                            String uCategory = Category;
                            d.taskName = uText;
                            d.description = uDesc;
                            d.cat = uCategory;
                            d.setHasDate(hasDate);
                            if (hasDate && UpdateTime){
                                d.setStartDate(newUpdatedstartD);
                                d.setEndDate(newUpdatedEndD);
                                d.setStartTime(newUpdatedStartTime);
                                d.setEndTime(newUpdatedEndTime);
                            }else{
                                d.setEndDate(-1);
                                d.setStartTime(-1);
                                d.setStartTime(newUpdatedStartTime);
                                d.setEndTime(newUpdatedEndTime);
                            }
                            db.userDao().insertAll(d);
                            userListAdapter.notifyDataSetChanged();
                            //notifyItemRangeChanged(position, userList.size());
                            dialog.dismiss();
                        }
                    });
                }
            });

        }else{
            userList =db.userDao().getAll();
            TaskCatAdd.setVisibility(View.INVISIBLE);

        }
        userListAdapter.setUserList(userList);
    }
    private long getTodayLong() {
        Calendar cal = Calendar.getInstance();
        return cal.getTimeInMillis();
    }
    private void initDropDownList() {
        if(getIntent().hasExtra("EXTRA_CATname")){
            String catname = getIntent().getStringExtra("EXTRA_CATname");
            autoCompleteTextView.setText(catname);
            Category =catname;
        }
        else
            autoCompleteTextView.setText("Select Category");

        // initialize DB
        db = AppDatabase.getDbInstance(Task_View.this);
        // store DB value in data list
        categoryDataList = db.categoryDao().getAllC();

        String[] items = new String[categoryDataList.size()];
        int i =0;
        for (Object value: categoryDataList) {
            CategoryData data = categoryDataList.get(i);
            items[i] =  data.getTitle();
            i++;
        }
        adapterItems = new ArrayAdapter<String>(Task_View.this,R.layout.category_list_item,items);
        autoCompleteTextView.setAdapter(adapterItems);

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                Category = adapterView.getItemAtPosition(position).toString(); // store selection in item


            }
        });

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
        datePickerDialog = new DatePickerDialog(Task_View.this,style,dateSetListener ,year ,month ,day ); // initializing the dialog
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
            Toast toast = Toast.makeText(Task_View.this ,"Please Enter a Date first",Toast.LENGTH_SHORT);
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

            TimePickerDialog timePickerDialog = new TimePickerDialog(Task_View.this , onTimeSetListener , hour ,minute , true);
            timePickerDialog.setTitle("Select Time");
            timePickerDialog.show();}
    }
}
