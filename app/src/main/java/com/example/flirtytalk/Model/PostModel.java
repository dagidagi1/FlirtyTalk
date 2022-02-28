package com.example.flirtytalk.Model;

import java.util.List;

public class PostModel {

    PostModelFireBase postModelFireBase = new PostModelFireBase();

    public static final PostModel instance = new PostModel();

    private PostModel(){}

    public interface getAllPostsListener{
        void onComplete(List<Post> data);
    }
    public void getAllPosts(getAllPostsListener listener){
        postModelFireBase.getAllPosts(listener);
        /*MyApplication.executorService.execute(()->{
            List<Post> data = PostLocalDB.db.postDao().getAllPosts();
            MyApplication.mainHandler.post(()->{
                listener.onComplete(data);
            });
        });*/
    }

    public interface addPostListener {
        void onComplete();
    }
    public void addPost(Post post, addPostListener listener){
        postModelFireBase.addPost(post, listener);
        /*MyApplication.executorService.execute(()->{
            PostLocalDB.db.postDao().insert(post);
            MyApplication.mainHandler.post(()->{
                listener.onComplete();
            });
        });*/
    }

    public interface getPostListener {
        void onComplete(Post post);
    }
    public void getPost(String postId, getPostListener listener) {
        postModelFireBase.getPost(postId, listener);
        /*MyApplication.executorService.execute(()->{
            User user = UsersLocalDB.db.userDao().getUser(userId);
            MyApplication.mainHandler.post(()->{
                listener.onComplete(user);
            });
        });*/
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



}


