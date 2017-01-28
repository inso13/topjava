package ru.javawebinar.topjava.util;

import org.springframework.format.Formatter;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.StringJoiner;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME;

/**
 * Created by Inso on 28.01.2017.
 */
public class MyFormatter implements Formatter<LocalDateTime> {

   /* @Override
    public LocalDateTime parse(String s, Locale locale) throws ParseException {
             String[] str = s.split("T");
            if (str.length<2) throw new ParseException("", 1);
            String date = str[0];
            String time = str[1];
            String[] dates = date.split("-");
            if (dates.length<3)  throw new ParseException("", 1);
            int year = Integer.parseInt(dates[0]);
            int month = Integer.parseInt(dates[1]);
            int day = Integer.parseInt(dates[2]);
            String[] times = time.split(":");
        if (dates.length<2)  throw new ParseException("", 1);
            int hour = Integer.parseInt(times[0]);
            int minute = Integer.parseInt(times[1]);
            int second = Integer.parseInt(times[2]);
        return LocalDateTime.of(year, month, day, hour, minute, second);
    }*/

    @Override
    public LocalDateTime parse(String s, Locale locale) throws ParseException {
        return LocalDateTime.parse(s);
    }

    /*@Override
    public String print(LocalDateTime dateTime, Locale locale) {
        String year = String.valueOf(dateTime.getYear());
        String month = String.valueOf(dateTime.getMonth());
        String day = String.valueOf(dateTime.getDayOfMonth());
        String hour = String.valueOf(dateTime.getHour());
        String minute = String.valueOf(dateTime.getMinute());
        String second = String.valueOf(dateTime.getSecond());
        return year+"-"+month+"-"+day+"T"+hour+":"+minute+":"+second;
    }*/

    @Override
    public String print(LocalDateTime dateTime, Locale locale) {
        return ISO_LOCAL_DATE_TIME.format(dateTime);
    }
}
