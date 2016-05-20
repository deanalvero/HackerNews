package com.deanalvero.news.hacker.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by dean on 18/05/16.
 */
public class ItemObject {
    public Long id;
    public boolean deleted;
    public String type;
    public String by;
    public Long time;
    public String text;
    public boolean dead;
    public Long parent;
    public List<Long> kids;
    public String url;
    public int score;
    public String title;
    public List<Long> parts;
    public int descendants;

    public List<ItemObject> kidObjects;
}


/**
 @SerializedName("id")
 @Expose
 Long id;

 @SerializedName("deleted")
 @Expose
 Boolean deleted;

 @SerializedName("type")
 @Expose
 String type;

 @SerializedName("by")
 @Expose
 String by;

 @SerializedName("time")
 @Expose
 Long time;

 @SerializedName("text")
 @Expose
 String text;

 @SerializedName("dead")
 @Expose
 Boolean dead;

 @SerializedName("parent")
 @Expose
 Long parent;

 @SerializedName("kids")
 @Expose
 List<Long> kids;

 @SerializedName("url")
 @Expose
 String url;

 @SerializedName("score")
 @Expose
 int score;

 @SerializedName("title")
 @Expose
 String title;

 @SerializedName("parts")
 @Expose
 List<Long> parts;

 @SerializedName("descendants")
 @Expose
 int descendants;

 **/