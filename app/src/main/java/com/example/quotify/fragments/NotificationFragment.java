package com.example.quotify.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.quotify.R;
import com.example.quotify.adapters.NotificationAdapter;
import com.example.quotify.models.NotificationModel;

import java.util.ArrayList;


public class NotificationFragment extends Fragment {

private View view ;
private RecyclerView notificationRecyclerView;
private ArrayList<NotificationModel> notificationModelList;
private NotificationAdapter notificationAdapter;
private LinearLayoutManager layoutManager;
private String userName, dateTimeStamp;
private int likeCount, commentCount, shareCount;

    public NotificationFragment() {}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_notification,container,false);
        initialise(view);
        return view;
    }

    private void initialise(View view)
    {
        notificationRecyclerView = view.findViewById(R.id.notif_recycler_view);
        notificationModelList = new ArrayList<>();
        notificationAdapter = new NotificationAdapter(getContext(),notificationModelList);
        layoutManager = new LinearLayoutManager(getContext());
        notificationRecyclerView.setLayoutManager(layoutManager);
        notificationRecyclerView.setHasFixedSize(true);
        notificationRecyclerView.setAdapter(notificationAdapter);


        NotificationModel notificationModel1 = new NotificationModel("profile_image","user_name",100,
                100,100,"hhmmdd");
        notificationModelList.add(notificationModel1);
        NotificationModel notificationModel2 = new NotificationModel("profile_image","user_name",100,
                100,100,"hhmmdd");
        notificationModelList.add(notificationModel2);
        NotificationModel notificationModel3 = new NotificationModel("profile_image","user_name",100,
                100,100,"hhmmdd");
        notificationModelList.add(notificationModel3);
        NotificationModel notificationModel4 = new NotificationModel("profile_image","user_name",100,
                100,100,"hhmmdd");
        notificationModelList.add(notificationModel4);
        NotificationModel notificationModel5 = new NotificationModel("profile_image","user_name",100,
                100,100,"hhmmdd");
        notificationModelList.add(notificationModel5);
        NotificationModel notificationModel6 = new NotificationModel("profile_image","user_name",100,
                100,100,"hhmmdd");
        notificationModelList.add(notificationModel6);
        NotificationModel notificationModel17= new NotificationModel("profile_image","user_name",100,
            100,100,"hhmmdd");

        notificationAdapter.notifyDataSetChanged();
    }


}