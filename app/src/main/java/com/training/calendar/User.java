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

    @ColumnInfo(name = "StartDate")
    public String StartDate;
    @ColumnInfo(name = "EndDate")
    public String EndDate;

    @ColumnInfo(name = "StartTime")
    public String StartTime;
    @ColumnInfo(name = "EndTime")
    public String EndTime;

    @ColumnInfo(name = "hasDate")
    public Boolean hasDate;

    @ColumnInfo(name = "Done")
    public Boolean done;

    public String getStartDate() {
        return StartDate;
    }

    public void setStartDate(String startDate) {
        StartDate = startDate;
    }

    public String getEndDate() {
        return EndDate;
    }

    public void setEndDate(String endDate) {
        EndDate = endDate;
    }

    public String getStartTime() {
        return StartTime;
    }

    public void setStartTime(String startTime) {
        StartTime = startTime;
    }

    public String getEndTime() {
        return EndTime;
    }

    public void setEndTime(String endTime) {
        EndTime = endTime;
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