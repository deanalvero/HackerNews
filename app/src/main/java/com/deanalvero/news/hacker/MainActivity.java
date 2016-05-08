package com.deanalvero.news.hacker;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.deanalvero.news.hacker.adapter.TopicObjectRecyclerViewAdapter;
import com.deanalvero.news.hacker.model.CommentObjectItem;
import com.deanalvero.news.hacker.model.HackerNewsAPIModel;
import com.deanalvero.news.hacker.manager.NAPIManager;
import com.deanalvero.news.hacker.model.NewsAPIModel;
import com.deanalvero.news.hacker.model.TopicObject;
import com.deanalvero.news.hacker.model.TopicObjectItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NewsAPIModel.NewsAPIListener, TopicObjectRecyclerViewAdapter.TopicObjectRecyclerViewAdapterListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private TopicObjectRecyclerViewAdapter mAdapter;
    private NewsAPIModel mNewsAPI;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private Toolbar mToolbar;

    private ArrayList<TopicObjectItem> mTopicObjectItemList;
    private static final String KEY_LIST_TOPICOBJECTITEM = "KEY_LIST_TOPICOBJECTITEM";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(getLinearLayoutManager());
        mRecyclerView.setAdapter(getTopicObjectRecyclerViewAdapter());

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchTopStories();
            }
        });

//        List<TopicObjectItem> topicObjectItemList = NDataManager.getInstance().getTopicObjectItemList();
//        if (topicObjectItemList != null){
//            getTopicObjectRecyclerViewAdapter().setTopicObjectItemList(topicObjectItemList);
//            return;
//        }

//        Log.e(TAG, "savedInstanceState = " + (savedInstanceState != null) );

        if (savedInstanceState != null){
            mTopicObjectItemList = savedInstanceState.getParcelableArrayList(MainActivity.KEY_LIST_TOPICOBJECTITEM);
            getTopicObjectRecyclerViewAdapter().setTopicObjectItemList(mTopicObjectItemList);
        }

        if (mTopicObjectItemList == null) {
            fetchTopStories();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.e(TAG, "onSaveInstanceState");
        outState.putParcelableArrayList(MainActivity.KEY_LIST_TOPICOBJECTITEM, mTopicObjectItemList);

    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
//        Log.e(TAG, "onRestoreInstanceState");
//
//        mTopicObjectItemList = savedInstanceState.getParcelableArrayList(MainActivity.KEY_LIST_TOPICOBJECTITEM);
//        getTopicObjectRecyclerViewAdapter().setTopicObjectItemList(mTopicObjectItemList);
    }

    private void fetchTopStories(){
//        Log.e(TAG, "fetchStories");
        getNewsAPI().fetchTopStories();
    }

    @Override
    public void didFinishFetchTopicObjectItemList() {
        if (mSwipeRefreshLayout != null){
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void didFetchTopicObjectItemList(List<TopicObjectItem> topicObjectItemList) {
//        NDataManager.getInstance().setTopicObjectItemList(topicObjectItemList);
        this.mTopicObjectItemList = (ArrayList<TopicObjectItem>) topicObjectItemList;
        getTopicObjectRecyclerViewAdapter().setTopicObjectItemList(topicObjectItemList);
    }

    @Override
    public void didFetchTopicObject(int position, TopicObjectItem topicObjectItem) {
//        Log.e(TAG, "didFetchTopicObject " + topicObjectItem.getTopicObject());
//        getTopicObjectRecyclerViewAdapter().notifyDataSetChanged();

        getTopicObjectRecyclerViewAdapter().notifyItemChanged(position);
    }

    @Override
    public void didFetchCommentObject(int position, CommentObjectItem commentObjectItem) {
        //  NOT USED
    }

    @Override
    public void didFetchReplyObject(int position, CommentObjectItem commentObjectItem) {
        //  NOT USED
    }

    private NewsAPIModel getNewsAPI(){
        if (this.mNewsAPI == null){
            this.mNewsAPI = new HackerNewsAPIModel(NAPIManager.getInstance().getService());
            this.mNewsAPI.setNewsAPIListener(this);
        }
        return this.mNewsAPI;
    }

    private TopicObjectRecyclerViewAdapter getTopicObjectRecyclerViewAdapter(){
        if (mAdapter == null){
            mAdapter = new TopicObjectRecyclerViewAdapter();
            mAdapter.setContext(this);
            mAdapter.setTopicObjectRecyclerViewAdapterListener(this);
        }
        return mAdapter;
    }

    private RecyclerView.LayoutManager getLinearLayoutManager(){
        if (mLayoutManager == null){
            mLayoutManager = new LinearLayoutManager(this);
        }
        return mLayoutManager;
    }

    @Override
    public void loadTopicObject(int position, TopicObjectItem item) {
//        Log.e(TAG, "pos = " + position + "  id = " + item.getId());

        getNewsAPI().fetchTopicObject(position, item);
    }

    @Override
    public void onClickTopicObjectItem(TopicObjectItem item) {
//        Log.e(TAG, "onClickTopicObjectItem");
//        Log.e(TAG, (item == null ? "null item" : "empty item"));
        if (item == null) { return; }
        TopicObject topicObject = item.getTopicObject();

        //  Do not go to next screen if item has not yet loaded.
        if (topicObject == null) { return; }

        Intent intent = new Intent(this, TopicViewActivity.class);
        intent.putExtra(TopicViewActivity.EXTRA_KEY_TOPIC_OBJECT, topicObject);
        startActivity(intent);
    }
}
