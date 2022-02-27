package com.example.flirtytalk.Model;

import android.graphics.Bitmap;
import android.net.Uri;
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
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Objects;


public class PostModelFireBase {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth;

    public void getAllPosts(PostModel.getAllPostsListener listener) {
        db.collection("Post")
                .get()
                .addOnCompleteListener(task -> {
                    LinkedList<Post> postsList = new LinkedList<>();
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Post post = Post.fromJson(document.getData());
                            if(post!= null)
                                postsList.add(post);
                        }
                    }
                    listener.onComplete(postsList);
                }).addOnFailureListener(e -> listener.onComplete(null));

    }

    public void addPost(Post post, PostModel.addPostListener listener) {
        db.collection("Post").document(post.getId()).set(post.toJson())
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

    public void saveImage(Bitmap image, String id, UsersModel.saveImageListener listener) {
        if(image == null){listener.onComplete(null); return;}
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        StorageReference imageRef = storageRef.child("avatar/" + id + ".jpg");
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

