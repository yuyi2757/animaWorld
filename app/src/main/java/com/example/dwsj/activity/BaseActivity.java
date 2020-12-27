package com.example.dwsj.activity;
/*
Created by xiaoyu on 2020/11/20

Describe: 所有activity的基类，为子类提供了布局加载，viewmodel加载和事件绑定操作,后续根据需求可适当增加内容


*/


import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.util.SparseArray;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.dwsj.MainApplication;
import com.example.dwsj.config.DataBindingConfig;
import com.example.dwsj.manager.ActivityManager;

public abstract class BaseActivity<B extends ViewDataBinding> extends AppCompatActivity {
    private B mBinding;
    private ViewModelProvider provider;

    protected abstract void initViewModel();
    protected abstract void PrepareLayoutResource(B binding);
    protected abstract DataBindingConfig getDataBindingConfig();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViewModel();
        DataBindingConfig dataBindingConfig = getDataBindingConfig();

        B binding = DataBindingUtil.setContentView(this, dataBindingConfig.getLayout());
        binding.setLifecycleOwner(this);
        //绑定viewmodel
        binding.setVariable(dataBindingConfig.getVmVariableId(), dataBindingConfig.getStateViewModel());
        SparseArray bindingParams = dataBindingConfig.getBindingParams();
        for (int i = 0, len = bindingParams.size(); i < len; i++) {
            binding.setVariable(bindingParams.keyAt(i), bindingParams.valueAt(i));
        }
        mBinding = binding;
        PrepareLayoutResource(binding);
    }


    public <T extends ViewModel> T getActivityViewModel(Class<T> tClass) {
        if (provider == null) {
            provider = new ViewModelProvider(this);

        }
        T t = provider.get(tClass);
        return t;
    }

  private   B binding;

    public B getViewDataBinding() {
        if (mBinding.getRoot() != null) {
            binding = (B) DataBindingUtil.getBinding(mBinding.getRoot());
        }
        return binding;
    }

    //获取整个application的viewmodel,可全局订阅接收数据
    protected <T extends ViewModel> T getApplicationScopeViewModel(Class<T> tClass) {
        ViewModelProvider applicationViewModel = new ViewModelProvider
                ((MainApplication) this.getApplicationContext(), getFactory(this));

        T t = applicationViewModel.get(tClass);
        return t;
    }

    private ViewModelProvider.Factory getFactory(Activity activity) {
        Application application = checkApplication(activity);
        return ViewModelProvider.AndroidViewModelFactory.getInstance(application);
    }

    private Application checkApplication(Activity activity) {
        Application application = activity.getApplication();
        if (application == null) {
            throw new IllegalStateException("Your activity/fragment is not yet attached to "
                    + "Application. You can't request ViewModel before onCreate call.");
        }
        return application;
    }
 //启动一个activity
    public void intoActivity(Class<Activity> tager) {
        Intent intent = new Intent();
        intent.setClass(this,tager);
        startActivity(intent);
    }
    //使用bundle携带数据进入新的activity
    public void intoActivity(Class<Activity> activity,Bundle bundle){
        Intent intent = new Intent(this,activity);
        if (bundle != null && bundle.size() != 0){
            intent.putExtra("data",bundle);

        }
        startActivity(intent);
    }

    //销毁当前的activity
    public void removeCurrentActivity(){
        ActivityManager.getActivityManager().removeCurrentActivity();
    }
}
