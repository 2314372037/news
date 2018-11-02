package com.example.zh231.neteasenews.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.zh231.neteasenews.R;

public class newsViewHolder extends RecyclerView.ViewHolder {

    TextView newsTitle;

    public newsViewHolder(@NonNull View itemView) {
        super(itemView);
        newsTitle=itemView.findViewById(R.id.newsTypeTextView);
    }
}
