package org.loose.fis.sre.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import org.loose.fis.sre.services.BookingRequestService;

public class OpenPastClientsController {
    private String username;
    @FXML
    private ListView<String> listView;
    @FXML
    private TextField clientUsername;
    @FXML
    private TextField clientMark;
    public void setUsername(String username) {
        this.username = username;
        listView.getItems().addAll(BookingRequestService.getPastClients(username));
    }
    public void handleLeaveReviewAction() {

    }

}
