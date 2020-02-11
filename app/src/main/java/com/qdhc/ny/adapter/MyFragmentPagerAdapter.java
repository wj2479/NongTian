package com.qdhc.ny.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * @Author wj
 * @Date 2019/11/12
 * @Desc
 * @Url http://www.chuangze.cn
 */
public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
    private String[] titles;
    private List<Fragment> fragments;

    private int mChildCount = 0;

    public MyFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        return fragments.get(i);
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    //自定义一个添加title和fragment的方法，供Activity使用
    public void addTitlesAndFragments(String[] titles, List<Fragment> fragments) {
        this.titles = titles;
        this.fragments = fragments;
    }

    @Override
    public void notifyDataSetChanged() {
        mChildCount = getCount();
        super.notifyDataSetChanged();
    }

}