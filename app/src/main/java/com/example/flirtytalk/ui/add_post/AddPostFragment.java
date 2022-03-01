package com.example.flirtytalk.ui.add_post;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.flirtytalk.Model.Post;
import com.example.flirtytalk.Model.PostModel;
import com.example.flirtytalk.Model.UsersModel;
import com.example.flirtytalk.R;

import java.util.ArrayList;
import java.util.List;

public class AddPostFragment extends Fragment {

    private TextView age_tv, city_tv, phone_tv, bio_tv;
    private Spinner spn;
    private int age;
    private ImageView post_pic;
    private Button add_btn;
    private ImageButton take_pic_btn;
    private Bitmap postPicBitmap;
    NavController navController;
    public AddPostFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =inflater.inflate(R.layout.fragment_add_post, container, false);
        add_btn = v.findViewById(R.id.new_post_add_btn);
        //age_tv = v.findViewById(R.id.new_post_age);
        city_tv = v.findViewById(R.id.new_post_city);
        phone_tv = v.findViewById(R.id.new_post_phone);
        bio_tv = v.findViewById(R.id.new_post_bio);
        take_pic_btn = v.findViewById(R.id.new_post_take_pic_btn);
        post_pic = v.findViewById(R.id.new_post_profile_pic);
        postPicBitmap = null;
        List ages = new ArrayList<Integer>();
        for (int i = 18; i < 100; i++) {
            ages.add(Integer.toString(i));
        }
        ArrayAdapter<Integer> saAdapter = new ArrayAdapter<Integer>(this.getContext(), android.R.layout.simple_spinner_item, ages);
        saAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn = (Spinner)v.findViewById(R.id.spn);
        spn.setAdapter(saAdapter);
        return v;
    }

    public void add_post(View view){
        String  city, phone, bio;
        age = 18 + spn.getSelectedItemPosition();
        city = city_tv.getText().toString();
        phone = phone_tv.getText().toString();
        bio = bio_tv.getText().toString();
        UsersModel.instance.getCurrentUser((id)->{
            Post p =new Post(id, age, phone, city, bio, null);
            PostModel.instance.saveImage(postPicBitmap,p.getId(),(url)->{
                p.setPhoto(url);
                Toast.makeText(getActivity(), ""+ url, Toast.LENGTH_LONG).show();
                PostModel.instance.addPost(p,()->{
                    navController.navigateUp();
                    Toast.makeText(getActivity(), ""+ p.getId(), Toast.LENGTH_LONG).show();
                });
            });
        });
    }
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
            post_pic.setImageBitmap(postPicBitmap);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        add_btn.setOnClickListener(unused -> add_post(view));
        take_pic_btn.setOnClickListener(unused-> dispatchTakePictureIntent());
    }
}