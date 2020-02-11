package com.sj.core.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import android.graphics.Bitmap
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory
import com.bumptech.glide.request.target.BitmapImageViewTarget
import com.bumptech.glide.load.resource.bitmap.TransformationUtils.centerCrop
import com.bumptech.glide.load.resource.bitmap.CircleCrop






/**
 * Created by 申健 on 2018/8/22.
 */
object ImageLoaderUtil {

    /**
     * 加载图片
     * @param context
     * @param url
     * @param iv
     */
    fun load(context: Context?, url: String?, iv: ImageView?) {
        iv?.let {
            Glide.with(context!!)
                    .load(url)
                    .transition(DrawableTransitionOptions().crossFade())
                    .into(iv)
        }
    } /**
     * 加载图片
     * @param context
     * @param url
     * @drawableid  失败的图片
     * @param iv
     */
    fun load(context: Context?, url: String?,  drawableid:Int, iv: ImageView?) {
        iv?.let {
                var requestOptions =RequestOptions()
                        .placeholder(drawableid)				//加载成功之前占位图
                        .error(drawableid)					//加载错误之后的错误图
            Glide.with(context!!)
                    .load(url)
                    .apply(requestOptions)
                    .transition(DrawableTransitionOptions().crossFade())
                    .into(iv)
        }
    }

    /**
     * 加载图片
     * @param context
     * @param url
     * @param iv
     * @param vorners -1 圆头像  其他为圆角大小
     * @param drawableId  默认头像
     */
    fun loadCorners(context: Context?, url: String?, iv: ImageView?, vorners: Int,drawableId:Int) {
        iv?.let {
            val mRequestOptions = if (vorners < 0) {
                RequestOptions.bitmapTransform(CircleCrop()).placeholder(drawableId)
                        .error(drawableId);
//                RequestOptions.circleCropTransform().diskCacheStrategy(DiskCacheStrategy.NONE).placeholder(drawableId)
//                        .error(drawableId)//图片加载失败后，显示的图片
//                  .skipMemoryCache(true)
            } else {
                //设置图片圆角角度
                val roundedCorners = RoundedCorners(vorners)
                //通过RequestOptions扩展功能
                RequestOptions.bitmapTransform(roundedCorners)
            }

            Glide.with(context!!)
                    .load(url)
                    .apply(mRequestOptions)
                    .into(iv)
        }
    }
}