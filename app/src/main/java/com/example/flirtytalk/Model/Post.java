package com.example.flirtytalk.Model;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.flirtytalk.My_application.MyApplication;
import com.google.firebase.firestore.FieldValue;

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
    public final static String LAST_UPDATED = "LAST_UPDATED";
    private Long lastUpdated = new Long(0);
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

    public Post(@NonNull String id, String user_id, Integer age, String phone,Long ts, String city, String text, String photo, boolean deleted){
        this.id = id;
        this.user_id = user_id;
        this.age = age;
        this.text = text;
        this.phone = phone;
        this.lastUpdated = ts;
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
    public void setLastUpdated(Long lastUpdated) {this.lastUpdated = lastUpdated;}

    public String getId(){return id;}
    public String getUser_id() {return user_id;}
    public Integer getAge(){return age;}
    public String getText() {return text;}
    public String getPhone() {return phone;}
    public String getCity() {return city;}
    public String getPhoto() {return photo;}
    public boolean getDeleted(){return deleted;}
    public Long getLastUpdated() {return lastUpdated;}

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
        json.put(LAST_UPDATED, FieldValue.serverTimestamp());
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
        //Log.d("TAGGG", (String)json.get(LAST_UPDATED));
        //Timestamp ts = (Timestamp)json.get(LAST_UPDATED);
        boolean deleted = (Boolean.getBoolean(Objects.requireNonNull(json.get("deleted")).toString()));
        Post post = new Post(id, user_id, age, phone, null, city, text, photo, false);
        return post;
    }
    static Long getLocalLastUpdated(){
        Long localLastUpdate = MyApplication.getContext().getSharedPreferences("TAG", Context.MODE_PRIVATE)
                .getLong("Post_LAST_UPDATE",0);
        return localLastUpdate;
    }

    static void setLocalLastUpdated(Long date){
        SharedPreferences.Editor editor = MyApplication.getContext()
                .getSharedPreferences("TAG", Context.MODE_PRIVATE).edit();
        editor.putLong("Post_LAST_UPDATE",date);
        editor.commit();
        Log.d("TAG", "new lud " + date);
    }
}
