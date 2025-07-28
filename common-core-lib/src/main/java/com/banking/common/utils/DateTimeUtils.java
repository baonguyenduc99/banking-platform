package com.banking.common.utils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public final class DateTimeUtils {

    public static final ZoneId UTC = ZoneId.of("UTC");
    public static final DateTimeFormatter ISO_DATE = DateTimeFormatter.ISO_LOCAL_DATE;
    public static final DateTimeFormatter ISO_DATE_TIME = DateTimeFormatter.ISO_OFFSET_DATE_TIME;

    private DateTimeUtils() {}


    public static LocalDate nowDate() {
        return LocalDate.now(UTC);
    }

    public static LocalDateTime nowDateTime() {
        return LocalDateTime.now(UTC);
    }

    public static ZonedDateTime nowZoned() {
        return ZonedDateTime.now(UTC);
    }

    public static String formatIso(LocalDateTime dateTime) {
        return dateTime.atZone(UTC).format(ISO_DATE_TIME);
    }

    public static String formatIso(ZonedDateTime zonedDateTime) {
        return zonedDateTime.withZoneSameInstant(UTC).format(ISO_DATE_TIME);
    }

    public static String formatDate(LocalDate date) {
        return date.format(ISO_DATE);
    }


    public static Instant toInstant(Date date) {
        return date.toInstant();
    }

    public static Date fromInstant(Instant instant) {
        return Date.from(instant);
    }

    public static LocalDateTime toLocalDateTime(Instant instant) {
        return LocalDateTime.ofInstant(instant, UTC);
    }
}
