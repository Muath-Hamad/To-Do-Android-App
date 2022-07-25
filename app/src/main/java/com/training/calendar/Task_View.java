package com.training.calendar;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.training.calendar.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import com.training.calendar.databinding.ActivityMainBinding;

public class Task_View extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_task_view);

    }
}
