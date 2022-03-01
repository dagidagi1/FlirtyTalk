package com.example.flirtytalk.Model;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.flirtytalk.My_application.MyApplication;

@Database(entities = {Post.class}, version = 2)
abstract class PostsLocalDbRepository extends RoomDatabase{
    public abstract PostDao postDao();
}

public class PostLocalDB {
    static public final PostsLocalDbRepository db =
            Room.databaseBuilder(MyApplication.getContext(),
                    PostsLocalDbRepository.class,
                    "PostLocalDB.db")
                    .fallbackToDestructiveMigration()
                    .build();
    private PostLocalDB(){}
}


