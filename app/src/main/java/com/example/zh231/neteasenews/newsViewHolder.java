package com.example.zh231.neteasenews;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class newsViewHolder extends RecyclerView.ViewHolder {

    TextView newsTitle;

    public newsViewHolder(@NonNull View itemView) {
        super(itemView);
        newsTitle=itemView.findViewById(R.id.newsTypeTextView);
    }
}
