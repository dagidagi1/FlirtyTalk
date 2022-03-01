package com.example.flirtytalk.ui.post_details;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.flirtytalk.R;
import com.example.flirtytalk.ui.home.HomeFragmentDirections;
import com.example.flirtytalk.ui.home.HomeViewModel;

public class PostDetailsFragment extends Fragment {

    TextView name_tv,age_tv,city_tv,text_tv,phone_tv;
    HomeViewModel viewModel;
    //photo
    public PostDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.fragment_post_details, container, false);
        name_tv = v.findViewById(R.id.post_details_name_tv);
        age_tv = v.findViewById(R.id.post_details_age_tv);
        city_tv = v.findViewById(R.id.post_details_city_tv);
        text_tv = v.findViewById(R.id.post_details_text_tv);
        phone_tv = v.findViewById(R.id.post_details_phone_tv);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView tv = view.findViewById(R.id.post_details_name_tv);
        int pos = Integer.valueOf(PostDetailsFragmentArgs.fromBundle(getArguments()).getPos());
        //Post - viewModel.getData().getValue().get(pos)

        name_tv.setText(viewModel.getData().getValue().get(pos).getUser_id());
        age_tv.setText(viewModel.getData().getValue().get(pos).getAge()+"");
        city_tv.setText(viewModel.getData().getValue().get(pos).getCity());
        text_tv.setText(viewModel.getData().getValue().get(pos).getText());
        phone_tv.setText(viewModel.getData().getValue().get(pos).getPhone());
    }
}