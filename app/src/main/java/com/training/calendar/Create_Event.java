package com.training.calendar;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Locale;

public class Create_Event extends AppCompatActivity {

    private DatePickerDialog datePickerDialog;
    private Button StartDate , EndDate ,StartTime , EndTime;
    private String date;
    private boolean Caller; // this is set by the clicked button
    private int hour , minute;
    private EditText eventTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);
        StartTime = findViewById(R.id.StartTimePicker);
        EndTime = findViewById(R.id.EndTimePicker);
        eventTitle = findViewById(R.id.TitleEdit);

        initDatePicker();
        StartDate = findViewById(R.id.CategoryColorPicker);
        EndDate = findViewById(R.id.EndDatePicker);
        StartDate.setText(getTodaysDate());
        EndDate.setText(getTodaysDate());

        eventTitle.setOnClickListener(new View.OnClickListener() { // clear field on first use
            @Override
            public void onClick(View view) {
                if (getString(R.string.eventTitleText).equals(eventTitle.getText().toString())){
                    eventTitle.setText("");
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

    private void initDatePicker() {

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

    public  void  openDatePicker(View view){
        if (view.equals(StartDate)){ // this will excute if the user clicks starts date picker
            Caller = true;
            datePickerDialog.show();
        }
        if (view.equals(EndDate)){// this will excute if the user clicks Ends date picker
            Caller = false;
            datePickerDialog.show();
        }

    }

    public void popTimePicker(View view){
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

    public void SaveButton(View view) { // this method is empty at the moment the plan is to make it save data to DB and go back to main page

    }
}