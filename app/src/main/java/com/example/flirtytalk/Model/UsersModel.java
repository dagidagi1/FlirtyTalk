package com.example.flirtytalk.Model;


import android.graphics.Bitmap;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.flirtytalk.My_application.MyApplication;

import java.util.List;

public class UsersModel {

    UsersModelFireBase usersModelFireBase = new UsersModelFireBase();

    public static final UsersModel instance = new UsersModel();

    MutableLiveData<List<User>> userListLD = new MutableLiveData<List<User>>();

    private UsersModel(){
        reloadUsersList();
    }

    public interface getAllUsersListener{
        void onComplete(List<User> data);
    }

    private void reloadUsersList() {
        usersModelFireBase.getAllUsers(User.getLocalLastUpdated(),(list)->{
            MyApplication.executorService.execute(()->{
                for(User u : list){
                    UsersLocalDB.db.userDao().insert(u);
                    if (u.getLastUpdated() > Post.getLocalLastUpdated()){
                        User.setLocalLastUpdated(u.getLastUpdated());
                    }
                }
                List<User> users_List = UsersLocalDB.db.userDao().getAllUsers();
                userListLD.postValue(users_List);
            });
        });
    }

    public LiveData<List<User>> get_all() {return userListLD;}

    public interface addUserListener {
        void onComplete();
    }
    public void addUser(User user, addUserListener listener){
        usersModelFireBase.addUser(user, ()->{
            reloadUsersList();
            listener.onComplete();
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
