package com.example.flirtytalk.Model;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserDao {
    @Query("SELECT * FROM User ORDER BY name ASC")
    List<User> getAllUsers();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(User... users);

    @Query("SELECT * FROM User WHERE id=:id ")
    User getUser(String id);
}
