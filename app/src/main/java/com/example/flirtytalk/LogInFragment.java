package com.example.flirtytalk;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.EmailAuthCredential;
import com.google.firebase.auth.FirebaseAuth;

public class LogInFragment extends Fragment {

    FirebaseAuth mAuth;
    NavController navController;
    EditText emailEditText, passwordEditText;
    Button loginBtn;

    public LogInFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mAuth = FirebaseAuth.getInstance();
        return inflater.inflate(R.layout.fragment_log_in, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        emailEditText = view.findViewById(R.id.login_email_field);
        passwordEditText = view.findViewById(R.id.login_pass_field);
        loginBtn = view.findViewById(R.id.login_log_btn);
        loginBtn.setOnClickListener(p -> login());
    }

    private void login() {
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                Toast.makeText(getActivity(), "Signed in successfully", Toast.LENGTH_LONG).show();
                navController.navigate(R.id.action_logInFragment_to_homeFragment);
            }
            else{
                Toast.makeText(getActivity(), "Not signed in", Toast.LENGTH_LONG).show();
            }
        });
    }
}