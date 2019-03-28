package com.example.zh231.neteasenews;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

public class fragment_me extends Fragment implements View.OnClickListener{
    private String ARG_PARAM1 = "param1";
    private String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private Button logout_btn=null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_fragment_me, container, false);

        logout_btn=view.findViewById(R.id.logout_btn);

        logout_btn.setOnClickListener(this);
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
                SharedPreferences.Editor editor=getContext().getSharedPreferences("loginInfo", Context.MODE_PRIVATE).edit();
                editor.putBoolean("islogin",false);
                editor.apply();
                Toast.makeText(getContext(),"已退出",Toast.LENGTH_SHORT).show();
                fragment_login fl=new fragment_login();
                getFragmentManager().beginTransaction().replace(R.id.fragment_frameLayout,fl).commit();

                break;
        }
    }
}
