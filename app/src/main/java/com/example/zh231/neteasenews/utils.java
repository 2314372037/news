package com.example.zh231.neteasenews;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.widget.TextView;

public class utils {

    public utils(){

    }


    /**
     * 重置选择
     * @param textView
     */
    public void resetSeleceed(TextView...textView){
        for (int i=0;i<textView.length;i++){
            if (textView[i].isSelected()){
                textView[i].setSelected(false);
            }
        }
    }

    /**
     * 隐藏fragment
     * @param fragmentTransaction
     */
    public void hideFragment(Fragment fragment,FragmentTransaction fragmentTransaction){
        if (fragment!=null&&fragmentTransaction!=null){
            fragmentTransaction.hide(fragment);
        }
    }
}
