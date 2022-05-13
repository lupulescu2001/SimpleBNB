package org.loose.fis.sre.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.loose.fis.sre.exceptions.PropertyAlreadyExistsException;
import org.loose.fis.sre.model.BookingRequest;
import org.loose.fis.sre.services.BookingRequestService;
import org.loose.fis.sre.services.FileSystemService;
import org.loose.fis.sre.services.PropertyService;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;


import java.io.IOException;
import java.util.Objects;

import static org.testfx.assertions.api.Assertions.assertThat;

@ExtendWith(ApplicationExtension.class)
public class PastReservationsControllerTest {
    public static final String PROP_NAME="nu";
    public static final String USERNAME="USERNAME";
    @BeforeEach
    void setUp() throws Exception {
        FileSystemService.APPLICATION_FOLDER = ".SimpleBNB";
        FileUtils.cleanDirectory(FileSystemService.getApplicationHomeFolder().toFile());
        BookingRequestService.initDatabase();
        PropertyService.initDatabase();
    }
    @Start
    void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("pastReservations.fxml"));
        primaryStage.setTitle("Review Test");
        primaryStage.setScene(new Scene(root, 600, 575));
        primaryStage.show();
    }

    @Test
    void TestPastReservationsController(FxRobot robot) throws PropertyAlreadyExistsException, IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/pastReservations.fxml"));
        Parent root = (Parent)fxmlLoader.load();

        PropertyService.addProperty(PROP_NAME,"Timisoara","bine","lupu");
        BookingRequest bookingRequest=new BookingRequest(0,USERNAME,PROP_NAME,"20","5","2020","23","5","2020",1);
        BookingRequestService.addBookingRequest(bookingRequest);
        PastReservationsController pastReservationsController= (PastReservationsController) fxmlLoader.getController();
        pastReservationsController.setclientusername(USERNAME);

        robot.clickOn("#propname");
        robot.write("n");
        robot.clickOn("#review");
        robot.write("1");
        robot.clickOn("#leavereview");
        assertThat(robot.lookup("#message").queryText()).hasText("A property with the name n does not exist!");
        robot.clickOn("#review");
        robot.write("9");
        robot.clickOn("#leavereview");
        assertThat(robot.lookup("#message").queryText()).hasText("Incorect type of score ,introduce a number from 1 to 10");
        robot.clickOn("#review");
        robot.press(KeyCode.BACK_SPACE);
        robot.clickOn("#propname");
       // robot.press(KeyCode.BACK_SPACE);
        robot.write("u");
        robot.clickOn("#leavereview");
        assertThat(robot.lookup("#message").queryText()).hasText("New review : " + PropertyService.getPropertyByName(PROP_NAME).getReview());
        robot.clickOn("#review");
        robot.write("0");
        robot.clickOn("#leavereview");

        assertThat(robot.lookup("#message").queryText()).hasText("New review : " + PropertyService.getPropertyByName(PROP_NAME).getReview());



    }

}
