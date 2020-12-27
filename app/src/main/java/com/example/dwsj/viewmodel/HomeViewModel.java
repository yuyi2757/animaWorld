package com.example.dwsj.viewmodel;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.alibaba.fastjson.JSON;
import com.example.dwsj.MainApplication;
import com.example.dwsj.R;
import com.example.dwsj.bean.animaPagerBean;
import com.example.dwsj.config.ScareTimeConfigInfo;
import com.example.dwsj.fragment.anima.AnimaFragment;
import com.example.dwsj.utils.BitmapUtils;
import com.example.dwsj.utils.Retrofit2Utils;
import com.example.dwsj.utils.TimeUtil;

import java.util.HashMap;
import java.util.Map;

public class HomeViewModel extends ViewModel {
    //动物类型的key
    private static int JIN_SI_HOU = 0;
    private static int MEI_HUA_LU = 1;
    private static int DONG_BEI_HU = 2;
    private static int BAI_TIAN_E = 3;
    private static int ZHONG_GUO_LONG = 4;
    private static int WANG_CAI_GOU = 5;
    private static int DA_HOG_YING = 6;
    private static int BAO_XI_NIAO = 7;
    private static int CHI_TU_MA = 8;
    private static int DA_XIONG_MAO = 9;
    //动物的名字
    public static String NAME_JIN_SI_HOU = "金丝猴";
    public static String NAME_DONG_BEI_HU = "东北虎";
    public static String NAME_DA_HONG_YING = "大红鹰";
    public static String NAME_BAO_XI_NIAO = "报喜鸟";
    public static String NAME_BAI_TIAN_E = "白天鹅";
    public static String NAME_ZHONG_GUO_LONG = "中国龙";
    public static String NAME_WANG_CAI_GOU = "旺财狗";
    public static String NAME_CHI_TU_MA = "赤兔马";
    public static String NAME_DA_XIONG_MAO = "大熊猫";
    public static String NAME_MEI_HUA_LU = "梅花鹿";

    //定义上午场开抢时间点  第1场
    public final static String START_SCARE_TIME_1 = "18:36:00";//八点整开抢
    public final static String END_SCARE_TIME_1 = "18:36:30";//结束时间

    //第2场
    public final static String START_SCARE_TIME_2 = "19:23:10";
    public final static String END_SCARE_TIME_2 = "23:23:20";

    //第3场
    public final static String START_SCARE_TIME_3 = "18:53:00";
    public final static String END_SCARE_TIME_3 = "18:53:10";


    //中午场 第1场
    public final static String START_SCARE_TIME_4 = "12:45:00";
    public final static String END_SCARE_TIME_4 = "13:50:00";
    //第2场
    public final static String START_SCARE_TIME_5 = "12:30:00";
    public final static String END_SCARE_TIME_5 = "15:00:00";
    //第3场
    public final static String START_SCARE_TIME_6 = "15:00:00";
    public final static String END_SCARE_TIME_6 = "16:30:00";

    //第4场
    public final static String START_SCARE_TIME_7 = "13:30:00";
    public final static String END_SCARE_TIME_7 = "16:58:00";


    //下午场 第1场
    public final static String START_SCARE_TIME_8 = "14:48:00";
    public final static String END_SCARE_TIME_8 = "14:51:00";


    //第2场
    public final static String START_SCARE_TIME_9 = "21:00:00";
    public final static String END_SCARE_TIME_9 = "23:00:00";
    //第3场
    public final static String START_SCARE_TIME_10 = "22:45:00";
    public final static String END_SCARE_TIME_10 = "23:47:00";

    HashMap<Integer, Bitmap> map;
    private MutableLiveData<animaPagerBean> animaPagerLiveData;//动物世界页面联网请求数据

    private MutableLiveData<Map<Integer, String>> scare;//只更新抢购倒计时

    private MutableLiveData<Map<Integer, ScareTimeConfigInfo>> scareStaue;//抢购状态更新，开始 停止 两个状态

    Map<Integer, String> itemScareTimeUpdata = new HashMap<>();//更新对应场次的倒计时时间 key:场次 value:倒计时时间，设置到UI上

