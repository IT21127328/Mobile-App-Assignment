package com.example.foodmania.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.foodmania.R;
import com.example.foodmania.UI_Models.GridModel;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class CookBookGridAdapter extends BaseAdapter {

    ArrayList<GridModel> list;
    Context context;
    LayoutInflater inflater;

    public CookBookGridAdapter(ArrayList<GridModel> list, Context context) {
        this.list = list;
        this.context = context;


    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.grid_item, parent, false);
        //convertView = inflater.inflate(R.layout.grid_item, null); // inflate the layout
        RoundedImageView icon = convertView.findViewById(R.id.grid_image); // get the reference of ImageView
//        TextView title=convertView.findViewById(R.id.txt_title);
        icon.setImageBitmap(list.get(position).getPicture()); // set logo images
//        title.setText(list.get(position).getTitle());
        return convertView;
    }
}
