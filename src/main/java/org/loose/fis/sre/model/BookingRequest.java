package org.loose.fis.sre.model;

import org.dizitart.no2.objects.Id;

import java.util.Objects;

public class BookingRequest {
    @Id
    private int id;
    private static int globalId=0;
    private String clientusername;
    private String propertyName;
    private String checkinDay;
    private String checkinMonth;
    private String checkinYear;
    private String checkoutDay;
    private String checkoutMonth;
    private String checkoutYear;

    public BookingRequest(int id,String clientusername,String propertyName,String checkinDay,String checkinMonth,String checkinYear,String checkoutDay,String checkoutMonth,String checkoutYear){
        this.id=id;
        this.clientusername=clientusername;
        this.propertyName=propertyName;
        this.checkinDay=checkinDay;
        this.checkinMonth=checkinMonth;
        this.checkinYear=checkinYear;
        this.checkoutDay=checkoutDay;
        this.checkoutMonth=checkoutMonth;
        this.checkoutYear=checkoutYear;
    }
    public BookingRequest(){}
    public int getId(){ return this.id;}

    public String getCheckinDay() {
        return checkinDay;
    }

    public String getCheckinMonth() {
        return checkinMonth;
    }

    public String getCheckinYear() {
        return checkinYear;
    }

    public String getCheckoutDay() {
        return checkoutDay;
    }

    public String getCheckoutMonth() {
        return checkoutMonth;
    }

    public String getCheckoutYear() {
        return checkoutYear;
    }

    public String getClientusername() {
        return clientusername;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public static int getGlobalId() {
        return globalId;
    }

    public void setCheckinDay(String checkinDay) {
        this.checkinDay = checkinDay;
    }

    public void setCheckinMonth(String checkinMonth) {
        this.checkinMonth = checkinMonth;
    }

    public void setCheckinYear(String checkinYear) {
        this.checkinYear = checkinYear;
    }

    public void setCheckoutDay(String checkoutDay) {
        this.checkoutDay = checkoutDay;
    }

    public void setCheckoutMonth(String checkoutMonth) {
        this.checkoutMonth = checkoutMonth;
    }

    public void setCheckoutYear(String checkoutYear) {
        this.checkoutYear = checkoutYear;
    }

    public void setClientusername(String clientusername) {
        this.clientusername = clientusername;
    }

    public static void setGlobalId() {
        globalId+=1;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

}
