package com.sherlocky.common.util;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ArrayUtils;
import sun.security.util.ArrayUtil;

import java.io.File;

/**
 * 文件相关工具类
 * @date 2022/5/21
 */
public final class FileUtis {

    private FileUtis() {}

    /**
     * 文件是否为空<br>
     * 目录：里面没有文件时为空
     * 文件：文件不存在或者文件大小为0时为空
     *
     * @param file 文件
     * @return 是否为空
     */
    public static boolean isEmpty(File file) {
        if (null == file) {
            return true;
        }

        if (!file.exists()) {
            return true;
        }

        if (file.isDirectory()) {
            String[] subFiles = file.list();
            return ArrayUtils.isEmpty(subFiles);
        }

        if (file.isFile()) {
            return file.length() <= 0;
        }

        return false;
    }

}
