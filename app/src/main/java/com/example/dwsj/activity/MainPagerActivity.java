package com.example.dwsj.activity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavGraph;
import androidx.navigation.NavGraphNavigator;
import androidx.navigation.Navigation;
import androidx.navigation.NavigatorProvider;
import androidx.navigation.fragment.FragmentNavigator;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.dwsj.BR;
import com.example.dwsj.MainApplication;
import com.example.dwsj.R;
import com.example.dwsj.config.DataBindingConfig;
import com.example.dwsj.databinding.ActivityMainPagerBinding;
import com.example.dwsj.fragment.MyFragmentNavigator;
import com.example.dwsj.fragment.anima.AnimaPagerBindingDataFragment;
import com.example.dwsj.fragment.assets.AssetsPagerBindingDataFragment;
import com.example.dwsj.fragment.associator.AssociatorPagerDataBindingFragment;
import com.example.dwsj.viewmodel.ManinPagerActivityViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainPagerActivity extends BaseActivity<ActivityMainPagerBinding> {

    private ManinPagerActivityViewModel mainModel;
    private ActivityMainPagerBinding mBinding;

    @Override
    protected void initViewModel() {
        mainModel = getActivityViewModel(ManinPagerActivityViewModel.class);
    }


    //视图初始化了，可以直接调用
    @Override
    protected void PrepareLayoutResource(ActivityMainPagerBinding binding) {
        this.mBinding = binding;


    }

    class RadioButtonCheckListener implements CompoundButton.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            switch (compoundButton.getId()) {
                case R.id.radio_button_left:
                    if (b) {
                        NavHostFragment.findNavController(getSupportFragmentManager().findFragmentById(R.id.nav_host_animafragment))
                                .navigate(navDestinations.findNode(R.id.radio_button_left).getId());//这两种导航都可以
                        checkTextColor(R.id.radio_button_left);
                    }
                    break;
                case R.id.radio_button_cent:
                    if (b) {
                        navController.navigate(R.id.radio_button_cent);
                        checkTextColor(R.id.radio_button_cent);
                    }
                    break;
                case R.id.radio_button_right:
                    if (b) {
                        navController.navigate(R.id.radio_button_right);
                        checkTextColor(R.id.radio_button_right);
                    }
                    break;
            }
        }

        private void checkTextColor(int id) {
            switch (id) {
                case R.id.radio_button_left:
                    mBinding.radioButtonCent.setChecked(false);
                    mBinding.radioButtonRight.setChecked(false);
                    mBinding.txvLeftAnimaworld.setTextColor(getResources().getColor(R.color.teal_700));
                    mBinding.txvCentAsset.setTextColor(getResources().getColor(R.color.black));
                    mBinding.txvRightUser.setTextColor(getResources().getColor(R.color.black));
                    break;
                case R.id.radio_button_cent:
                    mBinding.radioButtonLeft.setChecked(false);
                    mBinding.radioButtonRight.setChecked(false);
                    mBinding.txvLeftAnimaworld.setTextColor(getResources().getColor(R.color.black));
                    mBinding.txvCentAsset.setTextColor(getResources().getColor(R.color.teal_700));
                    mBinding.txvRightUser.setTextColor(getResources().getColor(R.color.black));
                    break;
                case R.id.radio_button_right:
                    mBinding.radioButtonLeft.setChecked(false);
                    mBinding.radioButtonCent.setChecked(false);
                    mBinding.txvRightUser.setTextColor(getResources().getColor(R.color.teal_700));
                    mBinding.txvLeftAnimaworld.setTextColor(getResources().getColor(R.color.black));
                    mBinding.txvCentAsset.setTextColor(getResources().getColor(R.color.black));
                    break;
            }
        }
    }

    @Override
    protected DataBindingConfig getDataBindingConfig() {
        return new DataBindingConfig(R.layout.activity_main_pager, BR.vm, mainModel);
    }


    private NavGraph navDestinations;
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);//
            //getWindow().setStatusBarColor(Color.TRANSPARENT);//必须设置，否则不会显示透明状态栏

        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
            Log.d("dwsj", "隐藏顶部标题栏: ");
        }

        //  BottomNavigationView navView = findViewById(R.id.nav_view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.nav_host_fragment)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_animafragment);

        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.nav_host_animafragment);
        //获取导航控制器
        // NavController navController = NavHostFragment.findNavController(fragment);
        //获取导航提供者
        NavigatorProvider provider = navController.getNavigatorProvider();
        //自定义导航
        MyFragmentNavigator fragmentNavigator = new MyFragmentNavigator(this, fragment.getChildFragmentManager(), fragment.getId());
        //添加自定义导航器进去 https://www.jianshu.com/p/adae9494d822
        provider.addNavigator(fragmentNavigator);
        //创建自己的导航图
        navDestinations = initNavGraph(provider, fragmentNavigator);

        //再设置自己定义的导航图，指定你需要切换的fragment
        navController.setGraph(navDestinations);

        //NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);//不要标题
        //NavigationUI.setupWithNavController(navView, navController);

//        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                navController.navigate(item.getItemId());
//                return true;
//            }
//        });


        RadioButtonCheckListener listener = new RadioButtonCheckListener();
        mBinding.radioButtonLeft.setOnCheckedChangeListener(listener);
        mBinding.radioButtonCent.setOnCheckedChangeListener(listener);
        mBinding.radioButtonRight.setOnCheckedChangeListener(listener);

        mBinding.radioButtonLeft.setChecked(true);
    }

    //手动创建导航图，把3个目的地添加进来
    private NavGraph initNavGraph(NavigatorProvider provider, MyFragmentNavigator fragmentNavigator) {
        NavGraph navGraph = new NavGraph(new NavGraphNavigator(provider));

        //用自定义的导航器来创建目的地  动物世界
        FragmentNavigator.Destination destination1 = fragmentNavigator.createDestination();
        //destination1.setId(R.id.navigation_home);
        destination1.setId(R.id.radio_button_left);
        destination1.setClassName(AnimaPagerBindingDataFragment.class.getCanonicalName());
        destination1.setLabel(getResources().getString(R.string.title_home));
        navGraph.addDestination(destination1);

        //资产中心
        FragmentNavigator.Destination destination2 = fragmentNavigator.createDestination();
        //destination2.setId(R.id.navigation_dashboard);
        destination2.setId(R.id.radio_button_cent);
        destination2.setClassName(AssetsPagerBindingDataFragment.class.getCanonicalName());
        destination2.setLabel(getResources().getString(R.string.title_dashboard));
        navGraph.addDestination(destination2);
        //会员中心
        FragmentNavigator.Destination destination3 = fragmentNavigator.createDestination();
        //destination3.setId(R.id.navigation_notifications);
        destination3.setId(R.id.radio_button_right);
        destination3.setClassName(AssociatorPagerDataBindingFragment.class.getCanonicalName());
        destination3.setLabel(getResources().getString(R.string.title_notifications));
        navGraph.addDestination(destination3);

        navGraph.setStartDestination(R.id.radio_button_left);

        return navGraph;
    }


    private boolean isBack = false;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK && isBack) {

            removeCurrentActivity();
            //return super.onKeyDown(keyCode, event);
        }
        isBack = true;
        Toast.makeText(this,"双击退出",Toast.LENGTH_LONG).show();
        handler.sendEmptyMessageDelayed(0x01,2000);
        return false;
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0x01:
                    isBack = false;
                    break;
            }
        }
    };
}