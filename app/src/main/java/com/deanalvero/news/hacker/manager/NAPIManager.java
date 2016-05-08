package com.deanalvero.news.hacker.manager;

import com.deanalvero.news.hacker.api.HackerNewsService;
import com.deanalvero.news.hacker.util.Constant;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by dean on 06/05/16.
 */

//  For single instance of Retrofit
public class NAPIManager {

    private static NAPIManager INSTANCE;
    private Retrofit retrofit;
    private HackerNewsService service;

    private NAPIManager(){
        this.retrofit = new Retrofit.Builder()
                .baseUrl(Constant.URL_BASE_WEB_SERVICE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        this.service = this.retrofit.create(HackerNewsService.class);
    }

    public static NAPIManager getInstance(){
        if (INSTANCE == null){
            INSTANCE = new NAPIManager();
        }
        return INSTANCE;
    }

    public HackerNewsService getService(){
        return service;
    }
}
