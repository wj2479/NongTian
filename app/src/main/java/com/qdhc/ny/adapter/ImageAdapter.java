package com.qdhc.ny.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.qdhc.ny.R;
import com.qdhc.ny.entity.Media;

import java.util.List;

/**
 * 选择银行列表
 */

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {
    Activity mContext;
    List<Media> data;

    RequestOptions options;

    protected OnItemClickListener mItemClickListener;

    public ImageAdapter(Activity mContext, @Nullable List<Media> data) {
        this.mContext = mContext;
        this.data = data;

        options = new RequestOptions().placeholder(R.drawable.common_load_default)//加载成功之前占位图
                .error(R.drawable.common_load_default)//加载错误之后的错误图
        ;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        String url = data.get(position).getUrl();

        if (url.toLowerCase().endsWith("mp4")) {
            holder.tv.setVisibility(View.VISIBLE);
        } else {
            holder.tv.setVisibility(View.GONE);
        }

        Glide.with(mContext).load(url).into(holder.iv);
        holder.iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mItemClickListener != null) {
                    mItemClickListener.onItemClick(position, holder.iv);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView iv;
        private TextView tv;

        private ViewHolder(View itemView) {
            super(itemView);
            iv = (ImageView) itemView.findViewById(R.id.fiv);
            tv = itemView.findViewById(R.id.tv_duration);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position, View v);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mItemClickListener = listener;
    }
}