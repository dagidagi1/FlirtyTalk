package com.example.flirtytalk.Model;

import com.example.flirtytalk.MyApplication;

import java.util.List;

public class UsersModel {

    public static final UsersModel instance = new UsersModel();

    private UsersModel(){}

    public interface getAllUsersListener{
        void onComplete(List<User> data);
    }
    public void getAllUsers(getAllUsersListener listener){
        MyApplication.executorService.execute(()->{
            List<User> data = UsersLocalDB.db.userDao().getAllUsers();
            MyApplication.mainHandler.post(()->{
                listener.onComplete(data);
            });
        });
    }

    public interface addUserListener {
        void onComplete();
    }
    public void addUser(User user, addUserListener listener){
        MyApplication.executorService.execute(()->{
            UsersLocalDB.db.userDao().insert(user);
            MyApplication.mainHandler.post(()->{
                listener.onComplete();
            });
        });
    }

    public interface deleteUserListener{
        void onComplete();
    }
    public void deleteUser(User user, deleteUserListener listener) {
        MyApplication.executorService.execute(()->{
            UsersLocalDB.db.userDao().delete(user);
            MyApplication.mainHandler.post(()->{
                listener.onComplete();
            });
        });
    }

    public interface getUserListener {
        void onComplete(User user);
    }
    public void getUser(String userId, getUserListener listener) {
        MyApplication.executorService.execute(()->{
            User user = UsersLocalDB.db.userDao().getUser(userId);
            MyApplication.mainHandler.post(()->{
                listener.onComplete(user);
            });
        });
    }

    public interface updateUserListener {
        void onComplete();
    }
    public void updateUser(User user, updateUserListener listener) {
        MyApplication.executorService.execute(()->{
            UsersLocalDB.db.userDao().update(user);
            MyApplication.mainHandler.post(()->{
                listener.onComplete();
            });
        });
    }

}
