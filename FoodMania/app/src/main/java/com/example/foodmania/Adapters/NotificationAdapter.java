package com.example.foodmania.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodmania.R;
import com.example.foodmania.UI_Models.NotificationsModel;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;


public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.viewHolder> {

    ArrayList<NotificationsModel> list;
    Context context;
    NotificationsModel model;


    public NotificationAdapter(ArrayList<NotificationsModel> list, Context context) {
        this.context = context;
        this.list = list;


    }

    @NonNull
    @Override
    public NotificationAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.notification_design, parent, false);
        return new NotificationAdapter.viewHolder(view);
    }

    public void onBindViewHolder(@NonNull NotificationAdapter.viewHolder holder, int position) {

        model = list.get(position);
        holder.profile_pic.setImageBitmap(model.getProfile());
        holder.username.setText(model.getUsername());
        holder.content.setText(model.getContent());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {

        TextView username, content;
        CircleImageView profile_pic;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            profile_pic = itemView.findViewById(R.id.profile_pic);
            username = itemView.findViewById(R.id.txt_username);
            content = itemView.findViewById(R.id.txt_content);

        }
    }


}