    Map<Integer, ScareTimeConfigInfo> scareStaueUpdata = new HashMap<>();//更新对应的场次和状态 key:场次  value：当前场次状态，ING，STOP

    private Context mContext;

    public HomeViewModel() {
        animaPagerLiveData = new MutableLiveData<>();
        scare = new MutableLiveData();
        scareStaue = new MutableLiveData<>();
    }

    public LiveData<animaPagerBean> getLiveData(Context context) {
        this.mContext = context;
        initAnimaDefIcon();
        return animaPagerLiveData;
    }

    //订阅抢购倒计时的时间变化，以秒钟计算
    public LiveData<Map<Integer, String>> startScare() {
        return scare;
    }

    //订阅抢购过程中以及程序初始化时的实时状态变化，在开抢后依旧会监测两种状态:ING  STOP  这两个状态必须实时监控，触发的时候通知界面
    public LiveData<Map<Integer, ScareTimeConfigInfo>> scareStaueListeren() {
        return scareStaue;
    }

    //初始化本地动物的头像
    private void initAnimaDefIcon() {
        if (map == null) {
            map = new HashMap();
        }

        Bitmap jin_si_hou = BitmapUtils.circleBitmap(BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.jinsihou));
        Bitmap mei_hua_lu = BitmapUtils.circleBitmap(BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.meihualu));
        Bitmap dong_bei_hu = BitmapUtils.circleBitmap(BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.laohu));
        Bitmap bai_tian_e = BitmapUtils.circleBitmap(BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.baitiane));
        Bitmap zhong_guo_long = BitmapUtils.circleBitmap(BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.shenlong));
        Bitmap wang_cai_gou = BitmapUtils.circleBitmap(BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.hashiqi));
        Bitmap da_hong_ying = BitmapUtils.circleBitmap(BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.dahongying));
        Bitmap bao_xi_niao = BitmapUtils.circleBitmap(BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.baoxiniao));
        Bitmap chi_tu_ma = BitmapUtils.circleBitmap(BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.ma));
        Bitmap da_xiong_mao = BitmapUtils.circleBitmap(BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.daxiongmao));
        map.put(JIN_SI_HOU, jin_si_hou);
        map.put(MEI_HUA_LU, mei_hua_lu);
        map.put(DONG_BEI_HU, dong_bei_hu);
        map.put(BAI_TIAN_E, bai_tian_e);
        map.put(ZHONG_GUO_LONG, zhong_guo_long);
        map.put(WANG_CAI_GOU, wang_cai_gou);
        map.put(DA_HOG_YING, da_hong_ying);
        map.put(BAO_XI_NIAO, bao_xi_niao);
        map.put(CHI_TU_MA, chi_tu_ma);
        map.put(DA_XIONG_MAO, da_xiong_mao);

    }

    public Bitmap getBitmapByName(String name) {
        if (name.equals(NAME_JIN_SI_HOU)) {
            return map.get(JIN_SI_HOU);
        } else if (name.equals(NAME_DONG_BEI_HU)) {
            return map.get(DONG_BEI_HU);
        } else if (name.equals(NAME_DA_HONG_YING)) {
            return map.get(DA_HOG_YING);
        } else if (name.equals(NAME_BAO_XI_NIAO)) {
            return map.get(BAO_XI_NIAO);
        } else if (name.equals(NAME_BAI_TIAN_E)) {
            return map.get(BAI_TIAN_E);
        } else if (name.equals(NAME_ZHONG_GUO_LONG)) {
            return map.get(ZHONG_GUO_LONG);
        } else if (name.equals(NAME_WANG_CAI_GOU)) {
            return map.get(WANG_CAI_GOU);
        } else if (name.equals(NAME_CHI_TU_MA)) {
            return map.get(CHI_TU_MA);
        } else if (name.equals(NAME_DA_XIONG_MAO)) {
            return map.get(DA_XIONG_MAO);
        } else {
            return map.get(MEI_HUA_LU);
        }


    }


    /*
    开始倒计时
    * @params:
    * timeConfigInfo  倒计时信息配置文件，包含场次，开始时间和结束时间
    * */
    public synchronized void registerGetSystemCurrentTime(ScareTimeConfigInfo timeConfigInfo) {
        //开始更新时间
        Message message = Message.obtain();
        message.obj = timeConfigInfo;
        message.what = 0x002;
        handler.sendMessageDelayed(message, 1000);
    }

    //抢购状态:进行中，或者结束了
    public void scareStaueUpdata(ScareTimeConfigInfo scareTimeConfigInfo) {
        scareStaueUpdata.put(scareTimeConfigInfo.number, scareTimeConfigInfo);
        scareStaue.setValue(scareStaueUpdata);
    }




    public final Handler handler = new Handler(MainApplication.mHandler.getLooper()) {
        private animaPagerBean pagerBean;

        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0x001:
                    pagerBean = (animaPagerBean) msg.obj;
                    animaPagerLiveData.setValue(pagerBean);
//                    Log.d("homeviewmodel", "handleMessage: " +
//                            "收到解析数据: "+pagerBean.getResult().size());
                    break;
                case 0x002:
                    ScareTimeConfigInfo timeConfigInfo = (ScareTimeConfigInfo) msg.obj;
                    //String timeExpend = TimeUtil.getTimeExpend(TimeUtil.getSystemTime(), timeConfigInfo.starTtime);
                    //timeExpend.equals("0:0:0"
                    if (TimeUtil.currentScareStaue(timeConfigInfo.getStarTtime(),timeConfigInfo.getEndTime()) == AnimaFragment.ScareStaue.ING) {

                        timeConfigInfo.setStartScare(true);//表示本场已经开始抢了
                        timeConfigInfo.setEndScare(false);//还未结束
                        timeConfigInfo.setStaue(AnimaFragment.ScareStaue.ING);
                        scareStaueUpdata(timeConfigInfo);//开始抢
                        registerGetSystemCurrentTime(timeConfigInfo);//通知继续循环更新时间监控这一场有没有结束
                    } else if (!timeConfigInfo.getStartScare() && TimeUtil.currentScareStaue(timeConfigInfo.getStarTtime(),timeConfigInfo.getEndTime()) == AnimaFragment.ScareStaue.UNSTART) {
                        //倒计时
                        String timeExpend = TimeUtil.getTimeExpend(TimeUtil.getSystemTime(), timeConfigInfo.starTtime);
                        timeConfigInfo.setTimeExpend(timeExpend);
                        timeConfigInfo.setStartScare(false);
                        timeConfigInfo.setStaue(AnimaFragment.ScareStaue.UNSTART);
                        scareStaueUpdata(timeConfigInfo);
                        registerGetSystemCurrentTime(timeConfigInfo);//通知继续循环更新时间

                    } else if (!timeConfigInfo.getEndScare() && TimeUtil.currentScareStaue(timeConfigInfo.getStarTtime(), timeConfigInfo.getEndTime()) == AnimaFragment.ScareStaue.STOP) {
                       // Log.d("homeviewmodel", "当前结束场次  :"+timeConfigInfo.number);
                        timeConfigInfo.setEndScare(true);//表示该场结束时间到了
                        timeConfigInfo.setStaue(AnimaFragment.ScareStaue.STOP);
                        scareStaueUpdata(timeConfigInfo);//更新界面抢购结束

                    }

                    break;
            }

        }
    };


    public void getDataFromeServer() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                Retrofit2Utils.getAnimaPagerData(new Retrofit2Utils.requestCallBack() {
                    @Override
                    public void success(String data) {
                        Log.d("homeviewmodel", "success: " + data);
                        animaPagerBean bean = JSON.parseObject(data, animaPagerBean.class);
                        Message message = Message.obtain();
                        message.what = 0x001;
                        message.obj = bean;
                        handler.sendMessage(message);
                    }

                    @Override
                    public void onFail(Throwable throwable) {
                        Log.d("homeviewmodel", "联网失败: " + throwable.toString());
                    }
                });

            }
        }).start();

    }


}