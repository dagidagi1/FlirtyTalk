package com.example.flirtytalk.Model;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.flirtytalk.My_application.MyApplication;

@Database(entities = {Post.class}, version = 4)
abstract class PostsLocalDbRepository extends RoomDatabase{
    public abstract PostDao postDao();
}

public class PostLocalDB {
    public static final String POST_LOCAL_DB_DB = "PostLocalDB.db";
    static public final PostsLocalDbRepository db =
            Room.databaseBuilder(MyApplication.getContext(),
                    PostsLocalDbRepository.class,
                    POST_LOCAL_DB_DB)
                    .fallbackToDestructiveMigration()
                    .build();
    private PostLocalDB(){}
}


