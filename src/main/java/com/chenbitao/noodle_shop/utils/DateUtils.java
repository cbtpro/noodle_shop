package com.chenbitao.noodle_shop.utils;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class DateUtils {

    /**
     * 判断给定日期是否为周末（周六或周日）
     * 
     * @param date 日期
     * @return 如果是周末返回 true，否则返回 false
     */
    public static boolean isWeekend(LocalDate date) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        return dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY;
    }

    /**
     * 获取当前日期的月份与日
     * 
     * @param date 日期
     * @return 返回 int 数组 [month, day]
     */
    public static int[] getMonthAndDay(LocalDate date) {
        return new int[] { date.getMonthValue(), date.getDayOfMonth() };
    }
}