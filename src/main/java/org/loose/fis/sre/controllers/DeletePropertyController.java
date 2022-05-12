package org.loose.fis.sre.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.loose.fis.sre.exceptions.PropertyDoesNotExistException;
import org.loose.fis.sre.services.PropertyService;

public class DeletePropertyController {
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
    public void handleDeletePropertyAction() {
        try {
            PropertyService.deleteProperty(nameField.getText(), username);
            addMessage.setText("Property Deleted Successfully");
        } catch (PropertyDoesNotExistException e) {
            addMessage.setText(e.getMessage());
        }
    }
}
