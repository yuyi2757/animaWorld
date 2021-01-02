package com.example.dwsj.fragment;
/*
Created by xiaoyu on 2020/11/19
Describe: 所有fragment的基类，负责初始化和绑定自定义事件，viewmodel的初始化
*/


import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.dwsj.MainApplication;
import com.example.dwsj.config.DataBindingConfig;

public abstract class BaseFragment<B extends ViewDataBinding> extends Fragment implements View.OnTouchListener {
    public AppCompatActivity mContext;
    private ViewModelProvider provider;
    /*
      function :initViewModel()
      初始化各子类的viewmodel,也可以同时初始化业务所需的其他shardViewModel作为共享数据的viewmodel。
     */

    protected abstract void initViewModel();

    /*
     function: getDataBindingConfig()
     每个Fragment或者Activity初始化时，根据自身业务需要进行
     绑定事件以及该界面的布局文件等等写到该配置文件中进行初始化绑定
    */
    protected abstract DataBindingConfig getDataBindingConfig();


    /*
     function:  PrepareLayoutResource(ViewDataBinding binding)
     每个fragment加载完成后生成对应的视图对象，可以把ViewDataBinding理解
     为常见的那种View view = View.inflate(R.layout.xxx,...);这里的参数binding就是view,
     利用binding对象可以为该布局文件下的所有控件赋值，
     比如AnimaFragment加载的布局文件中：binding.tvMor.setText("深夜了");
     */
    protected abstract void PrepareLayoutResource(B binding);


    /*
     * function:getDataFromeNet()
     *
     * 联网请求数据,当前界面初始化后，有需要联网请求的地方，
     * 可以根据自己业务需求在自己的viewmodel中请求服务端的数据并在当前界面
     * 中订阅LiveData即可收到网络数据并更新自己的界面
     * */
    protected abstract void getDataFromeNet();


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViewModel();
    }


    @Override
    public void onStart() {
        super.onStart();
        Log.d("basefragment", "onStart: ");
    }

    //关联activity时候
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = (AppCompatActivity) context;
    }

    protected B mBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        DataBindingConfig dataBindingConfig = getDataBindingConfig();
        B binding = DataBindingUtil.inflate(inflater, dataBindingConfig.getLayout(), container, false);
        binding.setLifecycleOwner(this);
        binding.setVariable(dataBindingConfig.getVmVariableId(), dataBindingConfig.getStateViewModel());

        SparseArray bindingParams = dataBindingConfig.getBindingParams();
        for (int i = 0; dataBindingConfig.getBindingParams().size() > i; i++) {
            binding.setVariable(bindingParams.keyAt(i), bindingParams.valueAt(i));
        }
        mBinding = binding;
        //binding.getRoot().setClickable(true);//这么写是可以防止事件穿透
        binding.getRoot().setOnTouchListener(this::onTouch);//可以防止事件穿透

        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        B binding = getViewDataBinding(view);
        PrepareLayoutResource(binding);
        getDataFromeNet();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return true;//解决fragment中的事件穿透，当有界面重叠时，事件会在当前界面消费掉
    }

    //获取当前fragment对应的viewmodel 2020
    protected <T extends ViewModel> T getFragmentViewModel(Class<T> tClass) {
        if (provider == null) {
            provider = new ViewModelProvider(this);
        }
        return provider.get(tClass);
    }

    //获取全局共享数据的viewModel，全局任意订阅都可以收到数据
    protected <T extends ViewModel> T getApplicationScopeViewModel(Class<T> tClass) {
        ViewModelProvider mApplicationProvider = new ViewModelProvider((MainApplication) mContext.getApplicationContext()
                , getApplicationFactory(mContext));

        return mApplicationProvider.get(tClass);
    }


    private ViewModelProvider.Factory getApplicationFactory(Activity mContext) {
        checkActivity(this);
        Application application = checkApplication(mContext);
        ViewModelProvider.AndroidViewModelFactory factory = ViewModelProvider.AndroidViewModelFactory.getInstance(application);
        return factory;
    }

    private Application checkApplication(Activity mContext) {
        Application application = mContext.getApplication();
        if (application == null) {
            throw new IllegalStateException("Your activity/fragment is not yet attached to "
                    + "Application. You can't request ViewModel before onCreate call.");
        }
        return application;
    }

    private void checkActivity(Fragment fragment) {
        Activity activity = fragment.getActivity();
        if (activity == null) {
            throw new IllegalStateException("Can't create ViewModelProvider for detached fragment");
        }
    }

    //提供给子类加载自己的视图
   protected B binding;
    protected B getViewDataBinding(View view) {
        if (view != null) {
            binding = (B) DataBindingUtil.getBinding(view);
        }
        return binding;
    }
}
