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
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;


public class RegisterFragment extends Fragment {

    private FirebaseAuth mAuth;
    NavController navController;
    EditText emailEditText, passwordEditText;

    public RegisterFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mAuth = FirebaseAuth.getInstance();
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        Button reg_btn = view.findViewById(R.id.reg_reg_btn);
        emailEditText = view.findViewById(R.id.reg_email_field);
        passwordEditText = view.findViewById(R.id.reg_password_field);

        reg_btn.setOnClickListener(p -> register());
    }

    private void register() {
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                Toast.makeText(getActivity(), "Registered successfully", Toast.LENGTH_LONG).show();
                navController.navigate(R.id.action_registerFragment_to_homeFragment);
            }
            else{
                Toast.makeText(getActivity(), "Register not succeeded", Toast.LENGTH_LONG).show();
            }
        });
    }
}