package com.example.dwsj;
/*
Created by xiaoyu on 2020/11/19

Describe:

*/


import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.ViewModelStoreOwner;

public class MainApplication extends Application implements ViewModelStoreOwner {

    public Context applicationContext;
    public static Handler mHandler;
    private ViewModelStore viewModelStore;

    @Override
    public void onCreate() {
        super.onCreate();
        applicationContext = getApplicationContext();
        mHandler = new Handler(Looper.getMainLooper());
        viewModelStore = new ViewModelStore();

    }

    @NonNull
    @Override
    public ViewModelStore getViewModelStore() {
        return viewModelStore;
    }
}
