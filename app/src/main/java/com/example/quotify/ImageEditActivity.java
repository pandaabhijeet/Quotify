package com.example.quotify;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextPaint;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.quotify.utilities.ColorFlag;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.jsibbold.zoomage.ZoomageView;
import com.skydoves.colorpickerview.ColorEnvelope;
import com.skydoves.colorpickerview.ColorPickerDialog;
import com.skydoves.colorpickerview.ColorPickerView;
import com.skydoves.colorpickerview.listeners.ColorEnvelopeListener;


public class ImageEditActivity extends AppCompatActivity {

    private String imageUrl;
    private ZoomageView editZoomageView;
    private EditText quoteText;
    private Button doneBtn;
    private View blurredBackground;
    private TextView moveQuoteText;
    private Bitmap imageBitmap;
    private BottomNavigationView editNaviagtionView;
    private boolean isFontMenuOpen = false;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_edit);

        Initialise();

        Glide.with(this).asBitmap().load(imageUrl).into(editZoomageView);

        doneBtn.setOnClickListener(view -> {
            String quote_text_string = quoteText.getText().toString();

            if (TextUtils.isEmpty(quote_text_string)) {
                Toast.makeText(ImageEditActivity.this, "Your mind seems to be empty now", Toast.LENGTH_SHORT).show();
            } else {
                blurredBackground.setVisibility(View.GONE);
                doneBtn.setVisibility(View.GONE);
                quoteText.setVisibility(View.GONE);

                //setQuoteOnImage(quote_text_string, ImageEditActivity.this, imageUrl);
                moveQuoteText.setText(quote_text_string);
                moveQuoteText.setVisibility(View.VISIBLE);
            }
        });

        moveQuoteText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveQuoteText.setVisibility(View.GONE);
                blurredBackground.setVisibility(View.VISIBLE);
                doneBtn.setVisibility(View.VISIBLE);
                quoteText.setVisibility(View.VISIBLE);
            }
        });

        moveQuoteText.setOnTouchListener((view, motionEvent) -> {

            if (motionEvent.getAction() == MotionEvent.ACTION_MOVE) {
                if (isQuoteInsideImage(moveQuoteText.getX(), moveQuoteText.getY(), editZoomageView)) {
                    view.setX(motionEvent.getRawX() - view.getWidth());
                    view.setY(motionEvent.getRawY() - view.getHeight());

                }
            }
            return true;
        });

        editNaviagtionView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item)
            {
                switch (item .getItemId())
                {
                    case R.id.font_name:
                        openFontMenu();
                        return true;

                    case R.id.font_shadow:
                        openShadowMenu();
                        return true;

                    case R.id.font_color:
                        openColorMenu();
                        return true;

                    case R.id.font_style:
                        openFontStyleMenu();
                        return true;

                    case R.id.font_size:
                        openFontSizeMenu();
                        return true;
                }

                return false;
            }
        });

    }

    private void Initialise() {
        imageUrl = getIntent().getStringExtra("imageurl");
        editZoomageView = findViewById(R.id.edit_image_view);
        quoteText = findViewById(R.id.quote_text);
        moveQuoteText = findViewById(R.id.move_quote_text);
        doneBtn = findViewById(R.id.done_btn);
        blurredBackground = findViewById(R.id.blurred_background);
        editNaviagtionView = findViewById(R.id.text_edit_tab);
    }

    private void setQuoteOnImage(String quote, Context context, String imageUrl) {

        float bitmapRatio = (imageBitmap.getWidth()) / imageBitmap.getHeight();
        float imageViewRatio = (imageBitmap.getWidth()) / editZoomageView.getHeight();

        float scaleW = (float) imageBitmap.getWidth() / (float) editZoomageView.getWidth();
        float scaleH = (float) imageBitmap.getHeight() / (float) editZoomageView.getHeight();
        Bitmap.Config bitmapConfig = imageBitmap.getConfig();
        imageBitmap = imageBitmap.copy(bitmapConfig, true);


        float density = context.getResources().getDisplayMetrics().density;
        Canvas canvas = new Canvas(imageBitmap);
        canvas.setBitmap(imageBitmap);
        TextPaint tp = new TextPaint(TextPaint.ANTI_ALIAS_FLAG);
        tp.setColor(Color.WHITE);
        tp.setTextSize(quoteText.getTextSize() * density);

        Resources resources = context.getResources();
        float scale = resources.getDisplayMetrics().density;

        int[] xy = {0, 0};
        quoteText.getLocationOnScreen(xy);
        float x = xy[0] * (scaleW);
        float y = xy[1] * (scaleH);

        canvas.drawText(quoteText.getText().toString(), x, y, tp);
        canvas.save();

        editZoomageView.setImageBitmap(imageBitmap);

    }

    private boolean isQuoteInsideImage(float x, float y, ZoomageView view) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        int viewX = location[0];
        int viewY = location[1];

        return ((x > viewX && x < (viewX + view.getWidth())) && (y > viewY && y < (viewY + view.getHeight())));

    }

    private void openFontMenu()
    {
        PopupMenu fontPopupMenu = new PopupMenu(this, findViewById(R.id.font_name));
        fontPopupMenu.getMenuInflater().inflate(R.menu.font_menu,fontPopupMenu.getMenu());
        fontPopupMenu.show();
    }
    private void openFontSizeMenu()
    {
        PopupMenu fontSizePopupMenu = new PopupMenu(this, findViewById(R.id.font_size));
        fontSizePopupMenu.getMenuInflater().inflate(R.menu.font_size_menu,fontSizePopupMenu.getMenu());
        fontSizePopupMenu.show();
    }

    private void openFontStyleMenu()
    {
        PopupMenu fontStylePopupMenu = new PopupMenu(this, findViewById(R.id.font_style));
        fontStylePopupMenu.getMenuInflater().inflate(R.menu.font_style_menu,fontStylePopupMenu.getMenu());
        fontStylePopupMenu.show();
    }

    private void openColorMenu()
    {
//        ColorPickerView colorPickerView = new ColorPickerView.Builder(this)
//                .setPaletteDrawable(getDrawable(R.drawable.palette_background))
//                .setFlagView(new ColorFlag(this,R.layout.color_flag_layout))
//                .build();

        ColorPickerDialog.Builder colorPickerBuilder =  new ColorPickerDialog.Builder(this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT)
                .setTitle("What color would you like")
                .setPreferenceName("ColorPicker")
                .attachAlphaSlideBar(true)
                .attachBrightnessSlideBar(true)
                .setPositiveButton("Select", new ColorEnvelopeListener() {
                    @Override
                    public void onColorSelected(ColorEnvelope envelope, boolean fromUser)
                    {
                        moveQuoteText.setTextColor(envelope.getColor());
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        dialogInterface.dismiss();
                    }
                })
                .setBottomSpace(12);

        ColorPickerView colorPickerview = colorPickerBuilder.getColorPickerView();
        colorPickerview.setFlagView(new ColorFlag(this,R.layout.color_flag_layout));
        colorPickerBuilder.show();

    }

    private void openShadowMenu()
    {

    }

}