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
import org.loose.fis.sre.exceptions.UsernameAlreadyExistsException;
import org.loose.fis.sre.model.User;
import org.loose.fis.sre.services.FileSystemService;
import org.loose.fis.sre.services.UserService;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;


import static org.testfx.assertions.api.Assertions.assertThat;

@ExtendWith(ApplicationExtension.class)

public class LoginTest {

    public static final String USERNAME = "user";
    public static final String PASSWORD = "password";
    public static final String OWNER = "Owner";

    @BeforeEach
    void setUp() throws Exception {
        FileSystemService.APPLICATION_FOLDER = ".SimpleBNB";
        FileUtils.cleanDirectory(FileSystemService.getApplicationHomeFolder().toFile());
        UserService.initDatabase();
    }

    @Start
    void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("login.fxml"));
        primaryStage.setTitle("Registration Test");
        primaryStage.setScene(new Scene(root, 600, 575));
        primaryStage.show();
    }

    @Test
    void testLogin(FxRobot robot) throws UsernameAlreadyExistsException {

        UserService.addUser(USERNAME,USERNAME,USERNAME,PASSWORD,OWNER);
        robot.clickOn("#username");
        robot.write(USERNAME);
        robot.clickOn("#password");
        robot.write(PASSWORD);
        robot.clickOn("#loginbutton");
        assertThat(robot.lookup("#loginmessage").queryText()).hasText("Login successfully!");


    }
}
