package org.loose.fis.sre.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.loose.fis.sre.exceptions.IncorrectDateException;
import org.loose.fis.sre.exceptions.PropertyAlreadyExistsException;
import org.loose.fis.sre.exceptions.PropertyAlreadyUnavailableException;
import org.loose.fis.sre.services.BookingRequestService;
import java.util.*;
import java.io.IOException;
import org.loose.fis.sre.exceptions.ThisClientDidNotTryToRentYourPropertyException;
import org.loose.fis.sre.model.PropertyUnavailable;
import org.loose.fis.sre.services.PropertyUnavailableService;


public class OpenRequestsController {
    private String username;
    private List <String> clientUsernames;
    @FXML
    private TextField propertyName;
    @FXML
    private ListView<String> listView;
    @FXML
    private TextField clientUsername;
    @FXML
    private Text addMessage;
    @FXML
    private TextField checkindayField;

    @FXML
    private TextField checkinmonthField;

    @FXML
    private TextField checkinyearField;

    @FXML
    private TextField checkoutdayField;

    @FXML
    private TextField checkoutmonthField;

    @FXML
    private TextField checkoutyearField;
    public void setUsername(String username) {
        this.username = username;
        listView.getItems().addAll(BookingRequestService.getAllRequestsForOwner(username));
    }
    public void handleClickAcceptAction() {
        try {
            BookingRequestService.searchClientUsername(username, propertyName.getText(), clientUsername.getText(), checkindayField.getText(),
                    checkinmonthField.getText(), checkinyearField.getText(), checkoutdayField.getText(),
                    checkoutmonthField.getText(), checkoutyearField.getText());
            PropertyUnavailable x = BookingRequestService.theProperty(username, propertyName.getText(), clientUsername.getText(), checkindayField.getText(),
                    checkinmonthField.getText(), checkinyearField.getText(), checkoutdayField.getText(),
                    checkoutmonthField.getText(), checkoutyearField.getText());
            PropertyUnavailableService.bookingToUnavailable(x);
            BookingRequestService.setBookingStatus(1,username, propertyName.getText(), clientUsername.getText(), checkindayField.getText(),
                    checkinmonthField.getText(), checkinyearField.getText(), checkoutdayField.getText(),
                    checkoutmonthField.getText(), checkoutyearField.getText());
            addMessage.setText("Property booking accepted succesfully!");
        } catch (ThisClientDidNotTryToRentYourPropertyException e) {
            addMessage.setText(e.getMessage());
        } catch (IncorrectDateException e) {
            addMessage.setText(e.getMessage());
        } catch (PropertyAlreadyUnavailableException e) {
            addMessage.setText(e.getMessage());
        }
    }
    public void handleClickDenyAction() {
        try {
            BookingRequestService.searchClientUsername(username, propertyName.getText(), clientUsername.getText(), checkindayField.getText(),
                    checkinmonthField.getText(), checkinyearField.getText(), checkoutdayField.getText(),
                    checkoutmonthField.getText(), checkoutyearField.getText());
            BookingRequestService.setBookingStatus(2, username, propertyName.getText(), clientUsername.getText(), checkindayField.getText(),
                    checkinmonthField.getText(), checkinyearField.getText(), checkoutdayField.getText(),
                    checkoutmonthField.getText(), checkoutyearField.getText());
            addMessage.setText("Request denied successfully");
        } catch (ThisClientDidNotTryToRentYourPropertyException e) {
            addMessage.setText(e.getMessage());
        } catch (IncorrectDateException e) {
            addMessage.setText(e.getMessage());
        }
    }
}
