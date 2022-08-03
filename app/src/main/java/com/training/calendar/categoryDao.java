package com.training.calendar;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface categoryDao {

    // insert Query
    @Insert(onConflict = REPLACE)
    void insert(CategoryData categoryData );


    @Delete
    void delete(CategoryData categoryData);

    @Delete
    void reset(List<CategoryData> categoryData);

    @Query("UPDATE category SET title = :sTitle ,color = :sColor  WHERE ID = :sID")
    void update(int sID ,String sTitle , int sColor);

    @Query("SELECT * FROM category")
    List<CategoryData> getAllC();

    @Update
    void Update(CategoryData Cat);


}
