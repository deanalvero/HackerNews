package com.deanalvero.news.hacker.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.deanalvero.news.hacker.R;
import com.deanalvero.news.hacker.model.TopicObject;
import com.deanalvero.news.hacker.model.TopicObjectItem;
import com.deanalvero.news.hacker.util.Utility;

import java.util.List;

/**
 * Created by dean on 06/05/16.
 */
public class TopicObjectRecyclerViewAdapter extends RecyclerView.Adapter<TopicObjectRecyclerViewAdapter.NViewHolder>  {

    private static final String TAG = TopicObjectRecyclerViewAdapter.class.getSimpleName();
    private List<TopicObjectItem> topicObjectItemList;
    private TopicObjectRecyclerViewAdapterListener listener;
    private Context context;

    public interface TopicObjectRecyclerViewAdapterListener {
        void loadTopicObject(int position, TopicObjectItem item);
        void onClickTopicObjectItem(TopicObjectItem item);
    }

    public static class NViewHolder extends RecyclerView.ViewHolder{

        TextView textViewTitle, textViewNumber, textViewDescription;
        ProgressBar progressBar;
        NViewHolderListener listener;

        public NViewHolder(View itemView) {
            super(itemView);

            textViewTitle = (TextView) itemView.findViewById(R.id.textView_title);
            textViewNumber = (TextView) itemView.findViewById(R.id.textView_number);
            textViewDescription = (TextView) itemView.findViewById(R.id.textView_description);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null){
                        listener.onClickItem();
                    }
                }
            });
        }
    }

    public TopicObjectRecyclerViewAdapter(){
    }

    public void setContext(Context context){
        this.context = context;
    }

    public void setTopicObjectItemList(List<TopicObjectItem> topicObjectItemList){
        this.topicObjectItemList = topicObjectItemList;
        notifyDataSetChanged();
    }

    public void setTopicObjectRecyclerViewAdapterListener(TopicObjectRecyclerViewAdapterListener listener){
        this.listener = listener;
    }

    @Override
    public TopicObjectRecyclerViewAdapter.NViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_object_topic, parent, false);
        NViewHolder viewHolder = new NViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(NViewHolder holder, int position) {
        final TopicObjectItem item = this.topicObjectItemList.get(position);

        holder.textViewNumber.setText(String.format("%d", position+1)); //Utility.getNumberString(position));

        if (item.getTopicObject() == null){
            //  NOT YET LOADED!

            if (this.listener != null) {
                this.listener.loadTopicObject(position, item);
            }

            holder.textViewTitle.setText("");
            holder.textViewDescription.setText("");
            holder.progressBar.setVisibility(View.VISIBLE);
        } else {
            //  LOADED!

            TopicObject topicObject = item.getTopicObject();

            holder.textViewTitle.setText(topicObject.getTitle());
            holder.textViewDescription.setText(Utility.getDescriptionString(context, topicObject));
            holder.progressBar.setVisibility(View.GONE);
        }

        holder.listener = new NViewHolderListener() {
            @Override
            public void onClickItem() {
                if (TopicObjectRecyclerViewAdapter.this.listener != null){
                    TopicObjectRecyclerViewAdapter.this.listener.onClickTopicObjectItem(item);
                }
            }
        };

    }

    @Override
    public int getItemCount() {
        if (this.topicObjectItemList == null) return 0;
        return this.topicObjectItemList.size();
    }


    public interface NViewHolderListener {
        void onClickItem();
    }

}
