package com.example.flirtytalk.ui.gallery;

import static android.app.Activity.RESULT_OK;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.flirtytalk.Model.PostModel;
import com.example.flirtytalk.R;
import com.example.flirtytalk.ui.add_post.AddPostFragment;
import com.example.flirtytalk.ui.post_details.PostDetailsFragmentArgs;
import com.squareup.picasso.Picasso;

public class EditPostFragment extends Fragment {
    EditText city_tv, phone_tv, bio_tv;
    ImageView post_img;
    Bitmap postPicBitmap;
    Button edit_btn, delete_btn;
    ImageButton take_pic_btn;
    EditPostViewModel viewModel;
    NavController navController;
    ProgressBar progressBar;

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, AddPostFragment.REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == AddPostFragment.REQUEST_IMAGE_CAPTURE &&
                resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            postPicBitmap = (Bitmap) extras.get("data");
            post_img.setImageBitmap(postPicBitmap);
        }
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        viewModel = new ViewModelProvider(this).get(EditPostViewModel.class);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        viewModel.setPostId(PostDetailsFragmentArgs.fromBundle(getArguments()).getPos());
        edit_btn.setEnabled(false);
        delete_btn.setEnabled(false);
        take_pic_btn.setEnabled(false);
        progressBar.setVisibility(View.VISIBLE);
        PostModel.instance.getPostById(viewModel.getPostId(), (post) -> {
            Picasso.get().load(post.getPhoto()).resize(800, 800).centerInside().into(post_img);
            phone_tv.setText(post.getPhone());
            bio_tv.setText(post.getText());
            city_tv.setText(post.getCity());
            edit_btn.setEnabled(true);
            delete_btn.setEnabled(true);
            take_pic_btn.setEnabled(true);
            progressBar.setVisibility(View.GONE);
        });
        edit_btn.setOnClickListener(v -> editPost(view));
        delete_btn.setOnClickListener(v -> deletePost());
        take_pic_btn.setOnClickListener(x->dispatchTakePictureIntent());
    }

    private void deletePost() {
        edit_btn.setEnabled(false);
        delete_btn.setEnabled(false);
        take_pic_btn.setEnabled(false);
        progressBar.setVisibility(View.VISIBLE);
        PostModel.instance.deletePost(viewModel.getPostId(),()->{
            navController.navigateUp();
        });
    }

    private void editPost(View view){
        edit_btn.setEnabled(false);
        delete_btn.setEnabled(false);
        take_pic_btn.setEnabled(false);
        progressBar.setVisibility(View.VISIBLE);
        PostModel.instance.getPostById(viewModel.getPostId(), (post)->{
            post.setPhone(phone_tv.getText().toString());
            post.setText(bio_tv.getText().toString());
            post.setCity(city_tv.getText().toString());
            if(postPicBitmap == null){
                PostModel.instance.addPost(post, () -> {
                    navController.navigateUp();
                });
            }
            else {
                PostModel.instance.saveImage(postPicBitmap, post.getId(), (url) -> {
                    post.setPhoto(url);
                    PostModel.instance.addPost(post, () -> {
                        navController.navigateUp();
                    });
                });
            }
        });

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_post, container, false);
        phone_tv = view.findViewById(R.id.edit_post_phone);
        city_tv = view.findViewById(R.id.edit_post_city);
        bio_tv = view.findViewById(R.id.edit_post_bio);
        delete_btn = view.findViewById(R.id.edit_post_delete_btn);
        edit_btn = view.findViewById(R.id.edit_post_approve_btn);
        post_img = view.findViewById(R.id.edit_post_profile_pic);
        take_pic_btn = view.findViewById(R.id.edit_post_take_pic_btn);
        progressBar = view.findViewById(R.id.edit_post_progress_bar);
        return view;
    }
}