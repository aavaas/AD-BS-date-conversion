
//todo transform the code to pseudo code

to convert BS To AD 

    totalNepDaysCount =  count total days from starting nepali date to the required date

    // calculation of equivalent english date...
    int engYear = startingEngYear;
    int engMonth = startingEngMonth;
    int engDay = startingEngDay;
    int dayOfWeek = dayOfWeek;

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

    return (engYear, engMonth, engDay, dayOfWeek);


convertADToBS(MyDate adDate)

    int engYear = adDate.getYear();
    int engMonth = adDate.getMonth();
    int engDay = adDate.getDay();

    Calendar currentEngDate = Calendar.getInstance();
    currentEngDate.set(engYear, engMonth - 1, engDay);

    Calendar baseEngDate = Calendar.getInstance();
    baseEngDate.set(startingEngYear, startingEngMonth - 1, startingEngDay);
    long totalEngDaysCount = daysBetween(baseEngDate, currentEngDate);

    // initialize required Nepali date variables with starting Nepali date
    int nepYear = startingNepYear;
    int nepMonth = startingNepMonth;
    int nepDay = startingNepDay;
    int dayOfWeek = dayOfWeek;

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

    return (nepYear, nepMonth, nepDay, dayOfWeek);
