package com.example.flirtytalk;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.flirtytalk.Model.Post;
import com.example.flirtytalk.Model.PostModel;
import com.example.flirtytalk.Model.UsersModel;

import java.util.LinkedList;
import java.util.List;

public class HomeFragment extends Fragment {

    NavController navController;
    RecyclerView home_rv;
    String id;
    ImageButton logout_btn;
    MyAdapter adapter;
    List<Post> data;
    private void updateData(){
        PostModel.instance.getAllPosts((d)->{
            data = d;
            adapter.notifyDataSetChanged();
        });
    }
    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        UsersModel.instance.getCurrentUser((userId) -> {
            id = userId;
        });
        navController = Navigation.findNavController(view);
        logout_btn = view.findViewById(R.id.home_logout_btn);
        home_rv = view.findViewById(R.id.home_rv);
        home_rv.setHasFixedSize(true);
        home_rv.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        data = null;
        adapter = new MyAdapter();
        home_rv.setAdapter(adapter);
        adapter.setOnItemClickListener((position)-> {Log.d("TAG", "" + position);});
        logout_btn.setOnClickListener(v -> {
            //UsersModel.instance.logout();
            navController.navigate(R.id.action_homeFragment_to_addPostFragment);
        });
        updateData();
    }
    RecyclerView posts_list;

    static class MyViewHolder extends RecyclerView.ViewHolder{
        //init all row values.
        TextView name_tv, age_tv, city_tv, gender_tv;
        //photo

        public MyViewHolder(@NonNull View itemView, OnItemClickListner m_listener) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(m_listener != null){
                        int pos = getAdapterPosition();
                        if(pos != RecyclerView.NO_POSITION) {
                            m_listener.onClick(pos);
                        }
                    }
                }
            });
            name_tv = itemView.findViewById(R.id.post_view_name_tv);
            city_tv = itemView.findViewById(R.id.post_view_city);
            gender_tv = itemView.findViewById(R.id.post_view_gender_tv);
            age_tv = itemView.findViewById(R.id.post_view_age_tv);
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
            //init row data
            holder.name_tv.setText(data.get(position).getUser_id());
            //holder.age_tv.setText(data.get(position).getAge());
            holder.gender_tv.setText("vezdehod");
            holder.city_tv.setText(data.get(position).getCity());
        }

        @Override
        public int getItemCount() {
            if(data == null ) return 0;
            return data.size();
        }
    }

}