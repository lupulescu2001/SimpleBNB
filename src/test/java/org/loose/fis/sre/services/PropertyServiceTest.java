package org.loose.fis.sre.services;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.*;
import org.loose.fis.sre.exceptions.*;
import org.loose.fis.sre.model.BookingRequest;
import org.loose.fis.sre.model.Property;
import org.loose.fis.sre.model.User;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.testfx.assertions.api.Assertions.assertThat;

public class PropertyServiceTest {

    public static final String PROP_NAME="propname";
    public static final String CITY_NAME="cityname";
    public static final String DESCRIPTION="description";
    public static final String USERNAME="username";



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
    }
    @Test
    @DisplayName("Database is initialized, and there are no properties")
    void testDatabaseIsInitializedAndNoUserIsPersisted() {
        assertThat(PropertyService.getAll()).isNotNull();
        assertThat(PropertyService.getAll()).isEmpty();

    }
    @Test
    @DisplayName("Adding review to a property")
    void testAddReview() throws PropertyAlreadyExistsException, IncorrectScoreException, PropertyDoesNotExistException {
        PropertyService.addProperty(PROP_NAME,CITY_NAME,DESCRIPTION,USERNAME);
        BookingRequest bookingRequest=new BookingRequest(0,USERNAME,PROP_NAME,"20","5","2020","23","5","2020",1);
        BookingRequestService.addBookingRequest(bookingRequest);
        PropertyService.addReview(PROP_NAME,8,USERNAME);
        assertThat(PropertyService.getPropertyByName(PROP_NAME).getReview()).isEqualTo(8);
        PropertyService.addReview(PROP_NAME,6,USERNAME);
        assertThat(PropertyService.getPropertyByName(PROP_NAME).getReview()).isEqualTo(7);


    }
    @Test
    @DisplayName("Adding incorrect review to a property")
    void testBadReview() throws PropertyAlreadyExistsException {
        PropertyService.addProperty(PROP_NAME,CITY_NAME,DESCRIPTION,USERNAME);
        assertThrows(IncorrectScoreException.class,() ->{
            PropertyService.addReview(PROP_NAME,12,USERNAME);
        });
    }
    @Test
    @DisplayName("Adding a review to a non existing property")
    void testAddReviewToNonExistentProperty() throws PropertyAlreadyExistsException{
        PropertyService.addProperty(PROP_NAME,CITY_NAME,DESCRIPTION,USERNAME);

        assertThrows(PropertyDoesNotExistException.class,() ->{
            PropertyService.addReview( CITY_NAME,8,USERNAME);
        });
    }



}
