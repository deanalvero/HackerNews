package com.deanalvero.news.hacker.util;

import android.content.Context;

import com.deanalvero.news.hacker.R;
import com.deanalvero.news.hacker.model.CommentObject;
import com.deanalvero.news.hacker.model.TopicObject;

import java.util.Calendar;
import java.util.List;

/**
 * Created by dean on 06/05/16.
 */
public class Utility {
    private static final int SPACES_NUMBER_STRING = 3;

    public static String getNumberString(int position){
        String positionString = String.valueOf(position);

        int length = positionString.length();
        if (length < SPACES_NUMBER_STRING){
            StringBuilder stringSpace = new StringBuilder();
            for (int i = 0; i < (SPACES_NUMBER_STRING - length); i++){
                stringSpace.append(" ");
            }
            stringSpace.append(positionString);
            return stringSpace.toString();
        }
        return positionString;
    }

    public static String getCommentAuthorString(Context context, CommentObject commentObject){

        String by = commentObject.getBy();
        List<Long> longList = commentObject.getKids();
        int replyCount = (longList == null) ? 0 : longList.size();


        if (context == null){
            return String.format("%s (%d %s)",
                    (by == null) ? "Unknown Poster" : by,
                    replyCount,
                    (replyCount != 1) ? "replies" : "reply"
            );
        }

        return context.getString(R.string.author_comment_format,
                (by == null) ? context.getString(R.string.unknown_poster) : by,
                replyCount,
                (replyCount != 1) ? context.getString(R.string.string_replies) : context.getString(R.string.string_reply)
        );
    }


    public static String getDescriptionString(Context context, TopicObject topicObject){
        int scoreCount = topicObject.getScore();
        int commentCount = topicObject.getDescendants();
        String by = topicObject.getBy();
//        int hour = Calendar.getInstance().getTimeInMillis();

        if (context == null){
            //  In case you forgot to setContext for the adapter!

            return String.format("%d %s by %s %d %s ago | %d %s",
                    scoreCount,
                    scoreCount != 1 ? "points" : "point",
                    by,
//                    "",
//                    "",
                    commentCount,
                    commentCount != 1 ? "comments" : "comment"
                    );
        }

        return context.getString(R.string.description_topic_format,
                scoreCount,
                scoreCount != 1 ? context.getString(R.string.string_points) : context.getString(R.string.string_point),
                by,
//                0,
//                "",
                commentCount,
                commentCount != 0 ? context.getString(R.string.string_comments) : context.getString(R.string.string_comment)
        );
    }

}
