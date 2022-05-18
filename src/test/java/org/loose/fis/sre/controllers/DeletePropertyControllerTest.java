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

public class DeletePropertyControllerTest {
    public static final String PROP_NAME="PROP_NAME";
    @BeforeEach
    void setUp() throws Exception {
        FileSystemService.APPLICATION_FOLDER = ".SimpleBNB";
        FileUtils.cleanDirectory(FileSystemService.getApplicationHomeFolder().toFile());
        PropertyService.initDatabase();
        //  PropertyService.initDatabase();
    }

    @Start
    void start(Stage primaryStage) throws Exception {
        Parent root;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/deleteProperty.fxml"));
        root = (Parent) loader.load();
        DeletePropertyController deletePropertyController= loader.getController();
        deletePropertyController.setUsername("Lupu");
        primaryStage.setTitle("Add Property Test");
        primaryStage.setScene(new Scene(root, 600, 575));
        primaryStage.show();
    }
    @Test
    void TestDeletePropertyController(FxRobot robot) throws IOException, PropertyAlreadyExistsException {
        PropertyService.addProperty(PROP_NAME, "Timisoara", "", "Lupu");
        robot.clickOn("#delete");
        assertThat(robot.lookup("#addMessage").queryText()).hasText(String.format("A property with the name %s does not exist!", ""));
        robot.clickOn("#name");
        robot.write(PROP_NAME);
        robot.clickOn("#delete");
        assertThat(robot.lookup("#addMessage").queryText()).hasText("Property Deleted Successfully");
    }
}
