package com.example.flirtytalk.Model;


import android.graphics.Bitmap;

import java.util.List;
import java.util.Objects;

public class UsersModel {

    UsersModelFireBase usersModelFireBase = new UsersModelFireBase();

    public static final UsersModel instance = new UsersModel();

    private UsersModel(){}

    public interface getAllUsersListener{
        void onComplete(List<User> data);
    }
    public void getAllUsers(getAllUsersListener listener){
        usersModelFireBase.getAllUsers(listener);
        /*MyApplication.executorService.execute(()->{
            List<User> data = UsersLocalDB.db.userDao().getAllUsers();
            MyApplication.mainHandler.post(()->{
                listener.onComplete(data);
            });
        });*/
    }

    public interface addUserListener {
        void onComplete();
    }
    public void addUser(User user, addUserListener listener){
        usersModelFireBase.addUser(user, listener);
        /*MyApplication.executorService.execute(()->{
            UsersLocalDB.db.userDao().insert(user);
            MyApplication.mainHandler.post(()->{
                listener.onComplete();
            });
        });*/
    }

    public interface getUserListener {
        void onComplete(User user);
    }
    public void getUser(String userId, getUserListener listener) {
        usersModelFireBase.getUser(userId, listener);
        /*MyApplication.executorService.execute(()->{
            User user = UsersLocalDB.db.userDao().getUser(userId);
            MyApplication.mainHandler.post(()->{
                listener.onComplete(user);
            });
        });*/
    }

    public interface updateUserListener {
        void onComplete();
    }
    public void updateUser(User user, updateUserListener listener) {
        usersModelFireBase.updateUser(user, listener);
        /*MyApplication.executorService.execute(()->{
            UsersLocalDB.db.userDao().update(user);
            MyApplication.mainHandler.post(()->{
                listener.onComplete();
            });
        });*/
    }

    public interface registerUserListener{
        void onComplete(String id);
    }
    public void registerUser(String email, String password, registerUserListener listener){
        usersModelFireBase.registerUser(email, password, listener);
    }

    public interface loginUserListener{
        void onComplete(String id);
    }
    public void loginUser(String email, String password, loginUserListener listener){
        usersModelFireBase.loginUser(email,password,listener);
    }

    public interface getCurrentUserListener{
        void onComplete(String id);
    }
    public void getCurrentUser(getCurrentUserListener listener){
        usersModelFireBase.getCurrentUser(listener);
    }

    public void logout(){
        usersModelFireBase.logout();
    }

    public interface saveImageListener{
        void onComplete(String url);
    }
    public void saveImage(Bitmap image, String id, saveImageListener listener){
        usersModelFireBase.saveImage(image, id, listener);
    }
}
