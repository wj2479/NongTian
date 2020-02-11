package com.qdhc.ny.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.qdhc.ny.bean.IndexInfo;
import com.sj.core.glide.GlideApp;

import java.util.List;

/**
 * Created by 申健 on 2018/8/21.
 */

public class UltraPagerAdapter extends PagerAdapter {
    private boolean isMultiScr;
    private List<IndexInfo.BannerBean> mStringList;
    private Context mContext;
    private boolean isPoint;

    public UltraPagerAdapter(Context mContext, boolean isMultiScr, List<IndexInfo.BannerBean> mStringList) {
        this.isMultiScr = isMultiScr;
        this.mStringList = mStringList;
        this.mContext = mContext;

    }

    public UltraPagerAdapter(Context mContext, boolean isMultiScr, List<IndexInfo.BannerBean> mStringList, boolean isPoint) {
        this.isMultiScr = isMultiScr;
        this.mStringList = mStringList;
        this.mContext = mContext;
        this.isPoint = isPoint;
    }

    @Override
    public int getCount() {
        return mStringList.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView = new ImageView(mContext);
        imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        //加载图片
        if (!mStringList.isEmpty()) {
           String url = mStringList.get(position).getImgUrl();
            GlideApp.with(mContext)
                    .load(url)
//                    .placeholder(R.drawable.common_load_default)
                    .fitCenter()
                    .into(imageView);
        }
        container.addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ImageView view = (ImageView) object;
        container.removeView(view);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}