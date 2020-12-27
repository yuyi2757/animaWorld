package com.example.dwsj.fragment.associator;
/*
Created by xiaoyu on 2020/11/20

Describe: 会员界面UI更新或者联网更新部分数据，根据需求选择是否联网

*/


import com.example.dwsj.databinding.FragmentNotificationsBinding;

public class AssociatorPagerDataBindingFragment extends AssociatorFragment<FragmentNotificationsBinding> {
    private FragmentNotificationsBinding binding;

    @Override
    protected void PrepareLayoutResource(FragmentNotificationsBinding binding) {
        this.binding = binding;
    }

    @Override
    protected void getDataFromeNet() {
        //TODO 若有需要联网，可在对应的viewmodel中联网请求订阅，再更新UI
        setData();
    }

    private void setData() {
       // binding.textDashboard.setText("会员中心");
    }
}
