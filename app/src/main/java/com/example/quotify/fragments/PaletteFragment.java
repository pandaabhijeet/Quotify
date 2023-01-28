package com.example.quotify.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.quotify.R;
import com.example.quotify.adapters.PostAdapter;
import com.example.quotify.models.PostModel;

import java.util.ArrayList;

public class PaletteFragment extends Fragment {

    private View view;
    private RecyclerView postRecyclerView;
    private ArrayList<PostModel> postList;
    private LinearLayoutManager postLinearLayoutManager;
    private PostAdapter postAdapter;


    public PaletteFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_palette, container, false);

        initialise(view);

        return view;
    }

    private void initialise(View view)
    {
        postRecyclerView = view.findViewById(R.id.post_recycler_view);
        postList = new ArrayList<>();
        postAdapter = new PostAdapter(getContext(),postList);
        postLinearLayoutManager = new LinearLayoutManager(getContext());
        postRecyclerView.setLayoutManager(postLinearLayoutManager);
        postRecyclerView.setHasFixedSize(true);
        postRecyclerView.setAdapter(postAdapter);

        PostModel post1 = new PostModel("givenNmae","unqiueName","dd/mm/yy","html.com",
                100,100,100);
        postList.add(post1);
        PostModel post2 = new PostModel("givenNmae","unqiueName","dd/mm/yy","html.com",
                100,100,100);
        postList.add(post2);
        PostModel post3 = new PostModel("givenNmae","unqiueName","dd/mm/yy","html.com",

                100,100,100);
        postList.add(post3);

        postAdapter.notifyDataSetChanged();


    }
}