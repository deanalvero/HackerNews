package com.deanalvero.news.hacker;

import android.os.Parcel;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.SmallTest;

import com.deanalvero.news.hacker.model.TopicObject;
import com.deanalvero.news.hacker.model.TopicObjectItem;

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
public class TopicObjectItemUnitTest {

    private TopicObjectItem mTopicObjectItem;

    @Before
    public void createTopicObjectItem() {
        mTopicObjectItem = new TopicObjectItem(21L, new TopicObject(
                "Dean",
                2,
                34L,
                Arrays.asList(2L, 4L, 6L),
                12345678L,
                "LowBott Games",
                "story",
                "lowbottgames.com",
                51
        ));
    }

    @Test
    public void topicObjectItem_parcelable() {
        Parcel parcel = Parcel.obtain();
        assertNotNull(parcel);

        mTopicObjectItem.writeToParcel(parcel, 0);
        parcel.setDataPosition(0);

        TopicObjectItem parcelTopicObjectItem = TopicObjectItem.CREATOR.createFromParcel(parcel);

        TopicObject parcelTopicObject = parcelTopicObjectItem.getTopicObject();

        assertEquals(parcelTopicObjectItem.getId(), new Long(21L));

        assertEquals(parcelTopicObject.getBy(), "Dean");
        assertEquals(parcelTopicObject.getDescendants(), 2);
        assertEquals(parcelTopicObject.getId(), new Long(34L));
        assertEquals(parcelTopicObject.getKids(), Arrays.asList(2L, 4L, 6L));
        assertEquals(parcelTopicObject.getTime(), new Long(12345678L));
        assertEquals(parcelTopicObject.getTitle(), "LowBott Games");
        assertEquals(parcelTopicObject.getType(), "story");
        assertEquals(parcelTopicObject.getUrl(), "lowbottgames.com");
        assertEquals(parcelTopicObject.getScore(), 51);

    }

}
