package com.deanalvero.news.hacker;

import android.os.Parcel;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.SmallTest;

import com.deanalvero.news.hacker.model.CommentObject;
import com.deanalvero.news.hacker.model.CommentObjectItem;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by dean on 07/05/16.
 */
@RunWith(AndroidJUnit4.class)
@SmallTest
public class CommentObjectItemUnitTest {

    private CommentObjectItem mCommentObjectItem;

    @Before
    public void createCommentObjectItem() {
        CommentObjectItem commentObjectItem1 = new CommentObjectItem(110L, new CommentObject("Dean", 2L, Arrays.asList(1L, 2L), 3L, "Test Comment", 12345678L, "comment"), null);


        mCommentObjectItem = new CommentObjectItem(110L, new CommentObject("Dean", 2L, Arrays.asList(1L, 2L), 3L, "Test Comment", 12345678L, "comment"), null);
    }

    @Test
    public void commentObjectItem_parcelable() {
        Parcel parcel = Parcel.obtain();
        assertNotNull(parcel);

        mCommentObjectItem.writeToParcel(parcel, 0);
        parcel.setDataPosition(0);

        CommentObjectItem parcelCommentObjectItem = CommentObjectItem.CREATOR.createFromParcel(parcel);

        CommentObject parcelCommentObject = parcelCommentObjectItem.getCommentObject();

        assertEquals(parcelCommentObjectItem.getId(), new Long(110L));
        assertEquals(parcelCommentObject.getBy(), "Dean");
        assertEquals(parcelCommentObject.getId(), new Long(2));
        assertEquals(parcelCommentObject.getKids(), Arrays.asList(1L, 2L));
        assertEquals(parcelCommentObject.getParent(), new Long(3));
        assertEquals(parcelCommentObject.getText(), "Test Comment");
        assertEquals(parcelCommentObject.getTime(), new Long(12345678L));
        assertEquals(parcelCommentObject.getType(), "comment");
    }
}
