package com.example.flirtytalk.Model;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.LinkedList;
import java.util.Objects;


public class UsersModelFireBase {
    public static final String USERS = "Users";
    public static final String ERROR_WRITING_DOCUMENT = "Error writing document";

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth;

    public void getAllUsers(Long since, UsersModel.getAllUsersListener listener) {
        db.collection(USERS)
                .get()
                .addOnCompleteListener(task -> {
                    LinkedList<User> usersList = new LinkedList<>();
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            User user = User.fromJson(document.getData());
                            if(user!= null)
                                usersList.add(user);
                        }
                    }
                    listener.onComplete(usersList);
                }).addOnFailureListener(e -> listener.onComplete(null));

    }

    public void addUser(User user, UsersModel.addUserListener listener) {
        db.collection(USERS).document(user.getId()).set(user.toJson())
                .addOnSuccessListener(unused -> listener.onComplete())
                .addOnFailureListener(e -> Log.d("TAG", ERROR_WRITING_DOCUMENT, e));
    }


    public void registerUser(String email, String password, UsersModel.registerUserListener listener) {
        mAuth = FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                String id = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
                listener.onComplete(id);
            }
            else{
                listener.onComplete(null);
            }
        });
    }

    public void loginUser(String email, String password, UsersModel.loginUserListener listener) {
        mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                String id = mAuth.getCurrentUser().getUid();
                listener.onComplete(id);
            }
            else{
                listener.onComplete(null);
            }
        });
    }

    public void getCurrentUser(UsersModel.getCurrentUserListener listener) {
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null) {
            String id = currentUser.getUid();
            listener.onComplete(id);
        }
        else{
           listener.onComplete(null);
        }
    }

    public void logout() {
        mAuth = FirebaseAuth.getInstance();
        mAuth.signOut();
    }

    public void listenToChanges(UsersModel.fireBaseDataListener listener){
        db.collection("Post").addSnapshotListener( (value, error) -> {
            if (value!=null){
                listener.onComplete();
            }
        });
    }
}
