package com.chenbitao.noodle_shop.domain.model;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class Holiday {

    // 可以预先定义固定节假日从配置文件或数据库读取
    private static final Set<LocalDate> FIXED_HOLIDAYS = new HashSet<>();

    static {
        // 示例：固定日期节假日
        FIXED_HOLIDAYS.add(LocalDate.of(2025, 1, 1)); // 元旦
        FIXED_HOLIDAYS.add(LocalDate.of(2025, 10, 1)); // 国庆
        // TODO 继续添加其他节日
    }

    /**
     * 判断是否是节假日
     * 
     * @param date 日期
     * @return true 是节假日，false 否
     */
    public static boolean isHoliday(LocalDate date) {
        // 周末也算节假日
        DayOfWeek day = date.getDayOfWeek();
        if (day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY) {
            return true;
        }

        // 固定节假日
        if (FIXED_HOLIDAYS.contains(date)) {
            return true;
        }

        return false;
    }
}