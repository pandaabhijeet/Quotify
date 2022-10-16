 package com.example.quotify;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jsibbold.zoomage.ZoomageView;

 public class ImageViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view);

        String url = getIntent().getStringExtra("imageUrl");
        String description = getIntent().getStringExtra("description");
        String altDescription = getIntent().getStringExtra("altDescription");
        String username = getIntent().getStringExtra("username");
        String firstName = getIntent().getStringExtra("firstName");
        String lastName = getIntent().getStringExtra("lastName");
        String htmlLink = getIntent().getStringExtra("htmlLink");


        ZoomageView zoomageView = findViewById(R.id.zoom_image_view);
        AppCompatButton quoteButton = (AppCompatButton)findViewById(R.id.button);
        TextView descriptionView = findViewById(R.id.description);
        TextView attributionView = findViewById(R.id.attribution);

        String attributionLink = "Photo from : "+"<a href='"+htmlLink+"?utm_source=Quotify&utm_medium=referral'>"+username+"</a>"
                + " on "+"<a href='https://unsplash.com/?utm_source=Quotify&utm_medium=referral'>Unsplash</a>";

        Glide.with(this).load(url).into(zoomageView);

        quoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ImageViewActivity.this,ImageEditActivity.class);
                intent.putExtra("imageurl",url);
                startActivity(intent);
            }
        });

        if (description != null)
        {
            descriptionView.setText(description);
        }else if(altDescription != null)
        {
            descriptionView.setText(altDescription);
        } else
        {
            descriptionView.setText("No Description for this image");
        }

        attributionView.setClickable(true);
        attributionView.setMovementMethod(LinkMovementMethod.getInstance());

        if(Build.VERSION.SDK_INT >= 24)
        {
            attributionView.setText(Html.fromHtml(attributionLink,Html.FROM_HTML_MODE_LEGACY));
        }else
        {
            attributionView.setText(Html.fromHtml(attributionLink));
        }


    }

}