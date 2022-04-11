package org.loose.fis.sre.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.loose.fis.sre.exceptions.UsernameAlreadyExistsException;
import org.loose.fis.sre.services.UserService;
import java.io.IOException;

public class RegistrationController {

    @FXML
    private Text registrationMessage;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField usernameField;

    @FXML
    private TextField fullNameField;

    @FXML
    private TextField phoneNumberField;

    @FXML
    private ChoiceBox role;


    @FXML
    public void initialize() {
        role.getItems().addAll("Client", "Owner");
    }

    @FXML
    public void handleRegisterAction() {
        try {
            UserService.addUser(fullNameField.getText(),phoneNumberField.getText(),usernameField.getText(), passwordField.getText(), (String) role.getValue());
            registrationMessage.setText("Account created successfully!");
            Parent root;
            try {
                root = FXMLLoader.load(getClass().getClassLoader().getResource("login.fxml"));
                Stage stage=new Stage();
                stage.setTitle("Agentie Imobiliara");
                stage.setScene(new Scene(root,600,575));
                stage.show();

            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
        } catch (UsernameAlreadyExistsException e) {
            registrationMessage.setText(e.getMessage());
        }
    }
}
