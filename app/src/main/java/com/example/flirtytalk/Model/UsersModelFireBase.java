package com.example.flirtytalk.Model;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.LinkedList;


public class UsersModelFireBase {

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public void getAllUsers(UsersModel.getAllUsersListener listener) {
        db.collection("Users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        LinkedList<User> usersList = new LinkedList<User>();
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                User user = User.fromJson(document.getData());
                                if(user!= null)
                                    usersList.add(user);
                            }
                        }
                        listener.onComplete(usersList);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                listener.onComplete(null);
            }
        });

    }

    public void addUser(User user, UsersModel.addUserListener listener) {
        db.collection("Users").document(user.getId()).set(user.toJson())
                .addOnSuccessListener((successListener)->{
                    listener.onComplete();})
                .addOnFailureListener((e)->{
                    Log.d("TAG", "get failed with ", e);});
    }

    public void deleteUser(User user, UsersModel.deleteUserListener listener) {
        db.collection("cities").document(user.getId())
                .delete().addOnSuccessListener((successListener)->{
                    listener.onComplete();
                }).addOnFailureListener((e)->{
                    Log.w("TAG", "Error deleting document", e);
                    listener.onComplete();
                });
    }

    public void getUser(String userId, UsersModel.getUserListener listener) {
        DocumentReference docRef = db.collection("Users").document(userId);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
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
            }
        });
    }

    public void updateUser(User user, UsersModel.updateUserListener listener) {
        DocumentReference userRef = db.collection("Users").document(user.getId());
        userRef
                .update("phone", user.getPhone(),"bio", user.getBio())
                .addOnSuccessListener((successListener)->{
                    listener.onComplete();
                })
                .addOnFailureListener((e)->{
                    Log.w("TAG", "Error updating document", e);
                    listener.onComplete();
                });
    }
}
