package com.deanalvero.news.hacker;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.deanalvero.news.hacker.adapter.TopicCommentRecyclerViewAdapter;
import com.deanalvero.news.hacker.manager.NAPIManager;
import com.deanalvero.news.hacker.model.CommentObject;
import com.deanalvero.news.hacker.model.CommentObjectItem;
import com.deanalvero.news.hacker.model.HackerNewsAPIModel;
import com.deanalvero.news.hacker.model.NewsAPIModel;
import com.deanalvero.news.hacker.model.TopicObject;
import com.deanalvero.news.hacker.model.TopicObjectItem;
import com.deanalvero.news.hacker.util.Utility;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dean on 06/05/16.
 */
public class TopicViewActivity extends AppCompatActivity implements NewsAPIModel.NewsAPIListener, TopicCommentRecyclerViewAdapter.TopicCommentRecyclerViewAdapterListener {

    private static final String TAG = TopicViewActivity.class.getSimpleName();
    public static final String EXTRA_KEY_TOPIC_OBJECT = "EXTRA_KEY_TOPIC_OBJECT";
    private static final String KEY_LIST_COMMENTOBJECTITEM = "KEY_LIST_COMMENTOBJECTITEM";

    private TopicObject mTopicObject;

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private NewsAPIModel mNewsAPI;
//    private SwipeRefreshLayout mSwipeRefreshLayout;
    private TopicCommentRecyclerViewAdapter mAdapter;
    private Toolbar mToolbar;
    private TextView textViewTitle, textViewDescription;

    private ArrayList<CommentObjectItem> mCommentObjectItemList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        textViewTitle = (TextView) findViewById(R.id.textView_title);
        textViewDescription = (TextView) findViewById(R.id.textView_description);

        Intent intent = getIntent();
        if (intent != null){
            if (intent.hasExtra(TopicViewActivity.EXTRA_KEY_TOPIC_OBJECT)) {
                mTopicObject = intent.getParcelableExtra(TopicViewActivity.EXTRA_KEY_TOPIC_OBJECT);
            }
        }

        //  Go back to previous screen if item is null
        if (mTopicObject == null){  finish();   return; }


        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(getLinearLayoutManager());
        mRecyclerView.setAdapter(getTopicCommentRecyclerViewAdapter());

        textViewTitle.setText(mTopicObject.getTitle());
        textViewDescription.setText(Utility.getDescriptionString(this, mTopicObject));

        if (savedInstanceState != null) {
            mCommentObjectItemList = savedInstanceState.getParcelableArrayList(TopicViewActivity.KEY_LIST_COMMENTOBJECTITEM);
            getTopicCommentRecyclerViewAdapter().setCommentObjectItemList(mCommentObjectItemList);
        }

        if (mCommentObjectItemList == null) {
            fetchTopicComments();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(TopicViewActivity.KEY_LIST_COMMENTOBJECTITEM, mCommentObjectItemList);
    }

    private void fetchTopicComments(){
        List<Long> commentIdList = this.mTopicObject.getKids();
        ArrayList<CommentObjectItem> objectItemList = new ArrayList<>();
        for (Long id: commentIdList){
            objectItemList.add(new CommentObjectItem(id, null, null));
        }
        this.mCommentObjectItemList = objectItemList;

        getTopicCommentRecyclerViewAdapter().setCommentObjectItemList(objectItemList);
    }

    private NewsAPIModel getNewsAPI(){
        if (this.mNewsAPI == null){
            this.mNewsAPI = new HackerNewsAPIModel(NAPIManager.getInstance().getService());
            this.mNewsAPI.setNewsAPIListener(this);
        }
        return this.mNewsAPI;
    }

    private RecyclerView.LayoutManager getLinearLayoutManager(){
        if (mLayoutManager == null){
            mLayoutManager = new LinearLayoutManager(this);
        }
        return mLayoutManager;
    }

    private TopicCommentRecyclerViewAdapter getTopicCommentRecyclerViewAdapter(){
        if (mAdapter == null){
            mAdapter = new TopicCommentRecyclerViewAdapter();
            mAdapter.setContext(this);
            mAdapter.setTopicCommentRecyclerViewAdapterListener(this);
        }
        return mAdapter;
    }

    @Override
    public void loadCommentObject(int position, CommentObjectItem item) {
//        Log.e(TAG, "loadCommentObject: " + position);
        getNewsAPI().fetchCommentObject(position, item);
    }


    @Override
    public void didFinishFetchTopicObjectItemList() {
        //  NOT USED
    }

    @Override
    public void didFetchTopicObjectItemList(List<TopicObjectItem> topicObjectItemList) {

        //  NOT USED
    }

    @Override
    public void didFetchTopicObject(int position, TopicObjectItem topicObjectItem) {
        //  NOT USED
    }

    @Override
    public void didFetchCommentObject(int position, CommentObjectItem commentObjectItem) {
        Log.e(TAG, "didFetchCommentObject: " + position);

        List<CommentObjectItem> replyList = commentObjectItem.getReplyList();
        if (replyList == null){
            List<Long> longList = commentObjectItem.getCommentObject().getKids();

            replyList = new ArrayList<>();

            if (longList != null) {

                for (Long id: longList) {
                    replyList.add(new CommentObjectItem(id, null, null));
                }
            }
            commentObjectItem.setReplyList(replyList);
        }


        boolean isFetching = false;
        for (CommentObjectItem replyItem: replyList){
            CommentObject reply = replyItem.getCommentObject();
            if (reply == null){
                isFetching = true;
                getNewsAPI().fetchReplyObject(position, commentObjectItem, replyItem);
            }
        }

        if (!isFetching){
            notifyFetchRepliesIsComplete(position);
        }
    }

    @Override
    public void didFetchReplyObject(int position, CommentObjectItem commentObjectItem) {
        if (hasRepliesBeenFetched(commentObjectItem.getReplyList())){
            notifyFetchRepliesIsComplete(position);
        }
    }

    private void notifyFetchRepliesIsComplete(int position){
        getTopicCommentRecyclerViewAdapter().notifyItemChanged(position);
    }

    private boolean hasRepliesBeenFetched(List<CommentObjectItem> replyList){
//        if (replyList == null){
//            return true;
//        }

        for (CommentObjectItem reply: replyList){
            if (reply == null){
                return false;
            }
        }
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
