package com.example.flirtytalk.ui.gallery;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.flirtytalk.Model.Post;
import com.example.flirtytalk.Model.PostModel;
import com.example.flirtytalk.Model.UsersModel;

import java.util.List;

public class GalleryViewModel extends ViewModel {

    private LiveData<List<Post>> my_post_list;
    public GalleryViewModel(){
        my_post_list = PostModel.instance.getMyPosts();
    }
    public LiveData<List<Post>> getData() {
        return my_post_list;
    }
}