package org.rental.core.services;

import org.junit.jupiter.api.Test;
import org.rental.core.util.DateTimeUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class DateTimeServiceTest {

private DateTimeUtil dateTimeService = new DateTimeUtil();

    @Test
    void shouldDaysBetweenBeZero() {
        Date date1 = createDate("01.01.2024");
        Date date2 = createDate("01.01.2024");
        var daysBetween = dateTimeService.getDaysBetween(date1, date2);
        assertEquals(daysBetween, 0L);
    }

    @Test
    void shouldDaysBetweenBePositive() {
        Date date1 = createDate("01.01.2024");
        Date date2 = createDate("10.01.2024");
        var daysBetween = dateTimeService.getDaysBetween(date1, date2);
        assertEquals(daysBetween, 9L);
    }

    @Test
    void shouldDaysBetweenBeNegative() {
        Date date1 = createDate("10.01.2024");
        Date date2 = createDate("01.01.2024");
        var daysBetween = dateTimeService.getDaysBetween(date1, date2);
        assertEquals(daysBetween, -9L);
    }


    private Date createDate(String dateStr) {
        try {
            return new SimpleDateFormat("dd.MM.yyyy").parse(dateStr);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}