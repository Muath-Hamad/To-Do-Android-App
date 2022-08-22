package com.training.calendar;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.sql.Timestamp;
import java.util.ArrayList;
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
        List<User> userList = new ArrayList<>();

        if (getIntent().getBooleanExtra("EXTRA_isCAT",false)){
            String catname = getIntent().getStringExtra("EXTRA_CATname");
            userList = db.userDao().getByCategory(catname);

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

        }else{
            userList =db.userDao().getAll();
        }
        userListAdapter.setUserList(userList);
    }
    private long getTodayLong() {
        Calendar cal = Calendar.getInstance();
        return cal.getTimeInMillis();
    }
}
