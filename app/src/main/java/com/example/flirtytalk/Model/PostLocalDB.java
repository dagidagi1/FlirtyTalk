package com.example.flirtytalk.Model;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.flirtytalk.MyApplication;

@Database(entities = {User.class}, version = 3)
abstract class PostsLocalDbRepository extends RoomDatabase{
    public abstract PostDao postDao();
}

public class PostLocalDB {
    static public final PostsLocalDbRepository db =
            Room.databaseBuilder(MyApplication.getContext(),
                    PostsLocalDbRepository.class,
                    "dbFileName.db")
                    .fallbackToDestructiveMigration()
                    .build();
    private PostLocalDB(){}
}


