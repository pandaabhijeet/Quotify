package com.example.quotify;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashScreenActivity extends AppCompatActivity {


    private TextView splashTitleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        splashTitleView = findViewById(R.id.title_splash);

        Typeface typeface = Typeface.createFromAsset(getAssets(),"fonts/crimson_italic.ttf");
        splashTitleView.setTypeface(typeface);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run()
            {
                Intent loginIntent = new Intent(SplashScreenActivity.this,MainActivity.class);
                startActivity(loginIntent);
            }
        },2000);


    }
}