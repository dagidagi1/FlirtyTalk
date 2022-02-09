package com.example.flirtytalk;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;

public class HomeFragment extends Fragment {

    NavController navController;
    TextView idTV;
    RecyclerView home_rv;
    String id;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        id = HomeFragmentArgs.fromBundle(getArguments()).getId();
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    RecyclerView posts_list;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        idTV = view.findViewById(R.id.home_id);
        idTV.setText(id);
        home_rv = view.findViewById(R.id.home_rv);
        home_rv.setHasFixedSize(true);
        home_rv.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        MyAdapter adapter = new MyAdapter(16);
        home_rv.setAdapter(adapter);
    }
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
                data.add("element " + i);
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