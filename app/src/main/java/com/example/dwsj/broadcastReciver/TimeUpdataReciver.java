package com.example.dwsj.broadcastReciver;
/*
Created by xiaoyu on 2020/11/24

Describe:接收系统时间变化的广播

*/


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class TimeUpdataReciver extends BroadcastReceiver {
    public TimeUpdataReciver(Context context) {

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("TimeUpdataReciver", "onReceive: 时间改变了");
    }
}
