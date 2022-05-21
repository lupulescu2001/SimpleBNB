package org.loose.fis.sre.controllers;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.apache.commons.io.FileUtils;
import org.assertj.core.internal.bytebuddy.asm.Advice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.loose.fis.sre.exceptions.PropertyAlreadyExistsException;
import org.loose.fis.sre.exceptions.UsernameAlreadyExistsException;
import org.loose.fis.sre.model.PropertyUnavailable;
import org.loose.fis.sre.model.User;
import org.loose.fis.sre.services.FileSystemService;
import org.loose.fis.sre.services.PropertyService;
import org.loose.fis.sre.services.PropertyUnavailableService;
import org.loose.fis.sre.services.UserService;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;


import static org.testfx.assertions.api.Assertions.assertThat;
@ExtendWith(ApplicationExtension.class)

public class MakeUnavailableControllerTest {
    public static final String USERNAME = "user";
    public static final String PASSWORD = "password";
    public static final String OWNER = "Owner";

    @BeforeEach
    void setUp() throws Exception {
        FileSystemService.APPLICATION_FOLDER = ".SimpleBNB";
        FileUtils.cleanDirectory(FileSystemService.getApplicationHomeFolder().toFile());
        PropertyUnavailableService.initDatabase();
        PropertyService.initDatabase();
    }

    @Start
    void start(Stage primaryStage) throws Exception {
        Parent root;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/makeUnavailable.fxml"));
        root = (Parent) loader.load();
        MakeUnavailableController makeUnavailableController= loader.getController();
        makeUnavailableController.setUsername("Lupu");
        primaryStage.setTitle("Add Property Test");
        primaryStage.setScene(new Scene(root, 600, 575));
        primaryStage.show();
    }

    @Test
    void testMakeUnavailable(FxRobot robot) throws PropertyAlreadyExistsException {

        PropertyService.addProperty("la mare", "timisoara", "", "Lupu");
        robot.clickOn("#name");
        robot.write("la mare");
        robot.clickOn("#fd");
        robot.write("13");
        robot.clickOn("#fm");
        robot.write("02");
        robot.clickOn("#fy");
        robot.write("2022");
        robot.clickOn("#ld");
        robot.write("15");
        robot.clickOn("#lm");
        robot.write("02");
        robot.clickOn("#ly");
        robot.write("2022");
        robot.clickOn("#unavbutton");
        assertThat(robot.lookup("#addmessage").queryText()).hasText("Property was set to unavailable during the selected dates");
        robot.clickOn("#fd");

    }
}
