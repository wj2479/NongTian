package com.qdhc.ny.view;

import android.content.Context;
import android.util.AttributeSet;

import com.jaeger.ninegridimageview.NineGridImageView;
import com.qdhc.ny.entity.Media;

public class MyNineGridImageView extends NineGridImageView<Media> {
    public MyNineGridImageView(Context context) {
        super(context);
    }

    public MyNineGridImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
}
