package com.example.flirtytalk.Model;

import android.graphics.Bitmap;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.flirtytalk.My_application.MyApplication;

import java.util.List;

public class PostModel {

    PostModelFireBase postModelFireBase = new PostModelFireBase();

    public static final PostModel instance = new PostModel();

    private PostModel(){
        reloadPostsList();
        fireBaseDataChanged(()->{
            reloadPostsList();
        });
    }

    MutableLiveData<List<Post>> post_list_ld = new MutableLiveData<List<Post>>();
    MutableLiveData<List<Post>> my_posts_list_ld = new MutableLiveData<List<Post>>();
    private void reloadPostsList() {
        postModelFireBase.getAllPosts(Post.getLocalLastUpdated(),(list)->{
            MyApplication.executorService.execute(()->{
                Long lastUpdate = new Long(0);
                for(Post s : list){
                    if(s.getDeleted()){
                        PostLocalDB.db.postDao().delete_post(s);
                    }
                    else {
                        PostLocalDB.db.postDao().insert(s);
                        if (lastUpdate < s.getLastUpdated()) {
                            lastUpdate = s.getLastUpdated();
                        }
                    }
                }
                Post.setLocalLastUpdated(lastUpdate);
                List<Post> post_List = PostLocalDB.db.postDao().getAllPosts();
                post_list_ld.postValue(post_List);
                UsersModel.instance.getCurrentUser((id)->{
                    my_posts_list_ld.postValue(PostLocalDB.db.postDao().getPosts(id));

                });
            });
        });
    }
    public LiveData<List<Post>> getAll(){return post_list_ld;}
    public LiveData<List<Post>> getMyPosts(){return my_posts_list_ld;}
     public interface getAllPostsListener{
        void onComplete(List<Post> data);
    }

    public interface addPostListener {
        void onComplete();
    }

    public interface getPostByIdListener{
        void onComplete(Post p);
    }
    public Post getPostById(String post_id, getPostByIdListener listener){
        return PostLocalDB.db.postDao().getPost(post_id);
    }

    public void addPost(Post post, addPostListener listener){
        postModelFireBase.addPost(post, listener::onComplete);
    }

    public interface fireBaseDataListener{
        void onComplete();
    }

    private void fireBaseDataChanged(fireBaseDataListener listener){
        postModelFireBase.listenToChanges(()->{
            reloadPostsList();
            listener.onComplete();
        });
    }
    public interface deletePostListener {
        void onComplete();
    }
    public void deletePost(String postId, deletePostListener listener) {
        postModelFireBase.deletePost(postId, ()->{
            reloadPostsList();
            listener.onComplete();
        });

    }


    public interface saveImageListener {
        void onComplete(String url);
    }

    public void saveImage(Bitmap img_bitmap,String post_id, saveImageListener listener) {
        postModelFireBase.saveImage(img_bitmap,post_id,listener);
    }

}


