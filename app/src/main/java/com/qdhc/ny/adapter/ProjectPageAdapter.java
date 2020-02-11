package com.qdhc.ny.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qdhc.ny.R;
import com.qdhc.ny.bmob.Project;

import java.util.List;

/**
 * @Author wj
 * @Date 2019/12/25
 * @Desc
 * @Url http://www.chuangze.cn
 */
public class ProjectPageAdapter extends PagerAdapter {

    private List<Project> list;
    private Context context;

    public ProjectPageAdapter(Context context, List<Project> list) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = View.inflate(context, R.layout.item_page_project, null);
        TextView tv = view.findViewById(R.id.tv_title);
        tv.setText(list.get(position).getName());
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }
}
