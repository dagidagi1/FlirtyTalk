package com.example.flirtytalk;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.Toast;

import com.example.flirtytalk.Model.User;
import com.example.flirtytalk.Model.UsersModel;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;


public class RegisterFragment extends Fragment {

    private FirebaseAuth mAuth;
    NavController navController;
    EditText fnameEditText,  lnameEditText, phoneNumberEditText, addressEditText, bioEditText, emailEditText, passwordEditText;
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
        // Inflate the layout for this fragment
        mAuth = FirebaseAuth.getInstance();
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        reg_btn = view.findViewById(R.id.reg_reg_btn);
        fnameEditText = view.findViewById(R.id.reg_fname_field);
        lnameEditText = view.findViewById(R.id.reg_lname_field);
        emailEditText = view.findViewById(R.id.reg_email_field);
        phoneNumberEditText = view.findViewById(R.id.reg_phone_number_field);
        passwordEditText = view.findViewById(R.id.reg_password_field);
        addressEditText = view.findViewById(R.id.reg_address_field);
        genderSwitch = view.findViewById(R.id.reg_gender_switch);
        bioEditText = view.findViewById(R.id.reg_bio_field);
        progressBar = view.findViewById(R.id.reg_progress_bar);
        reg_btn.setOnClickListener(p -> register());
    }

    private void register() {
        String fname = fnameEditText.getText().toString();
        String lname = lnameEditText.getText().toString();
        String phoneNumber = phoneNumberEditText.getText().toString();
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String address = addressEditText.getText().toString();
        String bio = bioEditText.getText().toString();
        char gender = genderSwitch.isActivated() ? 'm' : 'f';
        if(fname.isEmpty())
        {
            fnameEditText.setError("First name is required");
            fnameEditText.requestFocus();
            return;
        }
        if(lname.isEmpty())
        {
            lnameEditText.setError("Last name is required");
            lnameEditText.requestFocus();
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
        if(phoneNumber.isEmpty())
        {
            phoneNumberEditText.setError("Phone number is required");
            phoneNumberEditText.requestFocus();
            return;
        }
        if(password.isEmpty())
        {
            passwordEditText.setError("Password is required");
            passwordEditText.requestFocus();
            return;
        }
        if(password.length()<6)
        {
            passwordEditText.setError("Password must be at least 6 characters");
            passwordEditText.requestFocus();
            return;
        }
        reg_btn.setEnabled(false);
        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                String id = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
                User user = new User(id, fname, lname, phoneNumber,address,gender, bio);
                UsersModel.instance.addUser(user, new UsersModel.addUserListener() {
                    @Override
                    public void onComplete() {
                    }
                });
                Toast.makeText(getActivity(), "Registered successfully", Toast.LENGTH_LONG).show();
                RegisterFragmentDirections.ActionRegisterFragmentToHomeFragment action = RegisterFragmentDirections.actionRegisterFragmentToHomeFragment(id);
                UsersModel.instance.addUser(user, () -> navController.navigate(action));
            }
            else{
                Toast.makeText(getActivity(), "Register not succeeded", Toast.LENGTH_LONG).show();
                reg_btn.setEnabled(true);
                progressBar.setVisibility(View.GONE);
            }
        });
    }
}