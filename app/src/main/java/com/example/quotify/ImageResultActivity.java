package com.example.quotify;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;

import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.quotify.R;
import com.jsibbold.zoomage.ZoomageView;

public class ImageResultActivity extends AppCompatActivity {

    private ZoomageView resultImageView;
    private RelativeLayout result_keep_post_layout,result_feed_post_layout,result_share_post_layout;
    private String resultImageUri;
    private ImageButton result_keep_btn,result_feed_btn,result_share_btn;
    private TextView result_keep_text,result_feed_text,result_share_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_result);

        resultImageUri = getIntent().getStringExtra("result_image_uri");
        initialise();

        if(resultImageUri != null)
        {
            Glide.with(this).load(resultImageUri).into(resultImageView);
        }

        result_feed_post_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    result_feed_btn.setBackgroundTintList(getColorStateList(R.color.primary_red));
                    result_feed_text.setTextColor(getColor(R.color.primary_red));
                }
            }
        });

        result_keep_post_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    result_keep_btn.setBackgroundTintList(getColorStateList(R.color.primary_red));
                    result_keep_text.setTextColor(getColor(R.color.primary_red));
                }
            }
        });

        result_share_post_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    result_share_btn.setBackgroundTintList(getColorStateList(R.color.primary_red));
                    result_share_text.setTextColor(getColor(R.color.primary_red));
                }
            }
        });

    }

    public void initialise()
    {
        resultImageView = findViewById(R.id.result_image_view);
        result_feed_post_layout = findViewById(R.id.result_feed_post_layout);
        result_keep_post_layout = findViewById(R.id.result_keep_post_layout);
        result_share_post_layout = findViewById(R.id.result_share_post_layout);

        result_feed_btn = findViewById(R.id.result_feed_btn);
        result_keep_btn = findViewById(R.id.result_keep_btn);
        result_share_btn = findViewById(R.id.result_share_btn);

        result_feed_text = findViewById(R.id.result_feed_text);
        result_share_text = findViewById(R.id.result_share_text);
        result_keep_text = findViewById(R.id.result_keep_text);
    }
}