package com.deanalvero.news.hacker.model;

import android.util.Log;


import com.deanalvero.news.hacker.api.HackerNewsService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by dean on 06/05/16.
 */
public class HackerNewsAPIModel implements NewsAPIModel {
    private static final String TAG = HackerNewsAPIModel.class.getSimpleName();

    private HackerNewsService service;
    private NewsAPIListener listener;

    public HackerNewsAPIModel(HackerNewsService service){
        this.service = service;
    }


    @Override
    public void setNewsAPIListener(NewsAPIListener listener){
        this.listener = listener;
    }

    @Override
    public void fetchTopStories(){
        Call<List<Long>> call = service.getTopStories();
        call.enqueue(new Callback<List<Long>>() {
            @Override
            public void onResponse(Call<List<Long>> call, Response<List<Long>> response) {

                if (response.isSuccessful()){

                    List<Long> body = response.body();
                    Log.e(TAG, "bodySize = " + body.size());

                    List<TopicObjectItem> objectItemList = new ArrayList<>();

                    for (Long item: body){
                        objectItemList.add(new TopicObjectItem(item, null));
                    }

                    if (HackerNewsAPIModel.this.listener != null) {
                        HackerNewsAPIModel.this.listener.didFetchTopicObjectItemList(objectItemList);
                    }

                } else {
                    //  TODO: ERROR
                }

                if (HackerNewsAPIModel.this.listener != null) {
                    HackerNewsAPIModel.this.listener.didFinishFetchTopicObjectItemList();
                }

            }

            @Override
            public void onFailure(Call<List<Long>> call, Throwable t) {

                if (HackerNewsAPIModel.this.listener != null) {
                    HackerNewsAPIModel.this.listener.didFinishFetchTopicObjectItemList();
                }
            }
        });
    }

    @Override
    public void fetchTopicObject(final int position, final TopicObjectItem item) {
        Call<TopicObject> call = service.getTopicObject(item.getId());
        call.enqueue(new Callback<TopicObject>() {
            @Override
            public void onResponse(Call<TopicObject> call, Response<TopicObject> response) {
                if (response.isSuccessful()){
//                    Log.e(TAG, "isSuccessful");

                    item.setTopicObject(response.body());

                    if (HackerNewsAPIModel.this.listener != null) {
                        HackerNewsAPIModel.this.listener.didFetchTopicObject(position, item);
                    }

                } else {
                    Log.e(TAG, "error: " + response.errorBody().toString());
                    //  TODO: ERROR
                }
            }

            @Override
            public void onFailure(Call<TopicObject> call, Throwable t) {

            }
        });
    }


    @Override
    public void fetchCommentObject(final int position, final CommentObjectItem item) {
        Call<CommentObject> call = service.getCommentObject(item.getId());
        call.enqueue(new Callback<CommentObject>() {
            @Override
            public void onResponse(Call<CommentObject> call, Response<CommentObject> response) {
                if (response.isSuccessful()){
                    Log.e(TAG, "isSuccessful");

                    item.setCommentObject(response.body());

                    if (HackerNewsAPIModel.this.listener != null) {
                        HackerNewsAPIModel.this.listener.didFetchCommentObject(position, item);
                    }

                } else {
                    Log.e(TAG, "error: " + response.errorBody().toString());
                    //  TODO: ERROR
                }
            }

            @Override
            public void onFailure(Call<CommentObject> call, Throwable t) {
                Log.e(TAG, "onFailure");
            }
        });
    }

    @Override
    public void fetchReplyObject(final int position, final CommentObjectItem item, final CommentObjectItem reply) {
        Call<CommentObject> call = service.getCommentObject(reply.getId());
        call.enqueue(new Callback<CommentObject>() {
            @Override
            public void onResponse(Call<CommentObject> call, Response<CommentObject> response) {
                if (response.isSuccessful()){
                    Log.e(TAG, "fetchReplyObject isSuccessful " + position);

                    reply.setCommentObject(response.body());

                    if (HackerNewsAPIModel.this.listener != null) {
                        HackerNewsAPIModel.this.listener.didFetchReplyObject(position, item);
                    }

                } else {
                    Log.e(TAG, "error: " + response.errorBody().toString());
                    //  TODO: ERROR
                }
            }

            @Override
            public void onFailure(Call<CommentObject> call, Throwable t) {
                Log.e(TAG, "onFailure");
            }
        });

    }
}
