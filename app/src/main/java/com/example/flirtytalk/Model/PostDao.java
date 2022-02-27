package com.example.flirtytalk.Model;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface PostDao {
    @Query("SELECT * FROM Post")
    List<Post> getAllPosts();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(User... users);

    @Update
    void update(User user);

    @Query("SELECT * FROM Post WHERE user_id=:id ")
    List<Post> getPosts(String id);
}