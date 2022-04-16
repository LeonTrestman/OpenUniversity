import java.util.Calendar;

public class BirthDate {

    private final int day;
    private final int month;
    private final int year;

    public BirthDate(int day, int month, int year) {

        if (!ValidBirthDate(day, month, year))
            throw new IllegalArgumentException("Invalid date");

        this.day = day;
        this.month = month;
        this.year = year;
    }

    //returns True for valid day month and year, else returns false
    private Boolean ValidBirthDate(int day, int month, int year) {
        return isValidBirthYear(year) &&
                isValidBirthMonth(month) &&
                isValidBirthDay(day, month, year);
    }

    //Birth year cannot be greater than the current year
    private Boolean isValidBirthYear(int year) {
        return year <= Calendar.getInstance().get(Calendar.YEAR);
    }

    //valid month is between 1-12 (jan to dec)
    private Boolean isValidBirthMonth(int month) {
        return month > 0 && month < 13;
    }

    //Valid day changes according to days in month and if it's leap year
    private Boolean isValidBirthDay(int day, int month, int year) {
        // 1-jan , 2 - feb ,... , 12 - dec
        switch (month) {
            //January,March,May,July,August,October,December have 31 days
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                return day > 0 && day <= 31;
            //February has 29 days in a leap year and 28 days in regular year
            case 2:
                if (isLeapYear(year))
                    return day > 0 && day <= 29;
                else //not leap year
                    return day > 0 && day <= 28;
                //April,June,September,November have 30 days
            case 4:
            case 6:
            case 9:
            case 11:
                return day > 0 && day <= 30;
        }
        return false;
    }


    private Boolean isLeapYear(int year) {
        return java.time.Year.of(year).isLeap();
    }

    @Override
    public String toString() {
        return "BirthDate: " + day + "/" + month + "/" + year ;
    }

    public int getMonth() {
        return month;
    }

    //returns if this month
    public boolean isBirthDayThisMonth(){
        //+1 to shift months because Calendar.MONTH is zero based meaning jan = 0,
        return month == Calendar.getInstance().get(Calendar.MONTH)+1 ;
    }

}
