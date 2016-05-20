package com.deanalvero.news.hacker;

import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.deanalvero.news.hacker.adapter.TopicItemRecyclerViewAdapter;
import com.deanalvero.news.hacker.manager.HDataManager;
import com.deanalvero.news.hacker.model.FetchCommentItem;
import com.deanalvero.news.hacker.model.FetchTopicItem;
import com.deanalvero.news.hacker.model.ItemObject;

import java.util.List;


public class MainActivity extends AppCompatActivity implements FetchTopicItem.FetchTopicItemListener, TopicItemRecyclerViewAdapter.TopicItemRecyclerViewAdapterListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private Toolbar mToolbar;

    private TopicItemRecyclerViewAdapter mTopicItemRecyclerViewAdapter;
    private List<ItemObject> mItemObjectList;
    private LinearLayoutManager mLinearLayoutManager;
    private CoordinatorLayout mCoordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        mCoordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);

        mLinearLayoutManager = new LinearLayoutManager(this);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setAdapter(getTopicItemRecyclerViewAdapter());

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                clearTopicObject();
                fetchTopicObjectIds();
            }
        });


        fetchTopicObjectIds();     //  Will not fetch if currently fetching
        updateUI();
    }

    private RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            if (mLinearLayoutManager != null &&
                    mLinearLayoutManager.findLastCompletelyVisibleItemPosition() == getItemObjectList().size() - 1){
                fetchTopicObject();
            }
        }
    };

    private void addOnScrollListenerToRecyclerView(){
        if (mRecyclerView != null) mRecyclerView.addOnScrollListener(mOnScrollListener);
    }

    private void clearOnScrollListenersToRecyclerView(){
        if (mRecyclerView != null) mRecyclerView.clearOnScrollListeners();
    }

    private void updateUI() {
        updateSwipeRefreshLayout();
        updateSnackbar();
    }

    private void updateSnackbar() {
        if (getFetchTopicItem().isFetchingTopicObject()){
            showSnackbarMessage(R.string.fetching_topics);
        }
    }

    private void showSnackbarMessage(int stringId){
        if (this.mCoordinatorLayout != null) {
            Snackbar.make(this.mCoordinatorLayout, stringId, Snackbar.LENGTH_LONG).show();
        }
    }

    private void updateSwipeRefreshLayout() {
        if (mSwipeRefreshLayout != null) {
            if (getFetchTopicItem().isFetchingTopicObjectIds()
                    && !getFetchTopicItem().didCompleteFetchingTopicObjectIds()){

                mSwipeRefreshLayout.post(new Runnable() {
                    @Override
                    public void run() {
                        mSwipeRefreshLayout.setRefreshing(true);
                    }
                });

            } else {
                mSwipeRefreshLayout.setRefreshing(false);
            }
        }
    }

    private void clearTopicObject(){
        clearOnScrollListenersToRecyclerView();
        getFetchTopicItem().clearTopicObject();
        getTopicItemRecyclerViewAdapter().notifyDataSetChanged();
    }

    private void fetchTopicObjectIds() {
        getFetchTopicItem().setFetchTopicItemListener(this);
        getFetchTopicItem().fetchTopicObjectIds();
    }

    private void fetchTopicObject(){
        getFetchTopicItem().setFetchTopicItemListener(this);
        getFetchTopicItem().fetchTopicObject();
    }

    public RecyclerView.Adapter getTopicItemRecyclerViewAdapter() {
        if (mTopicItemRecyclerViewAdapter == null){
            mTopicItemRecyclerViewAdapter = new TopicItemRecyclerViewAdapter(this, getItemObjectList(), this);
        }
        return mTopicItemRecyclerViewAdapter;
    }

    public List<ItemObject> getItemObjectList() {
        if (mItemObjectList == null){
            mItemObjectList = getFetchTopicItem().getItemObjectList();
        }
        return mItemObjectList;
    }

    @Override
    public void onStartedFetchingTopicObject() {
        clearOnScrollListenersToRecyclerView();
        updateUI();
    }

    @Override
    public void onNextFetchingTopicObject(ItemObject itemObject) {
        clearOnScrollListenersToRecyclerView();
        getTopicItemRecyclerViewAdapter().notifyDataSetChanged();
    }

    @Override
    public void onErrorFetchingTopicObject() {
        updateUI();
        addOnScrollListenerToRecyclerView();
        showSnackbarMessage(R.string.fetching_topics_error);
    }

    @Override
    public void onCompletedFetchingTopicObject() {
        updateUI();
        addOnScrollListenerToRecyclerView();
    }

    @Override
    public void onStartedFetchingTopicObjectIds() {
        updateUI();
    }

    @Override
    public void onErrorFetchingTopicObjectIds() {
        updateUI();
        showSnackbarMessage(R.string.fetching_topics_error);
    }

    @Override
    public void onCompletedFetchingTopicObjectIds() {
        fetchTopicObject();
        updateUI();
    }

    @Override
    protected void onResume() {
        super.onResume();
        addOnScrollListenerToRecyclerView();
    }

    @Override
    protected void onPause() {
        super.onPause();

        clearOnScrollListenersToRecyclerView();
    }

    @Override
    protected void onDestroy() {
        clearOnScrollListenersToRecyclerView();
        super.onDestroy();
    }

    @Override
    public void onClickTopicItem(ItemObject itemObject, int position) {
        getFetchCommentItem().setSelectedItemObject(itemObject);

        Intent intent = new Intent(this, TopicViewActivity.class);
        startActivity(intent);
    }

    private FetchCommentItem getFetchCommentItem(){
        return HDataManager.getInstance().getFetchCommentItem();
    }

    private FetchTopicItem getFetchTopicItem(){
        return HDataManager.getInstance().getFetchTopicItem();
    }
}
