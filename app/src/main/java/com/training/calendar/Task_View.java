package com.training.calendar;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.sql.Timestamp;
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

        if (getIntent().getBooleanExtra("EXTRA_isCAT",false)){
            String catname = getIntent().getStringExtra("EXTRA_CATname");
            userList = db.userDao().getByCategory(catname);

        }else if (getIntent().getBooleanExtra("EXTRA_TODAY",false)){ // if the caller is today button then today will be loaded in the list else all events will be loaded

            List<User> eventsList = db.userDao().getTodayLong(getTodayLong(),true);
            List<User>TasksList = db.userDao().getTasks(false);
            userList = TasksList;
            userList.addAll(eventsList);

        }else{
            userList =db.userDao().getAll();
        }
        userListAdapter.setUserList(userList);
    }
    private long getTodayLong() {
        Calendar cal = Calendar.getInstance();
        System.out.println("cal.getTimeInMillis() in Task View"+ cal.getTimeInMillis());
        return cal.getTimeInMillis();
    }
}
