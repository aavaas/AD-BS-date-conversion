package utils.dateconverter;

import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Aavaas on 1/29/17.
 */
public class MyDate {
    private int year;
    private int month;
    private int dayOfMonth;
    private int dayOfWeek;

    public MyDate(int year, int month, int dayOfMonth) {
        this.year = year;
        this.month = month;
        this.dayOfMonth = dayOfMonth;
    }

    public MyDate(int year, int month, int dayOfMonth, int dayOfWeek) {
        this.year = year;
        this.month = month;
        this.dayOfMonth = dayOfMonth;
        this.dayOfWeek = dayOfWeek;
    }

    @Override
    public String toString() {
        return year + "/" + month + "/" + dayOfMonth;
    }

    public Date toDate() {
        return new GregorianCalendar(year, month - 1, dayOfMonth).getTime(); //change form 1 indexed to 0 indexed
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return dayOfMonth;
    }

    public int getDayOfWeek() {
        return dayOfWeek;
    }

}
