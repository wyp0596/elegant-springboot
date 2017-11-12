package com.ajavac.util;

import org.junit.Assert;
import org.junit.Test;

import java.util.Set;

/**
 * Created by wyp0596 on 12/11/2017.
 */
public class GsonUtilsTest {

    @Test
    public void toSet() throws Exception {
        String json = "[12345,2345,5556,777]";
        Set<Long> longSet = GsonUtils.toLongSet(json);
        Set<Integer> intSet = GsonUtils.toIntegerSet(json);
        Assert.assertTrue(longSet.contains(12345L));
        Assert.assertTrue(intSet.contains(12345));

    }
}