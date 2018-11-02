package com.example.zh231.neteasenews.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.zh231.neteasenews.R;

public class newsTypeAdapter extends RecyclerView.Adapter<newsViewHolder> {

    private Context context;
    private String[] newsTypeStrings;

    public newsTypeAdapter(Context context,String[] newsTypeStrings) {
        super();
        this.context=context;
        this.newsTypeStrings=newsTypeStrings;
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @NonNull
    @Override
    public newsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.news_item_layout,viewGroup,false);
        newsViewHolder newsVH=new newsViewHolder(view);
        return newsVH;
    }

    @Override
    public void onBindViewHolder(@NonNull newsViewHolder newsViewHolder, int i) {
        final int j=i;
        newsViewHolder.newsTitle.setText(newsTypeStrings[i]);
        newsViewHolder.newsTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,newsTypeStrings[j],Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return newsTypeStrings.length;
    }
}
