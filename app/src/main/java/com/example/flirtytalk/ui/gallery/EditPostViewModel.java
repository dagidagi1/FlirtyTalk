package com.example.flirtytalk.ui.gallery;

import androidx.lifecycle.ViewModel;

import com.example.flirtytalk.Model.Post;
import com.example.flirtytalk.Model.PostModel;

public class EditPostViewModel extends ViewModel {
    private String post_id;
    public EditPostViewModel(){}
    public String getPostId() {
        return post_id;
    }
    public void setPostId(String id){post_id=id;}
}
