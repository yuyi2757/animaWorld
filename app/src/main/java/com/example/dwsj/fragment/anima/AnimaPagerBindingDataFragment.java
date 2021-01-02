package com.example.dwsj.fragment.anima;
/*
Created by xiaoyu on 2020/11/20

Describe: 联网请求所需数据并更新UI

*/

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.lifecycle.Observer;

import com.alibaba.fastjson.JSON;
import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.example.dwsj.MainApplication;
import com.example.dwsj.R;
import com.example.dwsj.bean.animaPagerBean;

import com.example.dwsj.config.ScareTimeConfigInfo;
import com.example.dwsj.databinding.FragmentHomeBinding;
import com.example.dwsj.utils.BitmapUtils;
import com.example.dwsj.utils.Constans;
import com.example.dwsj.view.MyScrollView;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.example.dwsj.viewmodel.HomeViewModel.NAME_JIN_SI_HOU;

/*      下拉圆圈刷新
        https://github.com/baoyongzhang/android-PullRefreshLayout
 *      implementation 'com.baoyz.pullrefreshlayout:library:1.2.0'
 *
 *      当前使用的刷新工具
 *      https://github.com/android-cjj/Android-MaterialRefreshLayout
 *      materialRefreshLayout.autoRefresh();//drop-down refresh automatically 自动刷新
        materialRefreshLayout.autoRefreshLoadMore();// pull up refresh automatically
 * */

public class AnimaPagerBindingDataFragment extends AnimaFragment<FragmentHomeBinding> {


    private FragmentHomeBinding binding;

