package org.loose.fis.sre.controllers;
import java.util.*;
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

public class AvailableAccommodationsController {

    @FXML
    private ListView<String> listview;

    public void setAvailableList(List<String> list){
        listview.getItems().addAll(list);
    }


}
