package com.example.zh231.neteasenews.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.zh231.neteasenews.R;
import com.example.zh231.neteasenews.jsonParse.homeListData;

import java.util.ArrayList;

public class fragment_home_adapter<T> extends commonAdapter<T> {

    public fragment_home_adapter(Context context, ArrayList<T> data){
        super(context,data);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder=ViewHolder.get(mContext,convertView,parent, R.layout.news_list_layout,position);

        ImageView image=viewHolder.getView(R.id.image);
        TextView title = viewHolder.getView(R.id.title);
        TextView source = viewHolder.getView(R.id.source);
        TextView replyCount = viewHolder.getView(R.id.replyCount);

        String replyText =((homeListData) data.get(position)).getcommentCount()+"跟帖";
        title.setText(((homeListData)data.get(position)).gettitle());
        source.setText(((homeListData) data.get(position)).getsource());
        replyCount.setText(replyText);
        if (((homeListData)data.get(position)).gethasImg()==1){//存在图片才加载图片
            Glide.with(mContext)
                    .load(((homeListData)data.get(position)).getimgsrc())
                    .into(image);
        }

        return viewHolder.getConvertView();
    }
}
