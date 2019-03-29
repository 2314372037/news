package com.example.zh231.neteasenews;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import static android.content.Context.MODE_PRIVATE;

public class fragment_me extends Fragment implements View.OnClickListener{

    private EditText username_edt=null;
    private EditText password_edt=null;
    private TextView error_text=null;
    private Button login_btn1=null;
    private TextView forget_psd=null;
    private TextView register_user=null;
    private ImageButton login_qq=null;
    private ImageButton login_weibo=null;
    private Button logout_btn=null;
    private boolean islogin=false;

    FrameLayout me_content;
    FrameLayout me_login;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Bundle bundle = getArguments();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view;

        view = inflater.inflate(R.layout.fragment_fragment_me, container, false);
        logout_btn=view.findViewById(R.id.logout_btn);
        logout_btn.setOnClickListener(this);

        me_content=view.findViewById(R.id.me_content);
        me_login = view.findViewById(R.id.me_login);

        //判断是否登录
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("loginInfo", MODE_PRIVATE);
        if (sharedPreferences==null){
            return null;
        }

        islogin = sharedPreferences.getBoolean("islogin", false);

        if (!islogin) {
            View ffl=LayoutInflater.from(getContext()).inflate(R.layout.fragment_fragment_login,null);

            username_edt=ffl.findViewById(R.id.username_edt);
            password_edt=ffl.findViewById(R.id.password_edt);
            error_text=ffl.findViewById(R.id.error_text);
            login_btn1=ffl.findViewById(R.id.login_btn1);
            forget_psd=ffl.findViewById(R.id.forget_psd);
            register_user=ffl.findViewById(R.id.register_user);
            login_qq=ffl.findViewById(R.id.login_qq);
            login_weibo=ffl.findViewById(R.id.login_weibo);

            login_btn1.setOnClickListener(this);
            forget_psd.setOnClickListener(this);
            register_user.setOnClickListener(this);
            login_qq.setOnClickListener(this);
            login_weibo.setOnClickListener(this);

            me_login.addView(ffl);
            me_content.setVisibility(View.INVISIBLE);
            me_login.setVisibility(View.VISIBLE);
        }

        //setStatusBarColor();
        return view;
    }

    private void setStatusBarColor(){
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
            getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getActivity().getWindow().setStatusBarColor(Color.WHITE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.logout_btn:
                SharedPreferences.Editor editor=getContext().getSharedPreferences("loginInfo", MODE_PRIVATE).edit();
                editor.putBoolean("islogin",false);
                editor.apply();
                Toast.makeText(getContext(),"已退出",Toast.LENGTH_SHORT).show();
                me_content.setVisibility(View.VISIBLE);
                me_login.removeAllViews();
//                getFragmentManager().beginTransaction().hide(this).commit();
//                getFragmentManager().beginTransaction().remove(this).commit();
//                getFragmentManager().beginTransaction().replace(R.id.fragment_frameLayout,fl).commit();

                break;
            case R.id.login_btn1://登录
                Log.d("调试","点击登录");
                if (username_edt.getText().toString().equals("")){
                    error_text.setText("请输入账号");
                    error_text.setVisibility(View.VISIBLE);
                    return;
                }
                if (password_edt.getText().toString().equals("")){
                    error_text.setText("请输入密码");
                    error_text.setVisibility(View.VISIBLE);
                    return;
                }
                error_text.setVisibility(View.INVISIBLE);
                //一系列登录操作...
                SharedPreferences.Editor editor1=getContext().getSharedPreferences("loginInfo", Context.MODE_PRIVATE).edit();
                if (editor1!=null){
                    editor1.putBoolean("islogin",true);
                    //editor.putString("token","");
                    //editor.putString("cookie","");
                    editor1.apply();
                    me_content.setVisibility(View.INVISIBLE);
                    me_login.setVisibility(View.VISIBLE);
//                    getFragmentManager().beginTransaction().hide(this).commit();
//                    getFragmentManager().beginTransaction().remove(this).commit();
//                    getFragmentManager().beginTransaction().replace(R.id.fragment_frameLayout,fm).commit();
                }
                break;
            case R.id.forget_psd://忘记密码

                break;
            case R.id.register_user://注册用户

                break;
            case R.id.login_qq://qq登录

                break;
            case R.id.login_weibo://微博登录

                break;
        }
    }
}
