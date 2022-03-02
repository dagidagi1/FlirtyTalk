package com.example.flirtytalk.Model;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface PostDao {
    @Query("SELECT * FROM Post WHERE deleted = 0 ORDER BY id DESC")
    List<Post> getAllPosts();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Post... posts);

    @Query("SELECT * FROM Post WHERE user_id=:id ")
    List<Post> getPosts(String id);

    @Query("SELECT * FROM Post WHERE id =:post_id")
    Post getPost(String post_id);

    @Delete
    void delete_post(Post p);
}