package br.com.karatedopi.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.temporal.ChronoUnit;

public class Utils {

    public static int getAgeByBirthday(LocalDate birthday) {
        if (birthday == null) {
            throw new IllegalArgumentException("A data de nascimento não pode ser nula");
        }
        Period period = Period.between(birthday, LocalDate.now());
        return period.getYears();
    }

    public static long getDifferenceInHours(LocalDateTime dateTime1, LocalDateTime dateTime2) {
        long difference = ChronoUnit.HOURS.between(dateTime1, dateTime2);
        return difference < 0 ? -1 * difference : difference;
    }
}
