package com.sherlocky.common.util;

import org.junit.Test;

public class UrlUtilsTest {

    @Test
    public void test() {
        System.out.println(UrlUtils.getRelativePathFromUrl("https://sherlocky.github.com/2022/03/30/index.html"));
    }
}
