package org.loose.fis.sre.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import org.loose.fis.sre.exceptions.ClientDoesNotHavePastReservationsException;
import org.loose.fis.sre.exceptions.MarkIsIncorrectException;
import org.loose.fis.sre.model.User;
import org.loose.fis.sre.services.BookingRequestService;
import org.loose.fis.sre.services.UserService;
import javafx.scene.text.Text;

public class OpenPastClientsController {
    private String username;
    @FXML
    private ListView<String> listView;
    @FXML
    private TextField clientUsername;
    @FXML
    private TextField clientMark;
    @FXML
    private Text addMessage;
    public void setUsername(String username) {
        this.username = username;
        listView.getItems().addAll(BookingRequestService.getPastClients(username));
    }
    public void handleLeaveReviewAction() {
        try {
            UserService.addReview(username, clientUsername.getText(), clientMark.getText());
            addMessage.setText("Review added successfully");
        } catch (MarkIsIncorrectException e) {
            addMessage.setText(e.getMessage());
        } catch (ClientDoesNotHavePastReservationsException e) {
            addMessage.setText(e.getMessage());
        }
    }

}
