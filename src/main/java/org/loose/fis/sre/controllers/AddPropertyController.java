package org.loose.fis.sre.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.loose.fis.sre.exceptions.PropertyAlreadyExistsException;
import org.loose.fis.sre.services.PropertyService;

public class AddPropertyController {
    @FXML
    private TextField nameField;
    private String username;
    @FXML
    private TextField descriptionField;
    @FXML
    private TextField cityNameField;
    @FXML
    private Text addMessage;
    public void setUsername(String username) {
        this.username = username;
    }
    public void handleAddPropertyAction() {
        try {
            PropertyService.addProperty(nameField.getText(), cityNameField.getText(), descriptionField.getText(), this.username);
            addMessage.setText("Property Added Successfully");
        } catch (PropertyAlreadyExistsException e) {
            addMessage.setText(e.getMessage());
        }
    }
}
