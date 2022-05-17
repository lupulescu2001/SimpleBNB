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

public class AvailableAccommodationsControllerTest {

    public static final String PROP_NAME="PROP_NAME";
    @BeforeEach
    void setUp() throws Exception {
        FileSystemService.APPLICATION_FOLDER = ".SimpleBNB";
        FileUtils.cleanDirectory(FileSystemService.getApplicationHomeFolder().toFile());
        BookingRequestService.initDatabase();
      //  PropertyService.initDatabase();
    }

    @Start
    void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("availableAccommodations.fxml"));
        primaryStage.setTitle("Review Test");
        primaryStage.setScene(new Scene(root, 600, 575));
        primaryStage.show();
    }
    @Test
    void TestAvailableAccommodationsController(FxRobot robot) throws IOException{

        robot.clickOn("#propertyname");
        robot.write(PROP_NAME);
        robot.clickOn("#book");
        assertThat(robot.lookup("#message").queryText()).hasText("Name of the property incorrect");
        robot.clickOn("#home");
    }
}
