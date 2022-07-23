package com.training.calendar;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.TextView;

public class Create_Event extends AppCompatActivity {

    private DatePickerDialog datePickerDialog;
    private TextView StartDate , EndDate ,StartTime , EndTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);
    }
}