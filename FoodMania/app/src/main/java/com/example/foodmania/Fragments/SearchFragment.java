package com.example.foodmania.Fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.foodmania.Adapters.SearchAdapters;
import com.example.foodmania.Api_Models.APISearchModel;
import com.example.foodmania.Api_Util.ApiClient;
import com.example.foodmania.Api_Util.ApiInterface;
import com.example.foodmania.R;
import com.example.foodmania.UI_Models.SearchModel;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SearchFragment extends Fragment {

    View view;
    RecyclerView searchRV;
    ArrayList<SearchModel> searchList;
    LinearLayout progress;
    EditText txt_name;
    Button btn_search;
    TextView txt_location_name;
    String fname, lname, encoded_Image, description, designation, email;
    int sender_id;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Places.initialize(getContext(), getString(R.string.google_maps_key));
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_search, container, false);
        //GetBundle
        get_data_bundle();
        //onCreate Objects
        oncreate();
        System.out.println(sender_id);
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progress.setVisibility(View.VISIBLE);
                get_profiles();
            }
        });

        txt_location_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchLocation();
            }
        });

        txt_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

//                progress.setVisibility(View.VISIBLE);
//                get_profiles();


            }
        });

        return view;
    }

    private void oncreate() {
        searchRV = view.findViewById(R.id.search_result);
        btn_search = view.findViewById(R.id.btn_search);
        txt_name = view.findViewById(R.id.txt_name);
        progress = view.findViewById(R.id.progress);
        txt_location_name = view.findViewById(R.id.txt_location_name);
    }

    private void get_profiles() {
        String name = txt_name.getText().toString();
        String location = txt_location_name.getText().toString();
        searchList = new ArrayList<>();
        Call<List<APISearchModel>> call = ApiClient.getApiClient().create(ApiInterface.class).search(name,location);
        call.enqueue(new Callback<List<APISearchModel>>() {
            @Override
            public void onResponse(Call<List<APISearchModel>> call, Response<List<APISearchModel>> response) {
                List<APISearchModel> data = response.body();
                //data.clear();
                for (int i = 0; i < data.size(); i++) {
                    String f_name = data.get(i).getF_name();
                    String l_name = data.get(i).getL_name();
                    String email = data.get(i).getEmail();
                    String designation = data.get(i).getDesignation();
                    String description = data.get(i).getDescription();
                    String encoded_image = data.get(i).getEncoded_image();
                    ;
                    int user_id = data.get(i).getUser_id();
                    int profile_id = data.get(i).getProfile_id();
                    byte[] imageInByte = Base64.decode(encoded_image, Base64.DEFAULT);
                    Bitmap decodedImage = BitmapFactory.decodeByteArray(imageInByte, 0, imageInByte.length);
                    searchList.add(new SearchModel(decodedImage, f_name + " " + l_name, designation, user_id));
                    SearchAdapters searchAdapters = new SearchAdapters(searchList, getContext(), sender_id);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                    searchRV.setLayoutManager(linearLayoutManager);
                    // searchRV.addItemDecoration(new DividerItemDecoration(searchRV.getContext(), DividerItemDecoration.VERTICAL));
                    // searchRV.setNestedScrollingEnabled(false);
                    searchRV.setAdapter(searchAdapters);
                    progress.setVisibility(View.INVISIBLE);


                }
            }

            @Override
            public void onFailure(Call<List<APISearchModel>> call, Throwable t) {

            }
        });

    }

    private void searchLocation()
    {
        List<Place.Field> fieldList = Arrays.asList(Place.Field.ADDRESS,Place.Field.ID,Place.Field.LAT_LNG,Place.Field.NAME);

        Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN,fieldList).setTypeFilter(TypeFilter.REGIONS).build(getContext());
        startActivityForResult(intent,105);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 105 && resultCode == -1)
        {
            Place place = Autocomplete.getPlaceFromIntent(data);
            getCityName(place);
        }
        else
        {
            Status status = Autocomplete.getStatusFromIntent(data);
            System.out.println(status);
        }
    }

    private void getCityName(Place place)
    {

        LatLng placeLatLng = place.getLatLng();
        String address = place.getAddress();

      //  txtServiceAreaId.setText(String.valueOf(placeLatLng));

        try {
            Geocoder geocoder = new Geocoder(getContext(), Locale.ENGLISH);
            List<Address> addresses = geocoder.getFromLocationName(address,1);

            System.out.println(addresses);
            if(addresses.size() > 0)
            {

                String cityName = addresses.get(0).getLocality();
                txt_location_name.setText(cityName);
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    //Get Data Bundle
    private void get_data_bundle() {

        fname = getArguments().getString("f_name");
        lname = getArguments().getString("l_name");
        encoded_Image = getArguments().getString("encodedImage");
        description = getArguments().getString("description");
        designation = getArguments().getString("designation");
        email = getArguments().getString("email");
        sender_id = getArguments().getInt("user_id");
    }

}