    @Override
    protected void PrepareLayoutResource(FragmentHomeBinding binding) {
        this.binding = binding;
        binding.materialRefresh.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                MainApplication.mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        binding.materialRefresh.finishRefresh();//结束
                        //下拉刷新

                    }
                }, 3000);
                Log.d("animapager", "onRefresh: ");

            }

            @Override
            public void onfinish() {
                super.onfinish();
                //刷新结束
                Log.d("animapager", "onfinish: ");
            }

        });
        //设置了对滑动布局的监听，可以拓展需求，暂时没什么用
        binding.scrollLayout.setScanScrollChangedListener(new MyScrollView.ISmartScrollChangedListener() {
            @Override
            public void onScrolledToBottom() {
                Log.d("animapager", "接近到底了 ");

            }

            @Override
            public void onScrolledToTop() {
                Log.d("animapager", "顶部=== ");
            }
        });

    }

    //接收在viewmodel中联网请求数据，适配界面
    @Override
    protected void getDataFromeNet() {
        homeViewModel.getLiveData(mContext).observe(mContext, new Observer<animaPagerBean>() {
            @Override
            public void onChanged(animaPagerBean bean) {
                setData(bean);
            }
        });
        // homeViewModel.getDataFromeServer();//联网


        //监控 抢购进行中和结束两种状态
        homeViewModel.scareStaueListeren().observe(mContext, new Observer<Map<Integer, ScareTimeConfigInfo>>() {
            @Override
            public void onChanged(Map<Integer, ScareTimeConfigInfo> integerScareStaueMap) {

                for (Iterator<Map.Entry<Integer, ScareTimeConfigInfo>> iterator = integerScareStaueMap.entrySet().iterator(); iterator.hasNext(); ) {
                    Map.Entry<Integer, ScareTimeConfigInfo> next = iterator.next();
                    switch (next.getKey()) {
                        case 1:
                            //Log.d("pager", "onChanged: 1场的状态:"+next.getValue().getStaue());
                            if (next.getValue().getStaue() == ScareStaue.ING) {
                                //设置抢购中的UI状态
                                binding.txvScareEnd11.setText("正在抢购");
                                binding.txvScareEnd11.setTextColor(getResources().getColor(R.color.colorAccent));
                            } else if (next.getValue().getStaue() == ScareStaue.STOP) {
                                //设置结束的UI状态

                                binding.txvScareEnd11.setText("抢购结束");
                                binding.txvScareEnd11.setTextColor(getResources().getColor(R.color.gray));
                            } else if (next.getValue().getStaue() == ScareStaue.UNSTART) {
                                binding.imgWatchLogo11.setText(next.getValue().getTimeExpend());
                            }

                            break;
                        case 2:
                            if (next.getValue().getStaue() == ScareStaue.ING) {
                                //设置抢购中的UI状态
                                binding.txvScareEnd12.setText("正在抢购");
                                binding.txvScareEnd12.setTextColor(getResources().getColor(R.color.colorAccent));
                            } else if (next.getValue().getStaue() == ScareStaue.STOP) {
                                //设置结束的UI状态
                                binding.txvScareEnd12.setText("抢购结束");
                                binding.txvScareEnd12.setTextColor(getResources().getColor(R.color.gray));
                            } else if (next.getValue().getStaue() == ScareStaue.UNSTART) {
                                binding.imgWatchLogo12.setText(next.getValue().getTimeExpend());
                            }
                            break;
                        case 3:
                            if (next.getValue().getStaue() == ScareStaue.ING) {
                                //设置抢购中的UI状态
                                binding.txvScareEnd13.setText("正在抢购");
                                binding.txvScareEnd13.setTextColor(getResources().getColor(R.color.colorAccent));
                            } else if (next.getValue().getStaue() == ScareStaue.STOP) {
                                //设置结束的UI状态
                                binding.txvScareEnd13.setText("抢购结束");
                                binding.txvScareEnd13.setTextColor(getResources().getColor(R.color.gray));
                            } else if (next.getValue().getStaue() == ScareStaue.UNSTART) {
                                binding.imgWatchLogo13.setText(next.getValue().getTimeExpend());
                            }
                            break;
                        case 4:

                            if (next.getValue().getStaue() == ScareStaue.ING) {
                                //设置抢购中的UI状态
                                binding.txvScareEnd21.setText("正在抢购");
                                binding.txvScareEnd21.setTextColor(getResources().getColor(R.color.colorAccent));
                            } else if (next.getValue().getStaue() == ScareStaue.STOP) {
                                //设置结束的UI状态
                                binding.txvScareEnd21.setText("抢购结束");
                                binding.txvScareEnd21.setTextColor(getResources().getColor(R.color.gray));
                            } else if (next.getValue().getStaue() == ScareStaue.UNSTART) {
                                binding.imgWatchLogo21.setText(next.getValue().getTimeExpend());
                            }
                            break;
                        case 5:
                            if (next.getValue().getStaue() == ScareStaue.ING) {
                                //设置抢购中的UI状态
                                binding.txvScareEnd22.setText("正在抢购");
                                binding.txvScareEnd22.setTextColor(getResources().getColor(R.color.colorAccent));
                            } else if (next.getValue().getStaue() == ScareStaue.STOP) {
                                //设置结束的UI状态
                                binding.txvScareEnd22.setText("抢购结束");
                                binding.txvScareEnd22.setTextColor(getResources().getColor(R.color.gray));
                            } else if (next.getValue().getStaue() == ScareStaue.UNSTART) {
                                binding.imgWatchLogo22.setText(next.getValue().getTimeExpend());
                            }
                            break;
                        case 6:
                            if (next.getValue().getStaue() == ScareStaue.ING) {
                                //设置抢购中的UI状态
                                binding.txvScareEnd23.setText("正在抢购");
                                binding.txvScareEnd23.setTextColor(getResources().getColor(R.color.colorAccent));
                            } else if (next.getValue().getStaue() == ScareStaue.STOP) {
                                //设置结束的UI状态
                                binding.txvScareEnd23.setText("抢购结束");
                                binding.txvScareEnd23.setTextColor(getResources().getColor(R.color.gray));
                            }else if (next.getValue().getStaue() == ScareStaue.UNSTART){
                                binding.imgWatchLogo23.setText(next.getValue().getTimeExpend());
                            }
                            break;
                        case 7:
                            if (next.getValue().getStaue() == ScareStaue.ING) {
                                //设置抢购中的UI状态
                                binding.txvScareEnd24.setText("正在抢购");
                                binding.txvScareEnd24.setTextColor(getResources().getColor(R.color.colorAccent));
                            } else if (next.getValue().getStaue() == ScareStaue.STOP) {
                                //设置结束的UI状态
                                binding.txvScareEnd24.setText("抢购结束");
                                binding.txvScareEnd24.setTextColor(getResources().getColor(R.color.gray));
                            }else if (next.getValue().getStaue() == ScareStaue.UNSTART){
                                binding.imgWatchLogo24.setText(next.getValue().getTimeExpend());
                            }
                            break;
                        case 8:
                            if (next.getValue().getStaue() == ScareStaue.ING) {
                                //设置抢购中的UI状态
                                binding.txvScareEnd31.setText("正在抢购");
                                binding.txvScareEnd31.setTextColor(getResources().getColor(R.color.colorAccent));
                            } else if (next.getValue().getStaue() == ScareStaue.STOP) {
                                //设置结束的UI状态
                                binding.txvScareEnd31.setText("抢购结束");
                                binding.txvScareEnd31.setTextColor(getResources().getColor(R.color.gray));
                            }else if (next.getValue().getStaue() == ScareStaue.UNSTART){
                                binding.imgWatchLogo31.setText(next.getValue().getTimeExpend());
                            }
                            break;
                        case 9:
                            if (next.getValue().getStaue() == ScareStaue.ING) {
                                //设置抢购中的UI状态
                                binding.txvScareEnd32.setText("正在抢购");
                                binding.txvScareEnd32.setTextColor(getResources().getColor(R.color.colorAccent));
                            } else if (next.getValue().getStaue() == ScareStaue.STOP) {
                                //设置结束的UI状态
                                binding.txvScareEnd32.setText("抢购结束");
                                binding.txvScareEnd32.setTextColor(getResources().getColor(R.color.gray));
                            }else if (next.getValue().getStaue() == ScareStaue.UNSTART){
                                binding.imgWatchLogo32.setText(next.getValue().getTimeExpend());
                            }
                            break;
                        case 10:
                            if (next.getValue().getStaue() == ScareStaue.ING) {
                                //设置抢购中的UI状态
                                binding.txvScareEnd33.setText("正在抢购");
                                binding.txvScareEnd33.setTextColor(getResources().getColor(R.color.colorAccent));
                            } else if (next.getValue().getStaue() == ScareStaue.STOP) {
                                //设置结束的UI状态
                                binding.txvScareEnd33.setText("抢购结束");
                                binding.txvScareEnd33.setTextColor(getResources().getColor(R.color.gray));
                            }else if (next.getValue().getStaue() == ScareStaue.UNSTART){
                                binding.imgWatchLogo33.setText(next.getValue().getTimeExpend());
                            }
                            break;
                    }
                }
            }
        });

        animaPagerBean pagerBean = JSON.parseObject(Constans.testJson, animaPagerBean.class);

        Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.tvx_anima);
        animation.setFillAfter(true);//动画结束后停留再这里
        binding.tvShowNumber11.startAnimation(animation);
        binding.tvShowNumber12.startAnimation(animation);
        binding.tvShowNumber13.startAnimation(animation);

        binding.tvShowNumber21.startAnimation(animation);
        binding.tvShowNumber22.startAnimation(animation);
        binding.tvShowNumber23.startAnimation(animation);
        binding.tvShowNumber24.startAnimation(animation);

        binding.tvShowNumber31.startAnimation(animation);
        binding.tvShowNumber32.startAnimation(animation);
        binding.tvShowNumber33.startAnimation(animation);

