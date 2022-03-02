package com.example.flirtytalk.ui.gallery;

import static android.app.Activity.RESULT_OK;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.flirtytalk.Model.Post;
import com.example.flirtytalk.Model.PostModel;
import com.example.flirtytalk.R;
import com.example.flirtytalk.ui.home.HomeViewModel;
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
    static final int REQUEST_IMAGE_CAPTURE = 1;
    final static int RESAULT_SUCCESS = 0;

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE &&
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
        String post_id = PostDetailsFragmentArgs.fromBundle(getArguments()).getPos();
        viewModel.setPost(post_id);
        Picasso.get().load(viewModel.getPost().getPhoto()).resize(800,800).centerInside().into(post_img);
        phone_tv.setText(viewModel.getPost().getPhone());
        bio_tv.setText(viewModel.getPost().getText());
        city_tv.setText(viewModel.getPost().getCity());
        edit_btn.setOnClickListener(v->editPost(view));
        take_pic_btn.setOnClickListener(v->dispatchTakePictureIntent());
//        phone_tv.setText(viewModel.getData().getValue().get(pos).getUser_id());
//        bio_tv.setText(viewModel.getData().getValue().get(pos).getText());
//        city_tv.setText(viewModel.getData().getValue().get(pos).getCity());
//        edit_btn.setOnClickListener(v->editPost(view,pos));
    }
    private void editPost(View view){
        viewModel.getPost().setPhone(phone_tv.getText().toString());
        viewModel.getPost().setText(bio_tv.getText().toString());
        viewModel.getPost().setCity(city_tv.getText().toString());
        PostModel.instance.saveImage(postPicBitmap,viewModel.getPost().getId(),(url)-> viewModel.getPost().setPhoto(url));
        PostModel.instance.addPost(viewModel.getPost(),()->{
            //Toast.makeText(getActivity(), "edited: "+ p.getId(), Toast.LENGTH_LONG).show();
            navController.navigateUp();
        });
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_post, container, false);
        phone_tv = view.findViewById(R.id.edit_post_phone);
        city_tv = view.findViewById(R.id.edit_post_city);
        bio_tv = view.findViewById(R.id.edit_post_bio);
        delete_btn = view.findViewById(R.id.edit_post_approve_btn);
        edit_btn = view.findViewById(R.id.edit_post_approve_btn);
        post_img = view.findViewById(R.id.edit_post_profile_pic);
        take_pic_btn = view.findViewById(R.id.edit_post_take_pic_btn);

        return view;
    }
}