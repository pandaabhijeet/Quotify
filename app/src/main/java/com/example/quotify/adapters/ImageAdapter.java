package com.example.quotify.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.example.quotify.ImageViewActivity;
import com.example.quotify.R;
import com.example.quotify.models.ImageModel;
import com.example.quotify.utilities.ApiUtilities;
import com.example.quotify.utilities.CommonUtilities;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

 public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder>
{
    private Context context;
    private ArrayList<ImageModel> imageList;

    private String description, altDescription,username, firstName, lastName, downloadLink, portfolioLink,regularLink;
    private int likeCount, downloadCount;

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
        Glide.with(context)
                .load(imageList.get(position).getUrls().getRegular())
                .into(holder.imageView);

        String photoId = imageList.get(position).getId();

        getImageDetails(photoId,holder,position);

        int listPosition = position;



        if(holder.isLiked)
        {
            holder.likeBtn.setImageResource(R.drawable.ic_heart_red);
        }
        if(holder.isDownloaded)
        {
            holder.downloadBtn.setImageResource(R.drawable.ic_download_done);
        }
        if(holder.isKept)
        {
            holder.keepBtn.setImageResource(R.drawable.ic_added);
        }


        holder.imageView.setOnClickListener(view -> {

            Intent zoomIntent = new Intent(context, ImageViewActivity.class);
            zoomIntent.putExtra("imageUrl",imageList.get(listPosition).getUrls().getRegular());
            zoomIntent.putExtra("description",imageList.get(listPosition).getDescription());
            zoomIntent.putExtra("altDescription",imageList.get(listPosition).getAltDescription());
            zoomIntent.putExtra("username",imageList.get(listPosition).getUser().getUsername());
            zoomIntent.putExtra("firstName",imageList.get(listPosition).getUser().getFirstName());
            zoomIntent.putExtra("lastName",imageList.get(listPosition).getUser().getLastName());
            zoomIntent.putExtra("htmlLink",imageList.get(listPosition).getUser().getLinks().getHtml());

            context.startActivity(zoomIntent);
        });

        holder.likeBtn.setOnClickListener(view -> {
            holder.likeBtn.setImageResource(R.drawable.ic_heart_red);
            holder.isLiked = true;
        });

        holder.downloadBtn.setOnClickListener(view -> {
            holder.downloadBtn.setImageResource(R.drawable.ic_download_done);
            holder.isDownloaded = true;
        });

        holder.keepBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.keepBtn.setImageResource(R.drawable.ic_added);
                holder.isKept = true;
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
        TextView likeCountView,downloadCountView;
        ImageButton likeBtn, downloadBtn, keepBtn;
        Boolean isLiked = false, isDownloaded = false, isKept = false;
        public ImageViewHolder(@NonNull View itemView)

        {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView_main);
            likeCountView = itemView.findViewById(R.id.like_count);
            downloadCountView = itemView.findViewById(R.id.download_count);

            likeBtn = itemView.findViewById(R.id.like_btn);
            downloadBtn = itemView.findViewById(R.id.download_btn);
            keepBtn = itemView.findViewById(R.id.keep_btn);

        }
    }

    public void getImageDetails(String photoId, ImageViewHolder holder, int position)
    {
        ApiUtilities.getApiInterface().getImageDetails(photoId)
                .enqueue(new Callback<ImageModel>() {
                    @Override
                    public void onResponse(Call<ImageModel> call, Response<ImageModel> response)
                    {
                        if (response.body() != null)
                        {
                             imageList.get(position).setLikes(response.body().getLikes());
                             imageList.get(position).setDownloads(response.body().getDownloads());
                             imageList.get(position).setDescription(response.body().getDescription());
                             imageList.get(position).setAltDescription(response.body().getAltDescription());
                             imageList.get(position).setLinks(response.body().getLinks());
                             imageList.get(position).setUser(response.body().getUser());
                             imageList.get(position).setUrls(response.body().getUrls());

                            CommonUtilities utility = new CommonUtilities();
                            String likeCountCool = utility.coolFormat(imageList.get(position).getLikes(),0);
                            String downloadCountCool = utility.coolFormat(imageList.get(position).getDownloads(),0);

                            holder.likeCountView.setText(likeCountCool);
                            holder.downloadCountView.setText(downloadCountCool);

                        }else
                        {
                            Toast.makeText(context, "Null Response", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ImageModel> call, Throwable t)
                    {
                        Toast.makeText(context, "err:"+t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }


}
