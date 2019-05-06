package com.deanalvero.news.hacker.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.deanalvero.news.hacker.R;
import com.deanalvero.news.hacker.model.ItemObject;
import com.deanalvero.news.hacker.util.Utility;

import java.util.List;

/**
 * Created by dean on 18/05/16.
 */
public class TopicItemRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<ItemObject> mItemObjectList;
    private Context mContext;
    private TopicItemRecyclerViewAdapterListener mListener;

    public TopicItemRecyclerViewAdapter(Context context, List<ItemObject> itemObjectList, TopicItemRecyclerViewAdapterListener listener){
        this.mContext = context;
        this.mItemObjectList = itemObjectList;
        this.mListener = listener;
    }

    public interface TopicItemRecyclerViewAdapterListener {
        void onClickTopicItem(ItemObject itemObject, int position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_topic, parent, false);
        return new TopicItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        TopicItemViewHolder topicItemViewHolder = (TopicItemViewHolder) holder;

        final ItemObject topicObject = this.mItemObjectList.get(position);

        topicItemViewHolder.textViewNumber.setText(String.format("%d", position+1));
        topicItemViewHolder.textViewTitle.setText(topicObject.title);
        topicItemViewHolder.textViewDescription.setText(Utility.getDescriptionString(mContext, topicObject));

        topicItemViewHolder.listener = new TopicItemViewHolder.TopicItemViewHolderListener() {
            @Override
            public void onClickItem() {
                if (TopicItemRecyclerViewAdapter.this.mListener != null){
                    TopicItemRecyclerViewAdapter.this.mListener.onClickTopicItem(topicObject, position);
                }
            }
        };
    }

    @Override
    public void onViewRecycled(RecyclerView.ViewHolder holder) {
        super.onViewRecycled(holder);

        TopicItemViewHolder topicItemViewHolder = (TopicItemViewHolder) holder;
        topicItemViewHolder.textViewNumber.setText("");
        topicItemViewHolder.textViewTitle.setText("");
        topicItemViewHolder.textViewDescription.setText("");
    }

    @Override
    public int getItemCount() {
        if (this.mItemObjectList == null){
            return 0;
        }
        return this.mItemObjectList.size();
    }

    public static class TopicItemViewHolder extends RecyclerView.ViewHolder {

        TextView textViewTitle, textViewNumber, textViewDescription;
        TopicItemViewHolderListener listener;

        public TopicItemViewHolder(View itemView) {
            super(itemView);

            textViewTitle = (TextView) itemView.findViewById(R.id.textView_title);
            textViewNumber = (TextView) itemView.findViewById(R.id.textView_number);
            textViewDescription = (TextView) itemView.findViewById(R.id.textView_description);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null){
                        listener.onClickItem();
                    }
                }
            });
        }

        public interface TopicItemViewHolderListener {
            void onClickItem();
        }
    }
}
