package com.example.flirtytalk.ui.register;

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

import com.example.flirtytalk.Activities.GeneralActivity;
import com.example.flirtytalk.Model.User;
import com.example.flirtytalk.Model.UsersModel;
import com.example.flirtytalk.R;


public class RegisterFragment extends Fragment {
    private NavController navController;
    private EditText nameEditText, emailEditText, passwordEditText;
    private Switch genderSwitch;
    private ProgressBar progressBar;
    private Button reg_btn;
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
        String gender = genderSwitch.isActivated() ? getString(R.string.f) : getString(R.string.f);
        if(name.isEmpty())
        {
            nameEditText.setError(getString(R.string.name_is_required));
            nameEditText.requestFocus();
            return;
        }
        if(email.isEmpty())
        {
            emailEditText.setError(getString(R.string.name_is_required));
            emailEditText.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            emailEditText.setError(getString(R.string.please_provide_valid_email));
            emailEditText.requestFocus();
            return;
        }
        if(password.isEmpty())
        {
            passwordEditText.setError(getString(R.string.password_is_required));
            passwordEditText.requestFocus();
            return;
        }
        if(password.length()<6) {
            passwordEditText.setError(getString(R.string.password_must_be_at_leas_6_characters));
            passwordEditText.requestFocus();
            return;
        }
        reg_btn.setEnabled(false);
        progressBar.setVisibility(View.VISIBLE);
        UsersModel.instance.registerUser(email, password, (id)->{
            if(id != null){
                User user = new User(id, name, gender);
                UsersModel.instance.addUser(user, ()-> {
                    Toast.makeText(getActivity(), getString(R.string.registered_successfully), Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getActivity(), GeneralActivity.class));
                    getActivity().finish();
                });
            }
            else{
                Toast.makeText(getActivity(), getString(R.string.register_not_succeeded), Toast.LENGTH_LONG).show();
                reg_btn.setEnabled(true);
                progressBar.setVisibility(View.GONE);
            }
        });
    }
}