package com.example.flirtytalk.ui.gallery;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.flirtytalk.Model.Post;
import com.example.flirtytalk.Model.PostModel;
import com.example.flirtytalk.R;
import com.example.flirtytalk.ui.home.HomeViewModel;
import com.example.flirtytalk.ui.post_details.PostDetailsFragmentArgs;

public class EditPostFragment extends Fragment {
    EditText city_tv, phone_tv, bio_tv;
    Button edit_btn, delete_btn;
    HomeViewModel viewModel;
    NavController navController;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        int pos = Integer.valueOf(PostDetailsFragmentArgs.fromBundle(getArguments()).getPos());
        phone_tv.setText(viewModel.getData().getValue().get(pos).getUser_id());
        bio_tv.setText(viewModel.getData().getValue().get(pos).getText());
        city_tv.setText(viewModel.getData().getValue().get(pos).getCity());
        edit_btn.setOnClickListener(v->editPost(view,pos));
    }
    private void editPost(View view,int pos){
        Post p = viewModel.getData().getValue().get(pos);
        p.setPhone(phone_tv.getText().toString());
        p.setText(bio_tv.getText().toString());
        p.setCity(city_tv.getText().toString());
        PostModel.instance.addPost(p,()->{
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

        return view;
    }
}