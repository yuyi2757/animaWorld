package com.example.dwsj.fragment.assets;
/*
 * Create by xiaoyu on 2020/11/19
 *
 * Describe:资产中心界面
 *
 *
 * */

import androidx.databinding.ViewDataBinding;

import com.example.dwsj.BR;
import com.example.dwsj.R;
import com.example.dwsj.config.DataBindingConfig;
import com.example.dwsj.fragment.BaseFragment;
import com.example.dwsj.viewmodel.DashboardViewModel;


public abstract class AssetsdFragment<B extends ViewDataBinding> extends BaseFragment<B> {

    private DashboardViewModel dashboardViewModel;

    @Override
    protected void initViewModel() {
        dashboardViewModel = getFragmentViewModel(DashboardViewModel.class);
    }

    @Override
    protected DataBindingConfig getDataBindingConfig() {
        return new DataBindingConfig(R.layout.fragment_dashboard, BR.vm, dashboardViewModel);
    }


}