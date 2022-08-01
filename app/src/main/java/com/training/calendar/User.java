package com.training.calendar;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "task_table")
public class User {
    @PrimaryKey (autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "task_name")
    public String taskName;

    @ColumnInfo(name = "date")
    public String date;

    @ColumnInfo(name = "Description")
    public String description;

    @ColumnInfo(name = "Category")
    public String cat;

}