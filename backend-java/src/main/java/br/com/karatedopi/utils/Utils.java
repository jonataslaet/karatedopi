package br.com.karatedopi.utils;

import java.time.LocalDate;
import java.time.Period;

public class Utils {

    public static int getAgeByBirthday(LocalDate birthday) {
        if (birthday == null) {
            throw new IllegalArgumentException("Birthday cannot be null");
        }
        Period period = Period.between(birthday, LocalDate.now());
        return period.getYears();
    }
}
