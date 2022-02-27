package com.example.flirtytalk.Model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.HashMap;
import java.util.Map;

@Entity
public class User {

    @PrimaryKey
    @NonNull
    private String id = "";
    private String fname;
    private String lname;
    private String phone;
    private String city;
    private String bio;
    private String gender;
    private String photo;

    public User(){}

    public User(@NonNull String id, String fname, String lname, String phone, String city, String gender, String bio){
        this.id = id;
        this.fname = fname;
        this.lname = lname;
        this.phone = phone;
        this.city = city;
        this.gender = gender;
        this.bio = bio;
    }

    @NonNull
    public String getId(){return id;}
    public String getFname(){return fname;}
    public String getLname(){return lname;}
    public String getPhone(){return phone;}
    public String getCity(){return city;}
    public String getGender(){return gender;}
    public String getBio(){return bio;}
    public String getPhoto() {return photo;}

    public void setId(@NonNull String id) {this.id = id;}
    public void setFname(String fname){this.fname = fname;}
    public void setLname(String lname){this.lname = lname;}
    public void setPhone(String phone){this.phone = phone;}
    public void setCity(String city){this.city = city;}
    public void setGender(String gender){this.gender = gender;}
    public void setBio(String bio){this.bio = bio;}
    public void setPhoto(String photo){this.photo = photo;}

    public Map<String, Object> toJson(){
        Map<String, Object> json = new HashMap<>();
        json.put("id", getId());
        json.put("fname", getFname());
        json.put("lname", getLname());
        json.put("phone", getPhone());
        json.put("city", getCity());
        json.put("gender", getGender());
        json.put("bio", getBio());
        json.put("photo", getPhoto());
        return json;
    }

    static User fromJson(Map<String, Object> json){
        String id = ((String)json.get("id"));
        if (id == null)
            return null;
        String fname = ((String)json.get("fname"));
        String lname = ((String)json.get("lname"));
        String phone = ((String)json.get("phone"));
        String address = ((String)json.get("city"));
        String gender = ((String)json.get("gender"));
        String bio = ((String)json.get("bio"));
        String photo = ((String)json.get("photo"));
        User user = new User(id,fname,lname,phone,address,gender,bio);
        user.setPhoto(photo);
        return user;
    }
}
