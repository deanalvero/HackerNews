package com.deanalvero.news.hacker.retrofit;

import com.deanalvero.news.hacker.util.Constant;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by dean on 18/05/16.
 */
public class HackerNewsService {

    private HackerNewsService(){

    }

    public static HackerNewsApi createHackerNewsService(){
        Retrofit.Builder builder = new Retrofit.Builder().addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Constant.URL_BASE_WEB_SERVICE);

        return builder.build().create(HackerNewsApi.class);
    }


}
