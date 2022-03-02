package com.example.flirtytalk.ui.logout;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.flirtytalk.Activities.MainActivity;
import com.example.flirtytalk.Model.UsersModel;
import com.example.flirtytalk.R;

public class LogoutFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        UsersModel.instance.logout();
        startActivity(new Intent(getActivity(), MainActivity.class));
        getActivity().finish();
        return inflater.inflate(R.layout.fragment_logout, container, false);
    }
}