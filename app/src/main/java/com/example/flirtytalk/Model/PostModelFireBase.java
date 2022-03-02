package com.example.flirtytalk.Model;

import android.graphics.Bitmap;
import android.util.Log;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.LinkedList;


public class PostModelFireBase {

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public void getAllPosts(Long since, PostModel.getAllPostsListener listener) {
        db.collection("Post")
                .whereGreaterThanOrEqualTo(Post.LAST_UPDATED, new Timestamp(since, 0))
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


    public void deletePost(String postId, PostModel.deletePostListener listener) {
        DocumentReference docRef = db.collection("Post").document(postId);
        docRef.update("deleted", true)
                .addOnSuccessListener((successListener)->listener.onComplete())
                .addOnFailureListener((e)->{
                    Log.w("TAG", "Error updating document", e);
                    listener.onComplete();
                });
    }

    public void saveImage(Bitmap image, String id, PostModel.saveImageListener listener) {
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

    public void listenToChanges(PostModel.fireBaseDataListener listener){
        db.collection("Post").addSnapshotListener( (value, error) -> {
           if (value!=null){
               listener.onComplete();
           }
        });
    }
}

