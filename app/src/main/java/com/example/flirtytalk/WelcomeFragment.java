package com.example.flirtytalk;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class WelcomeFragment extends Fragment {

    FirebaseAuth mAuth;
    NavController navController;

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
        mAuth = FirebaseAuth.getInstance();
        navController = Navigation.findNavController(view);
        Button login_btn = view.findViewById(R.id.to_login_frame_btn);
        Button register_btn = view.findViewById(R.id.to_register_frame_btn);
        login_btn.setOnClickListener(p -> navController.navigate(R.id.action_welcomeFragment_to_logInFragment));
        register_btn.setOnClickListener(p -> navController.navigate(R.id.action_welcomeFragment_to_registerFragment));
    }

    /*@Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            String id = currentUser.getUid();
            WelcomeFragmentDirections.ActionWelcomeFragmentToHomeFragment action = WelcomeFragmentDirections.actionWelcomeFragmentToHomeFragment(id);
            navController.navigate(action);
        }
    }*/
}