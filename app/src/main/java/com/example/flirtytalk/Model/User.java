package com.example.flirtytalk.Model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class User {

    @PrimaryKey
    @NonNull
    private String id;
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
    public String getFname(){return fname;}
    public String getLname(){return lname;}
    public String getPhone(){return phone;}
    public String getAddress(){return address;}
    public char getGender(){return gender;}
    public String getBio(){return bio;}
    //get photo

    public void setId(@NonNull String id) {this.id = id;}
    public void setFname(String fname){this.fname = fname;}
    public void setLname(String lname){this.lname = lname;}
    public void setPhone(String phone){this.phone = phone;}
    public void setAddress(String address){this.address = address;}
    public void setGender(char gender){this.gender = gender;}
    public void setBio(String bio){this.bio = bio;}
    //set photo
}
