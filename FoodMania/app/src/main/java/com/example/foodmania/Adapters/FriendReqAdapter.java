package com.example.foodmania.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodmania.R;
import com.example.foodmania.UI_Models.FriendReqModel;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class FriendReqAdapter extends RecyclerView.Adapter<FriendReqAdapter.viewHolder> {

    ArrayList<FriendReqModel> list;
    Context context;
    FriendReqModel model;
    public FriendReqAdapter(ArrayList<FriendReqModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.request_design, parent, false);
        return new viewHolder(view);
    }

    public  void onBindViewHolder(@NonNull viewHolder holder,int position)
    {
        model=list.get(position);
        holder.profile_pic.setImageBitmap(model.getProfile_pic());
        holder.username.setText(model.getUsername());
        holder.designation.setText(model.getDesignation());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {

      CircleImageView profile_pic;
      TextView username,designation;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            profile_pic=itemView.findViewById(R.id.img_profile);
            username=itemView.findViewById(R.id.txt_username);
            designation=itemView.findViewById(R.id.txt_designation);
        }
    }


}
