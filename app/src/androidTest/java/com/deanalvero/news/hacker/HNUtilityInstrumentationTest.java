package com.deanalvero.news.hacker;

import android.app.Activity;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.deanalvero.news.hacker.model.ItemObject;
import com.deanalvero.news.hacker.util.Utility;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

/**
 * Created by dean on 19/05/16.
 */
@RunWith(AndroidJUnit4.class)
public class HNUtilityInstrumentationTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(
            MainActivity.class);

    //  With Context
    @Test
    public void Utility_getDescriptionString(){
        Activity mActivity = mActivityRule.getActivity();

        assertEquals(Utility.getDescriptionString(mActivity, null), "");

        ItemObject itemObject1 = new ItemObject();
        itemObject1.by = "Dean";
        itemObject1.score = 3;
        itemObject1.descendants = 21;
        assertEquals(Utility.getDescriptionString(mActivity, itemObject1), "3 points by Dean | 21 comments");

        ItemObject itemObject2 = new ItemObject();
        itemObject2.by = "X";
        itemObject2.score = 0;
        itemObject2.descendants = 1;
        assertEquals(Utility.getDescriptionString(mActivity, itemObject2), "0 points by X | 1 comment");
    }

    //  With Context
    @Test
    public void Utility_getCommentAuthorString() throws Exception {
        Activity mActivity = mActivityRule.getActivity();

        assertEquals(Utility.getCommentAuthorString(mActivity, null), "");

        ItemObject itemObject1 = new ItemObject();
        itemObject1.by = "Dean";
        itemObject1.kids = Arrays.asList(1L, 2L, 3L);
        assertEquals(Utility.getCommentAuthorString(mActivity, itemObject1), "Dean (3 replies)");

        ItemObject itemObject2 = new ItemObject();
        itemObject2.by = "X";
        itemObject2.kids = Arrays.asList(1L);
        assertEquals(Utility.getCommentAuthorString(mActivity, itemObject2), "X (1 reply)");
    }
}
