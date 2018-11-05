package com.example.zh231.neteasenews.adapter;


import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ViewHolder {

    private SparseArray<View> mViews;
    private View mConvertView;

    public ViewHolder(Context context, ViewGroup viewGroup,int layoutId,int position){

        this.mViews=new SparseArray<>();
        this.mConvertView= LayoutInflater.from(context).inflate(layoutId,viewGroup,false);
        this.mConvertView.setTag(this);

    }

    public static ViewHolder get(Context context,View convertView,ViewGroup viewGroup,int layoutId,int position){
        if (convertView == null) {
            return new ViewHolder(context, viewGroup, layoutId, position);
        } else {
            return (ViewHolder)convertView.getTag();
        }
    }

    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);

        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    public View getConvertView() {
        return mConvertView;
    }

}
