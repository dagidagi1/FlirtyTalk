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
import java.util.Objects;
import java.util.Random;

@Entity
public class Post {
    public final static String LAST_UPDATED = "LAST_UPDATED";
    public static final String USER_ID = "user_id";
    public static final String ID = User.ID;
    public static final String AGE = "age";
    public static final String TEXT = "text";
    public static final String CITY = "city";
    public static final String PHOTO = "photo";
    public static final String DELETED = "deleted";
    public static final String TRUE = "true";
    private static final String PHONE = "phone";
    public static final String TAG = "TAG";
    public static final String POST_LAST_UPDATE = "Post_LAST_UPDATE";

    @PrimaryKey
    @NonNull
    private String id = "";
    private String user_id, phone, text, city, photo;
    private Integer age;
    boolean deleted;
    private Long lastUpdated = 0L;

    public Post(){
    }
    public Post(String user_id, Integer age, String phone, String city, String text, String photo){
        Random random = new Random();
        id = String.valueOf(random.nextLong());
        this.user_id = user_id;
        this.age = age;
        this.text = text;
        this.phone = phone;
        this.city = city;
        this.photo = photo;
        deleted = false;
    }

    public Post(@NonNull String id, String user_id, Integer age, String phone, String city, String text, String photo, boolean deleted, Long lastUpdated){
        this.id = id;
        this.user_id = user_id;
        this.age = age;
        this.text = text;
        this.phone = phone;
        this.city = city;
        this.photo = photo;
        this.deleted = deleted;
        this.lastUpdated = lastUpdated;
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
        json.put(ID, getId());
        json.put(USER_ID, getUser_id());
        json.put(AGE, getAge());
        json.put(TEXT, getText());
        json.put(PHONE, getPhone());
        json.put(CITY, getCity());
        json.put(PHOTO, getPhoto());
        json.put(DELETED, getDeleted());
        json.put(LAST_UPDATED, FieldValue.serverTimestamp());
        return json;
    }

    public static Post fromJson(Map<String, Object> json) {
        String id = ((String)json.get(ID));
        if (id == null)
            return null;
        String user_id = ((String)json.get(USER_ID));
        String text = ((String)json.get(TEXT));
        String phone = ((String)json.get(PHONE));
        Integer age = Integer.valueOf(Objects.requireNonNull(json.get(AGE)).toString());
        String city = ((String)json.get(CITY));
        String photo = ((String)json.get(PHOTO));
        Timestamp ts = (Timestamp)json.get(LAST_UPDATED);
        boolean deleted = json.get(DELETED).toString().equals(TRUE);
        Post post = new Post(id, user_id, age, phone, city, text, photo, deleted, ts.getSeconds());
        return post;
    }
    static Long getLocalLastUpdated(){
        Long localLastUpdate = MyApplication.getContext().getSharedPreferences(TAG, Context.MODE_PRIVATE)
                .getLong(POST_LAST_UPDATE,0);
        return localLastUpdate;
    }

    static void setLocalLastUpdated(Long date){
        SharedPreferences.Editor editor = MyApplication.getContext()
                .getSharedPreferences(TAG, Context.MODE_PRIVATE).edit();
        editor.putLong(POST_LAST_UPDATE,date);
        editor.commit();
    }
}
