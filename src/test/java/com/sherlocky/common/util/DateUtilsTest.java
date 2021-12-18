package com.sherlocky.common.util;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.junit.Assert;
import org.junit.Test;

import java.text.ParseException;
import java.util.Date;

/**
 * @author: zhangcx
 * @date: 2021/12/18
 * @since 0.5.0
 */
public class DateUtilsTest {
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
