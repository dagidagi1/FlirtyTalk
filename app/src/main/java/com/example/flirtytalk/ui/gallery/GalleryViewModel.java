package com.example.flirtytalk.ui.gallery;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.flirtytalk.Model.Post;
import com.example.flirtytalk.Model.PostModel;

import java.util.List;

public class GalleryViewModel extends ViewModel {

    private MutableLiveData<List<Post>> my_post_list = (MutableLiveData<List<Post>>) PostModel.instance.getAll();

    public LiveData<List<Post>> getData() {
        return my_post_list;
    }
}