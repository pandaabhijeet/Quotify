package com.example.quotify.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quotify.R;
import com.example.quotify.adapters.ImageAdapter;
import com.example.quotify.models.ImageModel;
import com.example.quotify.models.SearchModel;
import com.example.quotify.utilities.ApiUtilities;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchFragment extends Fragment {

    private View view;
    private EditText searchInput;
    private RecyclerView searchRecyclerView;
    private GridLayoutManager searchGridLayoutManager;
    private ImageAdapter searchImageAdapter;
    private TextView searchBtnTextView;
    private ArrayList<ImageModel> searchArrayList;
    private String searchString;

    public SearchFragment() {} //Empty constructor


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_search, container, false);

        initialise(view);

        searchBtnTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                searchString = searchInput.getText().toString().trim();

                if (TextUtils.isEmpty(searchString))
                {
                    Toast.makeText(getContext(), "Please enter a word !", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    getSearchedImages(searchString);
                }
            }
        });


        return view;
    }


    private void initialise(View view)
    {
        searchInput = view.findViewById(R.id.search_bar);
        searchRecyclerView = view.findViewById(R.id.search_recycler_view);
        searchBtnTextView = view.findViewById(R.id.search_button);

        searchArrayList = new ArrayList<>();
        searchImageAdapter = new ImageAdapter(getContext(),searchArrayList);
        searchGridLayoutManager = new GridLayoutManager(getContext(),2);

        searchRecyclerView.setLayoutManager(searchGridLayoutManager);
        searchRecyclerView.setHasFixedSize(true);
        searchRecyclerView.setAdapter(searchImageAdapter);

    }

    private void getSearchedImages(String query)
    {
        ApiUtilities.getApiInterface().searchImages(query)
                .enqueue(new Callback<SearchModel>() {
                    @Override
                    public void onResponse(Call<SearchModel> call, Response<SearchModel> response) {

                        if (response.body().getResults() != null)
                        {
                            searchArrayList.clear();
                            searchArrayList.addAll(response.body().getResults());
                            searchImageAdapter.notifyDataSetChanged();
                        }

                    }

                    @Override
                    public void onFailure(Call<SearchModel> call, Throwable t)
                    {
                        Toast.makeText(getContext(), "Error: "+t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}