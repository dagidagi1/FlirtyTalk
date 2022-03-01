package com.example.flirtytalk.Model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Entity
public class Post {
    @PrimaryKey
    @NonNull
    private String id = "";
    private String user_id, phone, text, city, photo;
    private Integer age;
    boolean deleted;
    //private final Boolean t = true;

    public Post(){
    }
    public Post(String user_id, Integer age, String phone, String city, String text, String photo){
        int t = (int) (System.currentTimeMillis());
        Timestamp tsTemp = new Timestamp(t);
        String ts =  tsTemp.toString();
        id = user_id + ts;
        this.user_id = user_id;
        this.age = age;
        this.text = text;
        this.phone = phone;
        this.city = city;
        this.photo = photo;
        deleted = false;
    }

    public Post(@NonNull String id, String user_id, Integer age, String phone, String city, String text, String photo, boolean deleted){
        this.id = id;
        this.user_id = user_id;
        this.age = age;
        this.text = text;
        this.phone = phone;
        this.city = city;
        this.photo = photo;
        this.deleted = deleted;
    }


    public void setId(String id) {this.id = id;}
    public void setUser_id(String user_id) {this.user_id = user_id;}
    public void setAge(Integer age){this.age = age;}
    public void setText(String text) {this.text = text;}
    public void setPhone(String phone) {this.phone = phone;}
    public void setCity(String city) {this.city = city;}
    public void setPhoto(String photo) {this.photo = photo;}
    public void setDeleted(boolean deleted){this.deleted = deleted;}

    public String getId(){return id;}
    public String getUser_id() {return user_id;}
    public Integer getAge(){return age;}
    public String getText() {return text;}
    public String getPhone() {return phone;}
    public String getCity() {return city;}
    public String getPhoto() {return photo;}
    public boolean getDeleted(){return deleted;}

    public Map<String, Object> toJson() {
        Map<String, Object> json = new HashMap<>();
        json.put("id", getId());
        json.put("user_id", getUser_id());
        json.put("age", getAge());
        json.put("text", getText());
        json.put("phone", getPhone());
        json.put("city", getCity());
        json.put("photo", getPhoto());
        json.put("deleted", getDeleted());
        return json;
    }

    public static Post fromJson(Map<String, Object> json) {
        String id = ((String)json.get("id"));
        if (id == null)
            return null;
        String user_id = ((String)json.get("user_id"));
        String text = ((String)json.get("text"));
        String phone = ((String)json.get("phone"));
        Integer age = Integer.valueOf(Objects.requireNonNull(json.get("age")).toString());
        String city = ((String)json.get("city"));
        String photo = ((String)json.get("photo"));
        boolean deleted = (Boolean.getBoolean(Objects.requireNonNull(json.get("deleted")).toString()));
        Post post = new Post(id, user_id, age, phone, city, text, photo, false);
        return post;
    }
}
