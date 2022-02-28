package com.example.flirtytalk;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.flirtytalk.Model.User;
import com.example.flirtytalk.Model.UsersModel;



public class RegisterFragment extends Fragment {

    NavController navController;
    EditText nameEditText, emailEditText, passwordEditText;
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    Switch genderSwitch;
    ProgressBar progressBar;
    Button reg_btn;

    public RegisterFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        reg_btn = view.findViewById(R.id.reg_reg_btn);
        nameEditText = view.findViewById(R.id.reg_name_field);
        emailEditText = view.findViewById(R.id.reg_email_field);
        passwordEditText = view.findViewById(R.id.reg_password_field);
        genderSwitch = view.findViewById(R.id.reg_gender_switch);
        progressBar = view.findViewById(R.id.reg_progress_bar);
        reg_btn.setOnClickListener(p -> register());
    }


    private void register() {
        String name = nameEditText.getText().toString();
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String gender = genderSwitch.isActivated() ? "f" : "m";
        if(name.isEmpty())
        {
            nameEditText.setError("First name is required");
            nameEditText.requestFocus();
            return;
        }
        if(email.isEmpty())
        {
            emailEditText.setError("Email is required");
            emailEditText.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            emailEditText.setError("Please provide valid email");
            emailEditText.requestFocus();
            return;
        }
        if(password.isEmpty())
        {
            passwordEditText.setError("Password is required");
            passwordEditText.requestFocus();
            return;
        }
        if(password.length()<6) {
            passwordEditText.setError("Password must be at least 6 characters");
            passwordEditText.requestFocus();
            return;
        }
        reg_btn.setEnabled(false);
        progressBar.setVisibility(View.VISIBLE);
        UsersModel.instance.registerUser(email, password, (id)->{
            if(id != null){
                User user = new User(id, name, gender);
                UsersModel.instance.addUser(user, ()-> {
                    Toast.makeText(getActivity(), "Registered successfully", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getActivity(), GeneralActivity.class));
                    getActivity().finish();
                });
            }
            else{
                Toast.makeText(getActivity(), "Register not succeeded", Toast.LENGTH_LONG).show();
                reg_btn.setEnabled(true);
                progressBar.setVisibility(View.GONE);
            }
        });
    }
}