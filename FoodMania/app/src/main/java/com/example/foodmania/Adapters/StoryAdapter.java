package com.example.foodmania.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodmania.R;
import com.example.foodmania.UI_Models.StoryModel;
import com.makeramen.roundedimageview.RoundedImageView;


import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class StoryAdapter extends RecyclerView.Adapter<StoryAdapter.viewHolder> {

    ArrayList<StoryModel> list;
    Context context;

    public StoryAdapter(ArrayList<StoryModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.story_design, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        StoryModel model = list.get(position);
        holder.stroyImg.setImageBitmap(model.getStory());
        holder.profile.setImageBitmap(model.getProfile());
        holder.name.setText(model.getF_name() + " " + model.getL_name());
        // holder.storyType.setImageResource(model.getStoryType());
        // holder.name.setText(model.getName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        CircleImageView profile;
        RoundedImageView stroyImg;
        TextView name;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            stroyImg = itemView.findViewById(R.id.addStoryImg);
            profile = itemView.findViewById(R.id.profile_image);
            // storyType = itemView.findViewById(R.id.storyType);
            name = itemView.findViewById(R.id.name);
        }
    }
}
