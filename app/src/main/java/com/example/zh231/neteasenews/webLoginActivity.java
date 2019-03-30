package com.example.zh231.neteasenews;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.Toast;

public class webLoginActivity extends AppCompatActivity {

    private WebView webview=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_login);
        webview=findViewById(R.id.webview);
        webview.setNetworkAvailable(true);
        webview.setWebViewClient(new webviewClient());
        Intent intent=getIntent();
        int from = intent.getIntExtra("from",0);//0为未知来源，1忘记密码，2注册，3qq登录，4微博登录
        switch (from){
            case 1:
                forgetPsd();
                break;
            case 2:
                userRegister();
                break;
            case 3:
                loginForQQ();
                break;
            case 4:
                loginForWeibo();
                break;
            default:
                Toast.makeText(this,"未知类型",Toast.LENGTH_SHORT).show();
                break;
        }
    }


    private void forgetPsd(){
        String url="https://reg.163.com/naq/findPassword#/verifyAccount";
        webview.loadUrl(url);
    }

    private void userRegister(){
        String url="https://zc.reg.163.com/m/regInitialized?pd=3g_163&pkid=urTbkXL&pkht=3g.163.com&curl=" +
                "https%3A%2F%2F3g.163.com%2Ftouch%2F%3Fclickfrom%3Durs_reg";
        webview.loadUrl(url);
    }

    private void loginForQQ(){

    }

    private void loginForWeibo(){//微博登录
        /*说明:访问url1获取请求头"Location"，访问获取到的Location获取请求头SINA-LB和SINA-TS，保存这两值(Location为微博登录api网页地址)，（未知:请求Location需要带一个Cookie，内容为SCF=xxxxx;SUB=xxxx）
          输入账号信息后点登录,然后请求https://api.weibo.com/oauth2/authorize，请求头Cookie:为之前的SCF=xxxxx;SUB=xxxx，保存响应头Set-Cookie,获取请求头Location，访问并获取请求头Set-Cookie保存
          (未知:访问时注意带_ntes_nnid)，后面的请求Cookie带_ntes_nnid=请求
         */
        String url1="https://reg.163.com/outerLogin/oauth2/connect.do?target=3&url=https://3g.163.com/touch/all?ver=c&url2=" +
                "https://3g.163.com/touch/all?ver=c&domains=163.com&display=mobile&product=3g_163";
        webview.loadUrl(url1);

    }

}
