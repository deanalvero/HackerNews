package com.deanalvero.news.hacker.api;

import com.deanalvero.news.hacker.model.CommentObject;
import com.deanalvero.news.hacker.model.TopicObject;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by dean on 06/05/16.
 */
public interface HackerNewsService {

    @GET("topstories.json")
    Call<List<Long>> getTopStories();

    @GET("item/{id}.json")
    Call<TopicObject> getTopicObject(@Path("id") Long id);

    @GET("item/{id}.json")
    Call<CommentObject> getCommentObject(@Path("id") Long id);
}
