package com.sherlocky.common.util;

import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.junit.Assert;
import org.junit.Test;

import java.text.ParseException;
import java.util.Date;

import static org.apache.commons.lang3.time.DateFormatUtils.ISO_8601_EXTENDED_DATETIME_FORMAT;

/**
 * @author: zhangcx
 * @date: 2021/12/18
 * @since 0.5.0
 */
public class DateUtilsTest {

    @Test
    public void test() throws Exception {
        Date fromDate = org.apache.commons.lang3.time.DateUtils.parseDate("2023-01-07 14:00:00.000", "yyyy-MM-dd HH:mm:ss.SSS");
        long startTime = fromDate.getTime();
        long endTime = new Date().getTime();
        int weeks = (int) ((endTime - startTime) / (7 * 24 * 3600 * 1000));

        String format = "GIT_COMMITTER_DATE=\"%s\" git commit --amend --date=\"%s\"";

        Date pos = new Date(startTime);
        System.out.println(weeks);
        for (int i = 1; i <= weeks; i++) {
            pos = org.apache.commons.lang3.time.DateUtils.addWeeks(fromDate, i);
            boolean isNeed = RandomUtils.nextInt() % 2 == 0;
            if (!isNeed) {
                continue;
            }
            int hour = 8 + RandomUtils.nextInt(1, 16);
            int minute = RandomUtils.nextInt(0, 60);
            int sec = RandomUtils.nextInt(0, 60);
            int millSec = RandomUtils.nextInt(0, 1000);
            pos = org.apache.commons.lang3.time.DateUtils.setHours(pos, hour);
            pos = org.apache.commons.lang3.time.DateUtils.setMinutes(pos, minute);
            pos = org.apache.commons.lang3.time.DateUtils.setSeconds(pos, sec);
            pos = org.apache.commons.lang3.time.DateUtils.setMilliseconds(pos, millSec);
            String dateStr = DateFormatUtils.format(pos, ISO_8601_EXTENDED_DATETIME_FORMAT.getPattern());
            System.out.println(String.format(format, dateStr, dateStr));
        }
    }

    @Test
    public void testMaxTicketDate() {
        Assert.assertEquals(format(DateUtils.getMaxTicketDate(getDate())), "2022-01-01");
    }

    @Test
    public void testMinPurchaseDate() {
        Assert.assertEquals(format(DateUtils.getMinPurchaseDate(getDate())), "2021-12-04");
    }

    private Date getDate() {
        try {
            return DateFormatUtils.ISO_8601_EXTENDED_DATE_FORMAT.parse("2021-12-18");
        } catch (ParseException e) {
            return new Date();
        }
    }

    private String format(Date date) {
        return DateFormatUtils.ISO_8601_EXTENDED_DATE_FORMAT.format(date);
    }
}
