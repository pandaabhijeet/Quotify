package com.example.quotify;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.FileProvider;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.TypefaceSpan;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.agrawalsuneet.dotsloader.loaders.LinearDotsLoader;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.quotify.utilities.ColorFlag;
import com.example.quotify.utilities.ScaleListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.shawnlin.numberpicker.NumberPicker;
import com.skydoves.colorpickerview.ColorEnvelope;
import com.skydoves.colorpickerview.ColorPickerDialog;
import com.skydoves.colorpickerview.ColorPickerView;
import com.skydoves.colorpickerview.listeners.ColorEnvelopeListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class ImageEditActivity extends AppCompatActivity {

    private String imageUrl;
    //private ZoomageView editZoomageView;
    private ImageView editZoomageView;
    private EditText quoteText;
    private AppCompatButton doneBtn,fontSizeBtn,shadowValueBtn;
    private View blurredBackground;
    private TextView moveQuoteText,saveImageButton;
    private BottomNavigationView editNaviagtionView;
    private boolean isFontMenuOpen = false;
    private SeekBar shadowSeekBar;
    private LinearLayout fontSizeLayout;
    private com.shawnlin.numberpicker.NumberPicker fontSizePicker;
    private float dX, dY;
    private float scaleFactor = 1f;
    private ScaleGestureDetector scaleGestureDetector;
    private LinearDotsLoader linearDotsLoader;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_edit);

        Initialise();

        Glide.with(this).asBitmap().load(imageUrl).into(editZoomageView);

        scaleGestureDetector = new ScaleGestureDetector(this,new ScaleListener(moveQuoteText,scaleFactor));

        doneBtn.setOnClickListener(view -> {
            String quote_text_string = quoteText.getText().toString();

            if (TextUtils.isEmpty(quote_text_string)) {
                Toast.makeText(ImageEditActivity.this, "Your mind seems to be empty now", Toast.LENGTH_SHORT).show();
            } else {
                blurredBackground.setVisibility(View.GONE);
                doneBtn.setVisibility(View.GONE);
                quoteText.setVisibility(View.GONE);
                editNaviagtionView.setVisibility(View.VISIBLE);

                moveQuoteText.setText(quote_text_string);
                moveQuoteText.setVisibility(View.VISIBLE);
                saveImageButton.setVisibility(View.VISIBLE);

            }
        });

        saveImageButton.setOnClickListener(view ->
        {
            saveImageButton.setText("saving");
            blurredBackground.setVisibility(View.VISIBLE);
            linearDotsLoader.setVisibility(View.VISIBLE);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run()
                {
                    try {
                        writeQuoteOnBitmapImage(imageUrl,editZoomageView,moveQuoteText);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            },2000);

        });

        moveQuoteText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        moveQuoteText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveQuoteText.setFocusableInTouchMode(true);
                moveQuoteText.setCursorVisible(true);
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.showSoftInput(moveQuoteText,InputMethodManager.SHOW_IMPLICIT);
            }
        });

        moveQuoteText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event){
                switch (event.getAction()) {
                // calculate the difference between current position of moveQuoteText and the position of the touch event when
                // finger is touched down, offset will be used to calculate the new position of the text
                    case MotionEvent.ACTION_DOWN:
                        dX = moveQuoteText.getX() - event.getRawX();
                        dY = moveQuoteText.getY() - event.getRawY();
                        break;

                    case MotionEvent.ACTION_MOVE:
                        int[] screenCoords = new int[2];
                        editZoomageView.getLocationOnScreen(screenCoords); //get the screen coordinates of the Image View on screen

                        /*
                        * imageLeft gets the leftmost X coordinate of the ImageView --> screenCoords[0]
                        * imageTop gets the topmost Y coordinates of the ImageView --> screenCoords[1]
                        * imageRight is calculated by adding imageRight coordinate with the width of the ImageView
                        * imageBottom is calculated by adding imageTop coordinate with the height of the ImageView
                        */
                        int imageLeft = screenCoords[0];
                        int imageTop = screenCoords[1];
                        int imageRight = imageLeft + editZoomageView.getWidth();
                        int imageBottom = imageTop + editZoomageView.getHeight();

                        //offset is added to the current coordinates of the touch event to calculate the new X and Y
                        //this will give a smooth drag effect
                        float newX = event.getRawX() + dX;
                        float newY = event.getRawY() + dY;

                        if (newX < imageLeft) //if new pos is smaller(beyond) leftmost coordinate
                        {
                            newX = imageLeft; //new pos is reset to leftmost
                        }
                        else if (newX + moveQuoteText.getWidth() > imageRight) //checks if new X pos + the width of the TextView is larger than the rightmost coordinate of ImageView
                            {
                                newX = imageRight - moveQuoteText.getWidth();//if it is,new X pos is set to rightmost coordinate - the textview width,so that the text view remains just at the end
                            }

                        if (newY  < imageTop + moveQuoteText.getHeight() + dY) //checks if the new Y is larger(beyond) topmost coordinate + height of text view
                        {
                            newY = imageTop + moveQuoteText.getHeight() + dY; //if it is, new Y is set to this n it is somehow working
                        } else if (newY + moveQuoteText.getHeight() > imageBottom + dY) //checks if new Y + text height outside image bottom most coordinate
                        {
                            newY = imageBottom - moveQuoteText.getHeight()+ dY; //if it is, then new Y is set to imageBottom - text height + dY, somehow working
                        }

                        moveQuoteText.setX(newX);
                        moveQuoteText.setY(newY);
                        break;

                    default:
                        return false;
                }
                return true;
            }
        });

        editNaviagtionView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @RequiresApi(api = Build.VERSION_CODES.P)
            @Override
            public boolean onNavigationItemSelected (@NonNull MenuItem item)
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
        doneBtn = (AppCompatButton) findViewById(R.id.done_btn);
        blurredBackground = findViewById(R.id.blurred_background);
        editNaviagtionView = findViewById(R.id.text_edit_tab);
        shadowSeekBar = findViewById(R.id.shadow_seek_bar);
        shadowValueBtn = (AppCompatButton) findViewById(R.id.shadow_value_btn);
        fontSizePicker = findViewById(R.id.font_size_picker);
        fontSizeLayout = findViewById(R.id.font_size_layout);
        fontSizeBtn = (AppCompatButton) findViewById(R.id.font_size_btn);
        saveImageButton = findViewById(R.id.save_btn);
        linearDotsLoader = findViewById(R.id.linear_dots_loader);
    }

    //overriding onTouchEvent, and passing motion event to scaleGestureDetector for it to scale textView accordingly
    @Override
    public boolean onTouchEvent (MotionEvent event)
    {
        scaleGestureDetector.onTouchEvent(event);
        return true;
    }

    private void writeQuoteOnBitmapImage(String imageUrl, ImageView imageView, TextView textView) throws IOException {
        String quoteText = textView.getText().toString();
        int textColor = textView.getCurrentTextColor();
        float textSize = textView.getTextSize();
        Typeface textTypeface = textView.getTypeface();
        //getting shadow effects from the textView one by one so that these can be applied on the paint and canvas
        float shadowRadius = textView.getShadowRadius();
        float shadowDx = textView.getShadowDx();
        float shadowDy = textView.getShadowDy();
        int shadowColor = textView.getShadowColor();

        //getting the location of the image using getLocationOnScreen() method
        int[] imageViewCoords = new int[2];
        imageView.getLocationOnScreen(imageViewCoords);

        //getting the location of the textView using getLocationOnScreen() method
        int[] textViewCoords = new int[2];
        textView.getLocationOnScreen(textViewCoords);

        //calculating the actual location of the text view w.r.t the image view
        float posX = textViewCoords[0] - imageViewCoords[0];
        float posY = textViewCoords[1] - imageViewCoords[1] + textSize;


        Glide.with(this)
                .asBitmap()
                .load(imageUrl)
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        // The Bitmap is ready and you can use it here
                        Canvas canvas = new Canvas(resource);
                        Paint paint = new Paint();
                        paint.setColor(textColor);
                        paint.setTextSize(textSize);
                        paint.setTypeface(textTypeface);
                        paint.setShadowLayer(shadowRadius,shadowDx,shadowDy,shadowColor);
                        canvas.drawText(quoteText, posX, posY, paint);

                        moveQuoteText.setVisibility(View.GONE);
                        try {
                            startResultImageActivity(resource);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }

                    }
                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {
                    }
                });

    }


    @RequiresApi(api = Build.VERSION_CODES.P)
    private void openFontMenu()
    {
        shadowSeekBar.setVisibility(View.GONE);
        shadowValueBtn.setVisibility(View.GONE);

        PopupMenu fontPopupMenu = new PopupMenu(this, findViewById(R.id.font_name));
        fontPopupMenu.getMenuInflater().inflate(R.menu.font_menu,fontPopupMenu.getMenu());

        String[] fonts = {"berkshire_swash_regular","black_cherry","block_script",
        "bungasai", "carbon", "clip", "cookie_regular", "crimson_bold", "crimson_bold_italic",
        "crimson_italic", "crimson_roman", "croissant_one", "deftone_stylus", "elsie_swash_caps_regular",
        "ethnocentric_rg", "ethnocentric_rg_it", "facon", "good_times_rg", "great_vibes_regular", "honey_script",
        "honey_script_bold", "lovers_quarrel", "magnolia_script","mathilde", "nasalization_rg", "open_sans_italic",
        "open_sans_light", "open_sans_regular", "oswald_regular", "pecita", "precious", "roboto_light", "roboto_thin",
        "sacramento_regular", "stalemate_regular", "ubuntu_regular","zyphyte"};

        for(int i=0; i<fontPopupMenu.getMenu().size(); i++)
        {
            String path = "fonts/"+fonts[i]+".ttf";
            Typeface font = Typeface.createFromAsset(getAssets(),path);
            MenuItem menuItem = fontPopupMenu.getMenu().getItem(i);
            String menuTitle = menuItem.getTitle().toString();

            TypefaceSpan span = new TypefaceSpan(font);
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(menuTitle);
            spannableStringBuilder.setSpan(span,0,menuTitle.length(),0);
            fontPopupMenu.getMenu().getItem(i).setTitle(spannableStringBuilder);

        }

        fontPopupMenu.show();


        fontPopupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem)
            {
                String fontPath = "fonts/"+ menuItem.getTitleCondensed().toString() + ".ttf";
                Typeface typeface = Typeface.createFromAsset(getAssets(),fontPath);
                moveQuoteText.setTypeface(typeface);
                return true;
            }
        });
    }
    private void openFontSizeMenu()
    {
        shadowSeekBar.setVisibility(View.GONE);
        shadowValueBtn.setVisibility(View.GONE);


        fontSizeLayout.setVisibility(View.VISIBLE);
        fontSizePicker.setMaxValue(120);
        fontSizePicker.setMinValue(5);
        fontSizePicker.setWrapSelectorWheel(true);
        fontSizePicker.setValue(16);

        fontSizePicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                moveQuoteText.setTextSize(newVal);
            }
        });

        fontSizeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fontSizeLayout.setVisibility(View.GONE);
            }
        });

    }


    private void openFontStyleMenu()
    {
        shadowSeekBar.setVisibility(View.GONE);
        shadowValueBtn.setVisibility(View.GONE);
        fontSizeLayout.setVisibility(View.GONE);

        PopupMenu fontStylePopupMenu = new PopupMenu(this, findViewById(R.id.font_style));
        fontStylePopupMenu.getMenuInflater().inflate(R.menu.font_style_menu,fontStylePopupMenu.getMenu());
        fontStylePopupMenu.show();

        fontStylePopupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem)
            {
                switch (menuItem.getItemId())
                {
                    case(R.id.font_bold):
                        moveQuoteText.setTypeface(moveQuoteText.getTypeface(),Typeface.BOLD);
                        return true;

                    case (R.id.font_italic):
                        moveQuoteText.setTypeface(moveQuoteText.getTypeface(),Typeface.ITALIC);
                        return true;

                    case (R.id.font_underlined):
                        moveQuoteText.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
                        return true;

                    case (R.id.font_bold_italic):
                        moveQuoteText.setTypeface(moveQuoteText.getTypeface(),Typeface.BOLD_ITALIC);
                        return true;

                    case (R.id.font_strike_through):
                        moveQuoteText.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                        return true;

                    case (R.id.font_normal):
                        moveQuoteText.setTypeface(null,Typeface.NORMAL);
                        moveQuoteText.getPaint().setUnderlineText(false);
                        moveQuoteText.getPaint().setStrikeThruText(false);
                        return true;
                }

                return false;
            }
        });
    }

    private void openColorMenu()
    {
        shadowSeekBar.setVisibility(View.GONE);
        shadowValueBtn.setVisibility(View.GONE);
        fontSizeLayout.setVisibility(View.GONE);

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

    private void startResultImageActivity(Bitmap resource) throws IOException
    {
        String imageFileName = new SimpleDateFormat("ddmmyyyy_hhmmss").format(new Date());
        File file = new File(this.getCacheDir(), imageFileName+".png");

        FileOutputStream fos = new FileOutputStream(file);
        resource.compress(Bitmap.CompressFormat.PNG, 100, fos);
        fos.flush();
        fos.close();

       Uri uri = FileProvider.getUriForFile(getApplicationContext(),BuildConfig.APPLICATION_ID + ".provider",file);
        Log.d("Uri: ",uri.toString());

        if(uri != null)
        {
            Intent intent = new Intent(ImageEditActivity.this, ImageResultActivity.class);
            intent.setType("image/*");
            intent.putExtra("result_image_uri", uri.toString());
            startActivity(intent);

        }

        linearDotsLoader.setVisibility(View.GONE);

    }

    private void openShadowMenu()
    {
        shadowSeekBar.setVisibility(View.VISIBLE);
        shadowValueBtn.setVisibility(View.VISIBLE);
        fontSizeLayout.setVisibility(View.GONE);

        shadowSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b)
            {
                shadowValueBtn.setText(String.valueOf(i));
                moveQuoteText.setShadowLayer(Integer.parseInt(shadowValueBtn.getText().toString())/10f,
                        1,1,Color.BLACK);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar)
            {
                //shadowValueBtn.setText(seekBar.getProgress());
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar)
            {
                //shadowValueBtn.setText(seekBar.getProgress());
            }

        });

        shadowValueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                moveQuoteText.setShadowLayer(Integer.parseInt(shadowValueBtn.getText().toString())/10f,1,1,Color.BLACK);
                shadowSeekBar.setVisibility(View.GONE);
                shadowValueBtn.setVisibility(View.GONE);
            }
        });


    }

}