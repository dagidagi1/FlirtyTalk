package com.example.flirtytalk;

import static android.app.Activity.RESULT_OK;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.provider.MediaStore;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.Toast;

import com.example.flirtytalk.Model.User;
import com.example.flirtytalk.Model.UsersModel;



public class RegisterFragment extends Fragment {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    NavController navController;
    EditText fnameEditText,  lnameEditText, phoneNumberEditText, cityEditText, bioEditText, emailEditText, passwordEditText;
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    Switch genderSwitch;
    ProgressBar progressBar;
    Button reg_btn;
    ImageButton camera_btn;
    Bitmap photo;

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
        fnameEditText = view.findViewById(R.id.reg_fname_field);
        lnameEditText = view.findViewById(R.id.reg_lname_field);
        emailEditText = view.findViewById(R.id.reg_email_field);
        phoneNumberEditText = view.findViewById(R.id.reg_phone_number_field);
        passwordEditText = view.findViewById(R.id.reg_password_field);
        cityEditText = view.findViewById(R.id.reg_city_field);
        genderSwitch = view.findViewById(R.id.reg_gender_switch);
        bioEditText = view.findViewById(R.id.reg_bio_field);
        progressBar = view.findViewById(R.id.reg_progress_bar);
        camera_btn = view.findViewById(R.id.reg_camera_btn);
        camera_btn.setOnClickListener(v -> {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
        });
        reg_btn.setOnClickListener(p -> register());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE &&
                resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            photo = (Bitmap) extras.get("data");

        }
    }

    private void register() {
        String fname = fnameEditText.getText().toString();
        String lname = lnameEditText.getText().toString();
        String phoneNumber = phoneNumberEditText.getText().toString();
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String city = cityEditText.getText().toString();
        String bio = bioEditText.getText().toString();
        String gender = genderSwitch.isActivated() ? "f" : "m";
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
        if(password.length()<6) {
            passwordEditText.setError("Password must be at least 6 characters");
            passwordEditText.requestFocus();
            return;
        }
        reg_btn.setEnabled(false);
        progressBar.setVisibility(View.VISIBLE);
        UsersModel.instance.registerUser(email, password, (id)->{
            if(id != null){
                User user = new User(id, fname, lname, phoneNumber,city,gender, bio);
                UsersModel.instance.saveImage(photo, id, (url) ->{
                   user.setPhoto(url);
                   UsersModel.instance.addUser(user, ()-> {
                       Toast.makeText(getActivity(), "Registered successfully", Toast.LENGTH_LONG).show();
                       navController.navigate(R.id.action_registerFragment_to_homeFragment);
                   });
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