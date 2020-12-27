package com.example.dwsj.utils;
/*
Created by xiaoyu on 2020/11/23

Describe: dip 与px之间的转换工具

*/


import android.content.Context;

public class DensityUtil {
    //把dp值转换为像素
    public static int dip2px(Context context, float dpValue) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * density + 0.5f);
    }

    //把像素转换为dp值
    public static int px2dip(Context context, float pxValue) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / density + 0.5f);
    }
}
