import org.junit.jupiter.api.Test;
import java.time.*;
import java.util.Date;
import static org.junit.jupiter.api.Assertions.*;

package com.banking.common.utils;

class DateTimeUtilsTest {

    @Test
    void testNowDate() {
        LocalDate expected = LocalDate.now(DateTimeUtils.UTC);
        LocalDate actual = DateTimeUtils.nowDate();
        assertEquals(expected, actual);
    }

    @Test
    void testNowDateTime() {
        LocalDateTime expected = LocalDateTime.now(DateTimeUtils.UTC);
        LocalDateTime actual = DateTimeUtils.nowDateTime();
        // Allowing a small difference due to execution time
        assertTrue(Duration.between(expected, actual).abs().getSeconds() < 2);
    }

    @Test
    void testNowZoned() {
        ZonedDateTime expected = ZonedDateTime.now(DateTimeUtils.UTC);
        ZonedDateTime actual = DateTimeUtils.nowZoned();
        assertEquals(expected.getZone(), actual.getZone());
        assertTrue(Duration.between(expected.toInstant(), actual.toInstant()).abs().getSeconds() < 2);
    }

    @Test
    void testFormatIsoLocalDateTime() {
        LocalDateTime ldt = LocalDateTime.of(2024, 6, 1, 12, 30, 45);
        String formatted = DateTimeUtils.formatIso(ldt);
        assertEquals("2024-06-01T12:30:45Z", formatted);
    }

    @Test
    void testFormatIsoZonedDateTime() {
        ZonedDateTime zdt = ZonedDateTime.of(2024, 6, 1, 12, 30, 45, 0, ZoneId.of("Asia/Ho_Chi_Minh"));
        String formatted = DateTimeUtils.formatIso(zdt);
        assertEquals("2024-06-01T05:30:45Z", formatted);
    }

    @Test
    void testFormatDate() {
        LocalDate date = LocalDate.of(2024, 6, 1);
        String formatted = DateTimeUtils.formatDate(date);
        assertEquals("2024-06-01", formatted);
    }

    @Test
    void testToInstant() {
        Date date = new Date(1717235445000L); // 2024-06-01T12:30:45Z
        Instant instant = DateTimeUtils.toInstant(date);
        assertEquals(1717235445L, instant.getEpochSecond());
    }

    @Test
    void testFromInstant() {
        Instant instant = Instant.ofEpochSecond(1717235445L);
        Date date = DateTimeUtils.fromInstant(instant);
        assertEquals(1717235445000L, date.getTime());
    }

    @Test
    void testToLocalDateTime() {
        Instant instant = Instant.parse("2024-06-01T12:30:45Z");
        LocalDateTime ldt = DateTimeUtils.toLocalDateTime(instant);
        assertEquals(LocalDateTime.of(2024, 6, 1, 12, 30, 45), ldt);
    }
}