package org.loose.fis.sre.services;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.*;
import org.loose.fis.sre.exceptions.IncorrectCredentials;
import org.loose.fis.sre.exceptions.MarkIsIncorrectException;
import org.loose.fis.sre.exceptions.UsernameAlreadyExistsException;
import org.loose.fis.sre.model.BookingRequest;
import org.loose.fis.sre.model.User;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.testfx.assertions.api.Assertions.assertThat;

public class UserServiceTest {
    public static final String OWNER = "Owner";
    public static final String CLIENT = "Client";

    @BeforeAll
    static void beforeAll() {
        System.out.println("Before Class");
    }

    @AfterAll
    static void afterAll() {
        System.out.println("After Class");
    }
    @BeforeEach
    void setUp() throws Exception {
        FileSystemService.APPLICATION_FOLDER = ".SimpleBNB";
        FileUtils.cleanDirectory(FileSystemService.getApplicationHomeFolder().toFile());
        UserService.initDatabase();
        BookingRequestService.initDatabase();
    }
    @AfterEach
    void tearDown() {
        System.out.println("After each");


    }

    @Test
    @DisplayName("Database is initialized, and there are no users")
    void testDatabaseIsInitializedAndNoUserIsPersisted() {
        assertThat(UserService.getAllUsers()).isNotNull();
        assertThat(UserService.getAllUsers()).isEmpty();
    }
    @Test
    @DisplayName("User is successfully persisted to Database")
    void testUserIsAddedToDatabase() throws UsernameAlreadyExistsException {
        UserService.addUser(OWNER,OWNER, OWNER, OWNER,OWNER);
        assertThat(UserService.getAllUsers()).isNotEmpty();
        assertThat(UserService.getAllUsers()).size().isEqualTo(1);
        User user = UserService.getAllUsers().get(0);
        assertThat(user).isNotNull();
        assertThat(user.getUsername()).isEqualTo(OWNER);
        assertThat(user.getPassword()).isEqualTo(UserService.encodePassword(OWNER, OWNER));
        assertThat(user.getRole()).isEqualTo(OWNER);
    }
    @Test
    @DisplayName("User can not be added twice")
    void testUserCanNotBeAddedTwice() {
        assertThrows(UsernameAlreadyExistsException.class, () -> {
            UserService.addUser(OWNER, OWNER, OWNER,OWNER,OWNER);
            UserService.addUser(OWNER, OWNER, OWNER,OWNER,OWNER);
        });
    }

    @Test
    @DisplayName("Login credentials are ok ")
    void testCheckCorrectUserCredentials() throws UsernameAlreadyExistsException, IncorrectCredentials {
        UserService.addUser(OWNER,OWNER,OWNER,OWNER,OWNER);
        assertThat(UserService.CheckUserCredentials(OWNER,OWNER,OWNER)).isEqualTo(2);
        UserService.addUser(CLIENT,CLIENT,CLIENT,CLIENT,CLIENT);
        assertThat(UserService.CheckUserCredentials(CLIENT,CLIENT,CLIENT)).isEqualTo(1);


    }
    @Test
    @DisplayName("Login credentials not correct")
    void testCheckIncorrectUserCredentials() throws UsernameAlreadyExistsException{
        UserService.addUser(OWNER,OWNER,OWNER,OWNER,OWNER);
        assertThrows(IncorrectCredentials.class,() ->{
            UserService.CheckUserCredentials(OWNER,CLIENT,OWNER);
        });
        assertThrows(IncorrectCredentials.class,() ->{
            UserService.CheckUserCredentials(CLIENT,OWNER,OWNER);
        });
        assertThrows(IncorrectCredentials.class,() ->{
            UserService.CheckUserCredentials(OWNER,OWNER,CLIENT);
        });
    }
    @Test
    @DisplayName("Get role by username function")
    void testGetRoleByUsername() throws IncorrectCredentials,UsernameAlreadyExistsException{
        UserService.addUser(OWNER,OWNER,OWNER,OWNER,OWNER);
        assertThat(UserService.getRoleByUsername(OWNER)).isEqualTo(OWNER);
        assertThrows(IncorrectCredentials.class,() -> {
            UserService.getRoleByUsername(CLIENT);
        });

    }
    @Test
    @DisplayName("Add review function")
    void testAddReview() throws MarkIsIncorrectException, UsernameAlreadyExistsException {
        assertThrows(MarkIsIncorrectException.class,() ->{
            UserService.addUser(OWNER,OWNER,OWNER,OWNER,OWNER);
            UserService.addUser("john", "john", "john", "john", "Client");
            BookingRequest x = new BookingRequest(0, "john","la mare","13","02","2022","15","02","2022", 1);
            BookingRequestService.addBookingRequest(x);
            UserService.addReview(OWNER, "john", "12");
        });
    }




}
