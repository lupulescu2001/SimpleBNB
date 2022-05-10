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
    public static List<Integer> getAllRequestIdsForOwner(String username) {
        List<Property> propertyList = PropertyService.getAll();
        List<String> propertyNameList = new ArrayList<String>();
        List<Integer> returnList = new ArrayList<Integer>();
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
            if (ok == 1)
                returnList.add(bookingRequest.getId());
        }
        return returnList;
    }
    public static int getIdForRequest(String username, String request) {
        List<String> requestList = getAllRequestsForOwner(username);
        List<Integer> idList = getAllRequestIdsForOwner(username);
        for (int i = 0; i < idList.size(); i++)
            if (Objects.equals(requestList.get(i), request))
                return idList.get(i);
        return -1;
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
    public static void setBookingStatus(int id, int status) {
        for (BookingRequest bookingRequest : BookingRequestRepository.find()) {
            if (Objects.equals(id, bookingRequest.getId())) {
                bookingRequest.setRequestStatus(status);
                BookingRequestRepository.update(bookingRequest);
            }
        }
    }
    public static PropertyUnavailable theProperty(int id, String username) {
        for (BookingRequest bookingRequest : BookingRequestRepository.find()) {
            if (Objects.equals(id, bookingRequest.getId())) {
                return new PropertyUnavailable(PropertyUnavailableService.getTheId(), username, bookingRequest.getPropertyName(),
                bookingRequest.getCheckinDay(), bookingRequest.getCheckinMonth(), bookingRequest.getCheckinYear(),
                bookingRequest.getCheckoutDay(), bookingRequest.getCheckoutMonth(), bookingRequest.getCheckoutYear());
            }
        }
        return null;
    }
}
