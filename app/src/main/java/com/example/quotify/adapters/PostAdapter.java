package com.example.quotify.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.quotify.R;
import com.example.quotify.models.PostModel;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder>{

    private Context context;
    private ArrayList<PostModel> postList;

    public PostAdapter(Context context, ArrayList<PostModel> postList) {
        this.context = context;
        this.postList = postList;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(context).inflate(R.layout.post_layout_main,parent,false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        Glide.with(context)
                .load(R.drawable.quotify_icon)
                .into(holder.postViewMain);
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    public class PostViewHolder extends RecyclerView.ViewHolder
    {
        CircleImageView userProfImage;
        TextView givenName, userName, likeCount,shareCount, commentCount;
        ImageView postViewMain;
        ImageButton likeBtn,shareBtn,commentBtn;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            userProfImage = itemView.findViewById(R.id.user_prof_image);
            givenName = itemView.findViewById(R.id.user_given_name);
            userName = itemView.findViewById(R.id.user_unique_name);
            likeCount = itemView.findViewById(R.id.post_like_count);
            shareCount = itemView.findViewById(R.id.share_count);
            commentCount = itemView.findViewById(R.id.comment_count);
            postViewMain = itemView.findViewById(R.id.post_view_main);
            likeBtn = itemView.findViewById(R.id.post_like_btn);
            shareBtn = itemView.findViewById(R.id.share_btn);
            commentBtn = itemView.findViewById(R.id.comment_btn);
        }
    }
}
