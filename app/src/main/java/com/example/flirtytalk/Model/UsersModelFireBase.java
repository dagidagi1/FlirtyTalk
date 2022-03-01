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
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.LinkedList;
import java.util.Objects;


public class UsersModelFireBase {
    public static final String USERS = "Users";
    public static final String AVATAR = "avatar/";

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
                .addOnFailureListener(e -> Log.d("TAG", "Error writing document", e));
    }


    public void getUser(String userId, UsersModel.getUserListener listener) {
        DocumentReference docRef = db.collection(USERS).document(userId);
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

    public void saveImage(Bitmap image, String id, UsersModel.saveImageListener listener) {
        if(image == null){listener.onComplete(null); return;}
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        StorageReference imageRef = storageRef.child(AVATAR + id + ".jpg");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = imageRef.putBytes(data);
        uploadTask.addOnFailureListener(exception -> listener.onComplete(null))
                .addOnSuccessListener(taskSnapshot -> imageRef.getDownloadUrl()
                        .addOnSuccessListener(uri -> {
                            listener.onComplete(uri.toString());
        }));
    }
}
