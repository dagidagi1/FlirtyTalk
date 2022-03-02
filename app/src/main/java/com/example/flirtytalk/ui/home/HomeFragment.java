package com.example.flirtytalk.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.flirtytalk.Model.Post;
import com.example.flirtytalk.Model.UsersModel;
import com.example.flirtytalk.R;
import com.example.flirtytalk.databinding.FragmentHomeBinding;
import com.squareup.picasso.Picasso;

public class HomeFragment extends Fragment {

    private NavController navController;
    private FragmentHomeBinding binding;
    private HomeViewModel viewModel;
    private RecyclerView home_rv;
    private HomeFragment.MyAdapter adapter;
    private String id;
    private SwipeRefreshLayout swipeRefreshLayout;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        viewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        swipeRefreshLayout = root.findViewById(R.id.home_swipe_refresh);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        UsersModel.instance.getCurrentUser((userId) -> {id = userId;});
        navController = Navigation.findNavController(view);
        home_rv = view.findViewById(R.id.home_rv);
        home_rv.setHasFixedSize(true);
        home_rv.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        adapter = new HomeFragment.MyAdapter();
        home_rv.setAdapter(adapter);
        adapter.setOnItemClickListener(() ->{});
        if(viewModel.getData().getValue() == null)
            swipeRefreshLayout.setRefreshing(true);
        viewModel.getData().observe(getViewLifecycleOwner(), (post_List)-> {
            if(swipeRefreshLayout.isRefreshing())
                swipeRefreshLayout.setRefreshing(false);
            adapter.notifyDataSetChanged(); });
        swipeRefreshLayout.setOnRefreshListener(() -> swipeRefreshLayout.setRefreshing(false));
    }

    static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView name_tv, age_tv, city_tv, gender_tv;
        ImageView avatar_img;

        public MyViewHolder(@NonNull View itemView, HomeFragment.OnItemClickListner m_listener) {
            super(itemView);
            name_tv = itemView.findViewById(R.id.post_view_name_tv);
            city_tv = itemView.findViewById(R.id.post_view_city);
            gender_tv = itemView.findViewById(R.id.post_view_gender_tv);
            age_tv = itemView.findViewById(R.id.post_view_age_tv);
            avatar_img = itemView.findViewById(R.id.post_view_avatar);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(m_listener != null){
                        int pos = getAdapterPosition();
                        if(pos != RecyclerView.NO_POSITION) {
                            m_listener.onClick();
                            HomeFragmentDirections.ActionNavHomeToPostDetailsFragment action = HomeFragmentDirections.actionNavHomeToPostDetailsFragment(String.valueOf(pos));
                            Navigation.findNavController(v).navigate(action);
                        }
                    }
                }
            });
        }
    }

    interface OnItemClickListner{
        void onClick();
    }
    public class MyAdapter extends RecyclerView.Adapter<HomeFragment.MyViewHolder> {
        private HomeFragment.OnItemClickListner m_listener;
        void setOnItemClickListener(HomeFragment.OnItemClickListner listener){
            m_listener = listener;
        }

        @NonNull
        @Override
        public HomeFragment.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_view, null);
            HomeFragment.MyViewHolder holder = new HomeFragment.MyViewHolder(v, m_listener);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull HomeFragment.MyViewHolder holder, int position) {
            Post post = viewModel.getData().getValue().get(position);
            UsersModel.instance.getUser(post.getUser_id(), (user)->{
                holder.name_tv.setText(user.getName());
                holder.age_tv.setText(String.valueOf(post.getAge()));
                holder.gender_tv.setText(user.getGender());
                holder.city_tv.setText(post.getCity());
                Picasso.get().load(viewModel.getData().getValue().get(position).getPhoto()).into(holder.avatar_img);
            });
        }

        @Override
        public int getItemCount() {
            if(viewModel.getData().getValue() == null) return 0;
            return viewModel.getData().getValue().size();
        }
    }
}