package org.loose.fis.sre.services;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.*;
import org.loose.fis.sre.exceptions.*;
import org.loose.fis.sre.model.BookingRequest;
import org.loose.fis.sre.model.Property;
import org.loose.fis.sre.model.PropertyUnavailable;
import org.loose.fis.sre.model.User;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.testfx.assertions.api.Assertions.assertThat;

public class PropertyUnavailableTest {
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
        PropertyUnavailableService.initDatabase();
        PropertyService.initDatabase();
    }
    @Test
    @DisplayName("Database is initialized, and there are no unavailable dates yet")
    void testDatabaseIsInitializedAndNoUserIsPersisted() {
        assertThat(PropertyUnavailableService.getAll()).isNotNull();
        assertThat(PropertyUnavailableService.getAll()).isEmpty();
    }
    @Test
    @DisplayName("You can add 1 unavailable date")
    void testAddUnavailableDate() throws PropertyAlreadyExistsException, IncorrectDateException, PropertyDoesNotExistException, PropertyAlreadyUnavailableException {
        PropertyService.addProperty(PROP_NAME, CITY_NAME, "", USERNAME);
        PropertyUnavailable x = new PropertyUnavailable(PropertyUnavailableService.getTheId(), USERNAME, PROP_NAME, "13", "02", "2020", "15", "02", "2020");
        PropertyUnavailableService.addUnavailableDate(x);
        assertThat(PropertyUnavailableService.getAll()).isNotNull();
        assertThat(PropertyUnavailableService.getAll().size()).isEqualTo(1);
    }
    @Test
    @DisplayName("You can't add an unavailable date to a non existing property")
    void testAddUnavailableDatetoNonExistingProperty() throws IncorrectDateException, PropertyDoesNotExistException, PropertyAlreadyUnavailableException {
        PropertyUnavailable x = new PropertyUnavailable(PropertyUnavailableService.getTheId(), USERNAME, PROP_NAME, "13", "02", "2020", "15", "02", "2020");
        assertThrows(PropertyDoesNotExistException.class, () -> {PropertyUnavailableService.addUnavailableDate(x);});

    }
    @Test
    @DisplayName("You can't add a wrong unavailable date ")
    void testAddUnavailableDateWithIncorrectDate() throws PropertyAlreadyExistsException, IncorrectDateException, PropertyDoesNotExistException, PropertyAlreadyUnavailableException {
        PropertyService.addProperty(PROP_NAME, CITY_NAME, "", USERNAME);
        PropertyUnavailable x = new PropertyUnavailable(PropertyUnavailableService.getTheId(), USERNAME, PROP_NAME, "17", "02", "2020", "15", "02", "2020");
        assertThrows(IncorrectDateException.class, () -> {PropertyUnavailableService.addUnavailableDate(x);});

    }
    @Test
    @DisplayName("You can't add a wrong unavailable date ")
    void testAddUnavailableAlreadyUnavailable() throws PropertyAlreadyExistsException, IncorrectDateException, PropertyDoesNotExistException, PropertyAlreadyUnavailableException {
        PropertyService.addProperty(PROP_NAME, CITY_NAME, "", USERNAME);
        PropertyUnavailable x = new PropertyUnavailable(PropertyUnavailableService.getTheId(), USERNAME, PROP_NAME, "13", "02", "2020", "15", "02", "2020");
        PropertyUnavailableService.addUnavailableDate(x);
        assertThrows(PropertyAlreadyUnavailableException.class, () -> {PropertyUnavailableService.addUnavailableDate(x);});

    }

}
