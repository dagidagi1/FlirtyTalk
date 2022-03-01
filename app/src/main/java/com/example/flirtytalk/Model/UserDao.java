package com.example.flirtytalk.Model;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserDao {
    @Query("SELECT * FROM User ORDER BY name ASC")
    List<User> getAllUsers();

    @Query("SELECT * FROM User WHERE id=:id ")
    User getUser(String id);
}