//        binding.tvMor.setText("上午场");
//        binding.tvEf.setText("中午场");
//        binding.tvNig.setText("下午场");


        setData(pagerBean);
    }

    private void setData(animaPagerBean bean) {
        List<animaPagerBean.ResultBean> result = bean.getResult();
        for (animaPagerBean.ResultBean resultBean : result) {
            BindData2Item(resultBean);
        }

    }

    //绑定数据
    private void BindData2Item(animaPagerBean.ResultBean resultBean) {
        switch (resultBean.getRanking()) {
            //1 2 3 为上午场
            case 1:
                binding.imgPetCat11.setImageBitmap(homeViewModel.getBitmapByName(resultBean.getName()));//图片
                binding.tvPrice11.setText(resultBean.getRemarks());//价格
                binding.tvSunNum11.setText(resultBean.getValue() + "");//太阳个数
                binding.tvPetName11.setText(resultBean.getName());//宠物名字
                break;
            case 2:
                binding.imgPetCat12.setImageBitmap(homeViewModel.getBitmapByName(resultBean.getName()));//图片
                binding.tvPrice12.setText(resultBean.getRemarks());//价格
                binding.tvSunNum12.setText(resultBean.getValue() + "");//太阳个数
                binding.tvPetName12.setText(resultBean.getName());//宠物名字
                break;
            case 3:
                binding.imgPetCat13.setImageBitmap(homeViewModel.getBitmapByName(resultBean.getName()));//图片
                binding.tvPrice13.setText(resultBean.getRemarks());//价格
                binding.tvSunNum13.setText(resultBean.getValue() + "");//太阳个数
                binding.tvPetName13.setText(resultBean.getName());//宠物名字
                break;
            //4 5 6 7 中午场
            case 4:
                binding.imgPetCat21.setImageBitmap(homeViewModel.getBitmapByName(resultBean.getName()));//图片
                binding.tvPrice21.setText(resultBean.getRemarks());//价格
                binding.tvSunNum21.setText(resultBean.getValue() + "");//太阳个数
                binding.tvPetName21.setText(resultBean.getName());//宠物名字
                break;
            case 5:
                binding.imgPetCat22.setImageBitmap(homeViewModel.getBitmapByName(resultBean.getName()));//图片
                binding.tvPrice22.setText(resultBean.getRemarks());//价格
                binding.tvSunNum22.setText(resultBean.getValue() + "");//太阳个数
                binding.tvPetName22.setText(resultBean.getName());//宠物名字
                break;
            case 6:
                binding.imgPetCat23.setImageBitmap(homeViewModel.getBitmapByName(resultBean.getName()));//图片
                binding.tvPrice23.setText(resultBean.getRemarks());//价格
                binding.tvSunNum23.setText(resultBean.getValue() + "");//太阳个数
                binding.tvPetName23.setText(resultBean.getName());//宠物名字
                break;
            case 7:
                binding.imgPetCat24.setImageBitmap(homeViewModel.getBitmapByName(resultBean.getName()));//图片
                binding.tvPrice24.setText(resultBean.getRemarks());//价格
                binding.tvSunNum24.setText(resultBean.getValue() + "");//太阳个数
                binding.tvPetName24.setText(resultBean.getName());//宠物名字
                break;
            //8 9 10 为下午场
            case 8:
                binding.imgPetCat31.setImageBitmap(homeViewModel.getBitmapByName(resultBean.getName()));//图片
                binding.tvPrice31.setText(resultBean.getRemarks());//价格
                binding.tvSunNum31.setText(resultBean.getValue() + "");//太阳个数
                binding.tvPetName31.setText(resultBean.getName());//宠物名字
                break;
            case 9:
                binding.imgPetCat32.setImageBitmap(homeViewModel.getBitmapByName(resultBean.getName()));//图片
                binding.tvPrice32.setText(resultBean.getRemarks());//价格
                binding.tvSunNum32.setText(resultBean.getValue() + "");//太阳个数
                binding.tvPetName32.setText(resultBean.getName());//宠物名字
                break;
            case 10:
                binding.imgPetCat33.setImageBitmap(homeViewModel.getBitmapByName(resultBean.getName()));//图片
                binding.tvPrice33.setText(resultBean.getRemarks());//价格
                binding.tvSunNum33.setText(resultBean.getValue() + "");//太阳个数
                binding.tvPetName33.setText(resultBean.getName());//宠物名字
                break;

        }


    }


}
