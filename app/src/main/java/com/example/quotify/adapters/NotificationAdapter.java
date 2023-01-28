package com.example.quotify.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.quotify.R;
import com.example.quotify.models.NotificationModel;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder>{
    private Context context;
    private ArrayList<NotificationModel> notificationList;
    public NotificationAdapter(Context context, ArrayList<NotificationModel> notificationList){
        this.context = context;
        this.notificationList = notificationList;
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.notification_layout,parent,false);
        return new NotificationAdapter.NotificationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return notificationList.size();
    }

    public static class NotificationViewHolder extends RecyclerView.ViewHolder
    {
        TextView notifTextView, dateTimeStampView;
        CircleImageView userProfileImageView;
        public NotificationViewHolder(@NonNull View itemView)
        {
            super(itemView);

            notifTextView = itemView.findViewById(R.id.notification_text);
            dateTimeStampView = itemView.findViewById(R.id.notification_time);
            userProfileImageView = itemView.findViewById(R.id.user_prof_image);
        }
    }
}
