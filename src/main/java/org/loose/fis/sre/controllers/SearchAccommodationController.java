package org.loose.fis.sre.controllers;
import java.util.*;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.loose.fis.sre.exceptions.IncorrectDateException;
import org.loose.fis.sre.exceptions.NoPropertyAvailable;
import org.loose.fis.sre.exceptions.NoPropertyInThisCity;
import org.loose.fis.sre.services.PropertyUnavailableService;
import javafx.scene.text.Text;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.util.List;

public class SearchAccommodationController{

    @FXML
    private TextField citynameField;

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

    @FXML
    private Text message;



    @FXML
    public void handleClickSearch(){
        try{
            List<String> possible_prop = new ArrayList<>();
            possible_prop=PropertyUnavailableService.searchByCity(citynameField.getText());
            possible_prop=PropertyUnavailableService.searchbByDate(possible_prop,checkindayField.getText(),checkinmonthField.getText(),checkinyearField.getText(),checkoutdayField.getText(),checkoutmonthField.getText(),checkoutyearField.getText());
            message.setText(String.format("%d accommodations found",possible_prop.size()));

        }
        catch(NoPropertyInThisCity e){
            message.setText(e.getMessage());
        }
        catch(IncorrectDateException e){
            message.setText(e.getMessage());
        }
        catch(NoPropertyAvailable e){
            message.setText(e.getMessage());
        }



    }

}