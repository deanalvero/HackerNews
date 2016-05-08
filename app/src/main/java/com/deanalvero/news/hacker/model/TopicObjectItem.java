package com.deanalvero.news.hacker.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by dean on 06/05/16.
 */
public class TopicObjectItem implements Parcelable{

    private Long id;
    private TopicObject topicObject;

    public TopicObjectItem(Long id, TopicObject topicObject){
        this.id = id;
        this.topicObject = topicObject;
    }

    public Long getId() { return this.id; }
    public TopicObject getTopicObject() { return this.topicObject; }
    public void setTopicObject(TopicObject topicObject){ this.topicObject = topicObject; }


    protected TopicObjectItem(Parcel in) {
        id = in.readLong();
        topicObject = in.readParcelable(TopicObject.class.getClassLoader());
    }

    public static final Creator<TopicObjectItem> CREATOR = new Creator<TopicObjectItem>() {
        @Override
        public TopicObjectItem createFromParcel(Parcel in) {
            return new TopicObjectItem(in);
        }

        @Override
        public TopicObjectItem[] newArray(int size) {
            return new TopicObjectItem[size];
        }
    };


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeParcelable(topicObject, flags);
    }



}
