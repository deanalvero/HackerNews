package com.deanalvero.news.hacker.model;

import java.util.List;

/**
 * Created by dean on 07/05/16.
 */
public interface NewsAPIModel {

    void fetchTopStories();
    void fetchTopicObject(final int position, final TopicObjectItem item);



    void setNewsAPIListener(NewsAPIListener listener);

    void fetchCommentObject(final int position, CommentObjectItem item);
    void fetchReplyObject(final int position, CommentObjectItem item, CommentObjectItem reply);

    interface NewsAPIListener {
        void didFinishFetchTopicObjectItemList();

        void didFetchTopicObjectItemList(List<TopicObjectItem> topicObjectItemList);
        void didFetchTopicObject(int position, TopicObjectItem topicObjectItem);

        void didFetchCommentObject(int position, CommentObjectItem commentObjectItem);
        void didFetchReplyObject(int position, CommentObjectItem commentObjectItem);
    }
}
