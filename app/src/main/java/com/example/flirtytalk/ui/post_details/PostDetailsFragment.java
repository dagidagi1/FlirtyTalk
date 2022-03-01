package com.example.flirtytalk.ui.post_details;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.flirtytalk.R;

public class PostDetailsFragment extends Fragment {

    TextView name_tv,age_tv,city_tv,text_tv,phone_tv;
    //photo
    public PostDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
        int pos = getArguments().getInt("pos");

    }
}