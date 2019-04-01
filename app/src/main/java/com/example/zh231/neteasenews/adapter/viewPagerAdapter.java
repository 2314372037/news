package com.example.zh231.neteasenews.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.ArrayList;


public class viewPagerAdapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> list;
    private String titles[];

    public viewPagerAdapter(FragmentManager fm,ArrayList<Fragment> list,String titles[]) {
        super(fm);
        this.list=list;
        this.titles=titles;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        super.destroyItem(container, position, object);
    }



    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public Fragment getItem(int i) {
        return list.get(i);
    }

    @Override
    public int getCount() {
        return list.size();
    }
}
