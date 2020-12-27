package com.example.dwsj.viewmodel;
/*
Created by xiaoyu on 2020/11/20

Describe: 主界面activity的viewmodel，若在该activity中有业务需求，可根据需求增加liveData

*/


import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ManinPagerActivityViewModel extends ViewModel {
    MutableLiveData mainPager = new MutableLiveData();
}
