package com.example.flirtytalk.Model;

import android.graphics.Bitmap;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.storage.FirebaseStorage;

import java.util.LinkedList;
import java.util.Objects;


public class UsersModelFireBase {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth;

    public void getAllUsers(UsersModel.getAllUsersListener listener) {
        db.collection("Users")
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
        db.collection("Users").document(user.getId()).set(user.toJson())
                .addOnSuccessListener(unused -> listener.onComplete())
                .addOnFailureListener(e -> Log.d("TAG", "Error writing document", e));
    }


    public void getUser(String userId, UsersModel.getUserListener listener) {
        DocumentReference docRef = db.collection("Users").document(userId);
        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    User user = User.fromJson(document.getData());
                    listener.onComplete(user);
                } else {
                    listener.onComplete(null);
                }
            } else {
                Log.d("TAG", "get failed with ", task.getException());
                listener.onComplete(null);
            }
        });
    }

    public void updateUser(User user, UsersModel.updateUserListener listener) {
        DocumentReference userRef = db.collection("Users").document(user.getId());
        userRef
                .update("phone", user.getPhone(),"bio", user.getBio())
                .addOnSuccessListener((successListener)-> listener.onComplete())
                .addOnFailureListener((e)->{
                    Log.w("TAG", "Error updating document", e);
                    listener.onComplete();
                });
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

    public void saveImage(Bitmap image, UsersModel.saveImageListener listener) {
        FirebaseStorage storage = FirebaseStorage.getInstance();

    }
}
