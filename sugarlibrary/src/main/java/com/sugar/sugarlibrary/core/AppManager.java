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
package com.sugar.sugarlibrary.core;

import android.app.Activity;
import android.content.Intent;

import java.util.Stack;

/**
 * @author wobiancao
 * @date 2019/5/15
 * desc :
 */
public class AppManager {
    /**
     * 栈：也就是stack
     */
    private static Stack<Activity> activityStack;
    private volatile static AppManager instance;

    private AppManager() {

    }

    /**
     * 单一实例
     */
    public static AppManager getAppManager() {
        if (instance == null) {
            synchronized (AppManager.class) {
                if (instance == null) {
                    instance = new AppManager();
                }
            }
        }
        return instance;
    }

    /**
     * 添加Activity到堆栈
     */
    public void addActivity(Activity activity) {
        if(activityStack == null){
            activityStack = new Stack<>();
        }
        activityStack.add(activity);
    }

    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    public Activity currentActivity() {
        if(activityStack==null || activityStack.size()==0){
            return null;
        }
        Activity activity = activityStack.lastElement();
        return activity;
    }

    public Activity getTopActivity() {
        if (activityStack == null) {
            return null;
        }
        return activityStack.size() > 0 ? activityStack.get(activityStack.size() - 1) : null;
    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    public void finishActivity() {
        if(activityStack!=null && activityStack.size()>0){
            Activity activity = activityStack.lastElement();
            finishActivity(activity);
        }
    }

    /**
     * 结束指定的Activity
     */
    public void finishActivity(Activity activity) {
        if(activityStack==null){
            return;
        }
        if (activity != null && !activity.isFinishing()) {
            activity.finish();
        }
    }

    /**
     * 移除指定的Activity
     * @param activity
     */
    public void removeActivity(Activity activity) {
        if(activityStack==null){
            return;
        }
        if (activity != null) {
            for (Activity act : activityStack) {
                if (act.getClass().equals(activity)) {
                    activityStack.remove(activity);
                    return;
                }
            }

        }
    }

    /**
     * 结束指定类名的Activity
     */
    public void finishActivity(Class<?> cls) {
        if(activityStack==null){
            return;
        }
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
            }
        }
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        if(activityStack==null){
            return;
        }
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i)) {
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
    }

    /**
     * 退出应用程序
     */
    public void appExit(Boolean isBackground) {
        try {
            //finish所有activity
            finishAllActivity();
            //杀死进程
            android.os.Process.killProcess(android.os.Process.myPid());
        } catch (Exception ignored) {
        } finally {
            if (!isBackground) {
                System.exit(0);
            }
        }
    }
    /**
     * 是否有某个Activity
     */
    public boolean hasActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity != null && activity.getClass().equals(cls)){
                return true;
            }
        }
        return false;
    }


    /**
     * 让在栈顶的 {@link Activity} ,打开指定的 {@link Activity}
     *
     * @param intent
     */
    public void startActivity(Intent intent) {
        if (getTopActivity() != null) {
            getTopActivity().startActivity(intent);
        }

    }

    /**
     * 让在栈顶的 {@link Activity} ,打开指定的 {@link Activity}
     *
     * @param activityClass
     */
    public void startActivity(Class activityClass) {
        if (getTopActivity() != null) {
            startActivity(new Intent(getTopActivity(), activityClass));
        }

    }

    /**
     * 除了cls都关闭
     **/
    public void finishActivityExcept(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity != null && !activity.getClass().equals(cls)){
                finishActivity(activity);
            }
        }
    }

    private String getLastName(String cls){
        String[] temp = cls.split("\\.");
        if (temp.length > 0){
            return temp[temp.length - 1];
        }else {
            return "";
        }

    }

}
