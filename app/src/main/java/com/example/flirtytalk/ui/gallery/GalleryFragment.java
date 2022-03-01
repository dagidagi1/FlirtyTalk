package com.example.flirtytalk.ui.gallery;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.flirtytalk.Model.Post;
import com.example.flirtytalk.Model.UsersModel;
import com.example.flirtytalk.R;
import com.example.flirtytalk.databinding.FragmentGalleryBinding;
import com.example.flirtytalk.ui.home.HomeFragment;
import com.example.flirtytalk.ui.home.HomeFragmentDirections;
import com.example.flirtytalk.ui.home.HomeViewModel;

import java.util.List;

public class GalleryFragment extends Fragment {

    private FragmentGalleryBinding binding;
    GalleryViewModel viewModel;
    NavController navController;
    RecyclerView gallery_rv;
    String id;
    MyAdapter adapter;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        //viewModel = new ViewModelProvider(this).get(GalleryViewModel.class);
        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        viewModel = new ViewModelProvider(this).get(GalleryViewModel.class);
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

//    private void updateData(){
//        //swipe refresh
//        PostModel.instance.getAllPosts((d)->{
//            viewModel.setData(d);
//            adapter.notifyDataSetChanged();
//            Log.d("tag","Gavno updated"+ d.size());
//        });
//    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        UsersModel.instance.getCurrentUser((userId) -> {
            id = userId;
        });
        navController = Navigation.findNavController(view);
        gallery_rv = view.findViewById(R.id.gallery_rv);
        //gallery_rv.setHasFixedSize(true);
        gallery_rv.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        //data = null;
        adapter = new MyAdapter();
        gallery_rv.setAdapter(adapter);
        adapter.setOnItemClickListener((position) -> {
            Log.d("TAG", "" + position);
        });
        viewModel.getData().observe(getViewLifecycleOwner(), (post_List)-> {
            adapter.notifyDataSetChanged();
        });
    }

    static class MyViewHolder extends RecyclerView.ViewHolder{
        //init all row values.

        //photo
        TextView name_tv, age_tv, city_tv, gender_tv;

        public MyViewHolder(@NonNull View itemView, OnItemClickListner m_listener) {
            super(itemView);
            name_tv = itemView.findViewById(R.id.post_view_name_tv);
            city_tv = itemView.findViewById(R.id.post_view_city);
            gender_tv = itemView.findViewById(R.id.post_view_gender_tv);
            age_tv = itemView.findViewById(R.id.post_view_age_tv);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(m_listener != null){
                        int pos = getAdapterPosition();
                        if(pos != RecyclerView.NO_POSITION) {
                            m_listener.onClick(pos);
                            Log.d("tag", ""+pos);
                            GalleryFragmentDirections.ActionNavGalleryToEditPostFragment action = GalleryFragmentDirections.actionNavGalleryToEditPostFragment("" + pos);
                            Navigation.findNavController(v).navigate(action);
                        }
                    }
                }});
        }
    }

    interface OnItemClickListner{
        void onClick(int position);
    }
    public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {
        private OnItemClickListner m_listener;
        void setOnItemClickListener(OnItemClickListner listener){
            m_listener = listener;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            //init row
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_view, null);
            MyViewHolder holder = new MyViewHolder(v, m_listener);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
//            init row data
//            set photo.
//            Log.d("ffag",holder.phone_tv.toString());
//
            holder.name_tv.setText(viewModel.getData().getValue().get(position).getUser_id());
            holder.age_tv.setText(viewModel.getData().getValue().get(position).getCity());
            holder.gender_tv.setText(String.valueOf(viewModel.getData().getValue().get(position).getDeleted()));
            holder.city_tv.setText(viewModel.getData().getValue().get(position).getCity());
        }

        @Override
        public int getItemCount() {
            if(viewModel.getData().getValue() == null) return 0;
            return viewModel.getData().getValue().size();
        }
    }

}