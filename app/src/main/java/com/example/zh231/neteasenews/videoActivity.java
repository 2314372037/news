package com.example.zh231.neteasenews;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;
import android.widget.Toast;
import android.widget.VideoView;


public class videoActivity extends AppCompatActivity {

    private String videoUrl;
    private VideoView videoView;
    private SeekBar seekbar;

    private Handler handler = new Handler();
    private Runnable run =  new Runnable() {
        int currentPosition, duration;
        public void run() {
            currentPosition = videoView.getCurrentPosition();
            duration = videoView.getDuration();
            int time = ((currentPosition * 100) / duration);
            seekbar.setProgress(time);
            //Log.d("获取进度",""+time);
            handler.postDelayed(run, 1000);
        }
    };

    protected void setDarkStatusIcon(boolean dark) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            View decorView = getWindow().getDecorView();
            if (decorView == null) return;
            int vis = decorView.getSystemUiVisibility();
            if (dark) {
                vis |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            } else {
                vis &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            }
            decorView.setSystemUiVisibility(vis);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        setDarkStatusIcon(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_play);

        videoView=findViewById(R.id.videoView);
        seekbar=findViewById(R.id.seekbar);
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                finish();
            }
        });

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
            handler.post(run);
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
            handler.removeCallbacks(run);
            videoView=null;
        }
        setDarkStatusIcon(false);
        super.onDestroy();
    }
}
