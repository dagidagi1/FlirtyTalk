package com.example.flirtytalk.ui.gallery;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flirtytalk.databinding.FragmentGalleryBinding;
import com.example.flirtytalk.ui.home.HomeFragment;
import com.example.flirtytalk.ui.home.HomeViewModel;

public class GalleryFragment extends Fragment {

    private FragmentGalleryBinding binding;
    HomeViewModel viewModel;
    NavController navController;
    RecyclerView home_rv;
    String id;
    HomeFragment.MyAdapter adapter;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        GalleryViewModel galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);

        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textGallery;
        galleryViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }










    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}