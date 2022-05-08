package org.loose.fis.sre.services;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.dizitart.no2.Nitrite;
import org.dizitart.no2.objects.Id;
import org.dizitart.no2.objects.ObjectRepository;
import org.jetbrains.annotations.NotNull;
import org.loose.fis.sre.exceptions.*;
import org.loose.fis.sre.model.BookingRequest;
import org.loose.fis.sre.model.Property;
import org.loose.fis.sre.model.PropertyUnavailable;
import org.loose.fis.sre.model.User;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import org.dizitart.no2.objects.filters.ObjectFilters;
import org.loose.fis.sre.services.PropertyService;
import static org.loose.fis.sre.services.FileSystemService.getPathToFile;
import org.loose.fis.sre.exceptions.ThisClientDidNotTryToRentYourPropertyException;

public class BookingRequestService {

    private static ObjectRepository<BookingRequest> BookingRequestRepository;
    public static void initDatabase(){
        Nitrite database = Nitrite.builder()
                .filePath(getPathToFile("SimpleBNBbookingrequest1.db").toFile())
                .openOrCreate("SimpleBNBbookingrequest1", "SimpleBNBbookingrequest1");
        BookingRequestRepository= database.getRepository(BookingRequest.class);
    }
    public static void addBookingRequest(BookingRequest b){
        BookingRequestRepository.insert(b);
    }
    public static int getLastId(){
        int id=0;
        for(BookingRequest bookingRequest : BookingRequestRepository.find())
            id= (id > bookingRequest.getId()) ? id : bookingRequest.getId();
        return id;
    }

    public static List<String> getAllUpcomingRequestsForUser(String username){
        List<String> sol = new ArrayList<>();

        LocalDateTime now= LocalDateTime.now();



        for (BookingRequest bookingRequest : BookingRequestRepository.find()){
            if (bookingRequest.getRequestStatus()==1 && Objects.equals(username,bookingRequest.getClientusername()))
                if(Integer.parseInt(bookingRequest.getCheckinYear())>now.getYear() ||
                        (Integer.parseInt(bookingRequest.getCheckinYear())==now.getYear() && Integer.parseInt(bookingRequest.getCheckinMonth())>now.getMonthValue())
                || (Integer.parseInt(bookingRequest.getCheckinYear())==now.getYear() && Integer.parseInt(bookingRequest.getCheckinMonth())==now.getMonthValue()
                && Integer.parseInt(bookingRequest.getCheckinDay())>=now.getDayOfMonth()))

                {
                sol.add("Reservation at : " + bookingRequest.getPropertyName() + " from " + bookingRequest.getCheckinDay() +
                        "/"+bookingRequest.getCheckinMonth()+"/"+bookingRequest.getCheckinYear()+ " to " +
                        bookingRequest.getCheckoutDay() +
                        "/"+bookingRequest.getCheckoutMonth()+"/"+bookingRequest.getCheckoutYear() + " was approved ");
                }
        }


        return sol;
    }

    public static List<String> getAllPastRequestsForUser(String username){
        List<String> sol = new ArrayList<>();

        LocalDateTime now= LocalDateTime.now();



        for (BookingRequest bookingRequest : BookingRequestRepository.find()){
            if (bookingRequest.getRequestStatus()==1 && Objects.equals(username,bookingRequest.getClientusername()))
                if(Integer.parseInt(bookingRequest.getCheckinYear())<now.getYear() ||
                        (Integer.parseInt(bookingRequest.getCheckinYear())==now.getYear() && Integer.parseInt(bookingRequest.getCheckinMonth())<now.getMonthValue())
                        || (Integer.parseInt(bookingRequest.getCheckinYear())==now.getYear() && Integer.parseInt(bookingRequest.getCheckinMonth())==now.getMonthValue()
                        && Integer.parseInt(bookingRequest.getCheckinDay())<=now.getDayOfMonth()))

                {
                    sol.add("Reservation at : " + bookingRequest.getPropertyName() + " from " + bookingRequest.getCheckinDay() +
                            "/"+bookingRequest.getCheckinMonth()+"/"+bookingRequest.getCheckinYear()+ " to " +
                            bookingRequest.getCheckoutDay() +
                            "/"+bookingRequest.getCheckoutMonth()+"/"+bookingRequest.getCheckoutYear() + " was approved ");
                }
        }


        return sol;
    }

