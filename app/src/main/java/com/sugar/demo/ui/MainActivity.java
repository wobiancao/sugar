package com.sugar.demo.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.sugar.demo.R;
import com.sugar.demo.router.RouterPageContant;
import com.sugar.sugarlibrary.base.BaseActivity;

@Route(path = RouterPageContant.MAIN_PAGE)
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

    public void Wan(View view) {
        startActivity(new Intent(MainActivity.this, WanActivity.class));
    }
}
