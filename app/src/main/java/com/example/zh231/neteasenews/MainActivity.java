package com.example.zh231.neteasenews;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView tv_home;
    private TextView tv_video;
    private TextView tv_jj;
    private TextView tv_me;
    private FrameLayout fragment_frameLayout;
    private FragmentManager fragmentManager;
    private ArrayList<Fragment> fragments;

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
    }

    /**
     * 初始化View
     */
    private void initView(){
        tv_home=(TextView)findViewById(R.id.text_home);
        tv_video=(TextView)findViewById(R.id.text_video);
        tv_jj=(TextView)findViewById(R.id.text_jj);
        tv_me=(TextView)findViewById(R.id.text_me);
        fragment_frameLayout=(FrameLayout)findViewById(R.id.fragment_frameLayout);
    }

    /**
     * 初始化View数据
     */
    private void initViewData(){
        tv_home.setOnClickListener(this);
        tv_video.setOnClickListener(this);
        tv_jj.setOnClickListener(this);
        tv_me.setOnClickListener(this);
        fragments=new ArrayList<Fragment>();

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

        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        for (int i=0;i<fragments.size();i++){
            new utils().hideFragment(fragments.get(i),fragmentTransaction);//隐藏全部Fragment
            fragments.remove(i);
        }

        switch (v.getId()){
            case R.id.text_home:
                new utils().resetSeleceed(tv_home,tv_video,tv_jj,tv_me);
                tv_home.setSelected(true);
                if (fragments.indexOf(new fragment_home())==-1){//-1为空
                    fragments.add(new fragment_home());
                    fragmentTransaction.add(R.id.fragment_frameLayout,fragments.get(0));
                }else{
                    fragmentTransaction.show(fragments.get(0));
                }

                break;
            case R.id.text_video:
                new utils().resetSeleceed(tv_home,tv_video,tv_jj,tv_me);
                tv_video.setSelected(true);
                if (fragments.indexOf(new fragment_video())==-1){//-1为空
                    fragments.add(new fragment_video());
                    fragmentTransaction.add(R.id.fragment_frameLayout,fragments.get(0));
                }else{
                    fragmentTransaction.show(fragments.get(0));
                }

                break;
            case R.id.text_jj:
                new utils().resetSeleceed(tv_home,tv_video,tv_jj,tv_me);
                tv_jj.setSelected(true);
                if (fragments.indexOf(new fragment_jj())==-1){
                    fragments.add(new fragment_jj());
                    fragmentTransaction.add(R.id.fragment_frameLayout,fragments.get(0));
                }else{
                    fragmentTransaction.show(fragments.get(0));
                }

                break;
            case R.id.text_me:
                new utils().resetSeleceed(tv_home,tv_video,tv_jj,tv_me);
                tv_me.setSelected(true);
                if (fragments.indexOf(new fragment_me())==-1){
                    fragments.add(new fragment_me());
                    fragmentTransaction.add(R.id.fragment_frameLayout,fragments.get(0));
                }else{
                    fragmentTransaction.show(fragments.get(0));
                }

                break;
        }
        fragmentTransaction.commit();
    }


}
