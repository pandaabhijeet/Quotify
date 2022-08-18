 package com.example.quotify;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.bumptech.glide.Glide;
import com.jsibbold.zoomage.ZoomageView;

 public class ImageViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view);

        String url = getIntent().getStringExtra("imageUrl");
        ZoomageView zoomageView = findViewById(R.id.zoom_image_view);
        Button quoteButton = findViewById(R.id.button);

        Glide.with(this).load(url).into(zoomageView);

        quoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ImageViewActivity.this,ImageEditActivity.class);
                intent.putExtra("imageurl",url);
                startActivity(intent);
            }
        });

    }
}