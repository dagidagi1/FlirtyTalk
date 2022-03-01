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
    @Query("SELECT * FROM Post ")
    List<Post> getAllPosts();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Post... posts);

    @Update
    void update(Post post);

    @Query("SELECT * FROM Post WHERE Post.id=:id")
    Post getPostById(String id);

    @Query("SELECT * FROM Post WHERE user_id=:id ")
    List<Post> getPosts(String id);
}