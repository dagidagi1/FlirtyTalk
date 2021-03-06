package com.example.flirtytalk.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.flirtytalk.Model.Post;
import com.example.flirtytalk.Model.PostModel;

import java.util.List;

public class HomeViewModel extends ViewModel {

    private LiveData<List<Post>> post_list = PostModel.instance.getAll();

    public LiveData<List<Post>> getData() {
        return post_list;
    }

}