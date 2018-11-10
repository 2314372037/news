package com.example.zh231.neteasenews;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;
import android.widget.VideoView;


public class videoPlay extends AppCompatActivity {

    private String videoUrl;
    private VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_play);

        videoView=findViewById(R.id.videoView);

        Intent intent=getIntent();
        videoUrl = intent.getStringExtra("url");
        try{
            PlayVideoByUrl(videoUrl);
        }catch (Throwable t){
            Toast.makeText(this,"报错了--!，"+t.getMessage(),Toast.LENGTH_LONG).show();
            t.printStackTrace();
        }
    }

    private void PlayVideoByUrl(String url) throws Throwable{
        if (!videoView.isPlaying()){
            videoView.setVideoURI(Uri.parse(url));
            videoView.start();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (videoView!=null){
            videoView.resume();
        }else{
            Toast.makeText(this,"播放失败",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onStop() {
        if (videoView != null) {
            if (videoView.canPause()) {
                videoView.pause();
            }
        }
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        if (videoView!=null){
            if (videoView.isPlaying()){
                videoView.stopPlayback();
            }
            videoView=null;
        }
        super.onDestroy();
    }
}
