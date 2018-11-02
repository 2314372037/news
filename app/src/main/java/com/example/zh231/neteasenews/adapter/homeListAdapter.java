package com.example.zh231.neteasenews.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.zh231.neteasenews.R;

public class homeListAdapter extends BaseAdapter {

    Context context;
    String[] title;
    String[] replyCount;

    public homeListAdapter(Context context, String[] title, String[] replyCount){
        this.context=context;
        this.title=title;
        this.replyCount=replyCount;
    }

    @Override
    public int getCount() {
        return title.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        if (convertView==null){
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.news_list_layout,null);
            viewHolder=new ViewHolder();
            viewHolder.imageView=convertView.findViewById(R.id.image);
            viewHolder.title=convertView.findViewById(R.id.title);
            viewHolder.replyCount=convertView.findViewById(R.id.replyCount);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }
            //viewHolder.imageView=
        viewHolder.title.setText(title[position]);
        viewHolder.replyCount.setText(replyCount[position]);

        return convertView;
    }

    private static class ViewHolder{
        ImageView imageView;
        TextView title;
        TextView replyCount;
    }
}
