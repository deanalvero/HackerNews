package com.deanalvero.news.hacker.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dean on 06/05/16.
 */
public class TopicObject implements Parcelable {

//    {
//        "by" : "AffableSpatula",
//            "descendants" : 71,
//            "id" : 11636847,
//            "kids" : [ 11637641, 11637209, 11637402, 11637608, 11637128, 11637596, 11637067, 11637601, 11637947, 11637576, 11637700, 11637927, 11637481, 11637081, 11637710, 11637471, 11637942, 11637612, 11637073, 11637420, 11637472, 11637163, 11637270 ],
//        "score" : 139,
//            "time" : 1462461499,
//            "title" : "Introducing TAuth: Why OAuth 2.0 is bad for banking APIs and how we're fixing it",
//            "type" : "story",
//            "url" : "https://blog.teller.io/2016/04/26/tauth.html"
//    }

    @SerializedName("by")
    @Expose
    String by;

    @SerializedName("descendants")
    @Expose
    int descendants;

    @SerializedName("id")
    @Expose
    Long id;

    @SerializedName("kids")
    @Expose
    List<Long> kids;

    @SerializedName("time")
    @Expose
    Long time;

    @SerializedName("title")
    @Expose
    String title;

    @SerializedName("type")
    @Expose
    String type;

    @SerializedName("url")
    @Expose
    String url;

    @SerializedName("score")
    @Expose
    int score;

    public TopicObject(String by, int descendants, Long id, List<Long> kids, Long time, String title, String type, String url, int score){
        this.by = by;
        this.descendants = descendants;
        this.id = id;
        this.kids = kids;
        this.time = time;
        this.title = title;
        this.type = type;
        this.url = url;
        this.score = score;
    }

    public Long getId(){ return this.id; }
    public String getBy(){ return this.by; }
    public Long getTime(){ return this.time; }
    public String getTitle(){ return this.title; }
    public String getType(){ return this.type; }
    public String getUrl(){ return this.url; }
    public int getScore(){ return this.score; }
    public int getDescendants(){ return this.descendants; }
    public List<Long> getKids(){ return this.kids; }


    @Override
    public String toString() {
        return String.format("{by: %s, title: %s, url: %s}", by, title, url);
    }





    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(by);
        dest.writeInt(descendants);
        dest.writeLong(id);
        dest.writeList(kids);
        dest.writeLong(time);
        dest.writeString(title);
        dest.writeString(type);
        dest.writeString(url);
        dest.writeInt(score);
    }

    protected TopicObject(Parcel in) {
        by = in.readString();
        descendants = in.readInt();
        id = in.readLong();

        kids = new ArrayList<>();
        in.readList(kids, null);

        time = in.readLong();
        title = in.readString();
        type = in.readString();
        url = in.readString();
        score = in.readInt();
    }

    public static final Creator<TopicObject> CREATOR = new Creator<TopicObject>() {
        @Override
        public TopicObject createFromParcel(Parcel in) {
            return new TopicObject(in);
        }

        @Override
        public TopicObject[] newArray(int size) {
            return new TopicObject[size];
        }
    };


}
