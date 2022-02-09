package com.example.flirtytalk.Model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class User {
    @PrimaryKey
    @NonNull
    private String id = "";
    private String fname, lname, phone, address, bio;
    private char gender;
//photo

    public User(){}

    public User(@NonNull String id, String fname,String lname,String phone,String address,char gender,String bio){
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
    public String get_fname(){return fname;}
    public String get_lname(){return lname;}
    public String get_phone(){return phone;}
    public String get_address(){return address;}
    public char get_gender(){return gender;}
    public String get_bio(){return bio;}
    //get photo

    private void set_id(@NonNull String id) {this.id = id;}
    public void set_fname(String fname){this.fname = fname;}
    public void set_lname(String lname){this.lname = lname;}
    public void set_phone(String phone){this.phone = phone;}
    public void set_address(String address){this.address = address;}
    public void set_gender(char gender){this.gender = gender;}
    public void set_bio(String bio){this.bio = bio;}
    //set photo
}
