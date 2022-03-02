package com.example.flirtytalk.ui.post_details;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.flirtytalk.Model.UsersModel;
import com.example.flirtytalk.R;
import com.example.flirtytalk.ui.home.HomeViewModel;
import com.squareup.picasso.Picasso;

public class PostDetailsFragment extends Fragment {

    private TextView name_tv,age_tv,city_tv,text_tv,phone_tv;
    private ImageView avatar_img;
    private HomeViewModel viewModel;
    private ImageButton call_me_btn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.fragment_post_details, container, false);
        name_tv = v.findViewById(R.id.post_details_name_tv);
        age_tv = v.findViewById(R.id.post_details_age_tv);
        city_tv = v.findViewById(R.id.post_details_city_tv);
        text_tv = v.findViewById(R.id.post_details_text_tv);
        phone_tv = v.findViewById(R.id.post_details_phone_tv);
        avatar_img = v.findViewById(R.id.post_details_avatar_iv);
        call_me_btn = v.findViewById(R.id.post_details_callme_image_btn);
        return v;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView tv = view.findViewById(R.id.post_details_name_tv);
        int pos = Integer.valueOf(PostDetailsFragmentArgs.fromBundle(getArguments()).getPos());
        call_me_btn.setEnabled(false);
        UsersModel.instance.getUser(viewModel.getData().getValue().get(pos).getUser_id(),(user)->{
            name_tv.setText(user.getName());
            age_tv.setText(viewModel.getData().getValue().get(pos).getAge()+"");
            city_tv.setText(viewModel.getData().getValue().get(pos).getCity());
            text_tv.setText(viewModel.getData().getValue().get(pos).getText());
            phone_tv.setText(viewModel.getData().getValue().get(pos).getPhone());
            Picasso.get().load(viewModel.getData().getValue().get(pos).getPhoto()).resize(800,800).centerInside().into(avatar_img);
            call_me_btn.setOnClickListener(x -> redirectToDialer(viewModel.getData().getValue().get(pos).getPhone()));
            call_me_btn.setEnabled(true);

        });
    }

    private void redirectToDialer(String phone) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:"+phone));
        startActivity(intent);
    }
}