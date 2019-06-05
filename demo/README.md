

`高德效果`
![高德效果](https://user-gold-cdn.xitu.io/2019/6/4/16b2213fef61d453?w=354&h=708&f=gif&s=5215473)

- 搜到的一个效果，附上链接,用的behavior，我没下源码看，因为我只是想尝试另外一种方式。具体效果暂不知[`Android 仿高德地图可拉伸的BottomSheet`](https://blog.csdn.net/hnkwei1213/article/details/78507997)


`以下是我用motionlayout实现的效果，没有达到丝滑流畅，优化就看小伙伴你了`


![](https://user-gold-cdn.xitu.io/2019/6/5/16b25fb38a848538?w=540&h=1080&f=gif&s=4475207)
[demo.apk下载体验](https://github.com/wobiancao/sugar/blob/master/demo-debug.apk)

缘由
------
- 使用高德地图的时候看着这种体验很好，随后就想试试怎么达到类似效果
- 最近正在看MotionLayout的东西，正好就尝试尝试

MotionLayout
-----


- [`「译」 MotionLayout 介绍 (Part I - IV) `](https://juejin.im/post/5c21d451518825235a055024)系列教会你如何使用MotionLayout
- 这里不做过多描述，总结一下在xml文件夹下创建`xxscene.xml` 主要用于描述场景动画的关键帧和view状态变化等
- `xxscene.xml`内容包括 主要为3个关键内容：
1. `Transition` 过渡

`constraintSetStart`:启动约束场景

`constraintSetEnd`：结束约束场景

`app:dragDirection="dragUp"` 拽动(拖拉) 

2. `KeyFrameSet`关键帧集合

`KeyAttribute`关键帧

`app:framePosition` 位置，进度

`app:target="@id/xxx` 被描述的view id

3. `ConstraintSet` 约束集合

```
 <Transition
        app:constraintSetEnd="@id/slideup_end"
        app:constraintSetStart="@id/slideup_start"
        app:duration="600"
        app:interpolator="easeIn">
        <OnSwipe
            app:dragDirection="dragUp"
            app:maxAcceleration="600"
            app:touchAnchorSide="top"
            app:touchAnchorId="@id/content"
           />
        <KeyFrameSet>
            <KeyAttribute
                android:alpha="0"
                app:framePosition="45"
                app:target="@id/sugar_title" />

            <KeyAttribute
                android:alpha="1"
                app:framePosition="90"
                app:target="@id/sugar_title" />
        ...
        </KeyFrameSet>
 </Transition>   
 
 <ConstraintSet android:id="@+id/slideup_start">

        <Constraint
        ···
        />
    ...
  </ConstraintSet>  
```

拆解过程
----

- 高德地图是上拉之后是三段式的，如图所示

![1](https://user-gold-cdn.xitu.io/2019/6/5/16b261de0a729d9b?w=320&h=640&f=png&s=201703)![2](https://user-gold-cdn.xitu.io/2019/6/5/16b261e25a2e129c?w=320&h=640&f=png&s=181066)![3](https://user-gold-cdn.xitu.io/2019/6/5/16b261dd3c13141c?w=320&h=640&f=png&s=135715)
- `MotionLayout`就只有一个初始约束和结束约束，没有中间约束，如何实现这种三段式效果？
- 答: 使用progress, `MotionLayout`自带进度
- 有进度，什么时候执行下一步操作，什么时候又执行上一步操作？
- 答: 根据手势，我们可以判断用户下一步是往上拉还是下拉，设定阶段阀值，超过进入下一步，未超过回到之前一步
- 高德地图中，只有手触碰到bottomview的时候手势才有效果，所以还需要判断touch事件是否在view范围内

`拆解完毕`

实现过程
------
- 设置阀值 
```
    /**
     * 初始位置
     */
    public final static float PROGRESS_START = 0f;
    /**
     * 顶部阀值 
     */
    public final static float PROGRESS_TOP = 0.9f;
    /**
     * 低部阀值 
     */
    public final static float PROGRESS_BOTTOM = 0.1f;
    /**
     * 中间位置 
     */
    public final static float PROGRESS_MIDDLE = 0.6f;
    /**
     * 结束位置 
     */
    public final static float PROGRESS_END = 1.0f;
```
- 重写`MotionLayout`的`onTouchEvent`事件 ,使用`hasMiddle`布尔值判断是否有中间状态
```
 @Override
    public boolean onTouchEvent(MotionEvent event) {
        float progress = getProgress();
        View viewGroup = findViewById(R.id.content);
        Rect mRect = new Rect();
        if (!mTouchStared) {
            viewGroup.getHitRect(mRect);
            mTouchStared = mRect.contains((int) event.getX(), (int) event.getY());
        }
        float endY;
        if (hasMiddle) {
            switch (event.getActionMasked()) {
                case MotionEvent.ACTION_CANCEL:
                    mTouchStared = false;
                    break;
                case MotionEvent.ACTION_DOWN:
                    startY = event.getY();
                    break;
                case MotionEvent.ACTION_UP:
                    endY = event.getY();
                    //手势向下
                    if ((endY - startY) > 0) {
                        if (progress >= PROGRESS_TOP) {
                            mTouchStared = false;
                            handleProgress(PROGRESS_END);

                        }
                        if (progress < PROGRESS_TOP && progress >= PROGRESS_MIDDLE) {
                            handleProgress(PROGRESS_MIDDLE);
                        }
                        if (progress < PROGRESS_MIDDLE) {
                            handleProgress(PROGRESS_START);
                        }
                        //手势向上
                    } else {
                        if (progress <= PROGRESS_BOTTOM) {
                            handleProgress(PROGRESS_START);
                        }
                        if (progress > PROGRESS_BOTTOM && progress <= PROGRESS_MIDDLE) {
                            handleProgress(PROGRESS_MIDDLE);
                        }
                        if (progress > PROGRESS_MIDDLE) {
                            mTouchStared = false;
                            handleProgress(PROGRESS_END);
                        }
                    }
                    return mTouchStared;
            }
        } else {
            if (event.getActionMasked() == MotionEvent.ACTION_CANCEL || event.getActionMasked() == MotionEvent.ACTION_UP) {
                mTouchStared = false;
                return super.onTouchEvent(event);
            }
        }
        return mTouchStared && super.onTouchEvent(event);

    }
```
- `bottom_scene.xml` 
 1. 上拉超过顶部阀值`PROGRESS_TOP`之后标题出现在屏幕内，其余时候出现在屏幕外即可；
 2. 初始状态这里把`scaleX`和`scaleY`设为0.9结束设为了1，仅仅是为了过渡好看，你可以不用设置随意修改即可
 3. 背景色过渡，最开始透明，结束为白色背景。中间过渡关键帧95变纯白背景
 
结果和改进
---

- 设置允许中间状态后，之后进入下一步的过程，如图，过于生硬

![](https://user-gold-cdn.xitu.io/2019/6/5/16b26308a26b0c28?w=511&h=1023&f=gif&s=4711098)

- 改进方向
1. 动画应该是匀速的，然而`setProgress(pro);`却是一步直达；
2. 设置时间间隔匀速达到最后的进度即可，源码已详细注释。改进之后见最上面效果图；
```
 private void handleProgress(float progress) {
        //如果需要设置的进度和当前进度相同不做处理
        if (progress == getProgress()){
            return;
        }
        //动画播放时间底值
        long time = 200;
        //进度间隔 >0 说明上拉 < 0说明下滑
        float interval = progress - getProgress();
        long startTime, endTime;
        if (interval > 0) {
            startTime = (long) (getProgress() * time);
            endTime = (long) (progress * time);
        } else {
            endTime = (long) (getProgress() * time);
            startTime = (long) (progress * time);
        }
        if (timeDisposable != null){
            timeDisposable.dispose();
        }
        //startTime 初始时间 endTime - startTime为次数 0为延迟时间 3为间隔 单位TimeUnit.MILLISECONDS 毫秒
        timeDisposable = Observable.intervalRange(startTime, endTime - startTime, 0, 3, TimeUnit.MILLISECONDS)
                .observeOn(Schedulers.io())
                .compose(((BaseActivity) getContext()).getProvider().bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<Long, Long>() {
                    @Override
                    public Long apply(Long aLong) throws Exception {
                        //下滑需要反向
                        if (interval < 0) {
                            long interStart = aLong - startTime;
                            return endTime - interStart;
                        }
                        return aLong;
                    }
                })
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        float pro = (Float.valueOf(aLong) / time);
                        setProgress(pro);
                    }
                });
    }
```

`源码已放入sugar demo中，sugar是我会长期维护的一个库⬇️⬇️⬇️`

### [`🍯 Sugar 简单便捷 快速开发Android项目，集合流行框架封装`](https://github.com/wobiancao/sugar)

About me
--------
* **Email**: <a420245103@gmail.com>  
* **掘金**: <https://juejin.im/user/568be89760b24d71fed19d2b>
* **简书**: <https://www.jianshu.com/u/114bbbfb977f>
* **apkbus**: <http://www.apkbus.com/?496060>
* **github**: <https://github.com/wobiancao>


License
--------
```
Copyright 2019, wobiancao       
  
   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at 
 
       http://www.apache.org/licenses/LICENSE-2.0 

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
   
```