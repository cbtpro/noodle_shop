package com.chenbitao.noodle_shop.noodle_shop;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.chenbitao.noodle_shop.domain.model.Holiday;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class HolidayTest {

    @Test
    void testWeekendIsHoliday() {
        // 周六
        LocalDate saturday = LocalDate.of(2025, 8, 16);
        assertTrue(Holiday.isHoliday(saturday));

        // 周日
        LocalDate sunday = LocalDate.of(2025, 8, 17);
        assertTrue(Holiday.isHoliday(sunday));
    }

    @Test
    void testFixedHoliday() {
        // 元旦
        LocalDate newYear = LocalDate.of(2025, 1, 1);
        assertTrue(Holiday.isHoliday(newYear));

        // 国庆
        LocalDate nationalDay = LocalDate.of(2025, 10, 1);
        assertTrue(Holiday.isHoliday(nationalDay));
    }

    @Test
    void testWorkdayIsNotHoliday() {
        // 普通工作日
        LocalDate weekday = LocalDate.of(2025, 8, 18); // 周一
        assertFalse(Holiday.isHoliday(weekday));
    }
}