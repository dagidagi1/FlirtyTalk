package com.example.flirtytalk.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.flirtytalk.Model.Post;
import com.example.flirtytalk.Model.PostModel;

import java.util.List;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<List<Post>> post_list = (MutableLiveData<List<Post>>) PostModel.instance.getAll();

    public LiveData<List<Post>> getData() {
        return post_list;
    }
//    public void setData(List<Post> newData){
//        post_list.postValue(newData);
//    }
}