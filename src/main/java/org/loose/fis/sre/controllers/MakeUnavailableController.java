package org.loose.fis.sre.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.loose.fis.sre.exceptions.PropertyDoesNotExistException;
import org.loose.fis.sre.model.PropertyUnavailable;
import org.loose.fis.sre.services.PropertyService;
import org.loose.fis.sre.services.PropertyUnavailableService;
import org.loose.fis.sre.exceptions.PropertyAlreadyUnavailableException;
import org.loose.fis.sre.exceptions.IncorrectDateException;

public class MakeUnavailableController {
    @FXML
    private TextField nameField;
    private String username;
    @FXML
    private TextField firstDay;
    @FXML
    private TextField firstMonth;
    @FXML
    private TextField firstYear;
    @FXML
    private TextField lastDay;
    @FXML
    private TextField lastMonth;
    @FXML
    private TextField lastYear;
    @FXML
    private Text addMessage;
    @FXML
    private ListView<String> listView;
    public void setUsername(String username) {
        this.username = username;
        //listView.getItems().addAll(PropertyUnavailableService.getAllUnavailable(username));
    }
    public void handleMakeUnavailableAction() {
        try {
            PropertyUnavailableService.addUnavailableDate(new PropertyUnavailable(PropertyUnavailableService.getTheId(), username, nameField.getText(), firstDay.getText(),
                    firstMonth.getText(), firstYear.getText(), lastDay.getText(), lastMonth.getText(), lastYear.getText()));
            addMessage.setText("Property was set to unavailable during the selected dates");
        } catch (IncorrectDateException e) {
            addMessage.setText(e.getMessage());
        } catch (PropertyDoesNotExistException e) {
            addMessage.setText(e.getMessage());
        } catch (PropertyAlreadyUnavailableException e) {
            addMessage.setText(e.getMessage());
        }
    }
}
