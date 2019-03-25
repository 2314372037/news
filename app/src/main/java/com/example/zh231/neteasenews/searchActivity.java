package com.example.zh231.neteasenews;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class searchActivity extends AppCompatActivity implements View.OnClickListener {

    Button search_startBtn=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        initView();

        search_startBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.search_startBtn:
                Toast.makeText(searchActivity.this,"开发中",Toast.LENGTH_LONG).show();
                break;
        }
    }

    private void initView() {
        search_startBtn=findViewById(R.id.search_startBtn);

    }
}
