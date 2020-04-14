package com.sherlocky.common;

import com.alibaba.fastjson.JSON;

/**
 * Base 单元测试类，便于单元测试时打印一些参数
 * @author: zhangcx
 * @date: 2020/4/14 9:30
 * @since:
 */
public class BaseTest {
    /**
     * 打印对象为 json 字符串
     * @param obj
     */
    public void printJson(Object obj) {
        printJson(obj, false);
    }

    /**
     * 打印对象为 json 字符，美化格式
     * @param obj
     */
    public void beautiful(Object obj) {
        printJson(obj, true);
    }

    private void printJson(Object obj, boolean prettyFormat) {
        if (obj == null) {
            println("null");
            return;
        }
        if (obj instanceof String) {
            if (!prettyFormat) {
                println(obj);
                return;
            }
            println(JSON.toJSONString(JSON.parseObject((String) obj), true));
            return;
        }
        println(JSON.toJSONString(obj, prettyFormat));
    }

    /**
     * 正常打印对象(同 System.out.println)
     * @param obj
     */
    public void println(Object obj) {
        System.out.println(obj);
    }
}
