package com.sugar.demo.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.sugar.demo.R;
import com.sugar.sugarlibrary.base.BaseActivity;


public class MainActivity extends BaseActivity {

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }



    @Override
    public void init(Bundle savedInstanceState) {

    }

    @Override
    public void loadData() {

    }

    public void Gank(View view) {
        startActivity(new Intent(MainActivity.this, GankActivity.class));
    }
}
