package com.example.quotify.utilities;

import android.content.Context;
import android.widget.TextView;

import com.example.quotify.R;
import com.skydoves.colorpickerview.AlphaTileView;
import com.skydoves.colorpickerview.ColorEnvelope;
import com.skydoves.colorpickerview.flag.FlagView;

public class ColorFlag extends FlagView
{
    private TextView colorHexView;
    private AlphaTileView colorTileView;

    public ColorFlag(Context context, int layout)
    {
        super(context, layout);
        colorHexView = findViewById(R.id.color_code);
        colorTileView = findViewById(R.id.color_tile);
    }

    @Override
    public void onRefresh(ColorEnvelope colorEnvelope)
    {
        colorHexView.setText("#" + colorEnvelope.getHexCode());
        colorTileView.setPaintColor(colorEnvelope.getColor());
    }
}
