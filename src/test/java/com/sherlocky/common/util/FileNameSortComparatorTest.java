package com.sherlocky.common.util;

import com.sherlocky.common.comparator.FileNameSortComparator;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class FileNameSortComparatorTest {
    private List<String> fileNames = new ArrayList<>();

    @Test
    public void test() {
        fileNames.sort(FileNameSortComparator.get());
        for (String s : fileNames) {
            System.out.println(s);
        }
    }

    @Before
    public void init() {
        fileNames.add("002");
        fileNames.add("002c");
        fileNames.add("003a");
        fileNames.add("1");
        fileNames.add("10");
        fileNames.add("100");
        fileNames.add("101");
        fileNames.add("11");
        fileNames.add("11a");
        fileNames.add("1a");
        fileNames.add("2");
        fileNames.add("2a");
        fileNames.add("2c");
        fileNames.add("3a");
        fileNames.add("a");
        fileNames.add("a000001");
        fileNames.add("a001");
        fileNames.add("a05");
        fileNames.add("a1");
        fileNames.add("a10001");
        fileNames.add("a1001");
        fileNames.add("a11");
        fileNames.add("a2");
        fileNames.add("aa");
        fileNames.add("ac");
        fileNames.add("b");
        fileNames.add("bd");
        fileNames.add("第00014");
        fileNames.add("第0001你002");
        fileNames.add("第0001我001");
        fileNames.add("第0001我002");
        fileNames.add("第0001我04");
        fileNames.add("第0001我10");
        fileNames.add("第0001我3");
        fileNames.add("第0001我5");
        fileNames.add("第0004");
        fileNames.add("第002");
        fileNames.add("第03");
        fileNames.add("第030");
        fileNames.add("第10");
        fileNames.add("第1088");
        fileNames.add("第20");
        fileNames.add("第3");
        fileNames.add("第三");
        fileNames.add("第二");
        fileNames.add("第四");
    }
}
