package com.example.flirtytalk.ui.gallery;

import androidx.lifecycle.ViewModel;

import com.example.flirtytalk.Model.Post;
import com.example.flirtytalk.Model.PostModel;

public class EditPostViewModel extends ViewModel {
    private Post p;

    public Post getPost() {
        return p;
    }
    public void setPost(String post_id){
        p = PostModel.instance.getAll().getValue().get(0);
        for(int i = 0; i<PostModel.instance.getAll().getValue().size();i++){
            if(PostModel.instance.getAll().getValue().get(i).getId().equals(post_id)){
                p = PostModel.instance.getAll().getValue().get(i);
            }
        }
    }
}
