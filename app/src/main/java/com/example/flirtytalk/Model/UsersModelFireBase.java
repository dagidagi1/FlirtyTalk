package com.example.flirtytalk.Model;

import com.google.firebase.firestore.FirebaseFirestore;

public class UsersModelFireBase {

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public void getAllUsers(UsersModel.getAllUsersListener listener) {
    }

    public void addUser(User user, UsersModel.addUserListener listener) {
    }

    public void deleteUser(User user, UsersModel.deleteUserListener listener) {
    }

    public void getUser(String userId, UsersModel.getUserListener listener) {
    }

    public void updateUser(User user, UsersModel.updateUserListener listener) {
    }
}
