package com.example.flirtytalk.Model;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface PostDao {
    @Query("SELECT * FROM Post ")
    List<Post> getAllPosts();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Post... posts);

    @Query("SELECT * FROM Post WHERE user_id=:id ")
    List<Post> getPosts(String id);
}