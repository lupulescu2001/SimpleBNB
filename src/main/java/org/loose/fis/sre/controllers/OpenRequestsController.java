package org.loose.fis.sre.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.loose.fis.sre.services.BookingRequestService;

import java.io.IOException;


public class OpenRequestsController {
    private String username;
    @FXML
    private ListView<String> listView;
    public void setUsername(String username) {
        this.username = username;
        listView.getItems().addAll(BookingRequestService.getAllRequestsForOwner(username));
    }
}
