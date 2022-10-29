package com.example.foodmania.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodmania.Api_Models.AllPosts;
import com.example.foodmania.R;
import com.example.foodmania.UI_Models.DashboardModel;


import java.util.ArrayList;

public class DashboardAdapter extends RecyclerView.Adapter<DashboardAdapter.viewHolder> {
    ArrayList<DashboardModel> list;
    Context context;

    public DashboardAdapter(ArrayList<DashboardModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.post_design, parent, false);
        return new viewHolder(view);
    }

    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        DashboardModel model = list.get(position);
        holder.profile.setImageBitmap(model.getProfile());
        holder.dashImg.setImageBitmap(model.getDashboardImg());
        holder.name.setText(model.getUser_name());
        holder.bio.setText(model.getBio());
        holder.cap.setText(model.getCap());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        ImageView profile, dashImg, saveImg;
        TextView name, bio,cap;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            profile = itemView.findViewById(R.id.profile_image);
            dashImg = itemView.findViewById(R.id.addStoryImg);
            cap = itemView.findViewById(R.id.cap);
            //saveImg = itemView.findViewById(R.id.saveImg);
            name = itemView.findViewById(R.id.userName);
            bio = itemView.findViewById(R.id.bio);
        }
    }

}
