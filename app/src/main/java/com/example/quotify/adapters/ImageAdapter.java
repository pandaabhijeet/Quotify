 package com.example.quotify.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.example.quotify.ImageViewActivity;
import com.example.quotify.R;
import com.example.quotify.models.ImageModel;

import java.util.ArrayList;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder>
{
    private Context context;
    private ArrayList<ImageModel> imageList;

    public ImageAdapter(Context context, ArrayList<ImageModel> imageList)
    {
        this.context = context;
        this.imageList = imageList;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.image_layout_main,parent,false);

        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position)
    {

        Glide.with(context).
                //load(R.mipmap.ic_launcher)
                 load(imageList.get(position).getUrls().getRegular())
                .into(holder.imageView);

        int list_position = position;
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent zoomIntent = new Intent(context, ImageViewActivity.class);
                zoomIntent.putExtra("imageUrl",imageList.get(list_position).getUrls().getRegular());
                context.startActivity(zoomIntent);

            }
        });
    }

    @Override
    public int getItemCount()
    {
        return imageList.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder
    {
        ImageView imageView;
        public ImageViewHolder(@NonNull View itemView)
        {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView_main);
        }
    }
}
