package org.loose.fis.sre.services;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.*;
import org.loose.fis.sre.exceptions.*;
import org.loose.fis.sre.model.BookingRequest;
import org.loose.fis.sre.model.Property;
import org.loose.fis.sre.model.User;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.testfx.assertions.api.Assertions.assertThat;

public class BookingRequestServiceTest {
    public static final String PROP_NAME="propname";
    public static final String CITY_NAME="cityname";
    public static final String DESCRIPTION="description";
    public static final String USERNAME="username";
    public static final String OWNERUSERNAME="lupu";
    public static final String OWNER = "Owner";

    Property property=new Property(CITY_NAME,DESCRIPTION,OWNERUSERNAME,PROP_NAME);
    BookingRequest bookingRequestPast=new BookingRequest(0,USERNAME,PROP_NAME,"1","1","2015","3","1","2015",1);
    BookingRequest bookingRequestFuture=new BookingRequest(1,USERNAME,PROP_NAME,"1","1","2025","3","1","2025",1);

    @BeforeAll
    static void beforeAll() {
        System.out.println("Before Class");
    }

    @AfterAll
    static void afterAll() {
        System.out.println("After Class");
    }
    @AfterEach
    void tearDown() {
        System.out.println("After each");
    }

    @BeforeEach
    void setUp() throws Exception {
        FileSystemService.APPLICATION_FOLDER = ".SimpleBNB";
        FileUtils.cleanDirectory(FileSystemService.getApplicationHomeFolder().toFile());
        PropertyService.initDatabase();
        BookingRequestService.initDatabase();
        UserService.initDatabase();
    }
    @Test
    @DisplayName("Database is initialized")
    void testDatabaseIsInitialized(){
        assertThat(BookingRequestService.getAllPastRequestsForUser(USERNAME)).isNotNull();
        assertThat(BookingRequestService.getAllPastRequestsForUser(USERNAME)).isEmpty();
    }


    @Test
    @DisplayName("Booking request is added")
    void testBookingRequestIsAdded(){
        BookingRequestService.addBookingRequest(bookingRequestPast);
        assertThat(BookingRequestService.getAllPastRequestsForUser(USERNAME)).isNotEmpty();
        assertThat(BookingRequestService.getAllPastRequestsForUser(USERNAME)).size().isEqualTo(1);
    }

    @Test
    @DisplayName("Getting all past reservations for user")
    void testGetAllPastRequestsForUser(){
        List<String> sol = new ArrayList<>();
        sol.add("Reservation at : " + bookingRequestPast.getPropertyName() + " from " + bookingRequestPast.getCheckinDay() +
                "/"+bookingRequestPast.getCheckinMonth()+"/"+bookingRequestPast.getCheckinYear()+ " to " +
                bookingRequestPast.getCheckoutDay() +
                "/"+bookingRequestPast.getCheckoutMonth()+"/"+bookingRequestPast.getCheckoutYear() + " was approved ");
        BookingRequestService.addBookingRequest(bookingRequestFuture);
        BookingRequestService.addBookingRequest(bookingRequestPast);
        assertThat(BookingRequestService.getAllPastRequestsForUser(USERNAME)).isEqualTo(sol);
        sol=new ArrayList<>();
        sol.add(bookingRequestPast.getPropertyName());
        assertThat(BookingRequestService.getAllPastRequestsNameForUser(USERNAME)).isEqualTo(sol);
    }
    @Test
    @DisplayName("Getting all upcoming reservations for user")
    void testGetAllUpcomingRequestsForUser(){
        List<String> sol = new ArrayList<>();
        sol.add("Reservation at : " + bookingRequestFuture.getPropertyName() + " from " + bookingRequestFuture.getCheckinDay() +
                "/"+bookingRequestFuture.getCheckinMonth()+"/"+bookingRequestFuture.getCheckinYear()+ " to " +
                bookingRequestFuture.getCheckoutDay() +
                "/"+bookingRequestPast.getCheckoutMonth()+"/"+bookingRequestFuture.getCheckoutYear() + " was approved ");
        BookingRequestService.addBookingRequest(bookingRequestFuture);
        BookingRequestService.addBookingRequest(bookingRequestPast);
        assertThat(BookingRequestService.getAllUpcomingRequestsForUser(USERNAME)).isEqualTo(sol);


    }
    @Test
    @DisplayName("Test getAllRequestsForOwner")
    void testGetAllRequestsForOwner() throws UsernameAlreadyExistsException, PropertyAlreadyExistsException {
        UserService.addUser(OWNER,OWNER,OWNER,OWNER,OWNER);
        UserService.addUser("john", "john", "john", "john", "Client");
        PropertyService.addProperty("la mare", "timisoara", "", OWNER);
        BookingRequest x = new BookingRequest(0, "john","la mare","13","02","2022","15","02","2022", 0);
        BookingRequestService.addBookingRequest(x);
        assertThat(BookingRequestService.getAllRequestsForOwner(OWNER).size()).isEqualTo(1);

    }
    @Test
    @DisplayName("Test getAllRequestIdsForOwner")
    void testGetAllRequestIdsForOwner() throws UsernameAlreadyExistsException, PropertyAlreadyExistsException {
        UserService.addUser(OWNER,OWNER,OWNER,OWNER,OWNER);
        UserService.addUser("john", "john", "john", "john", "Client");
        PropertyService.addProperty("la mare", "timisoara", "", OWNER);
        BookingRequest x = new BookingRequest(0, "john","la mare","13","02","2022","15","02","2022", 0);
        BookingRequestService.addBookingRequest(x);
        assertThat(BookingRequestService.getAllRequestIdsForOwner(OWNER).size()).isEqualTo(1);

    }
    @Test
    @DisplayName("Test getAllRequestIdsForOwner")
    void testGetIdForRequest() throws UsernameAlreadyExistsException, PropertyAlreadyExistsException {
        UserService.addUser(OWNER,OWNER,OWNER,OWNER,OWNER);
        UserService.addUser("john", "john", "john", "john", "Client");
        PropertyService.addProperty("la mare", "timisoara", "", OWNER);
        BookingRequest x = new BookingRequest(0, "john","la mare","13","02","2022","15","02","2022", 0);
        BookingRequestService.addBookingRequest(x);
        assertThat(BookingRequestService.getIdForRequest(OWNER, "")).isEqualTo(-1);

    }
    @Test
    @DisplayName("Test getPastClients")
    void testGetPastClients() throws UsernameAlreadyExistsException, PropertyAlreadyExistsException {
        UserService.addUser(OWNER,OWNER,OWNER,OWNER,OWNER);
        UserService.addUser("john", "john", "john", "john", "Client");
        PropertyService.addProperty("la mare", "timisoara", "", OWNER);
        BookingRequest x = new BookingRequest(0, "john","la mare","13","02","2022","15","02","2022", 1);
        BookingRequestService.addBookingRequest(x);
        assertThat(BookingRequestService.getPastClients(OWNER).size()).isEqualTo(1);

    }

}
