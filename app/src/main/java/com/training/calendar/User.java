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

    @ColumnInfo(name = "longStartTime")
    public long startTime;

    @ColumnInfo(name = "longEndTime")
    public long endTime;

    @ColumnInfo(name = "longStartDate")
    public long startDate;

    @ColumnInfo(name = "longEndDate")
    public long endDate;

    @ColumnInfo(name = "hasDate")
    public Boolean hasDate;

    @ColumnInfo(name = "Done")
    public Boolean done;

    @ColumnInfo(name = "CreationTime")
    public long CreateTime;


    @ColumnInfo(name = "taskDay") // this value holds a long value that indicates what day that task should be displayed in "myDay"
    public long taskDay;

    public long getTaskDay() {
        return taskDay;
    }

    public void setTaskDay(long taskDay) {
        this.taskDay = taskDay;
    }

    public void setStartDate(long startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(long endDate) {
        this.endDate = endDate;
    }

    public long getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(long createTime) {
        this.CreateTime = createTime;
    }


    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }


    public Boolean getHasDate() {
        return hasDate;
    }

    public void setHasDate(Boolean hasDate) {
        this.hasDate = hasDate;
    }

    public Boolean getDone() {
        return done;
    }

    public void setDone(Boolean done) {
        this.done = done;
    }
}