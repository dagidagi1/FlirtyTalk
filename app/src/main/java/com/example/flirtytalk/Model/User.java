package com.example.flirtytalk.Model;

public class User {
    private String fname, lname, phone, address, password, bio;
    private char gender;
//photo

    public User(String fname,String lname,String phone,String address,String password,char gender,String bio = NULL){
        set_fname(fname);
        set_lname(lname);
        set_phone(phone);
        set_address(address);
        set_password(password);
        set_gender(gender);
        set_bio(bio);
        //photo
    }
    public String get_fname(){return fname;}
    public String get_lname(){return lname;}
    public String get_phone(){return phone;}
    public String get_address(){return address;}
    public String get_password(){return password;}
    public char get_gender(){return gender;}
    public String get_bio(){return bio;}
    //get photo

    public void set_fname(String fname){this.fname = fname;}
    public void set_lname(String lname){this.lname = lname;}
    public void set_phone(String phone){this.phone = phone;}
    public void set_address(String address){this.address = address;}
    public void set_password(String password){this.password = password;}
    public void set_gender(char gender){this.gender = gender;}
    public void set_bio(String bio){this.bio = bio;}
    //set photo
}
