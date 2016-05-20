package com.deanalvero.news.hacker.manager;

import android.util.Log;

import com.deanalvero.news.hacker.model.FetchCommentItem;
import com.deanalvero.news.hacker.model.FetchTopicItem;
import com.deanalvero.news.hacker.model.ItemObject;
import com.deanalvero.news.hacker.retrofit.HackerNewsApi;
import com.deanalvero.news.hacker.retrofit.HackerNewsService;
import com.deanalvero.news.hacker.util.Constant;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by dean on 18/05/16.
 */
public class HDataManager {

    private static final String TAG = HDataManager.class.getSimpleName();

    private static HDataManager INSTANCE;
    private HackerNewsApi mNewsApi;
    private FetchTopicItem mFetchTopicItem;
    private FetchCommentItem mFetchCommentItem;

    private HDataManager(){}

    public static HDataManager getInstance(){
        if (INSTANCE == null){
            INSTANCE = new HDataManager();
        }
        return INSTANCE;
    }

    public FetchTopicItem getFetchTopicItem(){
        if (mFetchTopicItem == null){
            mFetchTopicItem = new FetchTopicItem(getHackerNewsAPI());
        }
        return mFetchTopicItem;
    }

    public FetchCommentItem getFetchCommentItem(){
        if (mFetchCommentItem == null){
            mFetchCommentItem = new FetchCommentItem(getHackerNewsAPI());
        }
        return mFetchCommentItem;
    }

    private HackerNewsApi getHackerNewsAPI(){
        if (this.mNewsApi == null){
            this.mNewsApi = HackerNewsService.createHackerNewsService();
        }
        return this.mNewsApi;
    }
}