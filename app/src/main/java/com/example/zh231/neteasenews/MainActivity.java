package com.example.zh231.neteasenews;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView tv_home;
    private TextView tv_video;
    private TextView tv_me;
    private RelativeLayout mainRootLayout;

    private fragment_home fh;
    private fragment_video fv;
    private fragment_me fm;

    FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initViewData();
    }

    private void setStatusBarColor(int colorId){
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            //注意要清除 FLAG_TRANSLUCENT_STATUS flag
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().setStatusBarColor(getResources().getColor(colorId));
        }
    }

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

    /**
     * 初始化View
     */
    private void initView(){
        setStatusBarColor(android.R.color.holo_red_light);

        tv_home=(TextView)findViewById(R.id.text_home);
        tv_video=(TextView)findViewById(R.id.text_video);
        tv_me=(TextView)findViewById(R.id.text_me);
        mainRootLayout=(RelativeLayout) findViewById(R.id.mainRootLayout);
    }

    /**
     * 初始化View数据
     */
    private void initViewData(){
        tv_home.setOnClickListener(this);
        tv_video.setOnClickListener(this);
        tv_me.setOnClickListener(this);

        //设置底部按钮大小及相对位置
        int drawable_width=72;
        int drawable_height=72;
        Drawable drawable_home = getDrawable(R.drawable.tab_menu_home_image);
        drawable_home.setBounds(0,0,drawable_width,drawable_height);
        tv_home.setCompoundDrawables(null,drawable_home,null,null);

        Drawable drawable_video = getDrawable(R.drawable.tab_menu_video_image);
        drawable_video.setBounds(0,0,drawable_width,drawable_height);
        tv_video.setCompoundDrawables(null,drawable_video,null,null);

        Drawable drawable_me = getDrawable(R.drawable.tab_menu_me_image);
        drawable_me.setBounds(0,0,drawable_width,drawable_height);
        tv_me.setCompoundDrawables(null,drawable_me,null,null);

        fragmentTransaction = getSupportFragmentManager().beginTransaction();

        tv_home.setSelected(true);
        if (fh == null) {
            fh = new fragment_home();//初始化fragment_home
            fragmentTransaction.add(R.id.fragment_frameLayout, fh);
        } else {
            fragmentTransaction.show(fh);
        }

        fragmentTransaction.commit();
    }

    @Override
    public void onClick(View v) {

        fragmentTransaction=getSupportFragmentManager().beginTransaction();//一个FragmentTransaction只能commit一次

        new utils(this).hideFragment(fh,fragmentTransaction);//隐藏一个Fragment
        new utils(this).hideFragment(fv,fragmentTransaction);
        new utils(this).hideFragment(fm,fragmentTransaction);

        switch (v.getId()){
            case R.id.text_home:
                new utils(this).resetSeleceed(tv_home,tv_video,tv_me);
                tv_home.setSelected(true);

                if (fh==null){
                    fh=new fragment_home();
                    fragmentTransaction.add(R.id.fragment_frameLayout,fh);
                }else{
                    fragmentTransaction.show(fh);
                }
                setStatusBarColor(android.R.color.holo_red_light);
                setDarkStatusIcon(false);
                break;


            case R.id.text_video:
                new utils(this).resetSeleceed(tv_home,tv_video,tv_me);
                tv_video.setSelected(true);

                if (fv==null){
                    fv=new fragment_video();
                    fragmentTransaction.add(R.id.fragment_frameLayout,fv);
                }else{
                    fragmentTransaction.show(fv);
                }
                setStatusBarColor(android.R.color.background_light);
                setDarkStatusIcon(true);
                break;


            case R.id.text_me:
                new utils(this).resetSeleceed(tv_home,tv_video,tv_me);
                tv_me.setSelected(true);

                if (fm==null){
                    fm=new fragment_me();
                    fragmentTransaction.add(R.id.fragment_frameLayout,fm);
                }else{
                    fragmentTransaction.show(fm);
                }
                setStatusBarColor(android.R.color.background_light);
                setDarkStatusIcon(true);
                break;


        }
        fragmentTransaction.commit();
    }


}
