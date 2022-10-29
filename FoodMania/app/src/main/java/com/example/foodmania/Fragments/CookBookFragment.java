package com.example.foodmania.Fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.foodmania.Activities.ViewCookBook;
import com.example.foodmania.Adapters.CookBookGridAdapter;
import com.example.foodmania.Api_Models.APIGetOwnPosts;
import com.example.foodmania.Api_Models.AllPosts;
import com.example.foodmania.Api_Util.ApiClient;
import com.example.foodmania.Api_Util.ApiInterface;
import com.example.foodmania.R;
import com.example.foodmania.UI_Models.GridModel;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CookBookFragment extends Fragment {


    View view;
    GridView gridView;
    ArrayList<GridModel> grid;
    int user_id;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_cook_book, container, false);

        //Get Data
        get_data_bundle();

        //On Create Objects
        onCreateObjects();

        get_all_posts();

        return view;
    }

    private void onCreateObjects() {
        gridView = view.findViewById(R.id.gridView);

    }

    private void get_all_posts() {

        grid = new ArrayList<>();
        Call<List<APIGetOwnPosts>> call = ApiClient.getApiClient().create(ApiInterface.class).getcookbook(user_id);
        call.enqueue(new Callback<List<APIGetOwnPosts>>() {
            @Override
            public void onResponse(Call<List<APIGetOwnPosts>> call, Response<List<APIGetOwnPosts>> response) {
                List<APIGetOwnPosts> data = response.body();

                for (int i = 0; i < data.size(); i++) {
                    String Title = data.get(i).getCaption();
                    String encodedImage = data.get(i).getEncodedImage();
                    byte[] imageInByte = Base64.decode(encodedImage, Base64.DEFAULT);
                    Bitmap decodedImage = BitmapFactory.decodeByteArray(imageInByte, 0, imageInByte.length);
                    grid.add(new GridModel(decodedImage, Title));
                    CookBookGridAdapter gridAdapter = new CookBookGridAdapter(grid, getContext());
                    gridView.setAdapter(gridAdapter);

                    gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            Intent intent = new Intent(getContext(), ViewCookBook.class);
                            String encoded;
                            Bitmap bitmap = grid.get(position).getPicture();
                            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 75, byteArrayOutputStream);
                            byte[] imageInByte = byteArrayOutputStream.toByteArray();
                            encoded = Base64.encodeToString(imageInByte, Base64.DEFAULT);
                            String titile = grid.get(position).getTitle();
                            intent.putExtra("image", encoded);
                            intent.putExtra("title", titile);
                            startActivity(intent);

                        }
                    });
                }

            }

            @Override
            public void onFailure(Call<List<APIGetOwnPosts>> call, Throwable t) {

            }
        });
    }

    private void get_data_bundle() {
        user_id = getArguments().getInt("user_id");
    }

}