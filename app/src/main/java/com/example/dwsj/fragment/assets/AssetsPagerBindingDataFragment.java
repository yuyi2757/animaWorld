package com.example.dwsj.fragment.assets;
/*
Created by xiaoyu on 2020/11/20

Describe:资产界面的UI联网更新

*/


import com.example.dwsj.databinding.FragmentDashboardBinding;

public class AssetsPagerBindingDataFragment extends AssetsdFragment<FragmentDashboardBinding> {

    private FragmentDashboardBinding binding;

    @Override
    protected void PrepareLayoutResource(FragmentDashboardBinding binding) {
        this.binding = binding;
    }

    @Override
    protected void getDataFromeNet() {
        //TODO 根据当前界面的需求选择联网
        setData();
    }

    private void setData() {
       // binding.textDashboard.setText("小资产");
    }
}
