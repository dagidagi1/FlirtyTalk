package com.example.flirtytalk.ui.gallery;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.flirtytalk.Model.Post;
import com.example.flirtytalk.Model.PostModel;
import com.example.flirtytalk.Model.UsersModel;

import java.util.List;

public class GalleryViewModel extends ViewModel {

    private MutableLiveData<List<Post>> my_post_list = new MutableLiveData<List<Post>>();
    public GalleryViewModel(){
        UsersModel.instance.getCurrentUser((id)->{
            PostModel.instance.getPosts(id, (list)->{
                my_post_list.postValue(list);
            });
        });
    }
    public LiveData<List<Post>> getData() {
        return my_post_list;
    }
}