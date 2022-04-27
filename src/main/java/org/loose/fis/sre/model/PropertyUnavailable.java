package org.loose.fis.sre.model;

import org.dizitart.no2.objects.Id;

import java.util.Objects;

public class PropertyUnavailable {
    @Id
    private int id;
    private static int globalId = 0;
    private String username;
    private String name;
    private String firstDay;
    private String firstMonth;
    private String firstYear;
    private String lastDay;
    private String lastMonth;
    private String lastYear;
    public PropertyUnavailable(int id, String username, String name, String firstDay, String firstMonth, String firstYear, String lastDay,
             String lastMonth, String lastYear) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.firstDay = firstDay;
        this.firstMonth = firstMonth;
        this.firstYear = firstYear;
        this.lastDay = lastDay;
        this.lastMonth = lastMonth;
        this.lastYear = lastYear;
    }
    public PropertyUnavailable() {

    }
    public static void setGlobalId() {
        globalId += 1;
    }
    public static int getGlobalId() {
        return globalId;
    }

    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getFirstDay() {
        return firstDay;
    }

    public String getFirstMonth() {
        return firstMonth;
    }

    public String getFirstYear() {
        return firstYear;
    }

    public String getLastDay() {
        return lastDay;
    }

    public String getLastMonth() {
        return lastMonth;
    }

    public String getLastYear() {
        return lastYear;
    }

    public void setFirstDay(String firstDay) {
        this.firstDay = firstDay;
    }

    public void setFirstMonth(String firstMonth) {
        this.firstMonth = firstMonth;
    }

    public void setFirstYear(String firstYear) {
        this.firstYear = firstYear;
    }

    public void setLastDay(String lastDay) {
        this.lastDay = lastDay;
    }

    public void setLastMonth(String lastMonth) {
        this.lastMonth = lastMonth;
    }

    public void setLastYear(String lastYear) {
        this.lastYear = lastYear;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public boolean firstGreaterThanLast() { // returns false if not valid (last <= first)
        int fYear = Integer.parseInt(firstYear);
        int fMonth = Integer.parseInt(firstMonth);
        int fDay = Integer.parseInt(firstDay);
        int lYear = Integer.parseInt(lastYear);
        int lMonth = Integer.parseInt(lastMonth);
        int lDay = Integer.parseInt(lastDay);
        if (lYear < fYear)
            return false;
        if (lMonth < fMonth && lYear == fYear)
            return false;
        if (lDay <= fDay && lYear == fYear && lMonth == fMonth)
            return false;
        return true;
    }
    public boolean date() { // returns false if not a date
        try {
            Integer.parseInt(firstDay);
            Integer.parseInt(firstMonth);
            Integer.parseInt(firstYear);
            Integer.parseInt(lastDay);
            Integer.parseInt(lastMonth);
            Integer.parseInt(lastYear);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    public boolean validDate() { // returns false if not valid
        int fYear = Integer.parseInt(firstYear);
        int fMonth = Integer.parseInt(firstMonth);
        int fDay = Integer.parseInt(firstDay);
        int lYear = Integer.parseInt(lastYear);
        int lMonth = Integer.parseInt(lastMonth);
        int lDay = Integer.parseInt(lastDay);
        if (fDay <= 0 || lDay <= 0)
            return false;
        if ((fMonth == 1 || fMonth == 3 || fMonth == 5 || fMonth == 7 || fMonth == 8 || fMonth == 10 || fMonth == 12) && fDay > 31)
            return false;
        if ((lMonth == 1 || lMonth == 3 || lMonth == 5 || lMonth == 7 || lMonth == 8 || lMonth == 10 || lMonth == 12) && lDay > 31)
            return false;
        if ((fMonth == 4 || fMonth == 6 || fMonth == 9 || fMonth == 1) && fDay > 30)
            return false;
        if ((lMonth == 4 || lMonth == 6 || lMonth == 9 || lMonth == 1) && lDay > 30)
            return false;
        if (fYear % 4 == 0 && fYear % 100 != 0 && fMonth == 2 && fDay > 29)
            return false;
        if (lYear % 4 == 0 && lYear % 100 != 0 && lMonth == 2 && lDay > 29)
            return false;
        if ((fYear % 4 != 0 || fYear % 100 == 0) && fMonth == 2 && fDay > 28)
            return false;
        if ((lYear % 4 != 0 || lYear % 100 == 0) && lMonth == 2 && lDay > 28)
            return false;
        return true;
    }
    public boolean compare(PropertyUnavailable x) { //returns true if we can add to database
        int fYear = Integer.parseInt(this.firstYear);
        int fMonth = Integer.parseInt(this.firstMonth);
        int fDay = Integer.parseInt(this.firstDay);
        int lYear = Integer.parseInt(this.lastYear);
        int lMonth = Integer.parseInt(this.lastMonth);
        int lDay = Integer.parseInt(this.lastDay);
        int xfYear = Integer.parseInt(x.firstYear);
        int xfMonth = Integer.parseInt(x.firstMonth);
        int xfDay = Integer.parseInt(x.firstDay);
        int xlYear = Integer.parseInt(x.lastYear);
        int xlMonth = Integer.parseInt(x.lastMonth);
        int xlDay = Integer.parseInt(x.lastDay);
        if (lYear < xfYear)
            return true;
        if (lMonth < xfMonth && lYear == xfYear)
            return true;
        if (lDay <= xfDay && lYear == xfYear && lMonth == xfMonth)
            return true;
        if (fYear > xlYear)
            return true;
        if (fMonth > xlMonth && fYear == xlYear)
            return true;
        if (fDay >= xlDay && fMonth == xlMonth && fYear == xlYear)
            return true;
        return false;
    }
}
