package com.training.calendar;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Task_View extends AppCompatActivity {

    private ListView noteListView;
    private UserListAdapter userListAdapter;
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
        List<User> userList =db.userDao().getAll();
        userListAdapter.setUserList(userList);
    }


}
