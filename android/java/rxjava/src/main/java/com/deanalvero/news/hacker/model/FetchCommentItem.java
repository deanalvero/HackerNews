package com.deanalvero.news.hacker.model;

import com.deanalvero.news.hacker.retrofit.HackerNewsApi;

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
public class FetchCommentItem {

    private HackerNewsApi mNewsApi;
    private FetchCommentItemListener mCommentListener;
    private ItemObject mSelectedItemObject;

    public FetchCommentItem(HackerNewsApi newsApi){
        this.mNewsApi = newsApi;
    }

    public void setSelectedItemObject(ItemObject selectedItemObject){
        this.mSelectedItemObject = selectedItemObject;
    }

    public ItemObject getSelectedItemObject(){
        return this.mSelectedItemObject;
    }

    public void setFetchCommentItemListener(FetchCommentItemListener listener){
        this.mCommentListener = listener;
    }

    public void fetchCommentObject(final ItemObject parentItemObject){
        if (parentItemObject == null){
            return;
        }

        List<Long> kidsList = parentItemObject.kids;

        if (kidsList != null && kidsList.size() > 0){

            final List<ItemObject> kidsItemObject = new ArrayList<>();

            Observable.from(kidsList)
                    .flatMap(new Func1<Long, Observable<ItemObject>>() {
                        @Override
                        public Observable<ItemObject> call(Long aLong) {
                            return FetchCommentItem.this.mNewsApi.getItemObject(aLong);
                        }
                    })
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<ItemObject>() {
                        @Override
                        public void onCompleted() {
                            parentItemObject.kidObjects = kidsItemObject;

                            if (FetchCommentItem.this.mCommentListener != null){
                                FetchCommentItem.this.mCommentListener.onCompletedFetchingCommentTree();
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            if (FetchCommentItem.this.mCommentListener != null){
                                FetchCommentItem.this.mCommentListener.onErrorFetchingCommentTree();
                            }
                        }

                        @Override
                        public void onNext(ItemObject itemObject) {
                            kidsItemObject.add(itemObject);
                            fetchCommentObject(itemObject);
                        }
                    });
        }
    }

    public interface FetchCommentItemListener {
        void onCompletedFetchingCommentTree();
        void onErrorFetchingCommentTree();
    }
}
