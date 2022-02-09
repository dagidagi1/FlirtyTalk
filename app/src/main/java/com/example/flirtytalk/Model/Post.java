package com.example.flirtytalk.Model;

public class Post {
    private String user_id;
    private int post_id;
    private String text;

    public Post(){
    }
    public Post(String user_id, String text){
        this.user_id = user_id;
        this.text = text;
        //set post id count.
        post_id = 5;
    }
    public void setUser_id(String user_id) {this.user_id = user_id;}
    public void setText(String text) {this.text = text;}
    public void setPost_id(int post_id) {this.post_id = post_id;}

    public String getUser_id() {return user_id;}
    public String getText() {return text;}
    public int getPost_id() {return post_id;}
}
