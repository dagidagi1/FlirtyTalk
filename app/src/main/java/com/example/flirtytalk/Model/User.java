package com.example.flirtytalk.Model;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.flirtytalk.My_application.MyApplication;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FieldValue;

import java.util.HashMap;
import java.util.Map;

@Entity
public class User {
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String GENDER = "gender";
    public static final String LIST_LAST_UPDATED = "USER_LAST_UPDATE";
    public static final String LAST_UPDATED = "last_updated";
    public static final String TAG = "TAG";


    @PrimaryKey
    @NonNull
    private String id = "";
    private String name;
    private String gender;
    private Long lastUpdated = 0L;

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
    public Long getLastUpdated(){return lastUpdated;}


    public void setId(@NonNull String id) {this.id = id;}
    public void setName(String name){this.name = name;}
    public void setGender(String gender){this.gender = gender;}
    public void setLastUpdated(Long lastUpdated){this.lastUpdated = lastUpdated;}

    public Map<String, Object> toJson(){
        Map<String, Object> json = new HashMap<>();
        json.put(ID, getId());
        json.put(NAME, getName());
        json.put(GENDER, getGender());
        json.put(LAST_UPDATED, FieldValue.serverTimestamp());
        return json;
    }

    static User fromJson(Map<String, Object> json){
        String id = ((String)json.get(ID));
        if (id == null)
            return null;
        String name = ((String)json.get(NAME));
        String gender = ((String)json.get(GENDER));
        User user = new User(id,name,gender);
        Timestamp ts = (Timestamp)json.get(LAST_UPDATED);
        user.setLastUpdated(ts.getSeconds());
        return user;
    }

    static Long getLocalLastUpdated(){
        Long localLastUpdate = MyApplication.getContext().getSharedPreferences(TAG, Context.MODE_PRIVATE)
                .getLong(User.LIST_LAST_UPDATED,0);
        return localLastUpdate;
    }

    static void setLocalLastUpdated(Long date){
        SharedPreferences.Editor editor = MyApplication.getContext()
                .getSharedPreferences(TAG, Context.MODE_PRIVATE).edit();
        editor.putLong(User.LIST_LAST_UPDATED,date);
        editor.commit();
    }
}
