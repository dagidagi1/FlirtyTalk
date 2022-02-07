package com.example.flirtytalk;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavGraph;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class WelcomeFragment extends Fragment {


    public WelcomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_welcome, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        NavController navcontroller = Navigation.findNavController(view);
        Button login_btn = view.findViewById(R.id.to_login_frame_btn);
        Button register_btn = view.findViewById(R.id.to_register_frame_btn);
        Button enter_btn = view.findViewById(R.id.login_no_user_enter_btn);
        login_btn.setOnClickListener(p -> navcontroller.navigate(R.id.action_welcomeFragment_to_logInFragment));
        register_btn.setOnClickListener(p -> navcontroller.navigate(R.id.action_welcomeFragment_to_registerFragment));
        enter_btn.setOnClickListener(p->navcontroller.navigate(R.id.action_welcomeFragment_to_homeFragment));
    }
}