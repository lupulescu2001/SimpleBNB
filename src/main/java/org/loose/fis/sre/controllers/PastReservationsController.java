package org.loose.fis.sre.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import java.util.List;

public class PastReservationsController {
    @FXML
    private ListView<String> listView;

    public void setPastReservations (List<String> list){
        listView.getItems().addAll(list);
    }


}
