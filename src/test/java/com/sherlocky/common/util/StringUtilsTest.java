package com.sherlocky.common.util;

import org.junit.Test;

/**
 * @date 2022/7/31
 */
public class StringUtilsTest {

    @Test
    public void test() {
        String template = "123{}abc{}+-={}___";
        System.out.println(StringUtils.format(template));
        System.out.println(StringUtils.format(template, "口"));
        System.out.println(StringUtils.format(template, "口", "口"));
        System.out.println(StringUtils.format(template, "口", "口", "口"));
        System.out.println(StringUtils.format(template, "口", "口", "口", "口"));
    }

}
