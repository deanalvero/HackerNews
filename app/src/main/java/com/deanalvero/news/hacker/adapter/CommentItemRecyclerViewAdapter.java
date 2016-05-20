package com.deanalvero.news.hacker.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.deanalvero.news.hacker.R;
import com.deanalvero.news.hacker.model.ItemObject;
import com.deanalvero.news.hacker.util.Utility;

import java.util.List;

/**
 * Created by dean on 18/05/16.
 */
public class CommentItemRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ItemObject mItemObject;
    private Context mContext;

    public CommentItemRecyclerViewAdapter(Context context, ItemObject itemObject){
        this.mContext = context;
        this.mItemObject = itemObject;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment, parent, false);
        return new CommentItemViewHolder(view);
    }

//    public void setContext(Context context){
//        this.mContext = context;
//    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        CommentItemViewHolder viewHolder = (CommentItemViewHolder) holder;

        List<ItemObject> itemObjectList = mItemObject.kidObjects;
        ItemObject itemObject = itemObjectList.get(position);

        String text = itemObject.text;

        viewHolder.textViewNumber.setText(String.format("%d", position + 1));

        if (text == null){
            viewHolder.textViewComment.setText("");
        } else {
            viewHolder.textViewComment.setText(Html.fromHtml(text));
        }

        viewHolder.textViewTitle.setText(Utility.getCommentAuthorString(mContext, itemObject));
        viewHolder.recyclerView.setLayoutManager(new LinearLayoutManager(mContext));

        CommentItemRecyclerViewAdapter commentItemRecyclerViewAdapter = new CommentItemRecyclerViewAdapter(mContext, itemObject);
        viewHolder.recyclerView.setAdapter(commentItemRecyclerViewAdapter);
    }

    @Override
    public int getItemCount() {
        if (mItemObject == null || mItemObject.kidObjects == null){
            return 0;
        }
        return mItemObject.kidObjects.size();
    }

    public static class CommentItemViewHolder extends RecyclerView.ViewHolder {

        TextView textViewNumber, textViewTitle, textViewComment;
        RecyclerView recyclerView;

        public CommentItemViewHolder(View itemView) {
            super(itemView);

            textViewNumber = (TextView) itemView.findViewById(R.id.textView_number);
            textViewTitle = (TextView) itemView.findViewById(R.id.textView_title);
            textViewComment = (TextView) itemView.findViewById(R.id.textView_comment);
            recyclerView = (RecyclerView) itemView.findViewById(R.id.recyclerView_child);
        }
    }
}
