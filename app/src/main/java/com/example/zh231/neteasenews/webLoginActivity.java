package com.example.zh231.neteasenews;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class webLoginActivity extends AppCompatActivity {

    private WebView webview=null;
    CookieManager cookieManager;
    private String cookie;
    private String nesUrl="https://3g.163.com/touch/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_login);

        webview=findViewById(R.id.webview);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                Log.d("调试","重定向:"+request.getUrl());
                if (request.getUrl().toString().contains("https://3g.163.com")){
                    return true;
                }
                return super.shouldOverrideUrlLoading(view, request);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                Log.d("调试","加载开始:"+url);
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                Log.d("调试","加载完成:"+url);
                cookieManager=CookieManager.getInstance();
                cookie=cookieManager.getCookie(nesUrl);
                if (cookie!=null){
                    if (cookie.contains("_antanalysis_s_id")&&
                            cookie.contains("NTES_OSESS")&&
                            cookie.contains("S_OINFO")&&
                            cookie.contains("P_OINFO"))
                    {
                        setResult(2);
                        //Toast.makeText(getApplicationContext(),"已获取到相关cookie",Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
                super.onPageFinished(view, url);
            }
        });
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

    private void setCookie(String cookies){
        if (cookieManager==null){
            cookieManager=CookieManager.getInstance();
        }
        String tempArr[] = cookies.split(";");
        for (String temp : tempArr){
            cookieManager.setCookie(nesUrl,temp);
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
        webview.loadUrl(nesUrl);
    }

    private void loginForWeibo(){//微博登录
        String url1="https://reg.163.com/outerLogin/oauth2/connect.do?target=3&url=https://3g.163.com/touch/all?ver=c&url2=" +
                "https://3g.163.com/touch/all?ver=c&domains=163.com&display=mobile&product=3g_163";
        webview.loadUrl(url1);

    }

}
