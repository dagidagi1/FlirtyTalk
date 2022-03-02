package com.example.flirtytalk.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.flirtytalk.Model.Post;
import com.example.flirtytalk.Model.PostModel;
import com.example.flirtytalk.Model.User;
import com.example.flirtytalk.Model.UsersModel;

import java.util.List;
import java.util.Objects;

public class HomeViewModel extends ViewModel {

    private LiveData<List<Post>> post_list;
    private LiveData<List<User>> users_list = UsersModel.instance.get_all();;

    public LiveData<List<Post>> getData() {
        if (post_list == null)
        {
            post_list = PostModel.instance.getAll();
        }
        return post_list;
    }

    public LiveData<List<User>> getUsers_list() {
        return users_list;
    }

    public User getUser(String id){
        for(User u: Objects.requireNonNull(users_list.getValue())){
            if(u.getId().equals(id)){
                return u;
            }
        }
        return null;
    }

}