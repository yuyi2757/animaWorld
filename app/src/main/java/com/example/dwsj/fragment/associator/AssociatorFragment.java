package com.example.dwsj.fragment.associator;
/*
* Create by xiaoyu on 2020/11/19
*
* Describe:会员中心界面
*
* */

import androidx.databinding.ViewDataBinding;

import com.example.dwsj.BR;
import com.example.dwsj.R;
import com.example.dwsj.config.DataBindingConfig;
import com.example.dwsj.fragment.BaseFragment;
import com.example.dwsj.viewmodel.NotificationsViewModel;


public abstract class AssociatorFragment<B extends ViewDataBinding> extends BaseFragment<B> {

    private NotificationsViewModel notificationsViewModel;

    @Override
    protected void initViewModel() {
        notificationsViewModel = getFragmentViewModel(NotificationsViewModel.class);
    }

    @Override
    protected DataBindingConfig getDataBindingConfig() {
        return new DataBindingConfig(R.layout.fragment_notifications, BR.vm,notificationsViewModel);
    }

}