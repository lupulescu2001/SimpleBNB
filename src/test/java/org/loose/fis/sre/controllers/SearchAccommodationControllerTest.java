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
import org.loose.fis.sre.exceptions.IncorrectDateException;
import org.loose.fis.sre.exceptions.PropertyAlreadyExistsException;
import org.loose.fis.sre.exceptions.PropertyAlreadyUnavailableException;
import org.loose.fis.sre.exceptions.PropertyDoesNotExistException;
import org.loose.fis.sre.model.BookingRequest;
import org.loose.fis.sre.model.PropertyUnavailable;
import org.loose.fis.sre.services.BookingRequestService;
import org.loose.fis.sre.services.FileSystemService;
import org.loose.fis.sre.services.PropertyService;
import org.loose.fis.sre.services.PropertyUnavailableService;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;


import java.io.IOException;
import java.util.Objects;

import static org.testfx.assertions.api.Assertions.assertThat;


@ExtendWith(ApplicationExtension.class)
public class SearchAccommodationControllerTest {
    public static final String CITYNAME="Timisoara";

    @BeforeEach
    void setUp() throws Exception {
        FileSystemService.APPLICATION_FOLDER = ".SimpleBNB";
        FileUtils.cleanDirectory(FileSystemService.getApplicationHomeFolder().toFile());
        BookingRequestService.initDatabase();
        PropertyService.initDatabase();
        PropertyUnavailableService.initDatabase();
    }

    @Start
    void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("searchAccommodation.fxml"));
        primaryStage.setTitle("Test Search Accommodation");
        primaryStage.setScene(new Scene(root, 600, 575));
        primaryStage.show();
    }

    @Test
    void TestSearchAccommodation(FxRobot robot) throws PropertyAlreadyExistsException, IncorrectDateException, PropertyDoesNotExistException, PropertyAlreadyUnavailableException {
        PropertyService.addProperty("NAME",CITYNAME,"BUN","lupu");
        PropertyUnavailable propertyUnavailable = new PropertyUnavailable(0,"lupu","NAME","1","1","2023","2","1","2023");
        PropertyUnavailableService.addUnavailableDate(propertyUnavailable);
        robot.clickOn("#cityname");
        robot.write("Timisoara2");
        robot.clickOn("#checkinday");
        robot.write("1");
        robot.clickOn("#checkinmonth");
        robot.write("1");
        robot.clickOn("#checkinyear");
        robot.write("2023");
        robot.clickOn("#checkoutday");
        robot.write("2");
        robot.clickOn("#checkoutmonth");
        robot.write("1");
        robot.clickOn("#checkoutyear");
        robot.write("2023");
        robot.clickOn("#search");

        assertThat(robot.lookup("#message").queryText()).hasText("There are no properties in this city : Timisoara2 , introduce different city");

        robot.clickOn("#cityname");
        robot.press(KeyCode.BACK_SPACE);
        robot.clickOn("#search");
        assertThat(robot.lookup("#message").queryText()).hasText("No properties available, please introduce different dates");
        robot.clickOn("#checkinday");
        robot.write("0");
        robot.clickOn("#search");
        assertThat(robot.lookup("#message").queryText()).hasText("The following dates are incorrect: 10/1/2023 and 2/1/2023");
        robot.clickOn("#checkoutday");
        robot.write("0");

        robot.clickOn("#search");

    }


}
