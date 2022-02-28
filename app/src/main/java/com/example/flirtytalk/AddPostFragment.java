package com.example.flirtytalk;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.flirtytalk.Model.Post;
import com.example.flirtytalk.Model.PostModel;
import com.example.flirtytalk.Model.UsersModel;

import java.util.ArrayList;
import java.util.List;

public class AddPostFragment extends Fragment {

    private TextView age_tv, city_tv, phone_tv, bio_tv;
    private Spinner spn;
    private int age;
    //photo.
    private Button add_btn;
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
            PostModel.instance.addPost(new Post(id, age, phone, city, bio, null),()->{
                Toast.makeText(getActivity(), "" + age + "\n"+city+"\n"+phone+"\n"+bio, Toast.LENGTH_LONG).show();
                Navigation.findNavController(view).navigateUp();
            });
        });
        }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        add_btn.setOnClickListener(unused -> add_post(view));
        //reference to photo.
        //photo


        //PostModel.instance.addPost(new Post());
        //ADD post to db.
    }
}