package org.loose.fis.sre.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.loose.fis.sre.exceptions.*;
import org.loose.fis.sre.services.BookingRequestService;
import java.util.*;
import java.io.IOException;
import org.loose.fis.sre.model.PropertyUnavailable;

import org.loose.fis.sre.model.PropertyUnavailable;
import org.loose.fis.sre.services.PropertyUnavailableService;
import java.net.URL;


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
    private List<Integer> ids;
    private String currentRequest;
    private int currentId = -1;
    String[] food = {"pizza", "sushi", "ramen"};
    private void itemSelected() throws NoRequestSelectedException {
        if (currentId == -1)
            throw new NoRequestSelectedException();
    }
    public void listViewSelectedItem() {
        currentRequest = listView.getSelectionModel().getSelectedItem();
        currentId = BookingRequestService.getIdForRequest(username, currentRequest);
    }
    public void setUsername(String username) {
        this.username = username;
        listView.getItems().addAll(BookingRequestService.getAllRequestsForOwner(username));
    }
    public void handleClickAcceptAction() {
        try {
            itemSelected();
            PropertyUnavailable propertyUnavailable = BookingRequestService.theProperty(currentId, username);
            PropertyUnavailableService.addUnavailableDate(propertyUnavailable);
            BookingRequestService.setBookingStatus(currentId, 1);
            listView.getItems().remove(currentRequest);
            addMessage.setText("Request Accepted successfully!");
        } catch (NoRequestSelectedException e) {
            addMessage.setText(e.getMessage());
        } catch (PropertyDoesNotExistException e) {
            addMessage.setText(e.getMessage());
        } catch (PropertyAlreadyUnavailableException e) {
            addMessage.setText(e.getMessage());
        } catch (IncorrectDateException e) {
            addMessage.setText(e.getMessage());
        }

    }
    public void handleClickDenyAction() {
        try {
            itemSelected();
            BookingRequestService.setBookingStatus(currentId, 2);
            listView.getItems().remove(currentRequest);
            addMessage.setText("Request Denied successfully!");
        } catch (NoRequestSelectedException e) {
            addMessage.setText(e.getMessage());
        }
    }
}
