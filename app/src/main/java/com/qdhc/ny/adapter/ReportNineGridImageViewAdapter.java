package com.qdhc.ny.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.jaeger.ninegridimageview.NineGridImageViewAdapter;
import com.qdhc.ny.R;
import com.qdhc.ny.entity.Media;

public class ReportNineGridImageViewAdapter extends NineGridImageViewAdapter<Media> {
    @Override
    protected void onDisplayImage(Context context, ImageView imageView, Media media) {
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.loading)
                .error(R.drawable.ic_default_pic);
        Glide.with(context)
                .load(media.getUrl())
                .apply(requestOptions)
                .into(imageView);
    }

    @Override
    protected ImageView generateImageView(Context context) {
        return super.generateImageView(context);
    }

}
