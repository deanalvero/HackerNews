package com.deanalvero.news.hacker.model;

import com.deanalvero.news.hacker.retrofit.HackerNewsApi;
import com.deanalvero.news.hacker.util.Constant;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by dean on 19/05/16.
 */
public class FetchTopicItem {

    //  TOPICS
    private FetchTopicItemListener mTopicListener;
    private List<ItemObject> mItemObjectList;
    private List<Long> mTopicObjectIdList;

    private boolean mIsFetchingTopicObject;
    private boolean mIsFetchingTopicObjectIds;
    private boolean mDidCompleteFetchingAllTopicObject;
    private boolean mDidCompleteFetchingTopicObjectIds;
    private int currentIndex;

    private HackerNewsApi mNewsApi;

    public FetchTopicItem(HackerNewsApi newsApi){
        this.mNewsApi = newsApi;
    }

    public void setFetchTopicItemListener(FetchTopicItemListener listener){
        this.mTopicListener = listener;
    }


    public List<ItemObject> getItemObjectList(){
        if (mItemObjectList == null){
            mItemObjectList = new ArrayList<>();
        }
        return mItemObjectList;
    }

    public void clearTopicObject(){
        this.mItemObjectList.clear();
        this.currentIndex = 0;
        this.mIsFetchingTopicObject = false;
        this.mDidCompleteFetchingAllTopicObject = false;
        this.mDidCompleteFetchingTopicObjectIds = false;
        this.mIsFetchingTopicObjectIds = false;
    }

    public void fetchTopicObject(){
        if (this.mTopicObjectIdList == null){
            return; //  DO NOTHING
        }

        if (this.mIsFetchingTopicObject){
            return;
        }

        final int idListSize = this.mTopicObjectIdList.size();
        if (this.currentIndex >= idListSize || mDidCompleteFetchingAllTopicObject){
            return; //  REACHED LAST
        }

        this.mIsFetchingTopicObject = true;

        if (this.mTopicListener != null){
            this.mTopicListener.onStartedFetchingTopicObject();
        }

        int lowerLimit = currentIndex;
        int upper = currentIndex + Constant.FETCH_LIMIT_TOPIC;
        final int upperLimit = upper >= idListSize ? idListSize : upper;

        Observable.from(this.mTopicObjectIdList.subList(lowerLimit, upperLimit))
                .flatMap(new Func1<Long, Observable<Long>>() {
                    @Override
                    public Observable<Long> call(Long aLong) {
                        return Observable.just(aLong);
                    }
                })
                .flatMap(new Func1<Long, Observable<ItemObject>>() {
                    @Override
                    public Observable<ItemObject> call(Long aLong) {
                        return FetchTopicItem.this.mNewsApi.getTopicObject(aLong);
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ItemObject>() {
                    @Override
                    public void onCompleted() {

                        FetchTopicItem.this.currentIndex = upperLimit;
                        FetchTopicItem.this.mIsFetchingTopicObject = false;

                        if (FetchTopicItem.this.currentIndex >= idListSize){
                            FetchTopicItem.this.mDidCompleteFetchingAllTopicObject = true;
                        }

                        if (FetchTopicItem.this.mTopicListener != null){
                            FetchTopicItem.this.mTopicListener.onCompletedFetchingTopicObject();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        FetchTopicItem.this.mIsFetchingTopicObject = false;

                        if (FetchTopicItem.this.mTopicListener != null){
                            FetchTopicItem.this.mTopicListener.onErrorFetchingTopicObject();
                        }
                    }

                    @Override
                    public void onNext(ItemObject itemObject) {
                        FetchTopicItem.this.mItemObjectList.add(itemObject);

                        if (FetchTopicItem.this.mTopicListener != null){
                            FetchTopicItem.this.mTopicListener.onNextFetchingTopicObject(itemObject);
                        }
                    }
                });
    }

    public void fetchTopicObjectIds(){
        if (this.mIsFetchingTopicObjectIds){   return; }
        if (this.mDidCompleteFetchingTopicObjectIds){ return; }

        this.mIsFetchingTopicObjectIds = true;

        if (this.mTopicListener != null){
            this.mTopicListener.onStartedFetchingTopicObjectIds();
        }

        //  Get list of IDs
        mNewsApi.getTopStoriesId()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Long>>() {
                    @Override
                    public void onCompleted() {
                        FetchTopicItem.this.mIsFetchingTopicObjectIds = false;
                        FetchTopicItem.this.mDidCompleteFetchingTopicObjectIds = true;

                        if (FetchTopicItem.this.mTopicListener != null){
                            FetchTopicItem.this.mTopicListener.onCompletedFetchingTopicObjectIds();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        FetchTopicItem.this.mIsFetchingTopicObjectIds = false;

                        if (FetchTopicItem.this.mTopicListener != null){
                            FetchTopicItem.this.mTopicListener.onErrorFetchingTopicObjectIds();
                        }
                    }

                    @Override
                    public void onNext(List<Long> longs) {
                        FetchTopicItem.this.mTopicObjectIdList = longs;

//                        if (HDataManager.this.mTopicListener != null){
//                            HDataManager.this.mTopicListener.onNextFetchingTopicObjectIds(longs);
//                        }
                    }
                });
    }

    public boolean isFetchingTopicObject(){
        return mIsFetchingTopicObject;
    }

    public boolean isFetchingTopicObjectIds(){
        return mIsFetchingTopicObjectIds;
    }

    public boolean didCompleteFetchingAllTopicObject(){
        return mDidCompleteFetchingAllTopicObject;
    }

    public boolean didCompleteFetchingTopicObjectIds(){
        return mDidCompleteFetchingTopicObjectIds;
    }

    public interface FetchTopicItemListener {
        void onStartedFetchingTopicObject();
        void onNextFetchingTopicObject(ItemObject itemObject);
        void onErrorFetchingTopicObject();
        void onCompletedFetchingTopicObject();


        void onStartedFetchingTopicObjectIds();
        void onErrorFetchingTopicObjectIds();
        void onCompletedFetchingTopicObjectIds();
//        void onNextFetchingTopicObjectIds(List<Long> ids);
    }
}