    public static List<String> getAllPastRequestsNameForUser(String username){
        List<String> sol = new ArrayList<>();

        LocalDateTime now= LocalDateTime.now();



        for (BookingRequest bookingRequest : BookingRequestRepository.find()){
            if (bookingRequest.getRequestStatus()==1 && Objects.equals(username,bookingRequest.getClientusername()))
                if(Integer.parseInt(bookingRequest.getCheckinYear())<now.getYear() ||
                        (Integer.parseInt(bookingRequest.getCheckinYear())==now.getYear() && Integer.parseInt(bookingRequest.getCheckinMonth())<now.getMonthValue())
                        || (Integer.parseInt(bookingRequest.getCheckinYear())==now.getYear() && Integer.parseInt(bookingRequest.getCheckinMonth())==now.getMonthValue()
                        && Integer.parseInt(bookingRequest.getCheckinDay())<=now.getDayOfMonth()))

                {
                    sol.add(bookingRequest.getPropertyName());
                }
        }


        return sol;
    }

    public static List<String> getAllRequestsForOwner(String username) {
        List<Property> propertyList = PropertyService.getAll();
        List<String> propertyNameList = new ArrayList<String>();
        List<String> returnList = new ArrayList<String>();
        for (Property property : propertyList)
            if (Objects.equals(property.getUsername(), username))
                propertyNameList.add(property.getName());
        for (BookingRequest bookingRequest: BookingRequestRepository.find()) {
            int ok = 0;
            for (String x : propertyNameList)
                if (Objects.equals(bookingRequest.getPropertyName(), x) && bookingRequest.getRequestStatus() == 0)
                    ok = 1;
            int rev = 0, mrk = 0;
            String smth = "abc";
            for (User user : UserService.getAllUsers())
                if (Objects.equals(user.getUsername(), bookingRequest.getClientusername())) {
                    mrk = user.getMark();
                    rev = user.getReviews();
                    smth = user.getUsername();
                }
            if (ok == 1 && rev > 0)
                returnList.add(bookingRequest.getClientusername() + " wants to rent the property " + bookingRequest.getPropertyName() + " during " +
                        bookingRequest.getCheckinDay() + '/' + bookingRequest.getCheckinMonth() + '/' + bookingRequest.getCheckinYear() + " and " +
                        bookingRequest.getCheckoutDay() + '/' + bookingRequest.getCheckoutMonth() + '/' + bookingRequest.getCheckoutYear() + " and the reviews are " + 1.0 * mrk / rev);
            if (ok == 1 && rev == 0)
                returnList.add(bookingRequest.getClientusername() + " wants to rent the property " + bookingRequest.getPropertyName() + " during " +
                        bookingRequest.getCheckinDay() + '/' + bookingRequest.getCheckinMonth() + '/' + bookingRequest.getCheckinYear() + " and " +
                        bookingRequest.getCheckoutDay() + '/' + bookingRequest.getCheckoutMonth() + '/' + bookingRequest.getCheckoutYear() + " and he has no reviews");
        }
        return returnList;
    }
    public static void searchClientUsername(String username, String propertyName, String clientUsername, String fDay, String fMonth, String fYear,
                                            String lDay, String lMonth, String lYear) throws ThisClientDidNotTryToRentYourPropertyException, IncorrectDateException {
        List<Property> propertyList = PropertyService.getAll();
        List<String> propertyNameList = new ArrayList<String>();
        List<String> checkList = new ArrayList<String>();
        PropertyUnavailable propertyUnavailable = new PropertyUnavailable(0, username, "", fDay, fMonth, fYear, lDay, lMonth, lYear);
        if (propertyUnavailable.date() == false)
            throw new IncorrectDateException(propertyUnavailable);
        if (propertyUnavailable.validDate() == false)
            throw new IncorrectDateException(propertyUnavailable);
        if (propertyUnavailable.firstGreaterThanLast() == false)
            throw new IncorrectDateException(propertyUnavailable);
        int iday = Integer.parseInt(fDay);
        int imonth = Integer.parseInt(fMonth);
        int iyear = Integer.parseInt(fYear);
        int oday = Integer.parseInt(lDay);
        int omonth = Integer.parseInt(lMonth);
        int oyear = Integer.parseInt(lYear);
        for (Property property : propertyList)
            if (Objects.equals(property.getUsername(), username))
                propertyNameList.add(property.getName());
        for (BookingRequest bookingRequest : BookingRequestRepository.find()) {
            int checkinday = Integer.parseInt(bookingRequest.getCheckinDay());
            int checkinmonth = Integer.parseInt(bookingRequest.getCheckinMonth());
            int checkinyear = Integer.parseInt(bookingRequest.getCheckinYear());
            int checkoutday = Integer.parseInt(bookingRequest.getCheckoutDay());
            int checkoutmonth = Integer.parseInt(bookingRequest.getCheckoutMonth());
            int checkoutyear = Integer.parseInt(bookingRequest.getCheckoutYear());
            for (String x : propertyNameList) {
                if (Objects.equals(bookingRequest.getPropertyName(), x) && bookingRequest.getRequestStatus() == 0
                        && checkinday == iday && checkinmonth == imonth && checkinyear == iyear && checkoutday == oday
                        && checkoutmonth == omonth && checkoutyear == oyear && Objects.equals(propertyName, bookingRequest.getPropertyName()))
                    checkList.add(bookingRequest.getClientusername());
            }
        }
        int ok = 0;
        for (String x : checkList) {
            if (Objects.equals(x, clientUsername))
                ok = 1;

        }
        if (ok == 0)
            throw new ThisClientDidNotTryToRentYourPropertyException(clientUsername);
    }
    public static PropertyUnavailable theProperty(String username, String propertyName, String clientUsername, String fDay, String fMonth, String fYear,
                                                String lDay, String lMonth, String lYear) {
        List<Property> propertyList = PropertyService.getAll();
        List<String> propertyNameList = new ArrayList<String>();
        List<String> checkList = new ArrayList<String>();
        List<String> nameList = new ArrayList<String>();
        int iday = Integer.parseInt(fDay);
        int imonth = Integer.parseInt(fMonth);
        int iyear = Integer.parseInt(fYear);
        int oday = Integer.parseInt(lDay);
        int omonth = Integer.parseInt(lMonth);
        int oyear = Integer.parseInt(lYear);
        return new PropertyUnavailable(PropertyUnavailableService.getTheId(), username, propertyName, fDay, fMonth, fYear, lDay, lMonth, lYear);
    }
    public static void setBookingStatus(int status,String username, String propertyName, String clientUsername, String fDay, String fMonth, String fYear,
                                        String lDay, String lMonth, String lYear) {
        List<Property> propertyList = PropertyService.getAll();
        List<String> propertyNameList = new ArrayList<String>();
        List<String> checkList = new ArrayList<String>();
        int iday = Integer.parseInt(fDay);
        int imonth = Integer.parseInt(fMonth);
        int iyear = Integer.parseInt(fYear);
        int oday = Integer.parseInt(lDay);
        int omonth = Integer.parseInt(lMonth);
        int oyear = Integer.parseInt(lYear);
        for (Property property : propertyList)
            if (Objects.equals(property.getUsername(), username))
                propertyNameList.add(property.getName());
        for (BookingRequest bookingRequest : BookingRequestRepository.find()) {
            int ok = 0;
            int checkinday = Integer.parseInt(bookingRequest.getCheckinDay());
            int checkinmonth = Integer.parseInt(bookingRequest.getCheckinMonth());
            int checkinyear = Integer.parseInt(bookingRequest.getCheckinYear());
            int checkoutday = Integer.parseInt(bookingRequest.getCheckoutDay());
            int checkoutmonth = Integer.parseInt(bookingRequest.getCheckoutMonth());
            int checkoutyear = Integer.parseInt(bookingRequest.getCheckoutYear());
            if (bookingRequest.getRequestStatus() == 0 && checkinday == iday && checkinmonth == imonth && checkinyear == iyear
                    && checkoutday == oday && checkoutmonth == omonth && checkoutyear == oyear
                    && Objects.equals(bookingRequest.getClientusername(), clientUsername) && Objects.equals(propertyName, bookingRequest.getPropertyName()))
                for (String x : propertyNameList)
                    if (Objects.equals(x, bookingRequest.getPropertyName())) {
                        ok = 1;
                        bookingRequest.setRequestStatus(status);
                        BookingRequestRepository.update(bookingRequest);
                        break;
                    }
        }
    }
    public static List<String> getPastClients(String username) {
        List<Property> propertyList = PropertyService.getAll();
        List<String> propertyNameList = new ArrayList<String>();
        for (Property property : propertyList)
            if (Objects.equals(property.getUsername(), username))
                propertyNameList.add(property.getName());
        LocalDateTime now= LocalDateTime.now();
        List<String> sol = new ArrayList<>();
        for (BookingRequest bookingRequest : BookingRequestRepository.find())
            if((Integer.parseInt(bookingRequest.getCheckinYear())<now.getYear() ||
                    (Integer.parseInt(bookingRequest.getCheckinYear())==now.getYear() && Integer.parseInt(bookingRequest.getCheckinMonth())<now.getMonthValue())
                    || (Integer.parseInt(bookingRequest.getCheckinYear())==now.getYear() && Integer.parseInt(bookingRequest.getCheckinMonth())==now.getMonthValue()
                    && Integer.parseInt(bookingRequest.getCheckinDay())<=now.getDayOfMonth())) && bookingRequest.getRequestStatus() == 1) {
                for (String property : propertyNameList)
                    if (Objects.equals(bookingRequest.getPropertyName(), property))
                        sol.add("The Client " + bookingRequest.getClientusername() + " stayed at : " + bookingRequest.getPropertyName() + " from " + bookingRequest.getCheckinDay() +
                                "/" + bookingRequest.getCheckinMonth() + "/" + bookingRequest.getCheckinYear() + " to " +
                                bookingRequest.getCheckoutDay() +
                                "/" + bookingRequest.getCheckoutMonth() + "/" + bookingRequest.getCheckoutYear());
            }
        return sol;
    }
    public static List<String> getAllPastClients(String username) {
        List<Property> propertyList = PropertyService.getAll();
        List<String> propertyNameList = new ArrayList<String>();
        for (Property property : propertyList)
            if (Objects.equals(property.getUsername(), username))
                propertyNameList.add(property.getName());
        LocalDateTime now= LocalDateTime.now();
        List<String> sol = new ArrayList<>();
        for (BookingRequest bookingRequest : BookingRequestRepository.find())
            if((Integer.parseInt(bookingRequest.getCheckinYear())<now.getYear() ||
                    (Integer.parseInt(bookingRequest.getCheckinYear())==now.getYear() && Integer.parseInt(bookingRequest.getCheckinMonth())<now.getMonthValue())
                    || (Integer.parseInt(bookingRequest.getCheckinYear())==now.getYear() && Integer.parseInt(bookingRequest.getCheckinMonth())==now.getMonthValue()
                    && Integer.parseInt(bookingRequest.getCheckinDay())<=now.getDayOfMonth())) && bookingRequest.getRequestStatus() == 1) {
                for (String property : propertyNameList)
                    if (Objects.equals(bookingRequest.getPropertyName(), property))
                        sol.add(bookingRequest.getClientusername());
            }
        return sol;
    }
}
