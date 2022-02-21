package com.example.flirtytalk.Model;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.flirtytalk.MyApplication;

@Database(entities = {User.class}, version = 2)
abstract class UsersLocalDbRepository extends RoomDatabase{
    public abstract UserDao userDao();
}

public class UsersLocalDB {
    static public final UsersLocalDbRepository db =
            Room.databaseBuilder(MyApplication.getContext(),
                    UsersLocalDbRepository.class,
                    "dbFileName.db")
                    .fallbackToDestructiveMigration()
                    .build();
    private UsersLocalDB(){}
}
