package com.example.quotify.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.quotify.MainActivity;
import com.example.quotify.R;
import com.example.quotify.adapters.ImageAdapter;
import com.example.quotify.models.ImageModel;
import com.example.quotify.utilities.ApiUtilities;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment {

    private View view;
    private RecyclerView imageRecyclerView;
    private GridLayoutManager gridLayoutManager;
    private ImageAdapter imageAdapter;
    private ArrayList<ImageModel> imageList;
    private int page = 1, perPage = 30;
    
    
    public HomeFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         view = inflater.inflate(R.layout.fragment_home, container, false);

         initialise(view);
         
         return  view;
    }

    private void initialise(View view)
    {
        imageRecyclerView = view.findViewById(R.id.ImageRecyclerView);
        imageList = new ArrayList<>();
        imageAdapter = new ImageAdapter(getContext(), imageList);
        gridLayoutManager = new GridLayoutManager(getContext(),2);
        
        imageRecyclerView.setLayoutManager(gridLayoutManager);
        imageRecyclerView.setHasFixedSize(true);
        imageRecyclerView.setAdapter(imageAdapter);
        getImageData();
    }


    private void getImageData() 
    {
        ApiUtilities.getApiInterface().getImages(page, perPage)
                .enqueue(new Callback<List<ImageModel>>() {
                    @Override
                    public void onResponse(Call<List<ImageModel>> call, Response<List<ImageModel>> response) {
                        if (response.body() != null)
                        {
                            imageList.addAll(response.body());
                            imageAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<ImageModel>> call, Throwable t) {
                        Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}