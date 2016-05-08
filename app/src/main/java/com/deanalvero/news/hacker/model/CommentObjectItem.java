package com.deanalvero.news.hacker.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dean on 07/05/16.
 */
public class CommentObjectItem implements Parcelable {

    private Long id;
    private CommentObject commentObject;
    private List<CommentObjectItem> mReplyList;

    public CommentObjectItem(Long id, CommentObject commentObject, List<CommentObjectItem> replyList) {
        this.id = id;
        this.commentObject = commentObject;
        this.mReplyList = replyList;
    }

    public void setCommentObject(CommentObject object){
        this.commentObject = object;
    }

    public Long getId(){ return this.id; }
    public CommentObject getCommentObject(){ return this.commentObject; }

    public void setReplyList(List<CommentObjectItem> replyList){
        this.mReplyList = replyList;
    }

    public List<CommentObjectItem> getReplyList(){
        return this.mReplyList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeParcelable(commentObject, flags);
        dest.writeList(mReplyList);

    }


    protected CommentObjectItem(Parcel in) {
        id = in.readLong();
        commentObject = in.readParcelable(CommentObject.class.getClassLoader());

        mReplyList = new ArrayList<>();
        in.readList(mReplyList, null);
    }

    public static final Creator<CommentObjectItem> CREATOR = new Creator<CommentObjectItem>() {
        @Override
        public CommentObjectItem createFromParcel(Parcel in) {
            return new CommentObjectItem(in);
        }

        @Override
        public CommentObjectItem[] newArray(int size) {
            return new CommentObjectItem[size];
        }
    };
}
