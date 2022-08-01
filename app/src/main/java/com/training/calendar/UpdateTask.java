package com.training.calendar;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class UpdateTask extends AppCompatActivity {
    private DatePickerDialog datePickerDialog;
    private Button StartDate , EndDate ,StartTime , EndTime; // these at first will have today's date until changed by user
    private String date;
    private boolean Caller; // this is set by the clicked button
    private int hour , minute;
    private EditText eventTitle;// this will have the name of the event entered by the user
    private EditText eventDecs;
    private SwitchCompat dateSwitch;
    private List<CategoryData> categoryDataList = new ArrayList<>();
    private AppDatabase AppDB;
    private AutoCompleteTextView autoCompleteTextView;
    private ArrayAdapter<String> adapterItems;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_task);
        initAllViews1();
        initSwitchListener1();
        initDatePicker1();
        initDropDownList1();

    }
    public String getDate() {
        return date;
    }
    public String getEventTitle() {
        return String.valueOf(eventTitle.getText());
    }
    public String getEventDesc() {
        return String.valueOf(eventDecs.getText());
    }
    private void initDropDownList1() {
        // initialize DB
        AppDB = AppDatabase.getDbInstance(this);
        // store DB value in data list
        categoryDataList = AppDB.categoryDao().getAllC();

        String[] items = new String[categoryDataList.size()];
        int i =0;
        for (Object value: categoryDataList) {
            CategoryData data = categoryDataList.get(i);
            items[i] =  data.getTitle();
            i++;
        }
        adapterItems = new ArrayAdapter<String>(this,R.layout.category_list_item,items);
        autoCompleteTextView.setAdapter(adapterItems);

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                String item = adapterView.getItemAtPosition(position).toString(); // store selection in item


            }
        });

    }
    private void initAllViews1() {
        StartTime = findViewById(R.id.StartTimeUpdate);
        EndTime = findViewById(R.id.EndTimeUpdate);
        eventTitle = findViewById(R.id.titleUpdate);
        eventDecs = findViewById(R.id.descUpdate);
        StartDate = findViewById(R.id.StartDateUpdate);
        EndDate = findViewById(R.id.EndDateUpdate);
        StartDate.setText(getTodaysDate());
        EndDate.setText(getTodaysDate());
        dateSwitch = findViewById(R.id.DateSwitchUpdate);
        autoCompleteTextView = findViewById(R.id.auto_complete_text_Update);
//         default state
        StartDate.setClickable(false);
        EndDate.setClickable(false);
        StartTime.setClickable(false);
        EndTime.setClickable(false);
    }
    private void initSwitchListener1() {

        dateSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean Checked) {
                if (Checked){
                    StartDate.setClickable(true);
                    StartTime.setHighlightColor(1111);
                    EndDate.setClickable(true);
                    StartTime.setClickable(true);
                    EndTime.setClickable(true);
                }else{
                    StartDate.setClickable(false);
                    EndDate.setClickable(false);
                    StartTime.setClickable(false);
                    EndTime.setClickable(false);
                }

            }
        });
    }
    private String getTodaysDate() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR); // this will help us set default value to Today's Date
        int month = cal.get(Calendar.MONTH);
        month += 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(year , month , day);
    }
    private void initDatePicker1() {

        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month += 1;
                date = makeDateString(day,month,year);
                if (Caller){
                    StartDate.setText(date);

                }else {
                    EndDate.setText(date);
                }
            }
        };
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR); // this will help us set default value to Today's Date
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;
        datePickerDialog = new DatePickerDialog(this,style,dateSetListener ,year ,month ,day ); // initializing the dialog
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
    public  void  openDatePicker1(View view){
        if (view.equals(StartDate)){ // this will excute if the user clicks starts date picker
            Caller = true;
            datePickerDialog.show();
        }
        if (view.equals(EndDate)){// this will excute if the user clicks Ends date picker
            Caller = false;
            datePickerDialog.show();
        }

    }
    public void popTimePicker1(View view){
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                hour = selectedHour; minute = selectedMinute;
                if (view.equals(StartTime)){
                    StartTime.setText(String.format(Locale.getDefault(), "%02d:%02d",hour ,minute));
                }
                if (view.equals(EndTime)){
                    EndTime.setText(String.format(Locale.getDefault(), "%02d:%02d",hour ,minute));
                }
            }
        };
        int style = AlertDialog.THEME_HOLO_LIGHT; // to change the style of the dialog plug in this style as 2nd parameter in the following method

        TimePickerDialog timePickerDialog = new TimePickerDialog(this , onTimeSetListener , hour ,minute , true);
        timePickerDialog.setTitle("Select Time");
        timePickerDialog.show();
    }
    public void SaveButton1(View view) { // this method is empty at the moment the plan is to make it save data to DB and go back to main page
        AppDatabase db = AppDatabase.getDbInstance(this.getApplicationContext());
        User user = new User();
        user.taskName = getEventTitle();
        user.description = getEventDesc();
        user.date = date;
        db.userDao().insertAll(user);
        finish();

    }
}
