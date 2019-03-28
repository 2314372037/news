package com.example.zh231.neteasenews;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class fragment_login extends Fragment implements View.OnClickListener{

    private EditText username_edt=null;
    private EditText password_edt=null;
    private TextView error_text=null;
    private Button login_btn1=null;
    private TextView forget_psd=null;
    private TextView register_user=null;
    private ImageButton login_qq=null;
    private ImageButton login_weibo=null;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragment_login,container,false);
        username_edt=view.findViewById(R.id.username_edt);
        password_edt=view.findViewById(R.id.password_edt);
        error_text=view.findViewById(R.id.error_text);
        login_btn1=view.findViewById(R.id.login_btn1);
        forget_psd=view.findViewById(R.id.forget_psd);
        register_user=view.findViewById(R.id.register_user);
        login_qq=view.findViewById(R.id.login_qq);
        login_weibo=view.findViewById(R.id.login_weibo);

        login_btn1.setOnClickListener(this);
        forget_psd.setOnClickListener(this);
        register_user.setOnClickListener(this);
        login_qq.setOnClickListener(this);
        login_weibo.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
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
                SharedPreferences.Editor editor=getContext().getSharedPreferences("loginInfo", Context.MODE_PRIVATE).edit();
                if (editor!=null){
                    editor.putBoolean("islogin",true);
                    //editor.putString("token","");
                    //editor.putString("cookie","");
                    editor.apply();
                    fragment_me fm=new fragment_me();
                    getFragmentManager().beginTransaction().replace(R.id.fragment_frameLayout,fm).commit();
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
