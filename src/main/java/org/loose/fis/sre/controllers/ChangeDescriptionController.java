package org.loose.fis.sre.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.loose.fis.sre.exceptions.PropertyDoesNotExistException;
import org.loose.fis.sre.services.PropertyService;

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
