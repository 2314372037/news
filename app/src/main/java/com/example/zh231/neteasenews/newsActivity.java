package com.example.zh231.neteasenews;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.lang.ref.WeakReference;

public class newsActivity extends AppCompatActivity {

    static RelativeLayout contentLayout;

    static class newsContentHandle extends Handler{
        final WeakReference<Context> context;

        public newsContentHandle(Context context) {
            this.context=new WeakReference<Context>(context);
        }

        @Override
        public void handleMessage(Message msg) {
            Context context=this.context.get();
            if (context==null){
                super.handleMessage(msg);
                return;
            }
            switch (msg.what){
                case 0:
                    WebView webView = new WebView(context);
                    webView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                    webView.loadData(String.valueOf(msg.obj),"text/html","utf-8");
                    contentLayout.addView(webView);
                    break;
            }


        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_content);
        Intent intent=getIntent();
        final String url = intent.getStringExtra("url");
        if (!url.isEmpty()){
            contentLayout = findViewById(R.id.newsContentLayout);
            final newsContentHandle handle=new newsContentHandle(newsActivity.this);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    String html=new utils(newsActivity.this).sendGet(url,null);
                    String main="解析失败";
                    try{
                        main=new utils(newsActivity.this).parseHtml(html);
                    }catch (Throwable t){
                        t.printStackTrace();
                    }
                    Message message=new Message();
                    message.what=0;
                    message.obj=main;
                    handle.sendMessage(message);
                }
            }).start();
        }
    }
}
