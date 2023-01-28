package com.example.quotify.utilities;

import android.util.TypedValue;
import android.view.ScaleGestureDetector;
import android.view.ViewGroup;
import android.widget.TextView;

public class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
    float scaleFactor ;
    TextView textView;
    private float minTextSize = 5; // minimum text size
    private float maxTextSize = 200; // maximum text size

    public ScaleListener(TextView textView, float scaleFactor)
    {
        this.textView = textView;
        this.scaleFactor = scaleFactor;
    }

    @Override
    public boolean onScale(ScaleGestureDetector detector)
    {

        float textSize = textView.getTextSize() * detector.getScaleFactor();
        textSize = Math.max(minTextSize, Math.min(textSize, maxTextSize));
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);

        return true;
    }
}
