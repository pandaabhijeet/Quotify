package com.example.quotify;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.quotify.adapters.ImageAdapter;
import com.example.quotify.fragments.HomeFragment;
import com.example.quotify.fragments.NotificationFragment;
import com.example.quotify.fragments.PaletteFragment;
import com.example.quotify.fragments.ProfileFragment;
import com.example.quotify.fragments.SearchFragment;
import com.example.quotify.models.ImageModel;
import com.example.quotify.utilities.ApiUtilities;

import java.util.ArrayList;
import java.util.List;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private MeowBottomNavigation bottomNavigation;
    private  FragmentManager fragmentManager;
    private Fragment fragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction()
                .replace(R.id.main_activity_container,new HomeFragment(),"Home Fragment");
        fragmentTransaction.commit();

        bottomNavigation = findViewById(R.id.meow_bottom_bar);
        setBottomNavigationBar();

        bottomNavigation.setOnClickMenuListener(new Function1<MeowBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(MeowBottomNavigation.Model model) {
                switch (model.getId()) {
                    case 1:
                        startFragment(new PaletteFragment());
                        break;
                    case 2:
                        startFragment(new SearchFragment());
                        break;
                    case 3:
                        startFragment(new HomeFragment());
                        break;
                    case 4:
                        startFragment(new NotificationFragment());
                        break;
                    case 5:
                        startFragment(new ProfileFragment());
                        break;
                }
                return null;
            }
        });
        bottomNavigation.show(3, true);

    }


    private void setBottomNavigationBar() {
        bottomNavigation.add(new MeowBottomNavigation.Model(1, R.drawable.ic_palette));
        bottomNavigation.add(new MeowBottomNavigation.Model(2, R.drawable.ic_search));
        bottomNavigation.add(new MeowBottomNavigation.Model(3, R.drawable.ic_home));
        bottomNavigation.add(new MeowBottomNavigation.Model(4, R.drawable.ic_notification));
        bottomNavigation.add(new MeowBottomNavigation.Model(5, R.drawable.ic_profile));

    }

    private void startFragment(Fragment fragment)
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_activity_container, fragment);
        fragmentTransaction.commit();
    }


}