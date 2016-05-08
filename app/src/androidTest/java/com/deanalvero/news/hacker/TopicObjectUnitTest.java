package com.deanalvero.news.hacker;

import android.os.Parcel;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.SmallTest;

import com.deanalvero.news.hacker.model.TopicObject;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;

/**
 * Created by dean on 07/05/16.
 */
@RunWith(AndroidJUnit4.class)
@SmallTest
public class TopicObjectUnitTest {

    private TopicObject mTopicObject;

    @Before
    public void createTopicObject() {
        mTopicObject = new TopicObject(
                "Dean",
                2,
                34L,
                Arrays.asList(2L, 4L, 6L),
                12345678L,
                "LowBott Games",
                "story",
                "lowbottgames.com",
                51
        );
    }

    @Test
    public void topicObject_parcelable() {
        Parcel parcel = Parcel.obtain();
        assertNotNull(parcel);

        mTopicObject.writeToParcel(parcel, 0);
        parcel.setDataPosition(0);

        TopicObject parcelTopicObject = TopicObject.CREATOR.createFromParcel(parcel);

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
