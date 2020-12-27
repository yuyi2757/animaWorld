package com.example.dwsj.config;
/*
Created by xiaoyu on 2020/11/19

Describe: 所有界面的viewmodel和自定义事件配置，用于初始化视图时对自定义事件进行绑定，如点击事件，recycleview,listview等等

视图的适配器以及业务扩展需要的一切事件的绑定，每个事件必须绑定事件原和id。绑定事件需要在布局文件中声明，自定义事件
需要将函数进行签注。大致做法为:

举例，若布局中有以下"adapter" “submitList”自定义适配器---->>>>>>>
 <data>
        <variable
            name="vm"
            type="com.aispeech.jet.viewmodel.ContentFragmentViewModel" />
        <variable
            name="adapter"
            type="androidx.recyclerview.widget.ListAdapter" />

    </data>


 <androidx.recyclerview.widget.RecyclerView
                    android:id="@+id/recycleView"
                    adapter="@{adapter}"
                    submitList="@{vm.request}"
                    android:layout_width="match_parent"
                    android:layout_height="match_parent"
                    app:layoutManager="androidx.recyclerview.widget.LinearLayoutManager"
                    tools:listitem="@layout/item_net_video">

                </androidx.recyclerview.widget.RecyclerView>
则需要在新建的一个类中以如下格式签注一个函数，程序初始化时会绑定到此进行初始化并适配数据
@BindingAdapter(value = {"adapter", "submitList"}, requireAll = false)
 public static void setRecycleViewData(RecyclerView recyclerView, ListAdapter adapter, List<MediaItemBean> data) {
        if (recyclerView != null && data != null) {
            recyclerView.setAdapter(adapter);
            adapter.submitList(data);
        }



=======================再举个例子=======================================
若希望在某个控件中完成一段操作，比如以下的一个TabLayout：

 <com.google.android.material.tabs.TabLayout
                    android:id="@+id/tablayout"
                    setTabAndViewPager="@{vm.observableBoolean}"
                    android:layout_width="match_parent"
                    android:layout_height="50dp"
                    android:fitsSystemWindows="true"
                    app:tabBackground="@color/white"
                    app:tabIndicatorColor="@color/gray"
                    app:tabIndicatorHeight="4dp"
                    app:tabSelectedTextColor="@color/gray"
                    app:tabTextColor="@color/light_gray">



该TabLayout中有一个自定义事件“setTabAndViewPager”标签,后面的vm.observableBoolean是赋值，则具体实现这个事件，跟上一个
例子一样，在一个java文件中(可以新建文件，也可以写在一个文件里，建议不同控件的事件单独建文件，方便管理)做如下声明

public class TablayoutBindViewPager {


    @BindingAdapter(value = {"setTabAndViewPager"},requireAll = false)
    public static void setTabAndViewPager(TabLayout tableLayout, boolean ispre) {
        //1 初始化并且绑定tablayout和viewpager，将viewpager也适配显示其包含的内容
        int tabCount = tableLayout.getTabCount();
        List<String> list = new ArrayList();
        for (int i = 0; i < tabCount; i++) {
            TabLayout.Tab tabItem = tableLayout.getTabAt(i);//得到每个子tab。包含它们自己的标题
            if (tabItem != null && tabItem.getText() != null) {
                list.add(tabItem.getText().toString());
            }
        }

        //2 初始化viewpager 绑定
        ViewPager viewPager = tableLayout.getRootView().findViewById(R.id.viewpager);
        if (viewPager != null) {
            ContentViewPagerAdapter adapter = new ContentViewPagerAdapter(tabCount, list, false);
            viewPager.setAdapter(adapter);
            tableLayout.setupWithViewPager(viewPager);
        }

    }
}
函数名可以随意取，但签注@BindingAdapter(value = {"setTabAndViewPager"},requireAll = false)必须与布局中的setTabAndViewPager
以制，否则操作界面的时候收不到数据或者点击事件等等都收不到


具体实现请查看代码和布局中的<data><data>引用，根据自己的业务需求添加事件
*/


import android.util.SparseArray;

import androidx.lifecycle.ViewModel;

public class DataBindingConfig {
    private final int layout;

    private final int vmVariableId;

    private final ViewModel stateViewModel;

    private SparseArray bindingParams = new SparseArray();

    public DataBindingConfig(int layout, int vmVariableId, ViewModel stateViewModel) {
        this.layout = layout;
        this.vmVariableId = vmVariableId;
        this.stateViewModel = stateViewModel;
    }

    public int getLayout() {
        return layout;
    }

    public int getVmVariableId() {
        return vmVariableId;
    }

    public ViewModel getStateViewModel() {
        return stateViewModel;
    }

    public SparseArray getBindingParams() {
        return bindingParams;
    }

    public DataBindingConfig addBindingParam(int variableId, Object object) {
        if (bindingParams.get(variableId) == null) {
            bindingParams.put(variableId, object);
        }
        return this;
    }
}
