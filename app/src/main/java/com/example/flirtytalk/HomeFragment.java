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

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.flirtytalk.Model.UsersModel;

import java.util.LinkedList;
import java.util.List;

public class HomeFragment extends Fragment {

    NavController navController;
    RecyclerView home_rv;
    String id;
    ImageButton logout_btn;

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
        UsersModel.instance.getCurrentUser((userId)->{id = userId;});
        navController = Navigation.findNavController(view);
        logout_btn = view.findViewById(R.id.home_logout_btn);
        home_rv = view.findViewById(R.id.home_rv);
        home_rv.setHasFixedSize(true);
        home_rv.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        MyAdapter adapter = new MyAdapter(16);
        home_rv.setAdapter(adapter);
        logout_btn.setOnClickListener(v -> {
            UsersModel.instance.logout();
            navController.navigate(R.id.action_homeFragment_to_welcomeFragment);
        });
    }

    RecyclerView posts_list;

    static class MyViewHolder extends RecyclerView.ViewHolder{
        //init all row values.
        TextView tv;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.post_view_name_tv);

        }
    }

    public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {
        List<String> data;
        MyAdapter(int num){
            data = new LinkedList<String>();
            for(int i = 0; i < num; i++)
                data.add("Name " + i);
        }
        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            //init row
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_view, null);
            MyViewHolder holder = new MyViewHolder(v);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            //init row data
            holder.tv.setText(data.get(position));
        }

        @Override
        public int getItemCount() {
            return data.size();
        }
    }

}