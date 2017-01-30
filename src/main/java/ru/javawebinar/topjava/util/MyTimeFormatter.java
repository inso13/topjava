package ru.javawebinar.topjava.util;

import org.springframework.format.Formatter;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Locale;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME;
import static java.time.format.DateTimeFormatter.ISO_LOCAL_TIME;

/**
 * Created by Inso on 28.01.2017.
 */
public class MyTimeFormatter implements Formatter<LocalTime> {

    @Override
    public String print(LocalTime localTime, Locale locale) {
        return ISO_LOCAL_TIME.format(localTime);
    }

    @Override
    public LocalTime parse(String s, Locale locale) throws ParseException {
        return DateTimeUtil.parseLocalTime(s);
    }
}
