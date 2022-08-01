package com.training.calendar;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.training.calendar.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    boolean isShown = false;
    FloatingActionButton addCateg, addEvent;
    RecyclerView recView;
    List<CategoryData> dataList = new ArrayList<>();
    AppDatabase AppDB;
    categoryAdapter adapter;
    LinearLayoutManager linearLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addEvent = findViewById(R.id.Create_EventBTN);
        addCateg = findViewById(R.id.Create_CategoryBTN);
        recView = findViewById(R.id.categoryRecycler);
        // initialize DB
        AppDB = AppDatabase.getDbInstance(this);
        // store DB value in data list
        dataList = AppDB.categoryDao().getAllC();
        //initialize LLM
        linearLayoutManager = new LinearLayoutManager(this);

        recView.setLayoutManager(linearLayoutManager);
        // initialize adapter
        adapter = new categoryAdapter(dataList, MainActivity.this);
        // set adapter
        recView.setAdapter(adapter);


    }

    public void AddTask(View v) {
        if (isShown) {
            addCateg.setVisibility(View.INVISIBLE);
            addEvent.setVisibility(View.INVISIBLE);
            isShown = false;
        } else {
            addCateg.setVisibility(View.VISIBLE);
            addEvent.setVisibility(View.VISIBLE);
            isShown = true;
        }
    }

    public void TaskView(View v) {
        Intent h = new Intent(MainActivity.this, Task_View.class);
        startActivity(h);
    }

    public void AddCategory(View view) {
        Intent h = new Intent(MainActivity.this, Create_Category.class);
        startActivity(h);
    }


    public void AddEvent(View view) {
        Intent h = new Intent(MainActivity.this, Create_Event.class);
        startActivity(h);
    }
}