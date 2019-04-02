package com.example.zh231.neteasenews;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;

import java.lang.ref.WeakReference;

public abstract class baseHandler extends Handler {

    public WeakReference<Fragment> fragmentWeakReference;
    public WeakReference<Activity> activityWeakReference;


    private baseHandler() {
        super();
    }

    public baseHandler(Fragment fragment){
        this.fragmentWeakReference=new WeakReference<>(fragment);
    }

    public baseHandler(Activity activity){
        this.activityWeakReference=new WeakReference<>(activity);
    }

    @Override
    public void handleMessage(Message msg) {
        if (fragmentWeakReference==null||fragmentWeakReference.get()==null){
            handleMessage(msg,utils.activity_gone);
        }else if (activityWeakReference==null||activityWeakReference.get()==null){
            handleMessage(msg,utils.activity_gone);
        }else{
            handleMessage(msg,msg.what);
        }

    }

    public abstract void handleMessage(Message msg,int what);

}
