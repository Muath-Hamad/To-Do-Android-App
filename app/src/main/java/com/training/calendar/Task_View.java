package com.training.calendar;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Calendar;

public class Task_View extends AppCompatActivity {
    private ImageView update;
    private ListView noteListView;
    private UserListAdapter userListAdapter;
    private String TodayDate;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_view);
        GetTodayDate();
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
        AppDatabase db = AppDatabase.getDbInstance(this.getApplicationContext());
        List<User> userList;
        System.out.println("???????????????????????????");
        System.out.println(TodayDate);

        System.out.println("???????????????????????????");
        if (getIntent().getBooleanExtra("EXTRA_TODAY",false)){ // if the caller is today button then today will be loaded in the list else all events will be loaded
            userList = db.userDao().getToday(TodayDate , true); // second argument will ensure that only events will be loaded
        }else{
            userList =db.userDao().getAll();
        }


        userListAdapter.setUserList(userList);
    }
    private void GetTodayDate(){
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR); // this will help us set default value to Today's Date
        int month = cal.get(Calendar.MONTH);
        month+=1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        TodayDate= makeDateString(day ,month,year);
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
    private String makeDateString(int day, int month, int year) {
        return getMonthFormat(month) + " " + day + " " + year;
    }

}
