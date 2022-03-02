package com.example.flirtytalk.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.flirtytalk.Activities.GeneralActivity;
import com.example.flirtytalk.Model.UsersModel;
import com.example.flirtytalk.R;


public class LogInFragment extends Fragment {

    private NavController navController;
    private EditText emailEditText, passwordEditText;
    private Button loginBtn;
    private ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_log_in, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        emailEditText = view.findViewById(R.id.login_email_field);
        passwordEditText = view.findViewById(R.id.login_pass_field);
        loginBtn = view.findViewById(R.id.login_log_btn);
        progressBar = view.findViewById(R.id.login_progressBar);
        loginBtn.setOnClickListener(p -> login());
    }

    private void login() {
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        if(email.isEmpty())
        {
            emailEditText.setError(getString(R.string.email_is_required));
            emailEditText.requestFocus();
            return;
        }
        if(password.isEmpty())
        {
            passwordEditText.setError(getString(R.string.password_is_required));
            passwordEditText.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        loginBtn.setEnabled(false);
        UsersModel.instance.loginUser(email, password,(id)->{
            if(id!= null){
                Toast.makeText(getActivity(), getString(R.string.signed_in_successfully), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getActivity(), GeneralActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
            else{
                Toast.makeText(getActivity(), getString(R.string.incorrect_email_or_password), Toast.LENGTH_LONG).show();
                loginBtn.setEnabled(true);
                progressBar.setVisibility(View.GONE);
            }
        });
    }
}