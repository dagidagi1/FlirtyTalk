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
    private String name;
    private String gender;

    public User(){}

    public User(@NonNull String id, String name, String gender){
        this.id = id;
        this.name = name;
        this.gender = gender;
    }

    @NonNull
    public String getId(){return id;}
    public String getName(){return name;}
    public String getGender(){return gender;}

    public void setId(@NonNull String id) {this.id = id;}
    public void setName(String name){this.name = name;}
    public void setGender(String gender){this.gender = gender;}

    public Map<String, Object> toJson(){
        Map<String, Object> json = new HashMap<>();
        json.put("id", getId());
        json.put("fname", getName());
        json.put("gender", getGender());
        return json;
    }

    static User fromJson(Map<String, Object> json){
        String id = ((String)json.get("id"));
        if (id == null)
            return null;
        String name = ((String)json.get("name"));
        String gender = ((String)json.get("gender"));
        User user = new User(id,name,gender);
        return user;
    }
}
