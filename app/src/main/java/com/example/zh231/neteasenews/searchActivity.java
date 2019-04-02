package com.example.zh231.neteasenews;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class searchActivity extends AppCompatActivity implements View.OnClickListener {

    Button search_startBtn=null;
    Button search_backBtn=null;
    ListView rec_list=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        initView();

        search_startBtn.setOnClickListener(this);
        search_backBtn.setOnClickListener(this);

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

    @Override
    protected void onStart() {
        super.onStart();
        setDarkStatusIcon(true);
    }

    @Override
    protected void onDestroy() {
        setDarkStatusIcon(false);
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.search_startBtn:
                Toast.makeText(searchActivity.this,"开发中",Toast.LENGTH_LONG).show();
                break;
            case R.id.search_backBtn:
                Toast.makeText(searchActivity.this,"销毁当前activity",Toast.LENGTH_SHORT).show();
                finish();
                break;
        }
    }

    private void initView() {
        search_startBtn=findViewById(R.id.search_startBtn);
        search_backBtn=findViewById(R.id.search_backBtn);
        rec_list=findViewById(R.id.rec_list);

        String list[] = {"测试","测试","测试","测试","测试","测试","测试","测试"};

        ArrayAdapter<String> adapter=new ArrayAdapter<String>(searchActivity.this,android.R.layout.simple_list_item_1,list);
        rec_list.setAdapter(adapter);
    }
}
