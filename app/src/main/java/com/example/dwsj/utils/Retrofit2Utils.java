package com.example.dwsj.utils;


import android.util.Log;



import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

//使用retrofit2联网请求
public class Retrofit2Utils {
private static String animaPagerBaseURL = "http://192.168.0.7:8080/";//动物世界页面数据
  private static String netVideoBaseURL = "http://api.m.mtime.cn/";

    private static Retrofit retrofit = new Retrofit.Builder().baseUrl(animaPagerBaseURL).build();
    private static ArrayList<Object> list;

    public static void getAnimaPagerData(requestCallBack callBack) {
        Retrofit2API api = retrofit.create(Retrofit2API.class);
        Call<ResponseBody> bodyCall = api.requestAnimaPagerData(RequestParmasUtil.getAnimaPagerParams());//api.requestNetVideoData();

        bodyCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response != null) {
                    try {
                        String data = response.body().string();
                        //list = jsonUtils.jsonToBean(data);
                        Log.d("RetrofitUtils", "onResponse: "+data);
                        if (callBack != null){
                            callBack.success(data);

                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("retrofitUtils", "onFailure: "+t);
                if (callBack != null){
                    callBack.onFail(t);
                }
            }
        });

    }



   public interface requestCallBack {
        void success(String data);

        void onFail(Throwable throwable);
    }


}
