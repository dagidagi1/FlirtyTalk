package com.example.flirtytalk.Model;

import java.sql.Timestamp;

public class Post {
    private String id, user_id, phone, text, city, photo;


    public Post(){
    }
    public Post(String user_id, String phone, String city, String text, String photo){
        int t = (int) (System.currentTimeMillis());
        Timestamp tsTemp = new Timestamp(t);
        String ts =  tsTemp.toString();
        id = user_id + ts;
        this.user_id = user_id;
        this.text = text;
        this.phone = phone;
        this.city = city;
        this.photo = photo;
    }
    public void setUser_id(String user_id) {this.user_id = user_id;}
    public void setText(String text) {this.text = text;}
    public void setPhone(String phone) {this.phone = phone;}
    public void setCity(String city) {this.city = city;}
    public void setPhoto(String photo) {this.photo = photo;}

    public String getId(){return id;}
    public String getUser_id() {return user_id;}
    public String getText() {return text;}
    public String getPhone() {return phone;}
    public String getCity() {return city;}
    public String getPhoto() {return photo;}

    public Object toJson() {
    }
}
