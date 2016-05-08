package com.deanalvero.news.hacker.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dean on 07/05/16.
 */
public class CommentObject implements Parcelable{

//    "by" : "wapapaloobop",
//    "id" : 11649654,
//    "kids" : [ 11650790 ],
//    "parent" : 11649195,
//    "text" : "I haven&#x27;t taken OxyContin but withdrawal symptoms and the concept of hell are interesting (well, <i>afterwards</i> at least).<p>It&#x27;s like the mind has a thought, then checks to see how the body reacts (i.e. with joy or fear) and this in turn affects how the thought is interpreted, which then influences what is the next thought, etc.<p>The oxycontin or whatever temporarily cuts off this mind-body connection (without paralysis!) but eventually the fear comes back in a downward spiral. Even the appearance of everyday objects can then seem threatening and disturbing.",
//    "time" : 1462630426,
//    "type" : "comment"

    @SerializedName("by")
    @Expose
    String by;

    @SerializedName("id")
    @Expose
    Long id;

    @SerializedName("kids")
    @Expose
    List<Long> kids;

    @SerializedName("parent")
    @Expose
    Long parent;

    @SerializedName("text")
    @Expose
    String text;

    @SerializedName("time")
    @Expose
    Long time;

    @SerializedName("type")
    @Expose
    String type;

    public CommentObject(String by, Long id, List<Long> kids, Long parent, String text, Long time, String type){
        this.by = by;
        this.id = id;
        this.kids = kids;
        this.parent = parent;
        this.text = text;
        this.time = time;
        this.type = type;
    }


    public String getText(){ return this.text; }
    public String getBy(){ return this.by; }
    public List<Long> getKids(){ return this.kids; }

    public Long getId(){ return this.id; }
    public Long getParent(){ return this.parent; }
    public Long getTime(){ return this.time; }
    public String getType(){ return this.type; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(by);
        dest.writeLong(id);
        dest.writeList(kids);
        dest.writeLong(parent);
        dest.writeString(text);
        dest.writeLong(time);
        dest.writeString(type);
    }


    protected CommentObject(Parcel in) {
        by = in.readString();
        id = in.readLong();

        kids = new ArrayList<>();
        in.readList(kids, null);

        parent = in.readLong();
        text = in.readString();
        time = in.readLong();
        type = in.readString();
    }

    public static final Creator<CommentObject> CREATOR = new Creator<CommentObject>() {
        @Override
        public CommentObject createFromParcel(Parcel in) {
            return new CommentObject(in);
        }

        @Override
        public CommentObject[] newArray(int size) {
            return new CommentObject[size];
        }
    };
}
