package com.deanalvero.news.hacker;

import com.deanalvero.news.hacker.model.ItemObject;
import com.deanalvero.news.hacker.util.Utility;

import org.junit.Test;


import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class HNUtilityUnitTest {

    //  No Context
    @Test
    public void Utility_getDescriptionString() throws Exception {
        assertEquals(Utility.getDescriptionString(null, null), "");

        ItemObject itemObject1 = new ItemObject();
        itemObject1.by = "Dean";
        itemObject1.score = 3;
        itemObject1.descendants = 21;
        assertEquals(Utility.getDescriptionString(null, itemObject1), "3 points by Dean | 21 comments");

        ItemObject itemObject2 = new ItemObject();
        itemObject2.by = "X";
        itemObject2.score = 0;
        itemObject2.descendants = 1;
        assertEquals(Utility.getDescriptionString(null, itemObject2), "0 points by X | 1 comment");
    }

    //  No Context
    @Test
    public void Utility_getCommentAuthorString() throws Exception {
        assertEquals(Utility.getCommentAuthorString(null, null), "");

        ItemObject itemObject1 = new ItemObject();
        itemObject1.by = "Dean";
        itemObject1.kids = Arrays.asList(1L, 2L, 3L);
        assertEquals(Utility.getCommentAuthorString(null, itemObject1), "Dean (3 replies)");

        ItemObject itemObject2 = new ItemObject();
        itemObject2.by = "X";
        itemObject2.kids = Arrays.asList(1L);
        assertEquals(Utility.getCommentAuthorString(null, itemObject2), "X (1 reply)");
    }


    @Test
    public void Utility_safeString() throws Exception {
        assertEquals(Utility.safeString(null), "");
        assertEquals(Utility.safeString("ABC"), "ABC");
    }
}