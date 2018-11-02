package com.example.zh231.neteasenews;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.ref.WeakReference;

import static com.example.zh231.neteasenews.fragment_home.home_data_count;
import static com.example.zh231.neteasenews.fragment_home.home_data_start;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView tv_home;
    private TextView tv_video;
    private TextView tv_jj;
    private TextView tv_me;

    private fragment_home fh;
    private fragment_video fv;
    private fragment_jj fj;
    private fragment_me fm;

    private final String fileName="localJson.json";//本地json文件名
    private final String url_main="http://c.m.163.com/nc/article/headline/T1348647853363/";//T1348647853363为新闻类型(不是很清楚)
    private final String url_video="http://c.m.163.com/recommend/getChanListNews?channel=T1457068979049&size=20";//视频

    private String TAG="MainActivity";

    FragmentTransaction fragmentTransaction;

    final myHandle handle = new myHandle(MainActivity.this);

    static class myHandle extends Handler {
        private final WeakReference<MainActivity> activity;

        public myHandle(MainActivity activity){
            this.activity=new WeakReference<MainActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            MainActivity mainActivity=this.activity.get();
            if (mainActivity==null){
                super.handleMessage(msg);
                return;
            }
            switch (msg.what){
                case 0://第一次加载/首页
                    Log.d("Handle----","首页头条数据获取完成");

                    mainActivity.fragmentTransaction=mainActivity.getSupportFragmentManager().beginTransaction();

                    String content_main=String.valueOf(msg.obj);

                    new utils(mainActivity).parseJson(content_main);
                    new utils(mainActivity).saveFile(mainActivity.fileName,String.valueOf(msg.obj));//解析完成后保存json数据，下次打开加载

                    if (mainActivity.fh==null){
                        mainActivity.fh=new fragment_home();
                        mainActivity.fragmentTransaction.add(R.id.fragment_frameLayout,mainActivity.fh);
                    }else{
                        mainActivity.fragmentTransaction.show(mainActivity.fh);
                    }
                    mainActivity.fragmentTransaction.commit();
                    break;
               case 2:
                   Log.d("Handle----","视频数据获取完成:"+String.valueOf(msg.obj));
                   break;
            }
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initActivity();
        setContentView(R.layout.activity_main);
        initView();
        initViewData();
    }


    /**
     * 初始化
     */
    private void initActivity(){
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }

    /**
     * 初始化View
     */
    private void initView(){
        tv_home=(TextView)findViewById(R.id.text_home);
        tv_video=(TextView)findViewById(R.id.text_video);
        tv_jj=(TextView)findViewById(R.id.text_jj);
        tv_me=(TextView)findViewById(R.id.text_me);
    }

    /**
     * 初始化View数据
     */
    private void initViewData(){

        tv_home.setOnClickListener(this);
        tv_video.setOnClickListener(this);
        tv_jj.setOnClickListener(this);
        tv_me.setOnClickListener(this);

        if (tv_home!=null){//默认选择
            tv_home.setSelected(true);

            String content=new utils(this).readFile(this.fileName);
            if (content==null||content==""||content.isEmpty()){
                Log.d(TAG,"本地数据为空，在线获取");
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        String con = new utils(MainActivity.this).sendGet(url_main+home_data_start+"-"+home_data_count+".html");
                        Message message = new Message();
                        message.what=0;
                        message.obj=String.valueOf(con);
                        handle.sendMessage(message);
                    }
                };
                Thread thread=new Thread(runnable);
                thread.start();
            }else{
                Log.d(TAG,"存在本地数据，直接读取");
                new utils(this).parseJson(content);
                fragmentTransaction=getSupportFragmentManager().beginTransaction();
                if (fh==null){
                    fh=new fragment_home();
                    fragmentTransaction.add(R.id.fragment_frameLayout,fh);
                }else{
                    fragmentTransaction.show(fh);
                }
                fragmentTransaction.commit();
            }
        }
        //设置drawableTop大小
        int drawable_width=72;
        int drawable_height=72;
        Drawable drawable_home = getDrawable(R.drawable.tab_menu_home_image);
        drawable_home.setBounds(0,0,drawable_width,drawable_height);
        tv_home.setCompoundDrawables(null,drawable_home,null,null);

        Drawable drawable_video = getDrawable(R.drawable.tab_menu_video_image);
        drawable_video.setBounds(0,0,drawable_width,drawable_height);
        tv_video.setCompoundDrawables(null,drawable_video,null,null);

        Drawable drawable_jj = getDrawable(R.drawable.tab_menu_jj_image);
        drawable_jj.setBounds(0,0,drawable_width,drawable_height);
        tv_jj.setCompoundDrawables(null,drawable_jj,null,null);

        Drawable drawable_me = getDrawable(R.drawable.tab_menu_me_image);
        drawable_me.setBounds(0,0,drawable_width,drawable_height);
        tv_me.setCompoundDrawables(null,drawable_me,null,null);
    }

    @Override
    public void onClick(View v) {

        fragmentTransaction=getSupportFragmentManager().beginTransaction();
        Runnable runnable;
        Thread thread;

        new utils(this).hideFragment(fh,fragmentTransaction);//隐藏全部Fragment
        new utils(this).hideFragment(fv,fragmentTransaction);//隐藏全部Fragment
        new utils(this).hideFragment(fj,fragmentTransaction);//隐藏全部Fragment
        new utils(this).hideFragment(fm,fragmentTransaction);//隐藏全部Fragment

        switch (v.getId()){
            case R.id.text_home:
                new utils(this).resetSeleceed(tv_home,tv_video,tv_jj,tv_me);
                tv_home.setSelected(true);
                Toast.makeText(MainActivity.this,"加载中...",Toast.LENGTH_SHORT).show();
                runnable = new Runnable() {
                    @Override
                    public void run() {
                        String con = new utils(MainActivity.this).sendGet(url_main+home_data_start+"-"+home_data_count+".html");
                        Message message = new Message();
                        message.what=0;
                        message.obj=String.valueOf(con);
                        handle.sendMessage(message);
                    }
                };
                thread=new Thread(runnable);
                thread.start();
                break;
            case R.id.text_video:
                new utils(this).resetSeleceed(tv_home,tv_video,tv_jj,tv_me);
                tv_video.setSelected(true);

                Toast.makeText(MainActivity.this,"加载中...",Toast.LENGTH_SHORT).show();
                runnable = new Runnable() {
                    @Override
                    public void run() {
                        String con = new utils(MainActivity.this).sendGet(url_video);
                        Message message = new Message();
                        message.what=2;
                        message.obj=String.valueOf(con);
                        handle.sendMessage(message);
                    }
                };
                thread=new Thread(runnable);
                thread.start();

                if (fv==null){//-1为空
                    fv=new fragment_video();
                    fragmentTransaction.add(R.id.fragment_frameLayout,fv);
                }else{
                    fragmentTransaction.show(fv);
                }

                break;
            case R.id.text_jj:
                new utils(this).resetSeleceed(tv_home,tv_video,tv_jj,tv_me);
                tv_jj.setSelected(true);

                if (fj==null){
                    fj=new fragment_jj();
                    fragmentTransaction.add(R.id.fragment_frameLayout,fj);
                }else{
                    fragmentTransaction.show(fj);
                }

                break;
            case R.id.text_me:
                new utils(this).resetSeleceed(tv_home,tv_video,tv_jj,tv_me);
                tv_me.setSelected(true);

                if (fm==null){
                    fm=new fragment_me();
                    fragmentTransaction.add(R.id.fragment_frameLayout,fm);
                }else{
                    fragmentTransaction.show(fm);
                }

                break;
        }
        fragmentTransaction.commit();
    }


}
