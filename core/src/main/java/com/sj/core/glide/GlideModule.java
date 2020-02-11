package com.sj.core.glide;

import android.content.Context;

import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.module.AppGlideModule;
import com.bumptech.glide.request.RequestOptions;

/**
 * Created by 申健 on 2018/8/21.
 */
@com.bumptech.glide.annotation.GlideModule
public final class GlideModule extends AppGlideModule {
    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        //自定义图片质量 增加图片清晰度
        builder.setDefaultRequestOptions(
                new RequestOptions()
                        .format(DecodeFormat.PREFER_RGB_565).disallowHardwareConfig());
        //设置磁盘缓存大小
        int size = 300 * 1024 * 1024;
        String dir = "images";
        //设置磁盘缓存
        builder.setDiskCache(new InternalCacheDiskCacheFactory(context, dir, size));
    }
    @Override
    public boolean isManifestParsingEnabled() {
        return false;
    }
}