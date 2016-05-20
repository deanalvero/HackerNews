package com.deanalvero.news.hacker.retrofit;

import com.deanalvero.news.hacker.model.ItemObject;
import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by dean on 18/05/16.
 */
public interface HackerNewsApi {

    @GET("topstories.json")
    Observable<List<Long>> getTopStoriesId();

    @GET("item/{id}.json")
    Observable<ItemObject> getTopicObject(@Path("id") Long id);

    @GET("item/{id}.json")
    Observable<ItemObject> getItemObject(@Path("id") Long id);
}
