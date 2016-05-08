package com.deanalvero.news.hacker;

import android.os.Parcel;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.SmallTest;

import com.deanalvero.news.hacker.model.CommentObject;

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
public class CommentObjectUnitTest {

    private CommentObject mCommentObject;

    @Before
    public void createCommentObject() {
        mCommentObject = new CommentObject("Dean", 2L, Arrays.asList(1L, 2L), 3L, "Test Comment", 12345678L, "comment");
    }

    @Test
    public void commentObject_parcelable() {
        Parcel parcel = Parcel.obtain();
        assertNotNull(parcel);

        mCommentObject.writeToParcel(parcel, 0);
        parcel.setDataPosition(0);

        CommentObject parcelCommentObject = CommentObject.CREATOR.createFromParcel(parcel);
        assertEquals(parcelCommentObject.getBy(), "Dean");
        assertEquals(parcelCommentObject.getId(), new Long(2));
        assertEquals(parcelCommentObject.getKids(), Arrays.asList(1L, 2L));
        assertEquals(parcelCommentObject.getParent(), new Long(3));
        assertEquals(parcelCommentObject.getText(), "Test Comment");
        assertEquals(parcelCommentObject.getTime(), new Long(12345678L));
        assertEquals(parcelCommentObject.getType(), "comment");

    }

}
