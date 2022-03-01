package com.example.flirtytalk.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.flirtytalk.Model.Post;
import com.example.flirtytalk.Model.PostModel;
import com.example.flirtytalk.Model.UsersModel;
import com.example.flirtytalk.R;
import com.example.flirtytalk.databinding.FragmentHomeBinding;

import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    NavController navController;
    RecyclerView home_rv;
    String id;
    HomeFragment.MyAdapter adapter;
    List<Post> data;
    private void updateData(){
        PostModel.instance.getAllPosts((d)->{
            data = d;
            adapter.notifyDataSetChanged();
        });
    }
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        UsersModel.instance.getCurrentUser((userId) -> {
            id = userId;
        });
        navController = Navigation.findNavController(view);
        home_rv = view.findViewById(R.id.home_rv);
        home_rv.setHasFixedSize(true);
        home_rv.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        data = null;
        adapter = new HomeFragment.MyAdapter();
        home_rv.setAdapter(adapter);
        adapter.setOnItemClickListener((position) -> {
            Log.d("TAG", "" + position);
        });
        updateData();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder{
        //init all row values.
        TextView name_tv, age_tv, city_tv, gender_tv;
        //photo

        public MyViewHolder(@NonNull View itemView, HomeFragment.OnItemClickListner m_listener) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(m_listener != null){
                        int pos = getAdapterPosition();
                        if(pos != RecyclerView.NO_POSITION) {
                            m_listener.onClick(pos);
                            Log.d("tag", ""+pos);
                            Bundle bundle = new Bundle();
                            HomeFragmentDirections.ActionNavHomeToPostDetailsFragment action = HomeFragmentDirections.actionNavHomeToPostDetailsFragment("" + pos);
                            Navigation.findNavController(v).navigate(action);
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
    public class MyAdapter extends RecyclerView.Adapter<HomeFragment.MyViewHolder> {
        private HomeFragment.OnItemClickListner m_listener;
        void setOnItemClickListener(HomeFragment.OnItemClickListner listener){
            m_listener = listener;
        }

        @NonNull
        @Override
        public HomeFragment.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            //init row
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_view, null);
            HomeFragment.MyViewHolder holder = new HomeFragment.MyViewHolder(v, m_listener);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull HomeFragment.MyViewHolder holder, int position) {
            //init row data
            holder.name_tv.setText(data.get(position).getUser_id());
            holder.age_tv.setText(data.get(position).getAge().toString());
            holder.gender_tv.setText(String.valueOf(data.get(position).getDeleted()));
            holder.city_tv.setText(data.get(position).getCity());
        }

        @Override
        public int getItemCount() {
            if(data == null ) return 0;
            return data.size();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}