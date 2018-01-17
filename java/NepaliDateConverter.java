
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

public class NepaliDateConverter {

    // english months map
    private static int[] daysInMonth = new int[]{0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    private static int[] daysInMonthOfLeapYear = new int[]{0, 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    public static MyDate convertBSToAD(MyDate bsDate) {

        int nepYear = bsDate.getYear();
        int nepMonth = bsDate.getMonth();
        int nepDay = bsDate.getDay();

        Map<Integer, int[]> nepaliMap = NepaliDateMap.getMap();

        long totalNepDaysCount = 0;

        // count total days in-terms of year
        for (int i = NepaliDateMap.startingNepYear; i < nepYear; i++) {
            for (int j = 1; j <= 12; j++) {
                totalNepDaysCount += nepaliMap.get(i)[j];
            }
        }

        // count total days in-terms of month
        for (int j = NepaliDateMap.startingNepMonth; j < nepMonth; j++) {
            totalNepDaysCount += nepaliMap.get(nepYear)[j];
        }

        // count total days in-terms of date
        totalNepDaysCount += nepDay - NepaliDateMap.startingNepDay;


        // calculation of equivalent english date...
        int engYear = NepaliDateMap.startingEngYear;
        int engMonth = NepaliDateMap.startingEngMonth;
        int engDay = NepaliDateMap.startingEngDay;
        int dayOfWeek = NepaliDateMap.dayOfWeek;

        int endDayOfMonth = 0;

        while (totalNepDaysCount != 0) {
            if (isLeapYear(engYear)) {
                endDayOfMonth = daysInMonthOfLeapYear[engMonth];
            } else {
                endDayOfMonth = daysInMonth[engMonth];
            }
            engDay++;
            dayOfWeek++;
            if (engDay > endDayOfMonth) {
                engMonth++;
                engDay = 1;
                if (engMonth > 12) {
                    engYear++;
                    engMonth = 1;
                }
            }
            if (dayOfWeek > 7) {
                dayOfWeek = 1;
            }
            totalNepDaysCount--;
        }

        return new MyDate(engYear, engMonth, engDay, dayOfWeek);
    }

    private static boolean isLeapYear(int year) {
        if (year % 100 == 0) {
            return year % 400 == 0;
        } else {
            return year % 4 == 0;
        }
    }

    public static int parseNepaliNumber(String num) {
        int digit = 0;
        for (char c : num.toCharArray()) {
            digit = (digit * 10) + (c - 2406);
        }
        return digit;
    }

    public static int parseNepaliMonth(String month) {
        for (int i = 0; i < 12; i++) {
            for (String dbname : NepaliDateMap.month[i]) {
                if (dbname.equals(month)) {
                    return i + 1;
                }
            }
        }

        //if no match use minimum edit distance
        double minDistance = Double.MAX_VALUE;
        int id = -2;
        for (int i = 0; i < 12; i++) {
            double dist = 0;

            for (String dbname : NepaliDateMap.month[i]) {
                dist += StringUtils.getLevenshteinDistance(dbname, month);
            }

            dist = dist / NepaliDateMap.month[i].length;

            if (dist < minDistance) {
                minDistance = dist;
                id = i;
            }
        }
        return id + 1;
    }


   public long daysBetween(Calendar startDate, Calendar endDate) {
       Calendar date = (Calendar) startDate.clone();
       long daysBetween = 0;
       while (date.before(endDate)) {
           date.add(Calendar.DAY_OF_MONTH, 1);
           daysBetween++;
       }
       return daysBetween;
       // return (endDate.getTimeInMillis() - startDate.getTimeInMillis()) /
       // (1000 * 60 * 60 * 24);//
   }

   public MyDate convertADToBS(MyDate adDate) {

       int engYear = adDate.getYear();
       int engMonth = adDate.getMonth();
       int engDay = adDate.getDay();

       Map<Integer, int[]> nepaliMap = NepaliDateMap.getMap();

       Calendar currentEngDate = Calendar.getInstance();
       currentEngDate.set(engYear, engMonth - 1, engDay);

       Calendar baseEngDate = Calendar.getInstance();
       baseEngDate.set(startingEngYear, startingEngMonth - 1, startingEngDay);
       long totalEngDaysCount = daysBetween(baseEngDate, currentEngDate);

       // initialize required Nepali date variables with starting Nepali date
       int nepYear = NepaliDateMap.startingNepYear;
       int nepMonth = NepaliDateMap.startingNepMonth;
       int nepDay = NepaliDateMap.startingNepDay;
       int dayOfWeek = NepaliDateMap.dayOfWeek;

       // decrement totalEngDaysCount until its value becomes 0
       while (totalEngDaysCount != 0) {

           // getting the total number of days in month nepMonth in year
           int daysInIthMonth = nepaliMap.get(nepYear)[nepMonth];


           nepDay++;
           if (nepDay > daysInIthMonth) {
               nepMonth++;
               nepDay = 1;
           }
           if (nepMonth > 12) {
               nepYear++;
               nepMonth = 1;
           }

           dayOfWeek++; // count the days in terms of 7 days
           if (dayOfWeek > 7) {
               dayOfWeek = 1;
           }

           totalEngDaysCount--;
       }

       return new MyDate(nepYear, nepMonth, nepDay, dayOfWeek);
   }
   
       public static void main(String[] args) {
        //System.out.println(convertBSToAD(new MyDate(2073, 10, 24)));
//        for (int i = 0; i < 12; i++) {
//            System.out.println(StringUtils.getLevenshteinDistance(NepaliDateMap.WeekArray1[i], NepaliDateMap.WeekArray2[i]) + ":" +
//                    StringUtils.getJaroWinklerDistance(NepaliDateMap.WeekArray1[i], NepaliDateMap.WeekArray2[i]));
//        }
//        for (String c : NepaliDateMap.DigitArray) {
//            System.out.println(Character.codePointAt(c, 0) - 2406);
//            System.out.println((int) c.toCharArray()[0]);
//        }
//        System.out.println(parseNepaliNumber("१४२२२५२०७३"));
       // System.out.println(parseNepaliMonth("आश्विन"));

    }

}
