package com.example.productapp.retroinstance;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroInstance {

    public static String BASE_URL = "https://api.tiki.vn/";

    private static Retrofit retrofit;

    public static Retrofit getRetrofitClient(){
        if(retrofit==null){
             retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            System.out.println("retrofit build:"+ retrofit.toString());
        }
        return retrofit;
    }
}
