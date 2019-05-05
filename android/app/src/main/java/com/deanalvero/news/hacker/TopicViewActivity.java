package com.deanalvero.news.hacker;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.deanalvero.news.hacker.adapter.CommentItemRecyclerViewAdapter;
import com.deanalvero.news.hacker.manager.HDataManager;
import com.deanalvero.news.hacker.model.FetchCommentItem;
import com.deanalvero.news.hacker.model.ItemObject;
import com.deanalvero.news.hacker.util.Utility;

/**
 * Created by dean on 06/05/16.
 */
public class TopicViewActivity extends AppCompatActivity implements FetchCommentItem.FetchCommentItemListener {
    private static final String TAG = TopicViewActivity.class.getSimpleName();

    private RecyclerView mRecyclerView;
    private CommentItemRecyclerViewAdapter mCommentItemRecyclerViewAdapter;
    private Toolbar mToolbar;
    private TextView textViewTitle, textViewDescription;
    private ItemObject mSelectedItemObject;

    private CoordinatorLayout mCoordinatorLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mCoordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);

        textViewTitle = (TextView) findViewById(R.id.textView_title);
        textViewDescription = (TextView) findViewById(R.id.textView_description);

        this.mSelectedItemObject = getFetchCommentItem().getSelectedItemObject();

        if (this.mSelectedItemObject == null){
            finish();
            return;
        }

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(getCommentItemRecyclerViewAdapter());

        textViewTitle.setText(Utility.safeString(this.mSelectedItemObject.title));
        textViewDescription.setText(Utility.getDescriptionString(this, this.mSelectedItemObject));

        fetchComments();
    }

    private void fetchComments() {
        showSnackbarMessage(R.string.fetching_comments);
        getFetchCommentItem().setFetchCommentItemListener(this);
        getFetchCommentItem().fetchCommentObject(this.mSelectedItemObject);
    }

    public CommentItemRecyclerViewAdapter getCommentItemRecyclerViewAdapter(){
        if (this.mCommentItemRecyclerViewAdapter == null){
            this.mCommentItemRecyclerViewAdapter = new CommentItemRecyclerViewAdapter(this, this.mSelectedItemObject);
        }
        return this.mCommentItemRecyclerViewAdapter;
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

    @Override
    public void onCompletedFetchingCommentTree() {
        getCommentItemRecyclerViewAdapter().notifyDataSetChanged();
    }

    @Override
    public void onErrorFetchingCommentTree() {
        showSnackbarMessage(R.string.fetching_comments_error);
    }

    private void showSnackbarMessage(int stringId){
        if (this.mCoordinatorLayout != null) {
            Snackbar.make(this.mCoordinatorLayout, stringId, Snackbar.LENGTH_LONG).show();
        }
    }

    private FetchCommentItem getFetchCommentItem(){
        return HDataManager.getInstance().getFetchCommentItem();
    }

    @Override
    protected void onDestroy() {
        getFetchCommentItem().setFetchCommentItemListener(null);
        super.onDestroy();
    }
}
