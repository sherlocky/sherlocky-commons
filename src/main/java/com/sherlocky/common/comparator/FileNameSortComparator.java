package com.sherlocky.common.comparator;

import java.util.Comparator;
import java.util.Objects;

/**
 * 文件名称排序器
 * @date 2022/4/17
 */
public class FileNameSortComparator implements Comparator<String> {

    public static Comparator<String> get() {
        return new FileNameSortComparator();
    }

    @Override
    public int compare(String x, String y) {
        char[] arr1 = x.toCharArray();
        char[] arr2 = y.toCharArray();
        int i = 0, j = 0;
        //逐字符处理
        while (i < arr1.length && j < arr2.length) {
            if (Character.isDigit(arr1[i]) && Character.isDigit(arr2[j])) {
                StringBuilder s1 = new StringBuilder();
                StringBuilder s2 = new StringBuilder();
                while (i < arr1.length && Character.isDigit(arr1[i])) {
                    s1.append(arr1[i]);
                    i++;
                }
                while (j < arr2.length && Character.isDigit(arr2[j])) {
                    s2.append(arr2[j]);
                    j++;
                }
                //TODO 有点bug，数字位可能会超长
                if (Integer.parseInt(s1.toString()) > Integer.parseInt(s2.toString())) {
                    return 1;
                }
                if (Integer.parseInt(s1.toString()) < Integer.parseInt(s2.toString())) {
                    return -1;
                }
            } else {
                if (arr1[i] > arr2[j]) {
                    return 1;
                }
                if (arr1[i] < arr2[j]) {
                    return -1;
                }
                i++;
                j++;
            }
        }
        if (arr1.length == arr2.length) {
            return 0;
        } else {
            return arr1.length > arr2.length ? 1 : -1;
        }

    }
}
