package com.example.flirtytalk.Model;

import android.graphics.Bitmap;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.flirtytalk.My_application.MyApplication;

import java.util.List;

public class PostModel {

    PostModelFireBase postModelFireBase = new PostModelFireBase();

    public static final PostModel instance = new PostModel();

    private PostModel(){
        reloadPostsList();
    }
    MutableLiveData<List<Post>> post_list_ld = new MutableLiveData<List<Post>>();
    private void reloadPostsList() {
        //1. get local last update
        Long localLastUpdate = Post.getLocalLastUpdated();
        Log.d("TAG","localLastUpdate: " + localLastUpdate);
        //2. get all students record since local last update from firebase
        postModelFireBase.getAllPosts(localLastUpdate,(list)->{
            MyApplication.executorService.execute(()->{
                //3. update local last update date
                //4. add new records to the local db
                Long lLastUpdate = new Long(0);
                Log.d("TAG", "FB returned " + list.size());
//                for(Post s : list){
//                    PostLocalDB.db.postDao().insert(s);
////                    if (s.getLastUpdated() > lLastUpdate){
//                        lLastUpdate = s.getLastUpdated();
//                    }
//                }
                Post.setLocalLastUpdated(lLastUpdate);

                //5. return all records to the caller
                List<Post> post_List = PostLocalDB.db.postDao().getAllPosts();
                post_list_ld.postValue(post_List);
            });
        });
    }
    public LiveData<List<Post>> getAll(){
        return post_list_ld;
    }

    public interface getAllPostsListener{
        void onComplete(List<Post> data);
    }
    public void getAllPosts(getAllPostsListener listener){

        postModelFireBase.getAllPosts(Post.getLocalLastUpdated(), listener);
        MyApplication.executorService.execute(()->{
            List<Post> data = PostLocalDB.db.postDao().getAllPosts();
            MyApplication.mainHandler.post(()->{
                listener.onComplete(data);
            });
        });
    }

    public interface addPostListener {
        void onComplete();
    }
    public void addPost(Post post, addPostListener listener){
        postModelFireBase.addPost(post, ()->{
            reloadPostsList();
            listener.onComplete();
                });
        MyApplication.executorService.execute(()->{
            PostLocalDB.db.postDao().insert(post);
            MyApplication.mainHandler.post(()->{
                listener.onComplete();
            });
        });
    }

    public interface getPostByIdListener {
        void onComplete(Post post);
    }
    public void getPostById(String postId, getPostByIdListener listener) {
        postModelFireBase.getPostById(postId, listener);
        MyApplication.executorService.execute(()->{
            Post post = PostLocalDB.db.postDao().getPostById(postId);
            MyApplication.mainHandler.post(()->{
                listener.onComplete(post);
            });
        });
    }

    /*public interface updatePostListener {
        void onComplete();
    }
    public void updatePost(Post post, updatePostListener listener) {
        postModelFireBase.updatePost(post, listener);
        *//*MyApplication.executorService.execute(()->{
            UsersLocalDB.db.userDao().update(user);
            MyApplication.mainHandler.post(()->{
                listener.onComplete();
            });
        });*//*
    }*/
    public interface saveImageListener {
        void onComplete(String url);
    }
    public void saveImage(Bitmap img_bitmap,String post_id, saveImageListener listener) {
        postModelFireBase.saveImage(img_bitmap,post_id,listener);
    }

}


