package com.example.zh231.neteasenews;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView tv_home;
    private TextView tv_video;
    private TextView tv_jj;
    private TextView tv_me;

    private fragment_home fh;
    private fragment_video fv;
    private fragment_jj fj;
    private fragment_me fm;

    private String TAG="MainActivity";

    FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initActivity();
        setContentView(R.layout.activity_main);
        test();
        initView();
        initViewData();
    }



    private void test (){
        int arrayy[]={3,1,14,5,8,6,7,3,11,15};
        for (int i=0;i<arrayy.length-1;i++){
            for (int j=0;j<arrayy.length-1-i;j++){
                if (arrayy[j]>arrayy[j+1]){
                    int temp=arrayy[j];
                    arrayy[j]=arrayy[j+1];
                    arrayy[j+1]=temp;
                }
            }
        }

    }


    /**
     * 初始化
     */
    private void initActivity(){
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            getWindow().setStatusBarColor(Color.TRANSPARENT);
//        }
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
            fragmentTransaction = getSupportFragmentManager().beginTransaction();
            if (fh == null) {
                fh = new fragment_home();//初始化fragment
                fragmentTransaction.add(R.id.fragment_frameLayout, fh);
            } else {
                fragmentTransaction.show(fh);
            }
            fragmentTransaction.commit();
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

        new utils(this).hideFragment(fh,fragmentTransaction);//隐藏全部Fragment
        new utils(this).hideFragment(fv,fragmentTransaction);//隐藏全部Fragment
        new utils(this).hideFragment(fj,fragmentTransaction);//隐藏全部Fragment
        new utils(this).hideFragment(fm,fragmentTransaction);//隐藏全部Fragment

        switch (v.getId()){
            case R.id.text_home:
                new utils(this).resetSeleceed(tv_home,tv_video,tv_jj,tv_me);
                tv_home.setSelected(true);

                if (fh==null){
                    fh=new fragment_home();
                    fragmentTransaction.add(R.id.fragment_frameLayout,fh);
                }else{
                    fragmentTransaction.show(fh);
                }
                break;
            case R.id.text_video:
                new utils(this).resetSeleceed(tv_home,tv_video,tv_jj,tv_me);
                tv_video.setSelected(true);

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
