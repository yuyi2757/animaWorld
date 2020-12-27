package com.example.dwsj.utils;

import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

public interface Retrofit2API {
    //网络视频地址
    @GET("PageSubArea/TrailerList.api")
    Call<ResponseBody> requestNetVideoData();

    @POST("product/getAll")
    Call<ResponseBody> requestAnimaPagerData(@QueryMap HashMap<String,String> map);
}
