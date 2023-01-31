package com.example.quotify;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;

import android.net.Uri;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import de.hdodenhof.circleimageview.CircleImageView;

public class PostEditActivity extends AppCompatActivity {

    private AppCompatButton post_cancel_btn,final_post_btn;
    private ImageButton post_edit_back_btn,add_gallery_image_btn,add_poll_btn;
    private CircleImageView post_edit_profile_img;
    private TextView post_edit_profile_name, post_edit_unique_name,post_text_count;
    private EditText edit_post_content_text;
    private CardView edit_post_card_view;
    private ImageView edit_post_image;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_edit);

        String resultImageUri = getIntent().getStringExtra("result_image_uri");

        initialise();
        edit_post_image.setImageURI(Uri.parse(resultImageUri));
        //Glide.with(this).load(resultImageUri).into(edit_post_image);
    }

    private void initialise()
    {
        edit_post_image = findViewById(R.id.edit_post_image);
        post_cancel_btn = findViewById(R.id.post_cancel_btn);
        final_post_btn = findViewById(R.id.final_post_btn);
        post_edit_back_btn = findViewById(R.id.post_edit_back_btn);
        add_gallery_image_btn = findViewById(R.id.add_gallery_image_btn);
        add_poll_btn = findViewById(R.id.add_poll_btn);
        post_text_count = findViewById(R.id.post_text_count);
        post_edit_profile_img = findViewById(R.id.post_edit_profile_img);
        post_edit_profile_name = findViewById(R.id.post_edit_profile_name);
        post_edit_unique_name = findViewById(R.id.post_edit_unique_name);
        edit_post_content_text = findViewById(R.id.edit_post_content_text);
        edit_post_card_view = findViewById(R.id.edit_post_card_view);

    }
}