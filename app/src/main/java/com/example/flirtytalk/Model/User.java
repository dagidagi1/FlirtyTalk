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
    private String id;
    private String fname, lname, phone, address, bio, gender;
//photo

    public User(){}

    public User(@NonNull String id, String fname,String lname,String phone,String address,String gender,String bio){
        this.id = id;
        this.fname = fname;
        this.lname = lname;
        this.phone = phone;
        this.address = address;
        this.gender = gender;
        this.bio = bio;
        //photo
    }

    @NonNull
    public String getId(){return id;}
    public String getFname(){return fname;}
    public String getLname(){return lname;}
    public String getPhone(){return phone;}
    public String getAddress(){return address;}
    public String getGender(){return gender;}
    public String getBio(){return bio;}
    //get photo

    public void setId(@NonNull String id) {this.id = id;}
    public void setFname(String fname){this.fname = fname;}
    public void setLname(String lname){this.lname = lname;}
    public void setPhone(String phone){this.phone = phone;}
    public void setAddress(String address){this.address = address;}
    public void setGender(String gender){this.gender = gender;}
    public void setBio(String bio){this.bio = bio;}
    //set photo

    public Map<String, Object> toJson(){
        Map<String, Object> json = new HashMap<>();
        json.put("id", getId());
        json.put("fname", getFname());
        json.put("lname", getLname());
        json.put("phone", getPhone());
        json.put("address", getAddress());
        json.put("gender", getGender());
        json.put("bio", getBio());
        //json.put("photo", user.getPhoto());
        return json;
    }

    static User fromJson(Map<String, Object> json){
        String id = ((String)json.get("id"));
        if (id == null)
            return null;
        String fname = ((String)json.get("fname"));
        String lname = ((String)json.get("lname"));
        String phone = ((String)json.get("phone"));
        String address = ((String)json.get("address"));
        String gender = ((String)json.get("gender"));
        String bio = ((String)json.get("bio"));
        //String photo = ((String)json.get("photo"));
        return new User(id,fname,lname,phone,address,gender,bio);
    }
}
