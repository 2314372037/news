package com.example.zh231.neteasenews;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;


public class videoActivity extends AppCompatActivity {

    private String videoUrl;
    private IjkMediaPlayer ijkMediaPlayer;
    private SurfaceView surfaceView;
    private int windowWidth;
    private int windowHeight;
    private TextView liveStartSeek;
    private SeekBar liveSeek;
    private TextView liveEndSeek;
    private ImageView liveFullScreenBtn;
    private boolean isFull=false;

    private Handler handler=new Handler();
    private Runnable runnable=new Runnable() {
        private long currentPostion,duration;
        @Override
        public void run() {
            currentPostion=ijkMediaPlayer.getCurrentPosition();//mms
            duration=ijkMediaPlayer.getDuration();

            liveStartSeek.setText((int)currentPostion/1000+"");
            liveEndSeek.setText((int)duration/1000+"");

            liveSeek.setMin(0);
            liveSeek.setMax((int)duration/1000);
            liveSeek.setProgress((int)currentPostion/1000);

            handler.postDelayed(runnable,1000);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_play);

        windowWidth = getWindow().getWindowManager().getDefaultDisplay().getWidth();
        windowHeight = getWindow().getWindowManager().getDefaultDisplay().getHeight();

        Intent intent = getIntent();
        videoUrl = intent.getStringExtra("url");

        ijkMediaPlayer = new IjkMediaPlayer();

        liveStartSeek = findViewById(R.id.liveStartSeek);
        liveSeek = findViewById(R.id.liveSeek);
        liveEndSeek = findViewById(R.id.liveEndSeek);
        liveFullScreenBtn = findViewById(R.id.liveFullScreenBtn);

        surfaceView = findViewById(R.id.surfaceView);

        surfaceView.getHolder().addCallback(callback);
    }

    private void tryFullScreen(boolean fullScreen) {
            ActionBar supportActionBar = getSupportActionBar();
            if (supportActionBar != null) {
                if (fullScreen) {
                    supportActionBar.hide();
                } else {
                    supportActionBar.show();
                }
            }
        setFullScreen(fullScreen);
    }

    private void setFullScreen(boolean fullScreen) {
        WindowManager.LayoutParams attrs = getWindow().getAttributes();
        if (fullScreen) {
            attrs.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
            getWindow().setAttributes(attrs);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        } else {
            attrs.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getWindow().setAttributes(attrs);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
    }


    private void PlayerInit(String url){
        if (url.contains("https")){
            url=url.replace("https","http");//暂不支持https,后续编译支持https
        }
        ijkMediaPlayer.setOnCompletionListener(new IMediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(IMediaPlayer iMediaPlayer) {
                videoActivity.this.finish();
            }
        });

        ijkMediaPlayer.setOnVideoSizeChangedListener(new IMediaPlayer.OnVideoSizeChangedListener() {
            @Override
            public void onVideoSizeChanged(IMediaPlayer iMediaPlayer, int i, int i1, int i2, int i3) {
                //Log.d("onVideoSizeChanged:","-------------i:"+i+"i1:"+i1+"i2:"+i2+"i3:"+i3);
                //i宽，i1高
                surfaceView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,i1+200));
            }
        });


        try {
            ijkMediaPlayer.setDataSource(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ijkMediaPlayer.prepareAsync();
    }

    private void release() {
        if (ijkMediaPlayer != null) {
            ijkMediaPlayer.stop();
            ijkMediaPlayer.release();
            ijkMediaPlayer = null;
        }
        IjkMediaPlayer.native_profileEnd();
    }

    private SurfaceHolder.Callback callback = new SurfaceHolder.Callback() {
        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            PlayerInit(videoUrl);
            Log.d("surfaceCreated:","-------------");
            ijkMediaPlayer.setDisplay(surfaceView.getHolder());
            handler.post(runnable);
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            if (surfaceView != null) {
                surfaceView.getHolder().removeCallback(callback);
                surfaceView = null;
            }
        }
    };

    protected void setDarkStatusIcon(boolean dark) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            View decorView = getWindow().getDecorView();
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
    protected void onResume() {
        if (ijkMediaPlayer != null) {
            if (ijkMediaPlayer.isPlayable()){
                ijkMediaPlayer.start();
            }
        }
        super.onResume();
    }

    @Override
    protected void onStop() {
        if (ijkMediaPlayer != null) {
            ijkMediaPlayer.stop();
        }
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        release();
        setDarkStatusIcon(false);
        handler.removeCallbacks(runnable);
        super.onDestroy();
    }
}
