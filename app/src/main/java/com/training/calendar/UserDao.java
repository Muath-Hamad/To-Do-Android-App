package com.training.calendar;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserDao {
    @Query("SELECT * FROM task_table")
    List<User> getAll();

//    @Query("SELECT * FROM task_table WHERE hasDate = :choice")
//    List<User> getTasksOrEvents(boolean choice);

    @Query("SELECT * FROM task_table WHERE Category = :category")
    List<User> getByCategory(String category);

    @Query("SELECT * FROM task_table WHERE StartDate = :date AND hasDate = :today")
    List<User> getToday(String date ,boolean today);

    @Query("SELECT * FROM task_table WHERE uid IN (:userIds)")
    List<User> loadAllByIds(int[] userIds);

    @Query("SELECT* FROM task_table WHERE task_name LIKE :first AND " +
            "date LIKE :last LIMIT 1")
    User findByName(String first, String last);

    @Insert
    void insertAll(User... users);

    @Delete
    void delete(User user);

    @Query("UPDATE task_table SET task_name = :title AND Description = :Desc WHERE uid = :sID")
    void update (int sID , String title , String Desc );

    @Update
    void Update(User user);

    @Query("UPDATE task_table SET Category = :newCat WHERE Category = :oldCat")
    void updateCat(String oldCat, String newCat);

    @Query("UPDATE task_table SET Done = :setDone WHERE uid = :sID")
    void setDone(int sID , boolean setDone);

}
