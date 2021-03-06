package com.example.zh231.neteasenews;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.ValueCallback;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import org.json.JSONObject;
import java.util.Random;

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
    private LinearLayout logout_btn=null;
    private LinearLayout email_btn=null;
    private LinearLayout comment_btn=null;
    private ImageView userImg=null;
    private TextView username_tv=null;
    private boolean islogin=false;
    CookieManager cookieManager=null;

    LinearLayout me_content;
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
        email_btn=view.findViewById(R.id.email_btn);
        comment_btn=view.findViewById(R.id.comment_btn);
        logout_btn.setOnClickListener(this);
        comment_btn.setOnClickListener(this);
        email_btn.setOnClickListener(this);

        me_content=view.findViewById(R.id.me_content);
        me_login = view.findViewById(R.id.me_login);
        userImg=view.findViewById(R.id.userImg);
        username_tv=view.findViewById(R.id.username_tv);

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
        username_edt.setHint("邮箱");
        password_edt.setHint("密码");
        me_login.addView(ffl);

        //判断是否登录
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("loginInfo", MODE_PRIVATE);
        if (sharedPreferences==null){
            return null;
        }

        islogin = sharedPreferences.getBoolean("islogin", false);

        if (!islogin) {
            me_content.setVisibility(View.INVISIBLE);
            me_login.setVisibility(View.VISIBLE);
        }else{
            me_content.setVisibility(View.VISIBLE);
            me_login.setVisibility(View.INVISIBLE);
            getUserInfo();
        }

        return view;
    }


    private void cleanCookie(){//当获取用户信息或判断cookie无效时，应该调用并重新登录
        if (cookieManager==null){
            cookieManager=CookieManager.getInstance();
        }
        cookieManager.removeAllCookies(new ValueCallback<Boolean>() {
            @Override
            public void onReceiveValue(Boolean value) {
                Log.d("调试","清除全部cookie"+value);
            }
        });
    }

    private void exitAndClean(){//退出登录状态，并清除cookie
        cleanCookie();
        SharedPreferences.Editor editor=getContext().getSharedPreferences("loginInfo", MODE_PRIVATE).edit();
        editor.putBoolean("islogin",false);
        editor.apply();
        me_content.setVisibility(View.INVISIBLE);
        me_login.setVisibility(View.VISIBLE);
    }

    //获取用户信息并显示看
    private void getUserInfo(){
        //获取用户信息需要生成8位随机数,例https://3g.163.com/touch/nc/api/jsonp/oauth/common/info.do?__rnd=78089314&callback=oauth_info，请求时带上cookies

        if (cookieManager==null){
            cookieManager= CookieManager.getInstance();
        }
        final String cookie=cookieManager.getCookie("https://3g.163.com/touch/");
                if (cookie.contains("_antanalysis_s_id")&&
                cookie.contains("NTES_OSESS")&&
                cookie.contains("S_OINFO")&&
                cookie.contains("P_OINFO"))
        {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    String rand="";
                    try{
                        for (int i=0;i<8;i++){
                            Random rnd=new Random();
                            rand+=String.valueOf(rnd.nextInt(10));
                            Thread.sleep(1);
                        }
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                    final String infoJson=utils.sendGet("https://3g.163.com/touch/nc/api/jsonp/oauth/common/info.do?__rnd="+rand+"&callback=oauth_info",cookie);
                    //oauth_info({"data":"不合法","code":403})
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            String json=infoJson;
                            if (json.contains("oauth_info(")){
                                json=json.replace("oauth_info(","");
                                json=json.substring(0,json.length()-1);
                            }
                            try{
                                JSONObject jsonObject=new JSONObject(json);
                                if (jsonObject.getInt("code")!=200){
                                    Toast.makeText(getContext(),"获取用户信息失败:"+json,Toast.LENGTH_LONG).show();
                                    exitAndClean();
                                }else{
                                    JSONObject data=jsonObject.getJSONObject("data");
                                    Glide.with(getContext()).load(data.getString("photoUrl")).into(userImg);
                                    username_tv.setText(data.getString("nickName"));
                                }
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }).start();
        }


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {//ui线程
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("调试","请求码:"+requestCode);
        Log.d("调试","返回码:"+resultCode);

        if (resultCode==2){//返回码为2就是登录成功
                SharedPreferences.Editor editor1=getContext().getSharedPreferences("loginInfo", Context.MODE_PRIVATE).edit();
                if (editor1!=null){
                    editor1.putBoolean("islogin",true);
                    editor1.apply();
                    me_content.setVisibility(View.VISIBLE);
                    me_login.setVisibility(View.INVISIBLE);
                    Toast.makeText(getContext(),"登录成功",Toast.LENGTH_SHORT).show();
                    //获取用户信息并显示
                    getUserInfo();
                }
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent=new Intent();
        intent.setClass(getContext(),webLoginActivity.class);
        switch (v.getId()){
            case R.id.logout_btn:
                exitAndClean();
                break;
            case R.id.comment_btn:

                break;
            case R.id.email_btn:

                break;
            case R.id.login_btn1://网易账号登录
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
                Toast.makeText(getContext(),"暂不可用",Toast.LENGTH_SHORT).show();
//                //一系列登录操作...
//                SharedPreferences.Editor editor1=getContext().getSharedPreferences("loginInfo", Context.MODE_PRIVATE).edit();
//                if (editor1!=null){
//                    editor1.putBoolean("islogin",true);
//                    //editor.putString("token","");
//                    //editor.putString("cookie","");
//                    editor1.apply();
//                    me_content.setVisibility(View.VISIBLE);
//                    me_login.setVisibility(View.INVISIBLE);
//                    Toast.makeText(getContext(),"登录成功",Toast.LENGTH_SHORT).show();
//                }
                break;
            case R.id.forget_psd://忘记密码
                Toast.makeText(getContext(),"忘记密码",Toast.LENGTH_SHORT).show();
                intent.putExtra("from",1);
                startActivity(intent);
                break;
            case R.id.register_user://注册用户
                Toast.makeText(getContext(),"注册用户",Toast.LENGTH_SHORT).show();
                intent.putExtra("from",2);
                startActivity(intent);
                break;
            case R.id.login_qq://qq登录
                Toast.makeText(getContext(),"QQ登录",Toast.LENGTH_SHORT).show();
                intent.putExtra("from",3);
                startActivity(intent);
                break;
            case R.id.login_weibo://微博登录
                Toast.makeText(getContext(),"微博登录",Toast.LENGTH_SHORT).show();
                intent.putExtra("from",4);
                startActivityForResult(intent,1);//返回码为2就是登录成功
                break;
        }
    }

}
