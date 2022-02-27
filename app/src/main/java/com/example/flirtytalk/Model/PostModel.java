package com.example.flirtytalk.Model;

import android.graphics.Bitmap;

import java.util.List;
import java.util.Objects;

public class PostModel {

    UsersModelFireBase usersModelFireBase = new UsersModelFireBase();

    public static final PostModel instance = new PostModel();

    private PostModel(){}

    public interface getAllPostsListener{
        void onComplete(List<Post> data);
    }
    public void getAllPosts(getAllPostsListener listener){
        PostModelFireBase.getAllPosts(listener);
        /*MyApplication.executorService.execute(()->{
            List<User> data = UsersLocalDB.db.userDao().getAllUsers();
            MyApplication.mainHandler.post(()->{
                listener.onComplete(data);
            });
        });*/
    }

    public interface addPostListener {
        void onComplete();
    }
    public void addPost(Post post, addPostListener listener){
        PostModelFireBase.addPost(post, listener);
        /*MyApplication.executorService.execute(()->{
            UsersLocalDB.db.userDao().insert(user);
            MyApplication.mainHandler.post(()->{
                listener.onComplete();
            });
        });*/
    }

    public interface getPostListener {
        void onComplete(Post post);
    }
    public void getPost(String userId, getPostListener listener) {
        PostModelFireBase.getPost(userId, listener);
        /*MyApplication.executorService.execute(()->{
            User user = UsersLocalDB.db.userDao().getUser(userId);
            MyApplication.mainHandler.post(()->{
                listener.onComplete(user);
            });
        });*/
    }

    public interface updatePostListener {
        void onComplete();
    }
    public void updatePost(Post post, updatePostListener listener) {
        PostModelFireBase.updatePost(post, listener);
        /*MyApplication.executorService.execute(()->{
            UsersLocalDB.db.userDao().update(user);
            MyApplication.mainHandler.post(()->{
                listener.onComplete();
            });
        });*/
    }



}


