package com.example.flirtytalk.ui.welcome;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.flirtytalk.R;

public class WelcomeFragment extends Fragment {
    private NavController navController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_welcome, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        Button login_btn = view.findViewById(R.id.to_login_frame_btn);
        Button register_btn = view.findViewById(R.id.to_register_frame_btn);
        login_btn.setOnClickListener(p -> navController.navigate(R.id.action_welcomeFragment_to_logInFragment));
        register_btn.setOnClickListener(p -> navController.navigate(R.id.action_welcomeFragment_to_registerFragment));
    }
}