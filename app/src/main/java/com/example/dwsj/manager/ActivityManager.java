package com.example.dwsj.manager;
/*
Created by xiaoyu on 2020/11/25

Describe: 负责管理activity

*/


import android.app.Activity;

import java.util.Stack;

public class ActivityManager {
    private static ActivityManager manager;
    public ActivityManager(){

    }

    private Stack<Activity> activityStack = new Stack();


    public static ActivityManager getActivityManager(){
        synchronized (ActivityManager.class){
            if (manager == null){
                manager = new ActivityManager();
            }
        }
        return manager;
    }
    //销毁当前的activity
    public void removeCurrentActivity(){
        Activity activity = activityStack.lastElement();
        activity.finish();
        activityStack.remove(activity);
    }

    //销毁指定的activity
    public void removeActivity(Activity CurrentActivity){
        for (int i = activityStack.size() -1;i >= 0; i--){
            Activity activity = activityStack.get(i);
            if (activity.getClass().equals(CurrentActivity.getClass())){
                activity.finish();
                activityStack.remove(i);//将当前的activity移出栈
            }
        }
    }
    //移除所有
    public void removeAllActivity(){
        for (int i = activityStack.size() -1;i>= 0;i--){
            Activity activity = activityStack.get(i);
            activity.finish();
            activityStack.remove(i);
        }
    }
}
