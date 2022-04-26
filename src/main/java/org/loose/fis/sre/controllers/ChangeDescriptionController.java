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
import org.loose.fis.sre.exceptions.PropertyAlreadyExistsException;
import org.loose.fis.sre.exceptions.PropertyDoesNotExistException;
import org.loose.fis.sre.services.PropertyService;
import java.io.IOException;
public class ChangeDescriptionController {
    @FXML
    private TextField nameField;
    private String username;
    @FXML
    private TextField descriptionField;
    @FXML
    private Text addMessage;
    public void setUsername(String username) {
        this.username = username;
    }
    public void handleChangeDescriptionAction() {
        try {
            PropertyService.changeDescription(nameField.getText(), username, descriptionField.getText());
            addMessage.setText("Property Description Changed Successfully");
        } catch (PropertyDoesNotExistException e) {
            addMessage.setText(e.getMessage());
        }
    }
}
