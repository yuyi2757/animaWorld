package com.example.dwsj.utils;
/*
Created by xiaoyu on 2020/11/24

Describe:时间计算的工具类

*/


import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.util.Log;

import com.example.dwsj.fragment.anima.AnimaFragment;

import java.security.PublicKey;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

public class TimeUtil {
    //时间转换为毫秒
    private static long getTimeMillis(String strTime) {
        long returnMillis = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");//yyyy/MM/dd HH:mm格式暂时不需要
        Date d = null;
        try {
            d = sdf.parse(strTime);
            returnMillis = d.getTime();
        } catch (ParseException e) {

        }
        return returnMillis;
    }

    //判断当前是否已经开始抢购了
    public static boolean isMiss(String currentTime) {
        long longStart = getTimeMillis(getSystemTime()); //获取当前系统时间毫秒数
        long longEnd = getTimeMillis(currentTime);  //获取开始抢购的时间毫秒数
        if (longStart > longEnd) {
            return true;//已经开始抢了
        }
        return false;//还没开始抢
    }

    /*判断当前抢购状态，未开始，进行中，结束
     * @params: startTime 当前宠物设定的开始抢购时间
     *          endTime  当前宠物设定的结束抢购时间
     * */
    public static AnimaFragment.ScareStaue currentScareStaue(String startTime, String endTime) {
        long currentMillis = getTimeMillis(getSystemTime()) / 1000;//当前系统时间毫秒
        long start = getTimeMillis(startTime) / 1000;
        long end = getTimeMillis(endTime) / 1000;
        if (currentMillis <= start) {
            // Log.d("time", "未开始 ");
            return AnimaFragment.ScareStaue.UNSTART;
        } else if (currentMillis > start && currentMillis <= end) {
            //Log.d("time", "开抢ing ");
            return AnimaFragment.ScareStaue.ING;//火爆抢购中
        } else {
            // Log.d("time", "结束stop ");
            return AnimaFragment.ScareStaue.STOP;
        }

    }

    /*
    根据设定的开抢时间倒计时
    startTime  系统当前时间
    endTime   抢购时间点
    return: 距离开始抢购时间倒计时
     */
    public static String getTimeExpend(String startTime, String endTime) {
        long longStart = getTimeMillis(startTime); //获取开始时间毫秒数
        long longEnd = getTimeMillis(endTime);  //获取结束时间毫秒数
        long longExpend = longEnd - longStart;  //获取时间差
        long second = longExpend / 1000;//转换成秒再计算
        long hours = second / (60 * 60);//小时
        long min = (second - hours * (60 * 60)) / 60;//分钟
        long sec = second - (hours * 60 * 60) - (min * 60);
        return hours + ":" + min + ":" + sec;
    }

    private static SimpleDateFormat format;

    //获取当前系统时间
    public static String getSystemTime() {
        if (format == null) {
            format = new SimpleDateFormat("HH:mm:ss");
        }
        String currentTime = format.format(new Date());
        return currentTime;

    }

    //初始化蓝牙
    private void initBluetooth() {
        BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
        Set<BluetoothDevice> bondedDevices = defaultAdapter.getBondedDevices();//已配对过的设备
        if (bondedDevices.size() != 0) {
            for (BluetoothDevice device : bondedDevices) {
                Log.d("BlueTooth", "收到倒设备: " + device.getName() + " address " + device.getAddress());
            }
        }
    }
}
