/*
 * Copyright 2019 wobiancao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.sugar.demo.ui;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.airbnb.lottie.LottieAnimationView;
import com.sugar.demo.R;
import com.sugar.sugarlibrary.base.BaseActivity;

/**
 * @author wobiancao
 * @date 2019-05-22
 * desc :
 */
public class StartActivity extends BaseActivity {
    LottieAnimationView mAnimationView;

    @Override
    protected int getContentView() {
        return R.layout.activity_start;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        mAnimationView = findViewById(R.id.animation_view);
        mAnimationView.setScale(2.0f);
        mAnimationView.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                if (!isDestroyed()){
                    startActivity(new Intent(StartActivity.this, MainActivity.class));
                    finish();
                }
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });

    }

    @Override
    public void loadData() {

    }
}
