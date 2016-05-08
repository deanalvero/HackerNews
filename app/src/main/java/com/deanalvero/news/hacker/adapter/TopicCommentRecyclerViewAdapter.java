package com.deanalvero.news.hacker.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.deanalvero.news.hacker.R;
import com.deanalvero.news.hacker.model.CommentObject;
import com.deanalvero.news.hacker.model.CommentObjectItem;
import com.deanalvero.news.hacker.model.TopicObject;
import com.deanalvero.news.hacker.util.Utility;

import java.util.List;

/**
 * Created by dean on 07/05/16.
 */
public class TopicCommentRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = TopicCommentRecyclerViewAdapter.class.getSimpleName();

    private List<CommentObjectItem> mObjectItemList;
    private TopicCommentRecyclerViewAdapterListener mListener;
    private Context context;

    public interface TopicCommentRecyclerViewAdapterListener {
        void loadCommentObject(int position, CommentObjectItem item);
    }

    public void setTopicCommentRecyclerViewAdapterListener(TopicCommentRecyclerViewAdapterListener listener){
        this.mListener = listener;
    }


    public void setCommentObjectItemList(List<CommentObjectItem> objectItemList){
        this.mObjectItemList = objectItemList;
        notifyDataSetChanged();
    }

    public void setContext(Context context){
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_object_comment, parent, false);
        viewHolder = new NViewHolderContent(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        NViewHolderContent viewHolderContent = (NViewHolderContent) holder;

        CommentObjectItem commentObjectItem = mObjectItemList.get(position); //cPosition);
        CommentObject commentObject = commentObjectItem.getCommentObject();

        viewHolderContent.textViewNumber.setText(String.format("%d", position + 1)); // cPosition + 1));
        viewHolderContent.textViewComment.setText("");
        viewHolderContent.textViewTitle.setText("");

        if (commentObject == null){
            if (this.mListener != null){
                this.mListener.loadCommentObject(position, commentObjectItem);
            }

            viewHolderContent.progressBar.setVisibility(View.VISIBLE);
        } else {

            String text = commentObject.getText();

            if (text != null) {
                viewHolderContent.textViewComment.setText(Html.fromHtml(text));
            } else {
                viewHolderContent.textViewComment.setText(R.string.deleted_comment);
            }

            viewHolderContent.recyclerView.setLayoutManager(new LinearLayoutManager(context));

            TopicReplyRecyclerViewAdapter replyRecyclerViewAdapter = new TopicReplyRecyclerViewAdapter();
            replyRecyclerViewAdapter.setCommentObjectItemList(commentObjectItem.getReplyList());
            replyRecyclerViewAdapter.setContext(context);
            replyRecyclerViewAdapter.setParentNumber(position);
            viewHolderContent.recyclerView.setAdapter(replyRecyclerViewAdapter);


            viewHolderContent.textViewTitle.setText(Utility.getCommentAuthorString(context, commentObject));
            viewHolderContent.progressBar.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        if (mObjectItemList == null) return 0;
        return mObjectItemList.size();

    }

    public static class NViewHolderContent extends RecyclerView.ViewHolder {

        TextView textViewNumber, textViewTitle, textViewComment; // textViewDescription;
        ProgressBar progressBar;
        RecyclerView recyclerView;

        public NViewHolderContent(View itemView) {
            super(itemView);

            textViewNumber = (TextView) itemView.findViewById(R.id.textView_number);
            textViewTitle = (TextView) itemView.findViewById(R.id.textView_title);
            textViewComment = (TextView) itemView.findViewById(R.id.textView_comment);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar);

            recyclerView = (RecyclerView) itemView.findViewById(R.id.recyclerView_reply);
        }
    }
}
