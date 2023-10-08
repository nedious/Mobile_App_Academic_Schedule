package com.example.termv30.helper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DateHelper {

    public static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("M/d/yyyy", Locale.US);

    public static LocalDate parseDate(String date) {
        String pattern = "M/d/yyyy";
        DateTimeFormatter sdf = DateTimeFormatter.ofPattern(pattern);
       LocalDate dateReturn = LocalDate.parse(date, sdf);
        return dateReturn;
    }

}
