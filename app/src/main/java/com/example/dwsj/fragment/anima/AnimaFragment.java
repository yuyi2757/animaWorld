package com.example.dwsj.fragment.anima;
/*
Created by xiaoyu on 2020/11/19

Describe:动物世界 页面. 实现自己的ViewModel和需要绑定的事件(后续可根据需求扩展任意事件绑定)，以及提供布局文件

*/

import android.util.Log;

import androidx.databinding.ViewDataBinding;

import com.example.dwsj.BR;
import com.example.dwsj.R;
import com.example.dwsj.config.DataBindingConfig;
import com.example.dwsj.config.ScareTimeConfigInfo;
import com.example.dwsj.fragment.BaseFragment;
import com.example.dwsj.utils.TimeUtil;
import com.example.dwsj.viewmodel.HomeViewModel;

import java.util.ArrayList;
import java.util.List;

import static com.example.dwsj.viewmodel.HomeViewModel.END_SCARE_TIME_1;
import static com.example.dwsj.viewmodel.HomeViewModel.END_SCARE_TIME_10;
import static com.example.dwsj.viewmodel.HomeViewModel.END_SCARE_TIME_2;
import static com.example.dwsj.viewmodel.HomeViewModel.END_SCARE_TIME_3;
import static com.example.dwsj.viewmodel.HomeViewModel.END_SCARE_TIME_4;
import static com.example.dwsj.viewmodel.HomeViewModel.END_SCARE_TIME_5;
import static com.example.dwsj.viewmodel.HomeViewModel.END_SCARE_TIME_6;
import static com.example.dwsj.viewmodel.HomeViewModel.END_SCARE_TIME_7;
import static com.example.dwsj.viewmodel.HomeViewModel.END_SCARE_TIME_8;
import static com.example.dwsj.viewmodel.HomeViewModel.END_SCARE_TIME_9;
import static com.example.dwsj.viewmodel.HomeViewModel.START_SCARE_TIME_1;
import static com.example.dwsj.viewmodel.HomeViewModel.START_SCARE_TIME_10;
import static com.example.dwsj.viewmodel.HomeViewModel.START_SCARE_TIME_2;
import static com.example.dwsj.viewmodel.HomeViewModel.START_SCARE_TIME_3;
import static com.example.dwsj.viewmodel.HomeViewModel.START_SCARE_TIME_4;
import static com.example.dwsj.viewmodel.HomeViewModel.START_SCARE_TIME_5;
import static com.example.dwsj.viewmodel.HomeViewModel.START_SCARE_TIME_6;
import static com.example.dwsj.viewmodel.HomeViewModel.START_SCARE_TIME_7;
import static com.example.dwsj.viewmodel.HomeViewModel.START_SCARE_TIME_8;
import static com.example.dwsj.viewmodel.HomeViewModel.START_SCARE_TIME_9;


public abstract class AnimaFragment<B extends ViewDataBinding> extends BaseFragment<B> {

    public HomeViewModel homeViewModel;
    List<ScareTimeConfigInfo> list = new ArrayList<>();

    @Override
    protected void initViewModel() {
        homeViewModel = getFragmentViewModel(HomeViewModel.class);
    }

    @Override
    protected DataBindingConfig getDataBindingConfig() {
        return new DataBindingConfig(R.layout.fragment_home, BR.vm, homeViewModel);
    }

    //当前抢购的状态，问开始，进行中，停止/当前宠物抢购的状态
    public static enum ScareStaue {
        UNSTART("没开始"), ING("抢购中"), STOP("结束"),NONE("未知状态");
        ScareStaue(String staue) {
        }
    }

    //准备好每场的开始结束时间
    private void initScareTime() {
        list.clear();
        list.add(new ScareTimeConfigInfo(1, START_SCARE_TIME_1, END_SCARE_TIME_1,false,false));
        list.add(new ScareTimeConfigInfo(2, START_SCARE_TIME_2, END_SCARE_TIME_2,false,false));
        list.add(new ScareTimeConfigInfo(3, START_SCARE_TIME_3, END_SCARE_TIME_3,false,false));
        list.add(new ScareTimeConfigInfo(4, START_SCARE_TIME_4, END_SCARE_TIME_4,false,false));
        list.add(new ScareTimeConfigInfo(5, START_SCARE_TIME_5, END_SCARE_TIME_5,false,false));
        list.add(new ScareTimeConfigInfo(6, START_SCARE_TIME_6, END_SCARE_TIME_6,false,false));
        list.add(new ScareTimeConfigInfo(7, START_SCARE_TIME_7, END_SCARE_TIME_7,false,false));
        list.add(new ScareTimeConfigInfo(8, START_SCARE_TIME_8, END_SCARE_TIME_8,false,false));
        list.add(new ScareTimeConfigInfo(9, START_SCARE_TIME_9, END_SCARE_TIME_9,false,false));
        list.add(new ScareTimeConfigInfo(10,START_SCARE_TIME_10, END_SCARE_TIME_10,false,false));
    }

    @Override
    public void onResume() {
        super.onResume();
        initScareTime();
        Log.d("animafragment", "onResume: 场次：" + list.size());

        for (int i = 0; i < list.size(); i++) {
            ScareTimeConfigInfo timeConfigInfo = list.get(i);

            ScareStaue scareStaue = TimeUtil.currentScareStaue(timeConfigInfo.starTtime,timeConfigInfo.endTime);
            switch (scareStaue) {
                case UNSTART:
                    Log.d("animafragment", "onResume: 开抢倒计时中");
                    timeConfigInfo.setStaue(ScareStaue.UNSTART);
                    homeViewModel.registerGetSystemCurrentTime(timeConfigInfo);//通知开始倒计时
                    break;
                case ING:
                    timeConfigInfo.setStartScare(true);
                    timeConfigInfo.setStaue(ScareStaue.ING);
                    homeViewModel.scareStaueUpdata(timeConfigInfo);//抢购进行中
                    break;
                case STOP:
                    timeConfigInfo.setStaue(ScareStaue.STOP);
                    homeViewModel.scareStaueUpdata(timeConfigInfo);//抢购结束了
                    break;
            }
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("animafragment", "onPause: ");

        homeViewModel.handler.removeCallbacksAndMessages(null);
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("animafragment", "onstop: ");

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d("animafragment", "onDestroyView: ");

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("animafragment", "onDestroy: ");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d("animafragment", "onDetach: ");
    }
}