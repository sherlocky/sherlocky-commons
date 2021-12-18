package com.sherlocky.common.util;

import java.util.Date;

/**
 * @author zhangxu
 * @date 2021/12/18
 * @since 0.5.0
 */
public class DateUtils {

    /**
     * 火车票最大可预订周期 30天
     */
    private static final int DEFAULT_MAX_SCHEDULED_DAYS = 30 - 1;
    /**
     * 疫情期间最多可预订周期 15天
     */
    private static final int COVID19_MAX_SCHEDULED_DAYS = 15 - 1;

    /**
     * 获取最大可预订日期
     * @return
     */
    public static Date getMaxTicketDate() {
        return getMaxTicketDate(new Date(), true);
    }

    public static Date getMaxTicketDate(Date date) {
        return getMaxTicketDate(date, true);
    }

    /**
     * 获取最大可预订日期
     * @param date
     * @param covid19Period 是否疫情期间
     * @return
     */
    public static Date getMaxTicketDate(Date date, boolean covid19Period) {
        Asserts.notNull(date, "日期不能为空");
        return org.apache.commons.lang3.time.DateUtils.addDays(date, getMaxScheduledDays(covid19Period));
    }

    public static Date getMinPurchaseDate(Date date) {
        return getMinPurchaseDate(date, true);
    }

    /**
     * 获取最早可购买日期
     * @param date
     * @return
     */
    public static Date getMinPurchaseDate(Date date, boolean covid19Period) {
        Asserts.notNull(date, "日期不能为空");
        return org.apache.commons.lang3.time.DateUtils.addDays(date, -getMaxScheduledDays(covid19Period));
    }

    private static int getMaxScheduledDays(boolean covid19Period) {
        return covid19Period ? COVID19_MAX_SCHEDULED_DAYS : DEFAULT_MAX_SCHEDULED_DAYS;
    }
}
